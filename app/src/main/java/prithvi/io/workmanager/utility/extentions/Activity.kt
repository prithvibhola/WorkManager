package prithvi.io.workmanager.utility.extentions

import android.arch.lifecycle.*
import android.support.v4.app.FragmentActivity

internal fun <T : ViewModel> FragmentActivity.getViewModel(modelClass: Class<T>, viewModelFactory: ViewModelProvider.Factory? = null): T {
    return viewModelFactory?.let { ViewModelProviders.of(this, it).get(modelClass) }
            ?: ViewModelProviders.of(this).get(modelClass)
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}