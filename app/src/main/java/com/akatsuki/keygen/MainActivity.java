package com.akatsuki.keygen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private TextView tvKeyDisplay;
    private Button btnGetKey, btnClearData, btnCopyKey;
    private LinearLayout resultPanel;
    private final String TARGET_URL = "https://aincradmods.com/getkey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        tvKeyDisplay = findViewById(R.id.tvKeyDisplay);
        btnGetKey = findViewById(R.id.btnGetKey);
        btnClearData = findViewById(R.id.btnClearData);
        btnCopyKey = findViewById(R.id.btnCopyKey);
        resultPanel = findViewById(R.id.resultPanel);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        btnGetKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(TARGET_URL);
                Toast.makeText(MainActivity.this, "Loading Gateway...", Toast.LENGTH_SHORT).show();
            }
        });

        btnClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CookieManager.getInstance().removeAllCookies(null);
                CookieManager.getInstance().flush();
                webView.clearCache(true);
                Toast.makeText(MainActivity.this, "Cookies Cleared!", Toast.LENGTH_SHORT).show();
            }
        });

        btnCopyKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyText = tvKeyDisplay.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied Key", keyText);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Key Copied!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
