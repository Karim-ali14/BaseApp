package com.karimali.baseapp.di


import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ReposModule {

    //********************** Repositories Will Be Provided Here ******************************\\
//    @Provides
//    @ViewModelScoped
//    fun provideAuthRepository(@Nullable services: Services?):AuthRepository{
//        return AuthRepositoryImp(services)
//    }

}