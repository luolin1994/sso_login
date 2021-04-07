package com.example.member.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 无需在做登录认证，因为登录认证这一块我们在cas-server里去做，只需做授权即可
 */
public class UserShiroCasRealm extends CasRealm {


    /**
     * 权限授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //表明当前登录者的角色(真实项目中这里会去查询DB，拿到用户的角色，存到redis里)
        info.addRole("admin");
        //表明当前登录者的角色(真实项目中这里会去查询DB，拿到该角色的资源权限，存到redis里)
        info.addStringPermission("admin:manage");
        return info;
    }

//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        return null;
//    }
}
