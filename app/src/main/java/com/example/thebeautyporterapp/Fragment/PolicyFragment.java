package com.example.thebeautyporterapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.thebeautyporterapp.R;

import static com.example.thebeautyporterapp.Activity.MainActivity.backArrow;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgFilter;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgMenu;
import static com.example.thebeautyporterapp.Activity.MainActivity.imgSearch;
import static com.example.thebeautyporterapp.Activity.MainActivity.linearActionBar;
import static com.example.thebeautyporterapp.Activity.MainActivity.txt_actionbar_Title;

/**
 * A simple {@link Fragment} subclass.
 */
public class PolicyFragment extends Fragment {

    View rootView;
    WebView myWebView;
    String url;
    String title;
    public PolicyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_policy, container, false);
        linearActionBar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        imgMenu.setVisibility(View.GONE);
        backArrow.setVisibility(View.VISIBLE);
        txt_actionbar_Title.setVisibility(View.VISIBLE);

        imgFilter.setVisibility(View.GONE);
        imgSearch.setVisibility(View.GONE);

        url= getArguments().getString("url");
        title= getArguments().getString("title");
        myWebView=(WebView)rootView.findViewById(R.id.myWebView);


        if (url != null && !url.isEmpty() && !url.equals("null"))
        {
            txt_actionbar_Title.setText(title);
        }
        myWebView.getSettings().setJavaScriptEnabled(true);

        if (url != null && !url.isEmpty() && !url.equals("null"))
        {
            myWebView.loadUrl(url);
        }



//        mWebview  = new WebView(getActivity());
//
//        url= getArguments().getString("url");
//        Log.v("url",url);
//
//        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
//
//
//        mWebview.setWebViewClient(new WebViewClient() {
//            @SuppressWarnings("deprecation")
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
//            }
//            @TargetApi(android.os.Build.VERSION_CODES.M)
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
//                // Redirect to deprecated method, so you can use it in all SDK versions
//                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
//            }
//        });
//
//        if (url != null && !url.isEmpty() && !url.equals("null"))
//        {
//            mWebview .loadUrl(url);
//        }
//
//        getActivity().setContentView(mWebview );
        return rootView;
    }

}
