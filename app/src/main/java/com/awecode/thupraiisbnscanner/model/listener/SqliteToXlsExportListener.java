package com.awecode.thupraiisbnscanner.model.listener;

public interface SqliteToXlsExportListener {

    public void onExportStart();

    public void onExportComplete(String filePath, String folderPath, String fileName);

    public void onExportError();
}
