package net.pubnative.hybidmopubbiddingdemo

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mopub.common.MoPub
import com.mopub.common.SdkConfiguration
import com.mopub.common.SdkInitializationListener
import com.mopub.common.logging.MoPubLog
import net.pubnative.hybidmopubbiddingdemo.databinding.ActivityMainBinding
import net.pubnative.lite.sdk.HyBid

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val hybidAppToken = "dde3c298b47648459f8ada4a982fa92d"
    private val mopubAdUnitId = "b8b82260e1b84a9ba361e03c21ce4caf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_banner,
                R.id.navigation_mrect,
                R.id.navigation_interstitial,
                R.id.navigation_rewarded
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        HyBid.initialize(hybidAppToken, application)
        initMoPubSDK(this, mopubAdUnitId)
    }


    fun initMoPubSDK(activity: Activity, adUnitId: String) {
        val sdkConfiguration = SdkConfiguration.Builder(adUnitId)
            .withLogLevel(MoPubLog.LogLevel.DEBUG)
            .build()

        MoPub.initializeSdk(activity, sdkConfiguration, initSdkListener())
    }

    private fun initSdkListener(): SdkInitializationListener? {
        return SdkInitializationListener {

        }
    }

}