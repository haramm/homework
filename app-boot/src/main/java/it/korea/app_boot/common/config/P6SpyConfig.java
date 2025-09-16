package it.korea.app_boot.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.korea.app_boot.common.listener.P6SpyEventListener;

@Configuration
public class P6SpyConfig {

    @Bean
    public P6SpyEventListener p6SpyEventListener() {
        return new P6SpyEventListener();
    }

    @Bean
    public P6sypSqlFormatter p6sypSqlFormatter() {
        return new P6sypSqlFormatter();
    }
}
