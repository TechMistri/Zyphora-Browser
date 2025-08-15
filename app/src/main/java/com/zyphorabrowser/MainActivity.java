package com.zyphorabrowser;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private WebView webView;
    private EditText urlEditText;
    private Button goButton;
    private Button savePdfButton;
    private Button toggleThemeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the NetworkManager to check for the browser mode.
        NetworkManager networkManager = new NetworkManager();
        BrowserMode currentMode = networkManager.getBrowserMode();

        // Log the determined browser mode.
        Log.d(TAG, "Current Browser Mode determined: " + currentMode);

        // Initialize UI components
        webView = findViewById(R.id.webview);
        urlEditText = findViewById(R.id.url_edit_text);
        goButton = findViewById(R.id.go_button);
        savePdfButton = findViewById(R.id.save_pdf_button);
        toggleThemeButton = findViewById(R.id.toggle_theme_button);

        // Set up the WebView
        webView.setWebViewClient(new WebViewClient()); // Ensures links open in the app
        webView.getSettings().setJavaScriptEnabled(true); // Enables JavaScript

        // Load a default page
        webView.loadUrl("https://www.google.com");

        // Set up the Go button
        goButton.setOnClickListener(v -> {
            String url = urlEditText.getText().toString();
            if (!url.isEmpty()) {
                // Basic URL validation
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "https://" + url;
                }
                webView.loadUrl(url);
            }
        });

        // Set up the Save as PDF button
        savePdfButton.setOnClickListener(v -> {
            PdfConverter.savePageAsPdf(webView, this);
        });

        // Set up the Theme Toggle button
        toggleThemeButton.setOnClickListener(v -> {
            int currentNightMode = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
            if (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                // Currently in dark mode, switch to light mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                // Currently in light mode, switch to dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if the app is locked and show the lock screen if necessary.
        if (AppLockManager.getInstance().isAppLocked()) {
            Intent intent = new Intent(this, LockScreenActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
