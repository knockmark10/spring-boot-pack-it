package com.markoid.packit.tracking.presentation.config

import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.tracking.data.dao.TripDao
import com.markoid.packit.tracking.data.datasource.TrackingDataSourceImpl
import com.markoid.packit.tracking.data.repository.TrackingRepository
import com.markoid.packit.tracking.data.repository.TrackingRepositoryImpl
import com.markoid.packit.tracking.domain.usecases.*
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

    @Bean
    fun providesGetActiveTripByIdUseCase(
        authRepository: AuthRepository,
        shipmentRepository: ShipmentRepository,
        trackingRepository: TrackingRepository
    ): GetActiveTripByDriveIdUseCase =
        GetActiveTripByDriveIdUseCase(authRepository, shipmentRepository, trackingRepository)

    @Bean
    fun providesGetAttachedShipmentUseCase(
        authRepository: AuthRepository,
        trackingRepository: TrackingRepository
    ): GetAttachedShipmentUseCase = GetAttachedShipmentUseCase(authRepository, trackingRepository)

    @Bean
    fun providesGetAttachedTripUseCase(
        authRepository: AuthRepository,
        trackingRepository: TrackingRepository
    ): GetAttachedTripUseCase = GetAttachedTripUseCase(authRepository, trackingRepository)

    @Bean
    fun providesGetLastLocationUseCase(trackingRepository: TrackingRepository): GetLastLocationUseCase =
        GetLastLocationUseCase(trackingRepository)

    @Bean
    fun providesUpdateShipmentStatusUseCase(
        shipmentRepository: ShipmentRepository,
        trackingRepository: TrackingRepository
    ): UpdateShipmentStatusUseCase = UpdateShipmentStatusUseCase(shipmentRepository, trackingRepository)

    @Bean
    fun providesUpdateTripStatusUseCase(
        authRepository: AuthRepository,
        shipmentRepository: ShipmentRepository,
        trackingRepository: TrackingRepository
    ): UpdateTripStatusUseCase = UpdateTripStatusUseCase(authRepository, shipmentRepository, trackingRepository)

}