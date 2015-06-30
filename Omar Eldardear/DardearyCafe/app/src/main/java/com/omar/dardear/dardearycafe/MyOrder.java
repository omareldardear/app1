package com.omar.dardear.dardearycafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MyOrder extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        getSupportActionBar().setTitle("My Order");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF1f5d65));
//        getSupportActionBar().hide();















        final ListView listoftypes;
        listoftypes=(ListView) findViewById(R.id.listView);

        ArrayList<CustomObject> objects = new ArrayList<CustomObject>();
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        final SharedPreferences.Editor editor = pref.edit();
        int SizeOfOrder=pref.getInt("Size",8);

        float sub=0;
        for (int x=0;x<SizeOfOrder ;x++)
        {

            sub=sub+ Float.parseFloat(pref.getString("Price"+x,"k"));
            objects.add(new CustomObject(pref.getString("Dish"+x,"k"),pref.getString("Price"+x,"k")) );

        }


        CustomAdapter customAdapter = new CustomAdapter(this, objects);
        listoftypes.setAdapter(customAdapter);


        TextView subtotal;
        subtotal=(TextView) findViewById(R.id.SubTotalPrice);
        subtotal.setText(String.format("%.2f", sub));


        float total=12*sub;
        total=total/100;
        total=total+sub;
        TextView Total;
        Total=(TextView) findViewById(R.id.TotalPrice);
        Total.setText(String.format("%.2f", total));



        ImageView home=(ImageView) findViewById(R.id.imageView2);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MyOrder.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack
                startActivity(intent);

            }
        });










        ImageView Menu=(ImageView) findViewById(R.id.imageView3);
        Menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                onBackPressed();

            }
        });




        ImageView Enter=(ImageView) findViewById(R.id.imageView5);
        Enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (pref.getInt("Size",0)!=0)
                {
                    GPSTracker mGPS = new GPSTracker(MyOrder.this);
                    if(mGPS.canGetLocation() ){
                        mGPS.location=mGPS.getLocation();
                        if(mGPS.getLatitude()==0.0)
                        {
                            Toast.makeText(MyOrder.this, "GPS just Opend please wait few seconds and try again", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(MyOrder.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(MyOrder.this, "Unable to Find Your Location Please Open GPS", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                    Toast.makeText(MyOrder.this, "No Dishes Selected", Toast.LENGTH_SHORT).show();

            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity  in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

            final SharedPreferences.Editor editor = pref.edit();
            editor.putInt("Size", 0);
            editor.commit();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
