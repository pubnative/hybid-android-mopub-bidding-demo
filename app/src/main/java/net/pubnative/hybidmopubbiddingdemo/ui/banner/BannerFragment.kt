package net.pubnative.hybidmopubbiddingdemo.ui.banner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubView
import net.pubnative.hybidmopubbiddingdemo.R
import net.pubnative.lite.sdk.api.BannerRequestManager
import net.pubnative.lite.sdk.api.RequestManager
import net.pubnative.lite.sdk.models.Ad
import net.pubnative.lite.sdk.utils.HeaderBiddingUtils


class BannerFragment : Fragment(R.layout.fragment_banner), RequestManager.RequestListener, MoPubView.BannerAdListener {
    val TAG = BannerFragment::class.java.simpleName

    private lateinit var requestManager: RequestManager
    private lateinit var mopubBanner: MoPubView
    private lateinit var loadButton: Button

    private val zoneId: String = "2"
    private val mopubAdUnitId = "94ef2036a5f4453b8eb096627359cffe"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mopubBanner = view.findViewById(R.id.mopub_banner)
        loadButton = view.findViewById(R.id.button_load)

        requestManager = BannerRequestManager()

        mopubBanner.bannerAdListener = this
        mopubBanner.autorefreshEnabled = false

        loadButton.setOnClickListener {
            loadBanner()
        }
    }

    override fun onDestroy() {
        mopubBanner.destroy()
        super.onDestroy()
    }

    private fun loadBanner() {
        requestManager.setZoneId(zoneId)
        requestManager.setRequestListener(this)
        requestManager.requestAd()
    }

    // -------------------- RequestManager's Listeners ------------------------
    override fun onRequestSuccess(ad: Ad?) {
        Log.d(TAG, "onRequestSuccess")
        mopubBanner.setAdUnitId(mopubAdUnitId)
        mopubBanner.setKeywords(
            HeaderBiddingUtils.getHeaderBiddingKeywords(
                ad,
                HeaderBiddingUtils.KeywordMode.TWO_DECIMALS
            )
        )
        mopubBanner.loadAd()
    }

    override fun onRequestFail(throwable: Throwable?) {
        Log.d(TAG, "onRequestFail", throwable)
        // Request ad to MoPub without adding the pre bid keywords
        mopubBanner.setAdUnitId(mopubAdUnitId)
        mopubBanner.loadAd()
    }

    // -------------------- MoPub's Listeners ------------------------
    override fun onBannerLoaded(banner: MoPubView) {
        Log.d(TAG, "onBannerLoaded")
    }

    override fun onBannerFailed(banner: MoPubView?, errorCode: MoPubErrorCode?) {
        Log.d(TAG, "onBannerFailed")
    }

    override fun onBannerClicked(banner: MoPubView?) {
        Log.d(TAG, "onBannerClicked")
    }

    override fun onBannerExpanded(banner: MoPubView?) {
        Log.d(TAG, "onBannerExpanded")
    }

    override fun onBannerCollapsed(banner: MoPubView?) {
        Log.d(TAG, "onBannerCollapsed")
    }

}