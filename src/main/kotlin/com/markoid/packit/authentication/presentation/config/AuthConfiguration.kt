package com.markoid.packit.authentication.presentation.config

import com.markoid.packit.authentication.data.cache.DriverCacheImpl
import com.markoid.packit.authentication.data.cache.UserCacheImpl
import com.markoid.packit.authentication.data.dao.DriverDao
import com.markoid.packit.authentication.data.dao.UserDao
import com.markoid.packit.authentication.data.datasource.AuthDataSourceImpl
import com.markoid.packit.authentication.data.repository.AuthRepository
import com.markoid.packit.authentication.data.repository.AuthRepositoryImpl
import com.markoid.packit.authentication.domain.usecases.SignInUseCase
import com.markoid.packit.authentication.domain.usecases.SignUpUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AuthConfiguration {

    @Bean
    fun providesUserCacheImpl(): UserCacheImpl = UserCacheImpl()

    @Bean
    fun providesDriverCacheImpl(): DriverCacheImpl = DriverCacheImpl()

    @Bean
    fun providesAuthDataSource(
        driverCacheImpl: DriverCacheImpl,
        driverDao: DriverDao,
        userCacheImpl: UserCacheImpl,
        userDao: UserDao
    ): AuthDataSourceImpl = AuthDataSourceImpl(driverCacheImpl, driverDao, userCacheImpl, userDao)

    @Bean
    fun providesAuthRepository(authDataSourceImpl: AuthDataSourceImpl): AuthRepositoryImpl =
        AuthRepositoryImpl(authDataSourceImpl)

    @Bean
    fun providesSignUpUseCase(authRepository: AuthRepository): SignUpUseCase =
        SignUpUseCase(authRepository)

    @Bean
    fun providesSignInUseCase(authRepository: AuthRepository): SignInUseCase =
        SignInUseCase(authRepository)

}