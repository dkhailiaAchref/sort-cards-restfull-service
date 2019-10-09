package com.example.sortCartes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;



@ComponentScan
@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class,SecurityAutoConfiguration.class })
@PropertySource(value = {"classpath:application.properties"})
//public class Application   extends SpringBootServletInitializer{
	public class Application {
		public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
