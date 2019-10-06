package com.qooence.base.admin.common.utils;

/**
 * Redis所有Keys
 * @author jyq
 * @email qooence@qooence.uu.me
 */
public class RedisKeys {

    /**
     * token组
     */
    public static String tokenGroup = "sys:authority:token:";

    /**
     * 路由组
     */
    public static String routeGroup = "sys:authority:route:";

    /**
     * 权限组
     */
    public static String permissionsGroup = "sys:authority:permissions:";

    /**
     * 权限组
     */
    public static String userGroup = "sys:user:";


    public static String getUserRouteKey(String key){
        return  routeGroup + key;
    }

    public static String getUserPermissionsKey(String key){
        return permissionsGroup + key;
    }

    public static String getUserTokenKey(String key){
        return tokenGroup + key;
    }

    public static String getUserKey(String key){
        return userGroup + key;
    }

}
