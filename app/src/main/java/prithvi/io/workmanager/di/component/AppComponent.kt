package prithvi.io.workmanager.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import prithvi.io.workmanager.FlavourDI
import prithvi.io.workmanager.WorkManagerApplication
import prithvi.io.workmanager.di.module.ActivityModule
import prithvi.io.workmanager.di.module.AppModule
import prithvi.io.workmanager.di.module.NetModule
import prithvi.io.workmanager.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    AppModule::class,
    NetModule::class,
    ViewModelModule::class,
    FlavourDI::class
])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: WorkManagerApplication)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}