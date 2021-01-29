package com.fy.system.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class FlywayConfig {

    @Autowired
    private Environment environment;

    @Bean(initMethod = "migrate")
    public Flyway flyway(){
        return new Flyway(Flyway.configure()
                .createSchemas(true)
                .schemas(environment.getRequiredProperty("spring.flyway.default-schema"))
                .dataSource(environment.getRequiredProperty("spring.flyway.url"),
                        environment.getRequiredProperty("spring.flyway.user"),
                        environment.getRequiredProperty("spring.flyway.password")));
    }
}
