package com.corn.crash;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logUncaughtExceptionHandler();

                final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
                Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < 101190000; i++ ) {
                                    if(i%10000 == 0) {
                                        System.out.println("thread name:" + Thread.currentThread().getName() + " xxxx-i:" + i);
                                    }
                                }
                            }
                        }).start();

                        try {
                            Thread.sleep(3000);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        defaultUncaughtExceptionHandler.uncaughtException(t, e);

                    }
                });
                errorMethod();


                System.out.println("thread name:" + Thread.currentThread().getName() + "come here");
                Thread thread = new Thread(new MyRunnable());
                //thread.start();

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public static void logUncaughtExceptionHandler(){
        Thread thread = Thread.currentThread();
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = thread.getUncaughtExceptionHandler();
        Thread.UncaughtExceptionHandler  defaultUncaughtExceptionHandler = thread.getDefaultUncaughtExceptionHandler();

        System.out.println("thread name:" + thread.getName() + " uncaughtExceptionHandler:" + uncaughtExceptionHandler
                + " defaultUncaughtExceptionHandler:" + defaultUncaughtExceptionHandler);

        try {
            Class threadClass = Class.forName("java.lang.Thread");
            Method method = threadClass.getDeclaredMethod("getUncaughtExceptionPreHandler", null);
            method.setAccessible(true);
            Object obj = method.invoke(threadClass, null);

            System.out.println("thread name:" + thread.getName() + " pre:" + (Thread.UncaughtExceptionHandler)obj);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            System.out.println("thread name:" + Thread.currentThread().getName() + " start run");

            errorMethod();

            System.out.println("thread name:" + Thread.currentThread().getName() + " end run");
        }
    }

    public static int errorMethod() {
        String name = null;

        return name.length();
    }
}
