package com.xuan.dao.impl;

import com.xuan.dao.UserDao;
import com.xuan.entity.User;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ASUS
 */
@Component
public class UserDaoImpl implements UserDao {

    @Resource
    private JdbcTemplate template;

    @Override
    public User getUserByUserName(String userName) {

        String sql = "select username,password from users where username=?";

        List<User> list = template.query(sql, new String[]{userName}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        });

        if(CollectionUtils.isEmpty(list)){
            return null;
        }

        return list.get(0);
    }

    @Override
    public List<String> queryRolesByUserName(String userName) {

        String sql = "select role_name from user_roles  where username=?";

        List<String> list = template.query(sql, new String[]{userName}, new RowMapper<String>() {

            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("role_name");
            }
        });
        return list;
    }

    @Override
    public List<String> queryPermissionByUserName(String userName) {

        String sql="SELECT permission FROM roles_permissions WHERE role_name IN (SELECT role_name FROM user_roles WHERE username=?)";

        List<String> list = template.query(sql, new String[]{userName}, new RowMapper<String>() {

            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("permission");
            }
        });
        return list;
    }
}
