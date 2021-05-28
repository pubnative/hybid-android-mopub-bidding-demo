package net.pubnative.hybidmopubbiddingdemo.ui.interstitial

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubInterstitial
import net.pubnative.hybidmopubbiddingdemo.R
import net.pubnative.lite.sdk.api.RequestManager
import net.pubnative.lite.sdk.models.Ad
import net.pubnative.lite.sdk.utils.HeaderBiddingUtils

class InterstitialFragment : Fragment(), RequestManager.RequestListener, MoPubInterstitial.InterstitialAdListener{
    val TAG = InterstitialFragment::class.java.simpleName

    private lateinit var requestManager: RequestManager
    private lateinit var mopubInterstitial: MoPubInterstitial

    private lateinit var loadButton: Button
    private lateinit var showButton: Button

    private val zoneId: String = "3"
    private var adUnitId: String = "0bd7ea20185547f2bd29a9574bfce917"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_interstitial, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadButton = view.findViewById(R.id.button_load)
        showButton = view.findViewById(R.id.button_show)

        mopubInterstitial = MoPubInterstitial(requireActivity(), adUnitId)
        mopubInterstitial.interstitialAdListener = this

        requestManager = RequestManager()

        loadButton.setOnClickListener {
            loadInterstitial()
        }

        showButton.setOnClickListener {
            mopubInterstitial?.show()
        }

    }

    override fun onDestroy() {
        mopubInterstitial?.destroy()
        super.onDestroy()
    }

    fun loadInterstitial() {
        requestManager.setZoneId(zoneId)
        requestManager.setRequestListener(this)
        requestManager.requestAd()
    }

    // -------------------- RequestManager's Listeners ------------------------
    override fun onRequestSuccess(ad: Ad?) {
        Log.d(TAG, "onRequestSuccess")
        mopubInterstitial.setKeywords(HeaderBiddingUtils.getHeaderBiddingKeywords(ad, HeaderBiddingUtils.KeywordMode.TWO_DECIMALS))
        mopubInterstitial.load()
    }

    override fun onRequestFail(throwable: Throwable?) {
        Log.d(TAG, "onRequestFail", throwable)
        // Request ad to MoPub without adding the pre bid keywords
        mopubInterstitial.load()
    }

    // -------------------- MoPub's Listeners ------------------------
    override fun onInterstitialLoaded(interstitial: MoPubInterstitial?) {
        showButton.isEnabled = true
        Log.d(TAG, "onInterstitialLoaded")
    }

    override fun onInterstitialFailed(interstitial: MoPubInterstitial?, errorCode: MoPubErrorCode?) {
        Log.d(TAG, "onInterstitialFailed")
    }

    override fun onInterstitialShown(interstitial: MoPubInterstitial?) {
        Log.d(TAG, "onInterstitialShown")
    }

    override fun onInterstitialClicked(interstitial: MoPubInterstitial?) {
        Log.d(TAG, "onInterstitialClicked")
    }

    override fun onInterstitialDismissed(interstitial: MoPubInterstitial?) {
        showButton.isEnabled = false
        Log.d(TAG, "onInterstitialDismissed")
    }
}