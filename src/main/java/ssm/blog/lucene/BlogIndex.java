package ssm.blog.lucene;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;
import ssm.blog.entity.Blog;
import ssm.blog.util.DateUtil;
import ssm.blog.util.PathUtil;
import ssm.blog.util.StringUtil;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author xp
 * @Description 博客索引类
 * @Date 2017/4/19 10:45
 */
@Component
public class BlogIndex {

    public Directory directory;
    //存放索引的物理位置
    public    String indexDir = PathUtil.getRootPath()+"/src/main/webapp/static/luceneIndex";

    /**
     * 获取写索引对象
     * @return
     * @throws IOException
     */
    public IndexWriter getIndexWriter() throws IOException {
        //实例化索引目录
        directory = FSDirectory.open(Paths.get(indexDir));
        //得到中文解析器
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        //注册中文解析器到写索引配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //实例化写索引对象
        IndexWriter indexWriter = new IndexWriter(directory,config);
        return indexWriter;
    }

    /**
     * 添加博客索引
     * @param blog
     * @throws IOException
     */
    public void addIndex(Blog blog) throws IOException {
        //获取写索引对象
        IndexWriter indexWriter = getIndexWriter();
        //实例化索引文档
        Document document = new Document();
        //设置索引文件字段
        document.add(new StringField("id",String.valueOf(blog.getId()), Field.Store.YES));
        document.add(new TextField("title",blog.getTitle(), Field.Store.YES));
        document.add(new StringField("releaseDate", DateUtil.formatDate(new Date(),"yyyy-mm-dd"), Field.Store.YES));
        document.add(new TextField("content",blog.getContentNoTag(), Field.Store.YES));
        //索引文档加入到写索引中
        indexWriter.addDocument(document);
        //关闭写索引
        indexWriter.close();
    }

    /**
     * 删除指定博客id的索引
     * @param blogId
     */
    public void deleteIndex(String blogId) throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        indexWriter.deleteDocuments(new Term("id",blogId));
        indexWriter.forceMergeDeletes();//强制删除
        indexWriter.commit();
        indexWriter.close();
    }

    /**
     * 更新博客索引文件
     * @param blog
     * @throws IOException
     */
    public void updateIndex(Blog blog) throws IOException {
        //获取写索引对象
        IndexWriter indexWriter = getIndexWriter();
        //实例化索引文档
        Document document = new Document();
        //设置索引文件字段
        document.add(new StringField("id",String.valueOf(blog.getId()), Field.Store.YES));
        document.add(new TextField("title",blog.getTitle(), Field.Store.YES));
        document.add(new StringField("releaseDate", DateUtil.formatDate(new Date(),"yyyy-mm-dd"), Field.Store.YES));
        document.add(new TextField("content",blog.getContentNoTag(), Field.Store.YES));
        //更新索引文档
        indexWriter.updateDocument(new Term("id",String.valueOf(blog.getId())),document);
        //关闭写索引
        indexWriter.close();
    }

    public List<Blog> searchBlog(String q) throws Exception{
        //实例化目录对象
        directory = FSDirectory.open(Paths.get(indexDir));
        //获取读索引对象
        IndexReader indexReader = DirectoryReader.open(directory);
        //获取索引查询对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //组合查询对象
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        //中文分析器
        SmartChineseAnalyzer chineseAnalyzer = new SmartChineseAnalyzer();
        //标题查询分析器
        QueryParser titleParser = new QueryParser("title",chineseAnalyzer);
        //标题查询器
        Query titleQuery = titleParser.parse(q);
        //内容查询分析器
        QueryParser contentParser = new QueryParser("content",chineseAnalyzer);
        //内容查询器
        Query contentQuery = contentParser.parse(q);
        //添加标题查询器  逻辑关系为或者
        builder.add(titleQuery, BooleanClause.Occur.SHOULD);
        //添加内容查询器  逻辑关系为或者
        builder.add(contentQuery, BooleanClause.Occur.SHOULD);
        //查询前100条记录反在his中
        TopDocs hits = indexSearcher.search(builder.build(),100);
        //将title得分高的放在前面
        QueryScorer queryScorer = new QueryScorer(titleQuery);
        //得分高的片段
        Fragmenter fragmenter = new SimpleSpanFragmenter(queryScorer);
        //格式化得分高片段
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
        //高亮显示
        Highlighter highlighter = new Highlighter(formatter,queryScorer);
        //将得分高的片段设置进去
        highlighter.setTextFragmenter(fragmenter);
        //用来封装查询到的博客
        List<Blog> blogIndexList = new LinkedList<Blog>();
        //遍历查询结果
        for(ScoreDoc score : hits.scoreDocs) {
            Document doc = indexSearcher.doc(score.doc);
            Blog blog = new Blog();
            blog.setId(Integer.parseInt(doc.get("id")));
            blog.setReleaseDateStr(doc.get("releaseDate"));
            String title = doc.get("title");
            String content = doc.get("content");
            if(title != null) {
                TokenStream tokenStream = chineseAnalyzer.tokenStream("title", new StringReader(title));
                String hTitle = highlighter.getBestFragment(tokenStream, title);
                if(StringUtil.isEmpty(hTitle)) {
                    blog.setTitle(title);
                } else {
                    blog.setTitle(hTitle);
                }
            }
            if(content != null) {
                TokenStream tokenStream = chineseAnalyzer.tokenStream("content", new StringReader(content));
                String hContent = highlighter.getBestFragment(tokenStream, content);
                if(StringUtil.isEmpty(hContent)) {
                    if(content.length() > 100) { //如果没查到且content内容又大于100的话
                        blog.setContent(content.substring(0, 100)); //截取100个字符
                    } else {
                        blog.setContent(content);
                    }
                } else {
                    blog.setContent(hContent);
                }
            }
            blogIndexList.add(blog);
        }

        return blogIndexList;
    }
}
