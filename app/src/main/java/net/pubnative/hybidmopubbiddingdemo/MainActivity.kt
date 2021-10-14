package net.pubnative.hybidmopubbiddingdemo

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
    companion object {
        private const val HYBID_APP_TOKEN = "dde3c298b47648459f8ada4a982fa92d"
        private const val MOPUB_AD_UNIT_ID = "94ef2036a5f4453b8eb096627359cffe"
    }

    private lateinit var binding: ActivityMainBinding

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

        HyBid.initialize(HYBID_APP_TOKEN, application)
        initMoPubSDK(MOPUB_AD_UNIT_ID)
    }


    private fun initMoPubSDK(adUnitId: String) {
        val sdkConfiguration = SdkConfiguration.Builder(adUnitId)
            .withLogLevel(MoPubLog.LogLevel.DEBUG)
            .build()

        MoPub.initializeSdk(this, sdkConfiguration, initSdkListener())
    }

    private fun initSdkListener(): SdkInitializationListener? {
        return SdkInitializationListener {

        }
    }

}