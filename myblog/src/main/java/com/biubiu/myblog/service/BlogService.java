package com.biubiu.myblog.service;

import com.biubiu.myblog.config.DictInfoCache;
import com.biubiu.myblog.constant.Constant;
import com.biubiu.myblog.controller.request.AddBlogRequest;
import com.biubiu.myblog.dao.BlogDao;
import com.biubiu.myblog.dao.TagDao;
import com.biubiu.myblog.dao.UserDao;
import com.biubiu.myblog.entity.Blog;
import com.biubiu.myblog.entity.Tag;
import com.biubiu.myblog.entity.User;
import com.biubiu.myblog.util.DateUtil;
import com.biubiu.myblog.util.FormatUtil;
import com.biubiu.myblog.util.UUIDUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * BlogService
 *
 * @Author wbbaijq
 * @Date 2020/5/4 22:18
 */
@Service
public class BlogService {

    @Resource
    private TagDao tagDao;
    @Resource
    private BlogDao blogDao;
    @Resource
    private UserDao userDao;
    //    @Resource
    private DictInfoCache dictInfoCache;

    /**
     * 返回的首页博客列表内容的最大字符数
     */
    private static final Integer MAX_BODY_CHAR_COUNT = 150;

    /**
     * 保存图片,返回url
     *
     * @param file
     * @return
     * @throws IOException
     */
    public String saveImg(MultipartFile file) throws IOException {
        //获取图片格式/后缀
        String format = FormatUtil.getFileFormat(file.getOriginalFilename());
        //获取图片保存路径
        String savePath = dictInfoCache.getValue(Constant.IMG_LOCAL_LOCATION);
        //存储已满
        if (!FormatUtil.checkStringNull(savePath)) {
            throw new IOException("存储已满 请联系管理员");
        }
        //保存图片
        String fileName = UUIDUtil.generateUUID() + format;
        File diskFile = new File(savePath + "/" + fileName);
        file.transferTo(diskFile);
        //将硬盘路径转换为url，返回
        return dictInfoCache.getValue(Constant.IMG_ACCESS_PATH).replaceAll("\\*", "") + fileName;
    }

    /**
     * 保存博文
     *
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveBlog(AddBlogRequest request) {
        //根据请求的tagId去查询
        List<Tag> tags = tagDao.selectBatchIds(Arrays.asList(request.getTags()));
        User defaultUser = userDao.getDefaultUser();
        //浏览量 评论数 正常状态
        Blog blog = new Blog()
                .setBlogViews(0)
                .setDiscussCount(0)
                .setState(1)
                .setCreateTime(DateUtil.getCurrentDate())
                .setTitle(request.getTitle())
                .setBody(request.getContext())
                .setImgUrl(StringUtils.isEmpty(request.getPicUrl()) ? "https://picsum.photos/id/501/300/200" : request.getPicUrl())
                .setUid(defaultUser.getId())
                .setUser(defaultUser)
                .setTags(tags);
        blog.setTagId(tags.stream().map(o -> String.valueOf(o.getId())).collect(Collectors.joining("_")));
        blogDao.insert(blog);
    }


    /**
     * 查询主页博客
     * 正常状态
     *
     * @param page      页码
     * @param showCount 显示条数
     * @return
     */
    public List<Blog> findHomeBlog(Integer page, Integer showCount) {
        // mysql 分页中的开始位置
        int start = (page - 1) * showCount;

        // 返回的blog列表
        List<Blog> blogs = new LinkedList<>();
        // 开始位置大于缓存数量 即查询范围不在缓存内 查询mysql 且不设置缓存
        blogs.addAll(blogDao.findHomeBlog(start, showCount));
        for (Blog blog : blogs) {
            blog.setTags(tagDao.findTagByBlogId(blog.getId()));
        }

        // 截取前150个字符 减少网络io
        for (Blog blog : blogs) {
            String body = blog.getBody();
            if (body.length() > BlogService.MAX_BODY_CHAR_COUNT) {
                blog.setBody(body.substring(0, BlogService.MAX_BODY_CHAR_COUNT));
            }
        }

        return blogs;
    }

    /**
     * 查询热门博文
     * 正常状态
     *
     * @return
     */
    public List<Blog> findHotBlog() {
        return blogDao.findHotBlog(6);
    }

    /**
     * 搜索博文
     * 正常状态
     *
     * @param searchText
     * @return
     */
    public List<Blog> searchBlog(String searchText, Integer page, Integer showCount) {
        List<Blog> blogs = blogDao.searchBlog(searchText, (page - 1) * showCount, showCount);
        for (Blog blog : blogs) {
            blog.setTags(tagDao.findTagByBlogId(blog.getId()));
        }
        return blogs;
    }


    /**
     * 查询所有博文 分页
     * 正常状态
     *
     * @return
     */
    public List<Blog> findAllBlog(Integer page, Integer showCount) {
        return blogDao.findAllblog((page - 1) * showCount, showCount);
    }

    /**
     * 查询所有博文
     * 正常状态
     *
     * @return
     */
    public List<Blog> findAllBlog() {
        return blogDao.findAllblogs();
    }

    /**
     * 管理员删除博文
     *
     * @param blogId
     */
    @Transactional(rollbackFor = Exception.class)
    public void adminDeleteBlog(Integer blogId) throws JsonProcessingException {

        Blog blog = new Blog();
        blog.setId(blogId);
        blog.setState(0);
        //更改博客状态
        blogDao.updateBlog(blog);
        //级联删除blog_tag
        tagDao.deleteTagByBlogId(blogId);

    }

    /**
     * 搜索博文
     * 所有状态
     *
     * @param searchText 搜索内容
     * @param page
     * @param showCount
     * @return
     */
    public List<Blog> searchAllBlog(String searchText, Integer page, Integer showCount) {
        List<Blog> blogs = blogDao.searchAllBlog(searchText, (page - 1) * showCount, showCount);
        return blogs;
    }

    /**
     * 按月份归档博客
     * 正常状态
     *
     * @return
     */
    public List<Map> statisticalBlogByMonth() throws IOException {
        return blogDao.statisticalBlogByMonth(6);
    }

    /**
     * 查询博客记录数
     * 所有状态
     *
     * @return
     */
    public Long getAllBlogCount() {
        return blogDao.getAllBlogCount();
    }
}
