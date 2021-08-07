package com.example.springboot.demo.config;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan("com.example.springboot.demo.mapper")
@EnableTransactionManagement
public class RepositoryConfiguration {

    @Bean
    public Interceptor pageHelperInterceptor() {
        PageHelper pageHelper = new PageHelper();
        final Properties pageHelperProps = new Properties();
        pageHelperProps.setProperty("helperDialect", "mysql");
        pageHelperProps.setProperty("offsetAsPageNum", "false");
        pageHelperProps.setProperty("rowBoundsWithCount", "false");
        pageHelperProps.setProperty("pageSizeZero", "true");
        pageHelperProps.setProperty("reasonable", "false");

        // 支持Mapper接口方法中的参数作为分页参数， 分页参数的名称可以自定义，此处为 pageHelperNum，pageHelperSize
        pageHelperProps.setProperty("supportMethodsArguments", "true");
        pageHelperProps.setProperty("params", "pageNum=pageHelperNum;pageSize=pageHelperSize");

        pageHelper.setProperties(pageHelperProps);
        return pageHelper;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, Interceptor[] interceptors) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setPlugins(interceptors);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        dataSourceTransactionManager.setNestedTransactionAllowed(true);
        return dataSourceTransactionManager;
    }
}
