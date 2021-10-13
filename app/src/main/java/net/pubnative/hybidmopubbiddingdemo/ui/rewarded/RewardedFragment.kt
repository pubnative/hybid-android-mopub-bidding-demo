package net.pubnative.hybidmopubbiddingdemo.ui.rewarded

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.mopub.common.MoPub
import com.mopub.common.MoPubReward
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubRewardedAdListener
import com.mopub.mobileads.MoPubRewardedAdManager
import com.mopub.mobileads.MoPubRewardedAds
import net.pubnative.hybidmopubbiddingdemo.R
import net.pubnative.lite.sdk.api.RequestManager
import net.pubnative.lite.sdk.api.RewardedRequestManager
import net.pubnative.lite.sdk.models.Ad
import net.pubnative.lite.sdk.utils.HeaderBiddingUtils

class RewardedFragment : Fragment(), RequestManager.RequestListener, MoPubRewardedAdListener {
    val TAG = RewardedFragment::class.java.simpleName

    private lateinit var requestManager: RequestManager

    private lateinit var loadButton: Button
    private lateinit var prepareButton: Button
    private lateinit var showButton: Button
    private lateinit var cachingCheckbox: CheckBox

    private val zoneId: String = "4"
    private val adUnitId: String = "51dea8bc737b455e8231b89153c81757"
    private var cachingEnabled: Boolean = true
    private var ad: Ad? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_rewarded, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadButton = view.findViewById(R.id.button_load)
        prepareButton = view.findViewById(R.id.button_prepare)
        showButton = view.findViewById(R.id.button_show)
        cachingCheckbox = view.findViewById(R.id.check_caching)
        cachingCheckbox.visibility = View.VISIBLE
        prepareButton.isEnabled = false
        showButton.isEnabled = false

        requestManager = RewardedRequestManager()

        MoPubRewardedAds.setRewardedAdListener(this)

        loadButton.setOnClickListener {
            loadRewardedVideo()
        }

        prepareButton.setOnClickListener {
            ad?.let { ad ->
                requestManager.cacheAd(ad)
            }
        }

        showButton.setOnClickListener {
            MoPubRewardedAds.showRewardedAd(adUnitId)
        }

        cachingCheckbox.setOnCheckedChangeListener { _, isChecked ->
            cachingEnabled = isChecked
            prepareButton.visibility = if (isChecked) View.GONE else View.VISIBLE
        }
    }

    private fun loadRewardedVideo() {
        requestManager.setZoneId(zoneId)
        requestManager.setRequestListener(this)
        requestManager.isAutoCacheOnLoad = cachingEnabled
        requestManager.requestAd()
    }

    // -------------------- RequestManager's Listeners ------------------------
    override fun onRequestSuccess(ad: Ad?) {
        this.ad = ad
        MoPubRewardedAds.loadRewardedAd(
            adUnitId,
            MoPubRewardedAdManager.RequestParameters(HeaderBiddingUtils.getHeaderBiddingKeywords(ad))
        )

        Log.d(TAG, "onRequestSuccess")
    }

    override fun onRequestFail(throwable: Throwable?) {
        Log.d(TAG, "onRequestFail: ", throwable)
        ad = null
        MoPubRewardedAds.loadRewardedAd(adUnitId)
    }

    // -------------------- MoPub's Listeners ------------------------
    override fun onRewardedAdLoadSuccess(adUnitId: String) {
        Log.d(TAG, "onRewardedAdLoadSuccess")
        showButton.isEnabled = true
    }

    override fun onRewardedAdLoadFailure(adUnitId: String, errorCode: MoPubErrorCode) {
        Log.d(TAG, "onRewardedAdLoadFailure")
    }

    override fun onRewardedAdStarted(adUnitId: String) {
        Log.d(TAG, "onRewardedAdStarted")
    }

    override fun onRewardedAdShowError(adUnitId: String, errorCode: MoPubErrorCode) {
        Log.d(TAG, "onRewardedAdShowError")
    }

    override fun onRewardedAdClicked(adUnitId: String) {
        Log.d(TAG, "onRewardedAdClicked")
    }

    override fun onRewardedAdClosed(adUnitId: String) {
        Log.d(TAG, "onRewardedAdClosed")
    }

    override fun onRewardedAdCompleted(adUnitIds: Set<String?>, reward: MoPubReward) {
        Log.d(TAG, "onRewardedAdCompleted")
    }


    override fun onStart() {
        super.onStart()
        MoPub.onStart(requireActivity())
    }

    override fun onResume() {
        super.onResume()
        MoPub.onResume(requireActivity())
    }

    override fun onPause() {
        super.onPause()
        MoPub.onPause(requireActivity())
    }

    override fun onStop() {
        super.onStop()
        MoPub.onStop(requireActivity())
    }

    override fun onDestroy() {
        MoPub.onDestroy(requireActivity())
        super.onDestroy()
    }
}