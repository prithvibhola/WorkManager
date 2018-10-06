package prithvi.io.workmanager.di.module

import android.app.Application
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import prithvi.io.workmanager.BuildConfig
import prithvi.io.workmanager.data.api.Api
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache = Cache(application.cacheDir, (10 * 1024 * 1024).toLong())

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideRetrofit(
            moshi: Moshi,
            okHttpClientBuilder: OkHttpClient.Builder
    ): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .baseUrl(BuildConfig.API_BASE_URL)
                .client(okHttpClientBuilder.build())
                .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) = retrofit.create(Api::class.java)

}