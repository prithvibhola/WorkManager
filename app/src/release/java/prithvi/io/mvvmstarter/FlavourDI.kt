package prithvi.io.mvvmstarter

import dagger.Module

@Module
class FlavourDI {

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(cache: Cache) = OkHttpClient.Builder().cache(cache)
}