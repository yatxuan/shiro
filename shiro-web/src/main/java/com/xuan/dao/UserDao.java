package com.xuan.dao;

import com.xuan.entity.User;

import java.util.List;

/**
 * @author ASUS
 */
public interface UserDao {
    /**
     * 通过用户名获取用户信息
     * @param userName 用户名
     * @return
     */
    User getUserByUserName(String userName);

    /**
     * 通过用户名获得用户权限身份
     * @param userName
     * @return
     */
    List<String> queryRolesByUserName(String userName);

    /**
     * 通过用户名获取身份的权限功能
     * @param userName
     * @return
     */
    List<String> queryPermissionByUserName(String userName);
}
