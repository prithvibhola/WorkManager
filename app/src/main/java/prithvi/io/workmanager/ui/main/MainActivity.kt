package prithvi.io.workmanager.ui.main

import android.Manifest
import android.content.IntentSender
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.android.gms.common.api.ResolvableApiException
import kotlinx.android.synthetic.main.activity_main.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import prithvi.io.workmanager.R
import prithvi.io.workmanager.data.models.Response
import prithvi.io.workmanager.ui.base.BaseActivity
import prithvi.io.workmanager.utility.extentions.getViewModel
import prithvi.io.workmanager.utility.extentions.isGPSEnabled
import prithvi.io.workmanager.utility.extentions.observe
import prithvi.io.workmanager.utility.extentions.toast
import prithvi.io.workmanager.viewmodel.ViewModelFactory
import javax.inject.Inject

@RuntimePermissions
class MainActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var mAdapter: MainAdapter

    companion object {
        const val REQUEST_CHECK_SETTINGS = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel(MainViewModel::class.java, viewModelFactory)
        viewModel.getSavedLocation()

        mAdapter = MainAdapter(this, listOf())
        rvLocation.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        btnTrack.setOnClickListener { getFromLocationWithPermissionCheck() }

        observe(viewModel.enableLocation) {
            it ?: return@observe
            when (it.status) {
                Response.Status.LOADING -> toast("Loading")
                Response.Status.SUCCESS -> getFromLocationWithPermissionCheck()
                Response.Status.ERROR -> {
                    if (it.error is ResolvableApiException) {
                        try {
                            it.error.startResolutionForResult(this@MainActivity, REQUEST_CHECK_SETTINGS)
                        } catch (sendEx: IntentSender.SendIntentException) {

                        }
                    }
                }
            }
        }

        observe(viewModel.location) {
            it ?: return@observe
            when (it.status) {
                Response.Status.LOADING -> {

                }
                Response.Status.SUCCESS -> {
                    it.data ?: return@observe
                    mAdapter.locations = it.data
                }
                Response.Status.ERROR -> {
                    toast("Error loading location")
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun getFromLocation() = if (isGPSEnabled()) viewModel.trackLocation() else viewModel.locationSetup()

}
