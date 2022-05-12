package com.nguyenvanminhnhat.projectcakeapp.view.profile.webview

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.nguyenvanminhnhat.projectcakeapp.R
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webViewSetUp()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetUp(){
        webView.webViewClient = WebViewClient()
        webView.apply {
            loadUrl("https://www.facebook.com/Tiembanhkemtalia")
            settings.javaScriptEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled = true
            }

        }
    }
}

