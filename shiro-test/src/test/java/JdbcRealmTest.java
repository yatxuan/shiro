import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {

    DruidDataSource dataSource=new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123");
    }

    @Test
    public void testAuthentication() {

        JdbcRealm jdbcRealm=new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
//        jdbcRealm.setPermissionsLookupEnabled(true);

       String sql="select password from test_user where name=?";
        jdbcRealm.setAuthenticationQuery(sql);

        String roleSql="select role_name from user_roles where username=?";
        jdbcRealm.setUserRolesQuery(roleSql);

        String perSql="select permission from roles_permissions where role_name = ?";
        jdbcRealm.setPermissionsQuery(perSql);


        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject=SecurityUtils.getSubject();

        UsernamePasswordToken user=new UsernamePasswordToken("admin","123");
        subject.login(user);

        System.out.println(subject.isAuthenticated());

//        subject.checkRole("user");
//        subject.checkPermission("user:delete");
//        subject.checkRole("admin");
//        subject.checkRoles("admin2","admin");
//        subject.checkPermission("user:delete");
    }
}
