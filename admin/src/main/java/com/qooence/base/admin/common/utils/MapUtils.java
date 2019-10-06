package com.qooence.base.admin.common.utils;

import java.util.HashMap;


/**
 * Map工具类
 * @author jyq
 * @email qooence@qooence.uu.me
 */
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
