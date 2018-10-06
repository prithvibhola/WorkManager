package prithvi.io.workmanager.ui.main

import prithvi.io.workmanager.data.repository.Repository
import prithvi.io.workmanager.ui.base.BaseViewModel
import prithvi.io.workmanager.utility.rx.Scheduler
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val repository: Repository,
        private val scheduler: Scheduler
) : BaseViewModel() {

}