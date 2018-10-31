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
import prithvi.io.workmanager.di.module.*
import prithvi.io.workmanager.utility.workmanager.TrackLocationWorker
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    ActivityModule::class,
    AppModule::class,
    NetModule::class,
    RoomModule::class,
    ViewModelModule::class,
    FlavourDI::class
])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: WorkManagerApplication)

    fun inject(worker: TrackLocationWorker)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}