package com.example.core.di.modules

import android.widget.ImageView
import com.example.core.domain.image.ILoadImage
import com.example.core.data.image.LoadImage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoadImageModule {
    @Singleton
    @Provides
    fun loadImage() : ILoadImage<ImageView> = LoadImage()
}