package ssm.blog.service.impl;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import ssm.blog.dao.LinkDao;
import ssm.blog.entity.Link;
import ssm.blog.entity.PageBean;
import ssm.blog.service.LinkService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xp on 2017/4/20.
 */
@Service
public class LinkServiceImpl implements LinkService{

    @Resource
    private LinkDao linkDao;

    @Override
    public List<Link> getTotalData() {
        return linkDao.getTotalData();
    }

    @Override
    public PageBean<Link> listByPage(PageBean<Link> pageBean) {
        pageBean.setResult(linkDao.listByPage(pageBean.getStart(),pageBean.getEnd()));
        pageBean.setTotal(linkDao.getTotalCount());
        return pageBean;
    }

    @Override
    public Long getTotalCount() {
        return linkDao.getTotalCount();
    }

    @Override
    public Integer addLink(Link link) {
        return linkDao.addLink(link);
    }

    @Override
    public Integer deleteLink(Integer id) {
        return linkDao.deleteLink(id);
    }

    @Override
    public Integer updateLink(Link link) {
        return linkDao.updateLink(link);
    }
}
