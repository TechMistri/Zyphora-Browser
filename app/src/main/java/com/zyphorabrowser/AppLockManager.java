package com.zyphorabrowser;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages the state of the application lock.
 * This is a singleton to ensure a single source of truth for the lock state.
 */
public class AppLockManager {

    private static volatile AppLockManager instance;
    private static final String PREFS_NAME = "AppLockPrefs";
    private static final String KEY_PIN = "app_pin";

    // In-memory state
    private boolean isAppLocked = true; // App starts as locked by default

    // Private constructor for singleton
    private AppLockManager() {}

    public static AppLockManager getInstance() {
        if (instance == null) {
            synchronized (AppLockManager.class) {
                if (instance == null) {
                    instance = new AppLockManager();
                }
            }
        }
        return instance;
    }

    /**
     * Sets the PIN for the app lock.
     * In a real app, this should be stored securely using EncryptedSharedPreferences.
     * @param context The application context.
     * @param pin The 4-digit PIN to set.
     */
    public void setPin(Context context, String pin) {
        // SIMULATION: Storing PIN in standard SharedPreferences for demonstration.
        // In a production app, use EncryptedSharedPreferences for security.
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(KEY_PIN, pin).apply();
    }

    /**
     * Checks if the provided PIN is correct.
     * @param context The application context.
     * @param pin The PIN to check.
     * @return true if the PIN is correct, false otherwise.
     */
    public boolean checkPin(Context context, String pin) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String savedPin = prefs.getString(KEY_PIN, "1234"); // Default PIN is "1234"
        return savedPin.equals(pin);
    }

    public boolean isAppLocked() {
        return isAppLocked;
    }

    public void unlockApp() {
        this.isAppLocked = false;
    }

    public void lockApp() {
        this.isAppLocked = true;
    }
}
