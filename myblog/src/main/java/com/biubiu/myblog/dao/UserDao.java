package com.biubiu.myblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biubiu.myblog.entity.User;

import java.util.List;

public interface UserDao extends BaseMapper<User> {

    /** 获取默认的用户 */
    User getDefaultUser();

    List<User> findAll();

    User findById(Integer id);

    List<User> find(User user);

    void save(User user);

    void update(User user);

    void delete(Integer id);

    void remove(Integer id);
}
