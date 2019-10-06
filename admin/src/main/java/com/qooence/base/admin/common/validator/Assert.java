package com.qooence.base.admin.common.validator;

import com.qooence.base.admin.common.exception.RRException;
import org.apache.commons.lang3.StringUtils;

/**
 * 数据校验
 * @author jyq
 * @email qooence@qooence.uu.me
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
}
