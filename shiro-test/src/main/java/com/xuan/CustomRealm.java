package com.xuan;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author ASUS
 */
public class CustomRealm extends AuthorizingRealm {

    Map<String, String> map = new HashMap<>(16);

    {
        map.put("Mark", "283538989cef48f3d7d8a1c1bdf2008f");
        super.setName("customRealm");
    }

    /**
     * 做授权管理
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

//        1.从主体传过来的认证信息中获取用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();

        Set<String> roles = getRoles(userName);

        Set<String> permissions = getPermissions(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    /**
     * 模拟访问数据库或缓存中
     * 获取权限功能数据
     *
     * @param userName 用户名
     * @return
     */
    private Set<String> getPermissions(String userName) {
        Set<String> set = new HashSet<>();
        set.add("user:delete");
        set.add("user:insert");

        return set;
    }

    /**
     * 模拟访问数据库或缓存中
     * 获取角色权限数据
     *
     * @param userName 用户名
     * @return
     */
    private Set<String> getRoles(String userName) {

        Set<String> set = new HashSet<>();
        set.add("admin");
        set.add("user");

        return set;
    }

    /**
     * 做认证  管理
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

//        1.从主体传过来的认证信息中获取用户名
        String userName = (String) authenticationToken.getPrincipal();

//       2.通过用户名到数据库中获取凭证

        String passWord = getPassWord(userName);

        if (passWord == null) {
            return null;
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName, passWord, "customRealm");

//        放入盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));

        return authenticationInfo;
    }

    /**
     * 模拟访问数据库
     *
     * @param userName 姓名
     * @return
     */
    private String getPassWord(String userName) {

        return map.get(userName);
    }


    public static void main(String[] args) {
        Md5Hash md5Hash=new Md5Hash("123456","Mark");
        System.out.println(md5Hash.toHex());
    }
}
