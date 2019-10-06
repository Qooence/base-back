package com.qooence.base.admin.modules.sys.redis;

import com.qooence.base.admin.common.utils.RedisKeys;
import com.qooence.base.admin.common.utils.RedisUtils;
import com.qooence.base.admin.modules.sys.entity.SysUserTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * token Redis
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Component
public class UserTokenRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void save(SysUserTokenEntity user, String token,Long expire) {
        if(token == null){
            return ;
        }
        String key = RedisKeys.getUserTokenKey(token);
        redisUtils.set(key, user, expire);
    }

    public SysUserTokenEntity get(String token){
        String key = RedisKeys.getUserTokenKey(token);
        return redisUtils.get(key, SysUserTokenEntity.class);
    }
}
