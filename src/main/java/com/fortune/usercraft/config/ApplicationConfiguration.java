package com.fortune.usercraft.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.fortune.usercraft.repository")
public class ApplicationConfiguration {
}
