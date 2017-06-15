package com.dreamfactory.recorder.common;

import android.os.Environment;

import java.io.File;

public class Constants {

    public static final String FOLDER_URL = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Records";

    public static final String RECORDING_NAME = "-recording.wav";

    public static final String KEY_ACTION_RECORDING = "KEY_ACTION_RECORDING";
    public static final String ACTION_START = "ACTION_START_RECORDING_START";
    public static final String ACTION_PAUSE = "ACTION_START_RECORDING_PAUSE";
    public static final String ACTION_STOP = "ACTION_START_RECORDING_STOP";
}
