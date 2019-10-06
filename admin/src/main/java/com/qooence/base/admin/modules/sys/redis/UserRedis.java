package com.qooence.base.admin.modules.sys.redis;

import com.qooence.base.admin.common.utils.RedisKeys;
import com.qooence.base.admin.common.utils.RedisUtils;
import com.qooence.base.admin.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户 redeis
 * @author jyq
 * @email qooence@qooence.uu.me
 *
 */
@Component
public class UserRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void save(SysUserEntity user) {
        if(user == null){
            return ;
        }
        String key = RedisKeys.getUserKey(Long.toString(user.getUserId()));
        redisUtils.set(key, user);
    }

    public void delete(String userId) {
        String key = RedisKeys.getUserKey(userId);
        redisUtils.delete(key);
    }

    public SysUserEntity get(String userId){
        String key = RedisKeys.getUserKey(userId);
        return redisUtils.get(key, SysUserEntity.class);
    }
}
