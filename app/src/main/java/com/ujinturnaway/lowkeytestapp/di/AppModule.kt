package com.ujinturnaway.lowkeytestapp.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.ujinturnaway.lowkeytestapp.domain.database.PhotoEntity
import com.ujinturnaway.lowkeytestapp.domain.database.PhotosDatabase
import com.ujinturnaway.lowkeytestapp.domain.network.AuthProvider
import com.ujinturnaway.lowkeytestapp.domain.network.AuthProviderImpl
import com.ujinturnaway.lowkeytestapp.domain.network.ImageResponseProcessor
import com.ujinturnaway.lowkeytestapp.domain.network.ImagesService
import com.ujinturnaway.lowkeytestapp.domain.network.PhotosApi
import com.ujinturnaway.lowkeytestapp.domain.network.PhotosApiImpl
import com.ujinturnaway.lowkeytestapp.domain.network.PhotosRemoteMediator
import com.ujinturnaway.lowkeytestapp.domain.repository.PhotoRepository
import com.ujinturnaway.lowkeytestapp.domain.repository.PhotoRepositoryImpl
import com.ujinturnaway.lowkeytestapp.domain.usecase.GetPhoto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAuthProvider(): AuthProvider {
        return AuthProviderImpl()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    fun providePhotosPager(
        photosApi: PhotosApi,
        photosDatabase: PhotosDatabase,
        authProvider: AuthProvider,
    ): Pager<Int, PhotoEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = {
                photosDatabase.photosDao.pagingSource()
            },
            remoteMediator = PhotosRemoteMediator(photosDatabase, photosApi, authProvider)
        )
    }

    @Provides
    fun provideGetPhoto(
        photoRepository: PhotoRepository,
    ): GetPhoto {
        return GetPhoto(photoRepository)
    }

    @Provides
    fun providePhotoRepository(
        pager: Pager<Int, PhotoEntity>,
        photosDatabase: PhotosDatabase,
    ): PhotoRepository {
        return PhotoRepositoryImpl(pager, photosDatabase)
    }

    @Provides
    @Singleton
    fun providePhotosDatabase(
        @ApplicationContext context: Context,
    ): PhotosDatabase {
        return Room
            .databaseBuilder(
                context,
                PhotosDatabase::class.java,
                "pokemon.db",
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideImagesApi(
        imagesService: ImagesService,
        responseProcessor: ImageResponseProcessor,
    ): PhotosApi {
        return PhotosApiImpl(imagesService, responseProcessor)
    }

    @Provides
    fun provideImagesService(
        gsonConverterFactory: GsonConverterFactory,
        retrofitBuilder: Retrofit.Builder,
    ): ImagesService {
        return retrofitBuilder.baseUrl(BASE_URL).addConverterFactory(gsonConverterFactory).build()
            .create(ImagesService::class.java)
    }

    @Provides
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient,
    ): Retrofit.Builder {
        return Retrofit.Builder().client(okHttpClient)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder().setPrettyPrinting().create()
        )
    }

    @Provides
    fun provideImageResponseProcessor(): ImageResponseProcessor {
        return ImageResponseProcessor()
    }

    companion object {
        private const val BASE_URL = "https://api.pexels.com/"
        private const val PAGE_SIZE = 10
    }

}