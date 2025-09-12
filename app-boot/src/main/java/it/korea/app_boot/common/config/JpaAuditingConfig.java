package it.korea.app_boot.common.config;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditorDateTimeProvider")
public class JpaAuditingConfig {

    @Bean(name = "auditorDateTimeProvider")
    public DateTimeProvider auditorDateTimeProvider() {
        return () -> Optional.of(LocalDateTime.now());
    }
}
