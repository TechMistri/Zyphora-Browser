package com.zyphorabrowser;

import java.util.Random;

/**
 * Manages network-related logic, such as determining the browsing mode
 * based on internet speed.
 */
public class NetworkManager {

    // The threshold for switching to ultra-lite mode, in kbps.
    private static final int SPEED_THRESHOLD_KBPS = 10;

    private Random randomSpeedSimulator = new Random();

    /**
     * Simulates checking the current network speed.
     * In a real application, this would involve using Android's ConnectivityManager
     * and performing a speed test.
     *
     * @return A simulated network speed in kbps.
     */
    private int getCurrentNetworkSpeedKbps() {
        // Returns a random speed between 1 and 100 kbps to simulate variety.
        return randomSpeedSimulator.nextInt(100) + 1;
    }

    /**
     * Determines the appropriate browser mode based on the current network speed.
     *
     * @return The recommended {@link BrowserMode}.
     */
    public BrowserMode getBrowserMode() {
        int currentSpeed = getCurrentNetworkSpeedKbps();
        System.out.println("Current simulated network speed: " + currentSpeed + " kbps");

        if (currentSpeed < SPEED_THRESHOLD_KBPS) {
            return BrowserMode.ULTRA_LITE;
        } else {
            return BrowserMode.FULL_BROWSER;
        }
    }
}
