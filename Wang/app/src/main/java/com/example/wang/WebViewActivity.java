package com.example.wang;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;


public class WebViewActivity extends ActionBarActivity {

    private static final String HTTP_HEAD = "http://";
    private static final String DEFAULT_WEB = "http://m.sm.cn";
    private WebView webView;
    private EditText editText;
    private Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.web_view);

        ActionBar bar = this.getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setCustomView(R.layout.view_custom_bar);
        bar.setDisplayShowCustomEnabled(true);

        editText = (EditText) bar.getCustomView().findViewById(R.id.edit_text);

        initWebView();
    }

    private void initWebView(){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(DEFAULT_WEB);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                webView.clearCache(true);
                webView.clearHistory();
                this.finish();
                break;
            case R.id.menu_web_site:
                String url = editText.getText().toString();
                if(!TextUtils.isEmpty(url)){
                    webView.loadUrl(HTTP_HEAD+url);
                } else {
                    Toast.makeText(this,"你特么输入一个网址啊",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            WebViewActivity.this.setProgress(newProgress);
        }
    }

    public class MyWebViewClient extends WebViewClient{
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            menu.findItem(R.id.menu_web_site).setTitle("加载中");
            editText.setHint("我在努力加载");
        }

        public void onPageFinished(WebView view, String url) {
            menu.findItem(R.id.menu_web_site).setTitle("来一发");
            editText.setHint("羞答答的网址呢~");
        }

        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            Toast.makeText(WebViewActivity.this,"Can't load page " + failingUrl +
                    " because error code " + errorCode +
                    " " + description,Toast.LENGTH_LONG);
        }
    }
}
