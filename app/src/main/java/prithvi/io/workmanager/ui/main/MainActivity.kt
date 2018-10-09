package prithvi.io.workmanager.ui.main

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import prithvi.io.workmanager.R
import prithvi.io.workmanager.ui.base.BaseActivity
import prithvi.io.workmanager.utility.extentions.getViewModel
import prithvi.io.workmanager.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel(MainViewModel::class.java, viewModelFactory)


        btnTrack.setOnClickListener { }


    }
}
