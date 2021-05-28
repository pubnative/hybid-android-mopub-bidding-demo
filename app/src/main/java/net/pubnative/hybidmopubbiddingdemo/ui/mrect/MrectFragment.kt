package net.pubnative.hybidmopubbiddingdemo.ui.mrect

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
import net.pubnative.lite.sdk.api.MRectRequestManager
import net.pubnative.lite.sdk.api.RequestManager
import net.pubnative.lite.sdk.models.Ad
import net.pubnative.lite.sdk.utils.HeaderBiddingUtils


class MrectFragment : Fragment(), RequestManager.RequestListener, MoPubView.BannerAdListener {
    val TAG = MrectFragment::class.java.simpleName

    private lateinit var requestManager: RequestManager
    private lateinit var mopubMRect: MoPubView

    private lateinit var loadButton: Button

    private val zoneId: String = "5"
    private val adUnitId: String = "1fafef6b872a4e10ba9fc573ca347e55"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_mrect, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mopubMRect = view.findViewById(R.id.mopub_mrect)
        loadButton = view.findViewById(R.id.button_load)

        requestManager = MRectRequestManager()

        loadButton.setOnClickListener {
            loadMRect()
        }
    }

    override fun onDestroy() {
        mopubMRect.destroy()
        super.onDestroy()
    }

    private fun loadMRect() {
        requestManager.setZoneId(zoneId)
        requestManager.setRequestListener(this)
        requestManager.requestAd()
    }

    // -------------------- RequestManager's Listeners ------------------------
    override fun onRequestSuccess(ad: Ad?) {
        mopubMRect.setAdUnitId(adUnitId)
        mopubMRect.setKeywords(HeaderBiddingUtils.getHeaderBiddingKeywords(ad, HeaderBiddingUtils.KeywordMode.TWO_DECIMALS));
        mopubMRect.loadAd()
        Log.d(TAG, "onRequestSuccess")
    }

    override fun onRequestFail(throwable: Throwable?) {
        Log.d(TAG, "onRequestFail: ", throwable)
        mopubMRect.setAdUnitId(adUnitId)
        mopubMRect.loadAd()
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