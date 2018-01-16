package charlesjamnajim.micropol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by cha on 5/20/2017.
 */

public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        timerThread.start();

    }

    protected void onPause(){
        super.onPause();
        finish();
    }
}
