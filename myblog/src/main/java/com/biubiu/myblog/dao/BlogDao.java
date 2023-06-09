package com.biubiu.myblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biubiu.myblog.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BlogDao extends BaseMapper<Blog> {
    /**
     * 查询主页博客数量
     *
     * @return
     */
    Long getHomeBlogCount();

    /**
     * 查询主页博客
     *
     * @param start
     * @param showCount
     * @return
     */
    List<Blog> findHomeBlog(@Param("start") Integer start, @Param("showCount") Integer showCount);

    /**
     * 查询热门博文
     *
     * @param count 显示数量
     * @return
     */
    List<Blog> findHotBlog(Integer count);

    /**
     * 搜索博文标题，内容
     *
     * @param searchText
     * @param start
     * @param showCount
     * @return
     */
    List<Blog> searchBlog(@Param("searchText") String searchText,
                          @Param("start") Integer start,
                          @Param("showCount") Integer showCount);

    /**
     * 查询所有博文 - 分页
     *
     * @param start
     * @param showCount
     * @return
     */
    List<Blog> findAllblog(@Param("start") Integer start, @Param("showCount") Integer showCount);

    /**
     * 插叙所有博文
     *
     * @param
     * @return
     */
    List<Blog> findAllblogs();

    /**
     * 符合关键词的博文数量
     *
     * @param searchText
     * @return
     */
    Long getSearchBlogCount(String searchText);

    /**
     * 符合关键字的博文数量
     * 所有状态
     *
     * @param searchText
     * @return
     */
    Long getSearchAllBlogCount(String searchText);

    /**
     * 搜索博文
     * 所有状态
     *
     * @param searchText
     * @param start
     * @param showCount
     * @return
     */
    List<Blog> searchAllBlog(@Param("searchText") String searchText, @Param("start") Integer start, @Param("showCount") Integer showCount);

    /**
     * 按月份归档博客
     *
     * @param count 最近几个月
     * @return month 月
     * year 年
     * count 数量
     */
    List<Map> statisticalBlogByMonth(Integer count);


    /**
     * 查询此标签下是否有博客
     *
     * @param tagId
     * @return
     */
    Integer findBlogCountByTagId(Integer tagId);

    /**
     * 查询博客记录数
     * 所有状态
     *
     * @return
     */
    Long getAllBlogCount();


    /**
     * 根据博客id更新博客
     *
     * @param blog
     */
    void updateBlog(Blog blog);
}
