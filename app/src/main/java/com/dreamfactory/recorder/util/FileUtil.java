package com.dreamfactory.recorder.util;

import android.os.Environment;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

public class FileUtil {

    /**
     * Create new folder by path.
     */
    public static boolean createFolder(String path) {
        boolean isCreated = false;
        if (isSDCardExist()) {
            File file = new File(path);
            if (!file.exists()) {
                isCreated = file.mkdirs();
            }
        }
        return isCreated;
    }

    /**
     * Delete file by path.
     */
    public static boolean deleteFile(String path) {
        boolean isSuccess = false;
        if (isSDCardExist()) {
            File file = new File(path);
            if (file.exists()) {
                isSuccess = file.delete();
            }
        }
        return isSuccess;
    }

    /**
     * Create file by path.
     */
    public static File createFile(String path) {
        if (isSDCardExist()) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            return file;
        }
        return null;
    }

    /**
     * Delete folder by path.
     */
    public static void deleteFolder(String path) {
        if (isSDCardExist()) {
            File file = new File(path);
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFiles = file.listFiles();
                if (childFiles == null || childFiles.length == 0) {
                    file.delete();
                    return;
                }
                for (int i = 0; i < childFiles.length; i++) {
                    childFiles[i].delete();
                }
                file.delete();
            }
        }

    }

    /**
     * Delete folder by file.
     */
    public static void deleteFile(File dir) {
        if (null == dir || !dir.exists()) return;
        if (isSDCardExist()) {
            File to = new File(dir.getAbsolutePath() + System.currentTimeMillis());
            dir.renameTo(to);
            if (to.isDirectory()) {
                String[] childrens = to.list();
                if (null == childrens || childrens.length == 0) {
                    to.delete();
                    return;
                }
                for (int i = 0; i < childrens.length; i++) {
                    deleteFile(new File(to, childrens[i]));
                }
            }
            to.delete();
        }
    }

    /**
     * Judge if the device has sdcard.
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static void closeQuietly(Closeable output) {
        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
}
