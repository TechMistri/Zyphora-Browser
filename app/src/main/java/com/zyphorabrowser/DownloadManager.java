package com.zyphorabrowser;

import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages all download operations for the browser.
 * This class is a singleton to ensure there's only one instance managing downloads.
 */
public class DownloadManager {

    private static final String TAG = "DownloadManager";
    private static volatile DownloadManager instance;

    private final List<DownloadItem> downloadQueue;
    private final AtomicInteger nextDownloadId = new AtomicInteger(0);

    // Private constructor for singleton pattern
    private DownloadManager() {
        downloadQueue = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Gets the singleton instance of the DownloadManager.
     */
    public static DownloadManager getInstance() {
        if (instance == null) {
            synchronized (DownloadManager.class) {
                if (instance == null) {
                    instance = new DownloadManager();
                }
            }
        }
        return instance;
    }

    /**
     * Creates a new download task and adds it to the queue.
     * @param url The URL of the file to download.
     * @return The ID of the new download task.
     */
    public int startDownload(String url) {
        int id = nextDownloadId.getAndIncrement();
        // In a real app, you'd derive the file path more intelligently.
        String filePath = "/storage/emulated/0/Download/file_" + id;
        DownloadItem newItem = new DownloadItem(id, url, filePath);
        newItem.setStatus(DownloadStatus.DOWNLOADING); // Set to downloading
        downloadQueue.add(newItem);
        Log.d(TAG, "Started download for URL: " + url + " with ID: " + id);
        // In a real app, you would start a background thread or service here.
        return id;
    }

    /**
     * Pauses an ongoing download.
     * @param downloadId The ID of the download to pause.
     */
    public void pauseDownload(int downloadId) {
        findDownloadById(downloadId).ifPresent(item -> {
            if (item.getStatus() == DownloadStatus.DOWNLOADING) {
                item.setStatus(DownloadStatus.PAUSED);
                Log.d(TAG, "Paused download with ID: " + downloadId);
            }
        });
    }

    /**
     * Resumes a paused download.
     * @param downloadId The ID of the download to resume.
     */
    public void resumeDownload(int downloadId) {
        findDownloadById(downloadId).ifPresent(item -> {
            if (item.getStatus() == DownloadStatus.PAUSED) {
                item.setStatus(DownloadStatus.DOWNLOADING);
                Log.d(TAG, "Resumed download with ID: " + downloadId);
            }
        });
    }

    /**
     * Cancels a download and removes it from the queue.
     * @param downloadId The ID of the download to cancel.
     */
    public void cancelDownload(int downloadId) {
        findDownloadById(downloadId).ifPresent(item -> {
            downloadQueue.remove(item);
            Log.d(TAG, "Canceled download with ID: " + downloadId);
        });
    }

    /**
     * Gets the list of all current downloads.
     * @return A list of {@link DownloadItem}s.
     */
    public List<DownloadItem> getAllDownloads() {
        return new ArrayList<>(downloadQueue); // Return a copy
    }

    /**
     * Finds a download item by its ID.
     * @param downloadId The ID of the download to find.
     * @return An Optional containing the DownloadItem if found.
     */
    private java.util.Optional<DownloadItem> findDownloadById(int downloadId) {
        return downloadQueue.stream()
                .filter(item -> item.getId() == downloadId)
                .findFirst();
    }
}
