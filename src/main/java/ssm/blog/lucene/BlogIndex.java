package ssm.blog.lucene;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import ssm.blog.entity.Blog;
import ssm.blog.util.DateUtil;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

/**
 * @Author xp
 * @Description 博客索引类
 * @Date 2017/4/19 10:45
 */
public class BlogIndex {

    private Directory directory;
    //存放索引的物理位置
    private String indexDir = "/Users/kumataira/Downloads/index/blog";

    /**
     * 获取写索引对象
     * @return
     * @throws IOException
     */
    private IndexWriter getIndexWriter() throws IOException {
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
    private void addIndex(Blog blog) throws IOException {
        //获取写索引对象
        IndexWriter indexWriter = getIndexWriter();
        //实例化索引文档
        Document document = new Document();
        //设置索引文件字段
        document.add(new StringField("id",String.valueOf(blog.getId()), Field.Store.YES));
        document.add(new TextField("title",blog.getTitle(), Field.Store.YES));
        document.add(new StringField("releaseDate", DateUtil.formatDate(new Date(),"yyyy-mm-dd"), Field.Store.YES));
        document.add(new TextField("content",blog.getContent(), Field.Store.YES));
        //索引文档加入到写索引中
        indexWriter.addDocument(document);
        //关闭写索引
        indexWriter.close();
    }

    /**
     * 删除指定博客id的索引
     * @param blogId
     */
    private void deleteIndex(String blogId) throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        indexWriter.deleteDocuments(new Term("id",blogId));
        indexWriter.forceMergeDeletes();//强制删除
        indexWriter.commit();
        indexWriter.close();
    }

    private void updateIndex(Blog blog) throws IOException {
        //获取写索引对象
        IndexWriter indexWriter = getIndexWriter();
        //实例化索引文档
        Document document = new Document();
        //设置索引文件字段
        document.add(new StringField("id",String.valueOf(blog.getId()), Field.Store.YES));
        document.add(new TextField("title",blog.getTitle(), Field.Store.YES));
        document.add(new StringField("releaseDate", DateUtil.formatDate(new Date(),"yyyy-mm-dd"), Field.Store.YES));
        document.add(new TextField("content",blog.getContent(), Field.Store.YES));
        //更新索引文档
        indexWriter.updateDocument(new Term("id",String.valueOf(blog.getId())),document);
        //关闭写索引
        indexWriter.close();
    }

}
