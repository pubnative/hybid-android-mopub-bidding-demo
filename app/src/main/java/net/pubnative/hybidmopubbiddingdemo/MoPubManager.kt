package net.pubnative.hybidmopubbiddingdemo

import android.content.Context
import com.mopub.common.MoPub
import com.mopub.common.SdkConfiguration
import com.mopub.common.SdkInitializationListener
import com.mopub.common.logging.MoPubLog

class MoPubManager {

    fun initMoPubSDK(context: Context, adUnitId: String){
        val sdkConfiguration = SdkConfiguration.Builder(adUnitId)
            .withLogLevel(MoPubLog.LogLevel.DEBUG)
            .build()

        MoPub.initializeSdk(context, sdkConfiguration, initSdkListener())
    }

    private fun initSdkListener(): SdkInitializationListener? {
        return SdkInitializationListener {

        }
    }
}