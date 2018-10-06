package prithvi.io.workmanager

import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
class FlavourDI {

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(context: Context, cache: Cache): OkHttpClient.Builder {
        Stetho.initializeWithDefaults(context)
        val chuckInterceptor = ChuckInterceptor(context).showNotification(true)
        return OkHttpClient.Builder()
                .addInterceptor(chuckInterceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .cache(cache)
    }
}