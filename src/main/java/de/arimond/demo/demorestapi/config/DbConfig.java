/*
 * Intellinet Beratung und Technologie GmbH
 */

package de.arimond.demo.demorestapi.config;

import com.mysql.jdbc.Driver;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.util.Properties;


/**
 * @author Matthias Oberthuer
 * @since 29.05.2018
 */
@Configuration
@EnableTransactionManagement
public class DbConfig {

    private static final Logger logger = LoggerFactory.getLogger(DbConfig.class);

    @Value("${datasource.jdbc.driver}")
    private String driver;

    @Value("${datasource.jdbc.url}")
    private String url;

    @Value("${datasource.jdbc.username}")
    private String username;

    @Value("${datasource.jdbc.password}")
    private String password;

    @Value("${spring.liquibase.url}")
    private String liquibaseUrl;

    @Value("${spring.liquibase.user}")
    private String liquibaseUsername;

    @Value("${spring.liquibase.password}")
    private String liquibasePassword;

    @Value("${datasource.maximumPoolSize:2}")
    private int maximumPoolSize;

    @Value("${datasource.autoCommit:true}")
    private boolean autoCommit;

    @Value("${datasource.maxLifetimeInMs:1800000}")
    private long maxLifetime;

    @Value("#{T(java.util.concurrent.TimeUnit).MINUTES.toMillis(${datasource.idleMaxAgeInMinutes:3})}")
    private long idleTimeout;

    @Value("${datasource.connectionTimeoutInMs:30000}")
    private long connectionTimeoutInMs;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Bean
    public DataSource dataSource() {
        logger.trace("Initialize DataSource");
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(maximumPoolSize);
        dataSource.setIdleTimeout(idleTimeout);
        dataSource.setConnectionTimeout(connectionTimeoutInMs);
        dataSource.setAutoCommit(autoCommit);
        dataSource.setMaxLifetime(maxLifetime);
        dataSource.setPoolName("Configuration-Hikari-Pool");
        return dataSource;
    }



    @Bean
    @LiquibaseDataSource
    public DataSource liquibaseDatasource() throws ClassNotFoundException {
        logger.trace("Initialize LiquibaseDatasource");
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass((Class<? extends Driver>) Class.forName(driver));
        dataSource.setUrl(liquibaseUrl);
        dataSource.setUsername(liquibaseUsername);
        dataSource.setPassword(liquibasePassword);
        return dataSource;
    }

    @Bean
    @DependsOn("liquibase")
    public LocalSessionFactoryBean sessionFactory() {
        logger.trace("Initialize sessionFactory");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("de.intellinet.econsuite.configuration");
        sessionFactory.setPhysicalNamingStrategy(new ImprovedNamingStrategy());
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", hibernateDialect);
        hibernateProperties.put("hibernate.show_sql", showSql);
        hibernateProperties.put("hibernate.use_sql_comments", "true");
        hibernateProperties.put("hibernate.globally_quoted_identifiers", "false");
        hibernateProperties.put("hibernate.enable_lazy_load_no_trans", "true");
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        logger.trace("Initialize TransactionManager");
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setDataSource(dataSource());
        transactionManager.setSessionFactory(sessionFactory().getObject());
        transactionManager.setHibernateManagedSession(false);
        return transactionManager;
    }

}
