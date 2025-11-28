package org.jeecg.modules.zxecg.phoenix.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
@Configuration
//@MapperScan(basePackages = "org.jeecg.**.mapper.phoenix**",sqlSessionTemplateRef = "phoenixSqlSessionTemplate", annotationClass = PhoenixMapper.class)
public class PhoenixDataSourceConfig {
    @Value("${spring.datasource.phoenix.mapper-locations}")
    private String mapperLocation;
    @Value("${spring.datasource.phoenix.jdbc-url}")
    private String jdbcUrl;
    @Value("${spring.datasource.phoenix.driver-class-name}")
    private String driverClassName;
//    @Value("${spring.datasource.phoenix.username}")
//    private String username;
//    @Value("${spring.datasource.phoenix.password}")
//    private String password;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.phoenix")
    public DataSource phoenixDataSource() throws SQLException {
        log.info("init phoenix data source");
//        DataSource ds = DataSourceBuilder.create().build();
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(jdbcUrl);
        ds.setDriverClassName(driverClassName);
//        ds.setUsername(username);
//        ds.setPassword(password);
        Properties prop = new Properties();
        prop.setProperty("phoenix.schema.isNamespaceMappingEnabled","true");
        prop.setProperty("phoenix.schema.mapSystemTablesToNamespace","true");
        ds.setConnectProperties(prop);
        return ds;
    }

    @Bean
    public SqlSessionFactory phoenixSqlSessionFactory(@Qualifier("phoenixDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager phoenixTransactionManager(@Qualifier("phoenixDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("phoenixSqlSessionTemplate")
    public SqlSessionTemplate phoenixSqlSessionTemplate(@Qualifier("phoenixSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

