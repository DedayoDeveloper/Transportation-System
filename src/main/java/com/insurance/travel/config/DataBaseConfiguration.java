/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author oreoluwa
 */
@Configuration
@ComponentScan(basePackages = "com.insurance.travel.repository")
public class DataBaseConfiguration {
    
          @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/travelinsurance?useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("Mysql@2019");
        return dataSource;
    }
    
    
}
