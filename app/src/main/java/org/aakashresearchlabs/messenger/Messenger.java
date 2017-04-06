package org.aakashresearchlabs.messenger;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by harshit on 06-04-2017.
 */

public class Messenger extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
