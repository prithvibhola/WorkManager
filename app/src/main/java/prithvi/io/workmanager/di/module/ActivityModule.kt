package prithvi.io.workmanager.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import prithvi.io.workmanager.di.ActivityScoped
import prithvi.io.workmanager.ui.main.MainActivity

@Module
abstract class ActivityModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [])
    abstract fun mainActivity(): MainActivity

}