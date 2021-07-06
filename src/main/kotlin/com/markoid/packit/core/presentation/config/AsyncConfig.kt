package com.markoid.packit.core.presentation.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

/**
 * This will enable the async execution for the methods marked with the @Async annotation.
 */
@Configuration
@EnableAsync
class AsyncConfig {

    @Bean(name = ["threadPoolTaskExecutor"])
    fun providesTaskExecutor(): Executor = ThreadPoolTaskExecutor().apply {
        corePoolSize = 4
        maxPoolSize = 4
        setQueueCapacity(500)
        setThreadNamePrefix("PackIt-")
        initialize()
    }

}