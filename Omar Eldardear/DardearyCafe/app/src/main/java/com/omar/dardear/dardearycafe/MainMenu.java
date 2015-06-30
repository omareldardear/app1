package com.omar.dardear.dardearycafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainMenu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type);
        getSupportActionBar().setTitle("Menu");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF1f5d65));
//        getSupportActionBar().hide();
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        final SharedPreferences.Editor editor = pref.edit();












       final ListView listoftypes;
        listoftypes=(ListView) findViewById(R.id.listView);



        ArrayList<String > tyepsdata=new ArrayList<String>();
        tyepsdata.add("Appetizers");
        tyepsdata.add("Main Dishs");
        tyepsdata.add("Pasta and Rice");
        tyepsdata.add("Pizza");
        tyepsdata.add("Drinks");
        tyepsdata.add("Deserts");



        ArrayAdapter<String> TypesAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.raw_view,R.id.row_view_ID,tyepsdata);
        listoftypes.setAdapter(TypesAdapter);






        listoftypes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(MainMenu.this, SecondMenu.class)
                        .putExtra(Intent.EXTRA_TEXT, listoftypes.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });



        ImageView home=(ImageView) findViewById(R.id.imageView2);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(MainMenu.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack
                startActivity(intent);

            }
        });










        ImageView Busket=(ImageView) findViewById(R.id.imageView4);
        Busket.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MainMenu.this, MyOrder.class);
                startActivity(intent);

            }
        });




        ImageView Enter=(ImageView) findViewById(R.id.imageView5);
        Enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (pref.getInt("Size",0)!=0)
                {
                    GPSTracker mGPS = new GPSTracker(MainMenu.this);
                    if(mGPS.canGetLocation() ){
                        mGPS.location=mGPS.getLocation();
                        if(mGPS.getLatitude()==0.0)
                        {
                            Toast.makeText(MainMenu.this, "GPS just Opend please wait few seconds and try again", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            String message = "";
                            for (int c = 0; c < pref.getInt("Size", 0); c++) {
                                message = message + pref.getString("Dish" + c, "f") + "\n";
                            }
                            message = message + "\n" + "Location:: " + "\n" + "Lat: " + mGPS.getLatitude() + "\n" + "Lon: " + mGPS.getLongitude();
                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("message/rfc822");
                            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"dardearynote@gmail.com"});
                            i.putExtra(Intent.EXTRA_SUBJECT, "Order");
                            i.putExtra(Intent.EXTRA_TEXT, message);
                            try {
                                startActivity(Intent.createChooser(i, "Send mail..."));
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(MainMenu.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(MainMenu.this, "Unable to Find Your Location Please Open GPS", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                    Toast.makeText(MainMenu.this, "No Dishes Selected", Toast.LENGTH_SHORT).show();


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
            final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

            final SharedPreferences.Editor editor = pref.edit();
            editor.putInt("Size", 0);
            editor.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
