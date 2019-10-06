package com.qooence.base.admin.datasources.annotation;

import java.lang.annotation.*;

/**
 * 多数据源注解
 * @author jyq
 * @email qooence@qooence.uu.me
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}
