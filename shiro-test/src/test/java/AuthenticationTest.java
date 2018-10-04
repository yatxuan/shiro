import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void add() {
        simpleAccountRealm.addAccount("Mark", "123456","admin","user");
    }

    /**
     * shiro认证
     */
    @Test
    public void testAuthentication() {

        // 1.构建SecurityManager
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);


        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);//设置环境
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Mark", "123456");
        subject.login(token);

        System.out.println("认证结果：");
        System.out.println("isAuthenticated:---------" + subject.isAuthenticated());

        /*退出认证
        subject.logout();

        System.out.println("认证结果：");
        System.out.println("isAuthenticated:--------" + subject.isAuthenticated());*/

        subject.checkRole("admin");//单个权限认证
        subject.checkRoles("admin","user");//多个权限认证

    }

}
