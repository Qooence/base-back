package com.qooence.base.admin.modules.sys.redis;

import com.qooence.base.admin.common.utils.RedisKeys;
import com.qooence.base.admin.common.utils.RedisUtils;
import com.qooence.base.admin.modules.sys.entity.SysRouteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 路由Redis
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Component
public class UserRouteRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void save(List<SysRouteEntity> route,String userId) {
        if(route == null){
            return ;
        }
        String key = RedisKeys.getUserRouteKey(userId);
        redisUtils.set(key, route);
    }

    public void delete(String userId) {
        String key = RedisKeys.getUserRouteKey(userId);
        redisUtils.delete(key);
    }

    public void deleteAll() {
        redisUtils.deleteAll(RedisKeys.routeGroup + "*");
    }

    public List<SysRouteEntity> get(String userId){
        String key = RedisKeys.getUserRouteKey(userId);
        return redisUtils.getList(key, SysRouteEntity.class);
    }
}
