package com.dreamfactory.recorder.util;

import android.os.AsyncTask;

public class ThreadUtil {

    /**
            * Check if the thread task is running.
	 *
             * @return true is running, false other wise;
	 */
    public static boolean isTaskAlive(Thread task) {
        return task != null && task.isAlive();
    }


    public static boolean isTaskAlive(AsyncTask<?, ?, ?> task) {
        return task != null && (task.getStatus() == AsyncTask.Status.RUNNING);
    }

}
