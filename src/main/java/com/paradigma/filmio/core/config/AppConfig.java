package com.paradigma.filmio.core.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.time.Clock;


@Configuration
@Import({SecurityConfig.class})
class AppConfig {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

}
