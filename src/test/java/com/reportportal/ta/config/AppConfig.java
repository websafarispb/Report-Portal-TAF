package com.reportportal.ta.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:env/${TESTING_ENV:dev}.properties",
//@PropertySource({"classpath:env/dev.properties",
    "classpath:reportportal.properties"})
public class AppConfig {

}