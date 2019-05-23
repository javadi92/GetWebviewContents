package com.javadi.getwebviewcontents;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    WebView webView;
    TextView textView;
    Button button;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView=(WebView)findViewById(R.id.web_view);
        button=(Button)findViewById(R.id.button);
        textView=(TextView)findViewById(R.id.textView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url)
            {
                /* This call inject JavaScript into the page which just finished loading. */
                webView.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                webView.setVisibility(View.GONE);
            }
        });
        webView.loadUrl("http://java.byethost9.com/");
    }

    class MyJavaScriptInterface
    {

        @SuppressWarnings("unused")
        @JavascriptInterface
        public void processHTML(final String html)
        {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int statr=html.indexOf("<body>");
                            int end=html.indexOf("</body>");
                            String result=html.substring(statr+6,end);
                            textView.setText(result);
                    /*Intent intent=new Intent(MainActivity.this,PlayActivity.class);
                    intent.putExtra("url",result);
                    startActivity(intent);*/
                            //webView.loadUrl("http://javadi.herokuapp.com/?q="+result);
                        }
                    });
                }
            });
        }
    }
}
