package com.markoid.packit.debug.presentation.configuration

import com.markoid.packit.debug.data.dao.DebugDao
import com.markoid.packit.debug.data.datasource.DebugDataSourceImpl
import com.markoid.packit.debug.data.repository.DebugRepository
import com.markoid.packit.debug.data.repository.DebugRepositoryImpl
import com.markoid.packit.debug.domain.usecases.GetLogMessageUseCase
import com.markoid.packit.debug.domain.usecases.SaveLogMessageUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DebugConfiguration {

    @Bean
    fun providesDebugDataSourceImpl(debugDao: DebugDao): DebugDataSourceImpl = DebugDataSourceImpl(debugDao)

    @Bean
    fun providesDebugRepository(debugDataSourceImpl: DebugDataSourceImpl): DebugRepositoryImpl =
        DebugRepositoryImpl(debugDataSourceImpl)

    @Bean
    fun providesGetLogMessageUseCase(debugRepository: DebugRepository): GetLogMessageUseCase =
        GetLogMessageUseCase(debugRepository)

    @Bean
    fun providesSaveLogMessageUseCase(debugRepository: DebugRepository): SaveLogMessageUseCase =
        SaveLogMessageUseCase(debugRepository)

}