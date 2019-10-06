package com.qooence.base.admin.modules.sys.oauth2;

import com.qooence.base.admin.modules.sys.entity.SysUserEntity;
import com.qooence.base.admin.modules.sys.entity.SysUserTokenEntity;
import com.qooence.base.admin.modules.sys.redis.UserRedis;
import com.qooence.base.admin.modules.sys.redis.UserTokenRedis;
import com.qooence.base.admin.modules.sys.service.ShiroService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
    @Autowired
    private ShiroService shiroService;

    @Autowired
    private UserTokenRedis tokenRedis;

    @Autowired
    private UserRedis userRedis;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity user = (SysUserEntity)principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        //用户权限列表
        Set<String> permsSet = shiroService.getUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        SysUserTokenEntity tokenEntity;

        // 从缓存中取出token
        tokenEntity = tokenRedis.get(accessToken);

        if(tokenEntity == null){
            //根据accessToken，查询用户信息
            tokenEntity = shiroService.queryByToken(accessToken);
        }

        //token失效
        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        //查询用户信息
        SysUserEntity user;
        // 从缓存中取出user
        user = userRedis.get(Long.toString(tokenEntity.getUserId()));
        if(null == user){
            user = shiroService.queryUser(tokenEntity.getUserId());
            // 存到缓存中
            userRedis.save(user);
        }

        //账号锁定
        if(user.getStatus() == 0){
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
