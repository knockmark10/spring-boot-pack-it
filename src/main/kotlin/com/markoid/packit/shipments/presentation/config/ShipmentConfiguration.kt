package com.markoid.packit.shipments.presentation.config

import com.markoid.packit.shipments.data.cache.ShipmentCacheImpl
import com.markoid.packit.shipments.data.dao.ShipmentDao
import com.markoid.packit.shipments.data.datasource.ShipmentDataSourceImpl
import com.markoid.packit.shipments.data.repository.ShipmentRepository
import com.markoid.packit.shipments.data.repository.ShipmentRepositoryImpl
import com.markoid.packit.shipments.domain.usecases.GetShipmentsUseCase
import com.markoid.packit.shipments.domain.usecases.SaveShipmentUseCase
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ShipmentConfiguration {

    @Bean
    fun providesShipmentCacheImpl(): ShipmentCacheImpl =
        ShipmentCacheImpl()

    @Bean
    fun providesShipmentDataSource(shipmentCache: ShipmentCacheImpl, shipmentDao: ShipmentDao): ShipmentDataSourceImpl =
        ShipmentDataSourceImpl(shipmentCache, shipmentDao)

    @Bean
    fun providesShipmentRepository(shipmentDataSource: ShipmentDataSourceImpl): ShipmentRepositoryImpl =
        ShipmentRepositoryImpl(shipmentDataSource)

    @Bean
    fun providesGetShipmentUseCase(
        shipmentRepository: ShipmentRepository,
        messageSource: MessageSource
    ): GetShipmentsUseCase = GetShipmentsUseCase(shipmentRepository, messageSource)

    @Bean
    fun providesSaveShipmentUseCase(
        shipmentRepository: ShipmentRepository,
        messageSource: MessageSource
    ): SaveShipmentUseCase = SaveShipmentUseCase(shipmentRepository, messageSource)

}