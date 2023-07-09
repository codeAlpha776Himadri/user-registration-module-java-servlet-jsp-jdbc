package com.dao;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class Config {

    private String connUrl = "jdbc:mysql://Localhost:3306/user_registration";
    private String userName = "root";
    private String password = "<put pass here>";
    private String drivername = "com.mysql.jdbc.Driver";

    @Bean("dataSource")
    public DataSource getDataSource() throws Exception {

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        try {

            /*
             * Not working with reading from properties file
             * 
             * InputStream inputStream = new FileInputStream("db.properties");
             * Properties connProperties = new Properties();
             * connProperties.load(inputStream)
             * 
             * driverManagerDataSource.setUrl((String)connProperties.get("URL"));
             * driverManagerDataSource.setUsername((String)connProperties.get("Uname"));
             * driverManagerDataSource.setPassword((String)connProperties.get("password"));
             * driverManagerDataSource.setDriverClassName((String)connProperties.get("Dname"
             * ));
             */

            driverManagerDataSource.setUrl(this.connUrl);
            driverManagerDataSource.setUsername(this.userName);
            driverManagerDataSource.setPassword(this.password);
            driverManagerDataSource.setDriverClassName(this.drivername);
            
        } catch (Exception e) {
            throw e;
        }

        return driverManagerDataSource;
    }

    @Bean(name = { "jdbcTemplate" })
    public JdbcTemplate getJdbcTemplate() throws Exception {
        return new JdbcTemplate(getDataSource());
    }

    @Bean(name = { "Dao" })
    public UserDaoImpl getUserDaoImpl() throws Exception {
        return new UserDaoImpl(getJdbcTemplate());
    }
}
