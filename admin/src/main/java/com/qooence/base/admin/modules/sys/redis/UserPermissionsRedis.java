package com.qooence.base.admin.modules.sys.redis;

import com.qooence.base.admin.common.utils.RedisKeys;
import com.qooence.base.admin.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限Redis
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Component
public class UserPermissionsRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void save(List<String> permissions, String userId) {
        if(permissions == null){
            return ;
        }
        String key = RedisKeys.getUserPermissionsKey(userId);
        redisUtils.set(key, permissions);
    }

    public void delete(String userId) {
        String key = RedisKeys.getUserPermissionsKey(userId);
        redisUtils.delete(key);
    }

    public void deleteAll() {
        redisUtils.deleteAll(RedisKeys.permissionsGroup + "*");
    }

    public List<String> get(String userId){
        String key = RedisKeys.getUserPermissionsKey(userId);
        return redisUtils.getList(key,String.class);
    }
}
