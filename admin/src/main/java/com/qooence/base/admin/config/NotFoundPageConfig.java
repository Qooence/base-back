package com.qooence.base.admin.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * vue router history模式下 配置使用
 * 不刷新跳转时其实不是通过请求服务器的，而是通过js操作history的API改变地址完成的。
 * 当刷新之后浏览器就耿直的去请求服务器了，然而服务器没有这个路由，于是就404了。
 * 解决方法1: 可以做个代理，让所有访问服务器的地址都返回同一个入口文件。（推荐使用）
 * 解决方法2: 采用静态文件的形式让每个目录都存在就不会报错了
 * vue router hash 模式下不用注释(因为起不到作用)
 */
//@Component
public class NotFoundPageConfig  implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        //错误类型为404，找不到网页的，默认显示404.html网页
        ErrorPage e404 = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
        factory.addErrorPages(e404);
    }
}
