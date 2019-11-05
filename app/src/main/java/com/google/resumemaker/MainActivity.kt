package com.google.resumemaker

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.resumemaker.di.AppComponent
import com.google.resumemaker.di.AppModule
import com.google.resumemaker.di.DaggerAppComponent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val DATE_FORMAT = "MM/dd/yy"
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var component: AppComponent = DaggerAppComponent.builder().appModule(AppModule(App.getApplicationContext())).build()
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_profile, R.id.nav_positions,
                R.id.nav_education), drawer_layout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        component.inject(viewModel)
        component.inject(viewModel.preferences)

        viewModel.setUpCompleted.observe(this, Observer {
            if (savedInstanceState == null) {
                // only if activity is not recreated
                navController.navigate(R.id.set_up_to_home_fragment)
            }
        })
        if (viewModel.resume == null) {
            viewModel.setUpProfile()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        //Handle the use cases when user started to modify the data, but considered to leave the fragment using navigation.
        //In this situation the previous state of the editable object has to be restored.
        val navController = findNavController(R.id.nav_host_fragment)

        if (navController.currentDestination!!.label == getString(R.string.edit_record_label)) {
            viewModel.recordToEditOrCreate = viewModel.cancelCollectionUpdate(viewModel.mode!!, viewModel.copyOfRecordToModify!!)
        }
        if (navController.currentDestination!!.label == getString(R.string.profile_label)) {
            viewModel.updateProfileOrCancelUpdate(true)
        }
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        //Handle the use cases when user started to modify the data, but considered to leave the fragment using navigation.
        //In this situation the previous state of the editable object has to be restored.
        super.onBackPressed()
        val navController = findNavController(R.id.nav_host_fragment)

        if (navController.currentDestination!!.label == getString(R.string.view_record_label)) {
            viewModel.recordToEditOrCreate = viewModel.cancelCollectionUpdate(viewModel.mode!!, viewModel.copyOfRecordToModify!!)
        }
        if (navController.currentDestination!!.label == getString(R.string.menu_profile)) {
            viewModel.updateProfileOrCancelUpdate(true)
        }
    }
}
