package com.zyphorabrowser;

/**
 * A data class representing a single item in the download queue.
 */
public class DownloadItem {

    private int id;
    private String url;
    private String filePath;
    private DownloadStatus status;
    private int progress; // Percentage, 0-100
    private long fileSize; // in bytes

    public DownloadItem(int id, String url, String filePath) {
        this.id = id;
        this.url = url;
        this.filePath = filePath;
        this.status = DownloadStatus.PENDING; // Downloads start as pending
        this.progress = 0;
        this.fileSize = 0;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getFilePath() {
        return filePath;
    }

    public DownloadStatus getStatus() {
        return status;
    }

    public int getProgress() {
        return progress;
    }

    public long getFileSize() {
        return fileSize;
    }

    // Setters
    public void setStatus(DownloadStatus status) {
        this.status = status;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
