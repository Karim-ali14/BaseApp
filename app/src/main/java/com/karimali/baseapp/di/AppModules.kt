package com.karimali.baseapp.di

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.widget.ImageView
import com.bumptech.glide.request.target.ViewTarget
import com.karimali.baseapp.R
import com.karimali.teacherpackage.shared.di.GlideApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModules {
    @Provides
    @Singleton
    fun loadImage(@ApplicationContext context : Context , imageView : ImageView , url : String?) : ViewTarget<ImageView, Drawable> {
        return if(url != null)
            GlideApp.with(context)
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView)
        else
            GlideApp.with(context)
                .load(R.drawable.ic_launcher_background)
                .into(imageView)
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext appContext : Context): ConnectivityManager {
        return appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }


}