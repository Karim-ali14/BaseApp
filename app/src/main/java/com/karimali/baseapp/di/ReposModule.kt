package com.karimali.baseapp.di


import com.karimali.baseapp.date.apis.Services
import com.karimali.baseapp.date.repositories.authRepo.AuthRepository
import com.karimali.baseapp.date.repositories.authRepo.AuthRepositoryImp
import com.karimali.baseapp.date.repositories.homeRepo.HomeRepository
import com.karimali.baseapp.date.repositories.homeRepo.HomeRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.annotation.Nullable

@Module
@InstallIn(ViewModelComponent::class)
object ReposModule {

    //********************** Repositories Will Be Provided Here ******************************\\
    @Provides
    @ViewModelScoped
    fun provideAuthRepository(@Nullable services: Services?): AuthRepository {
        return AuthRepositoryImp(services)
    }
    @Provides
    @ViewModelScoped
    fun provideHomeRepository(@Nullable services: Services?) :HomeRepository
    = HomeRepositoryImp(services)

}