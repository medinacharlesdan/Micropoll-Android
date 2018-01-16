package charlesjamnajim.micropol;

import android.app.Application;

import com.firebase.client.Firebase;

import com.firebase.client.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by cha on 5/17/2017.
 *
 */

public class FireApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Firebase.setAndroidContext(this);


    }
}
