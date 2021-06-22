package com.markoid.packit.tracking.presentation.config

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.tracking.data.dao.TripDao
import com.markoid.packit.tracking.data.datasource.TrackingDataSourceImpl
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.data.repository.TrackingRepositoryImpl
import com.markoid.packit.tracking.domain.usecases.AttachTrackerUseCase
import com.markoid.packit.tracking.domain.usecases.BroadcastLocationUseCase
import com.markoid.packit.tracking.domain.usecases.CreateNewTripUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TrackingConfiguration {

    @Bean
    fun providesTrackingDataSource(tripDao: TripDao): TrackingDataSourceImpl = TrackingDataSourceImpl(tripDao)

    @Bean
    fun providesTrackingRepositoryImpl(trackingDataSourceImpl: TrackingDataSourceImpl): TrackingRepositoryImpl =
        TrackingRepositoryImpl(trackingDataSourceImpl)

    @Bean
    fun providesAttachTrackerUseCase(
        authRepository: AuthRepository,
        trackingRepository: TrackingRepository
    ): AttachTrackerUseCase = AttachTrackerUseCase(authRepository, trackingRepository)

    @Bean
    fun providesBroadcastLocationUseCase(trackingRepository: TrackingRepository): BroadcastLocationUseCase =
        BroadcastLocationUseCase(trackingRepository)

    @Bean
    fun providesCreateNewTripUseCase(
        authRepository: AuthRepository,
        trackingRepository: TrackingRepository
    ): CreateNewTripUseCase = CreateNewTripUseCase(authRepository, trackingRepository)

}