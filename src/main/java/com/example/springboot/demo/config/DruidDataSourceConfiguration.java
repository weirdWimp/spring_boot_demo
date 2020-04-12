package com.example.springboot.demo.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidDataSourceConfiguration {

    @Bean("slf4j-filter")
    @ConfigurationProperties("datasource.druid.filter.slf4j")
    public Filter slf4jFilter() {
        return new Slf4jLogFilter();
    }

    @Bean("stat-filter")
    @ConfigurationProperties("datasource.druid.filter.stat")
    public Filter statFilter() {
        return new StatFilter();
    }

    @Bean
    public ServletRegistrationBean servletTLReportServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        servletRegistrationBean.addInitParameter("loginUsername", "guo");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

}
