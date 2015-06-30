package com.omar.dardear.dardearycafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    private String DB_NAME = "fooddb.sqlite";
    private String DB_PATH = "/data/data/com.omar.dardear.dardearycafe/";
    private String DB_DIR_PATH = "/data/data/com.omar.dardear.dardearycafe/databases";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        getSupportActionBar().hide();

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        final Editor editor = pref.edit();
        editor.putInt("Size", 0);
        editor.commit();

        StoreDatabase();









        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editor.putInt("Size", 0);
                editor.commit();
                Intent intent = new Intent(MainActivity.this, MainMenu.class);
                startActivity(intent);
            }
        });







    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void StoreDatabase() {
        try {
            //create the directory "databases"
            File databases_dir = new File(DB_DIR_PATH);
            databases_dir.mkdirs();
            //create the file "fooddb" in the directory "databases"
            File DbFile = new File(DB_PATH + DB_NAME);
            if (!DbFile.exists()) {
                DbFile.createNewFile();
                //open the file in the assets
                InputStream is = this.getAssets().open(DB_NAME);
                FileOutputStream fos = new FileOutputStream(DbFile);
                //copy from the file in the asset to the file in the databases
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0)
                    fos.write(buffer,0,length );
                //close both files
                fos.flush();
                fos.close();
                is.close();
                DbFile.renameTo(this.getDatabasePath("fooddb.sqlite"));

            }
        }
        catch (IOException e) {
            Toast.makeText(MainActivity.this, "Error in Attatchment", Toast.LENGTH_SHORT).show();
        }
    }




}
