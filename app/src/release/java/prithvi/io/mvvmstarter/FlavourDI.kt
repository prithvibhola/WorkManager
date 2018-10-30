package prithvi.io.workmanager

import dagger.Module

@Module
class FlavourDI {

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(cache: Cache) = OkHttpClient.Builder().cache(cache)
}