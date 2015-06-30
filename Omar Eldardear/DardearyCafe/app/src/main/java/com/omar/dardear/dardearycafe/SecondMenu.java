package com.omar.dardear.dardearycafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class SecondMenu extends ActionBarActivity  implements LoaderManager.LoaderCallbacks<Cursor>{




    private SecondMenuListAdapter TheAdapter;

    private Intent intent ;
    private String Type;
    private String path;
    private String Table;
    private static final int URL_LOADER = 0;

    private  final String[] columns={ "ID","Name","Price" };

    private String GetPath(String tablename)
    {
        switch (tablename)
        {
            case ("Appetizers"):
                return "Appetizers";
            case ("Main Dishs"):
                return "MainDishes";
            case ("Pasta and Rice"):
                return "PastaRice";
            case ("Pizza"):
                return "Pizza";
            case ("Drinks"):
                return "Drinks";
            case ("Deserts"):
                return "Deserts";
            default:
                return " ";
        }

    }




    private int GetTableNumber(String tablename)
    {
        switch (tablename)
        {
            case ("Appetizers"):
                return 1;
            case ("Main Dishs"):
                return 2;
            case ("Pasta and Rice"):
                return 3;
            case ("Pizza"):
                return 4;
            case ("Drinks"):
                return 5;
            case ("Deserts"):
                return 6;
            default:
                return 0;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listdishes);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF1f5d65));
//        getSupportActionBar().hide();



        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        final Editor editor = pref.edit();
//        editor.putInt("Size", 0);
        editor.commit();

        intent = this.getIntent();
        Type=intent.getStringExtra(Intent.EXTRA_TEXT);

        path=GetPath(Type);
        Table=Integer.toString(GetTableNumber(Type));
        getSupportActionBar().setTitle(Type);


















        getSupportLoaderManager().initLoader(URL_LOADER, null, this);


        TheAdapter=new SecondMenuListAdapter(this, null, 0);
        ListView list=(ListView) findViewById(R.id.listView);
        list.setAdapter(TheAdapter);






        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int i = 0;
            int p;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                i++;

                Handler handler = new Handler();
                Runnable r = new Runnable() {

                    @Override
                    public void run() {
                        i = 0;

                    }
                };

                if (i == 1) {
                    p =position;
                    handler.postDelayed(r, 250);
                } else if (i == 2) {
                    if (position==p) {
                        TextView temp = (TextView) view.findViewById(R.id.data_Name);
                        String name = temp.getText().toString();
                        temp = (TextView) view.findViewById(R.id.data_Price);
                        String price = temp.getText().toString();
                        editor.putInt("Size", pref.getInt("Size",0)+1);
                        editor.putString("Dish"+pref.getInt("Size",0),name);
                        editor.putString("Price"+pref.getInt("Size",0),price);
                        editor.commit();


                        Toast.makeText(SecondMenu.this, name +" Added To Your Order", Toast.LENGTH_SHORT).show();
                        i=0;
                    }
                    else
                    {
                        p=position;
                        i=1;
                    }
                }
            }
        });

        ImageView home=(ImageView) findViewById(R.id.imageView2);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(SecondMenu.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Removes other Activities from stack
                startActivity(intent);

            }
        });










        ImageView Busket=(ImageView) findViewById(R.id.imageView4);
        Busket.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SecondMenu.this, MyOrder.class);
                startActivity(intent);

            }
        });




        ImageView Enter=(ImageView) findViewById(R.id.imageView5);
        Enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (pref.getInt("Size",0)!=0)
                {
                    GPSTracker mGPS = new GPSTracker(SecondMenu.this);
                    if(mGPS.canGetLocation() ){
                        mGPS.location=mGPS.getLocation();
                        if(mGPS.getLatitude()==0.0)
                        {
                            Toast.makeText(SecondMenu.this, "GPS just Opend please wait few seconds and try again", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(SecondMenu.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(SecondMenu.this, "Unable to Find Your Location Please Open GPS", Toast.LENGTH_SHORT).show();

                    }
                }
                else
                    Toast.makeText(SecondMenu.this, "No Dishes Selected", Toast.LENGTH_SHORT).show();


            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second_menu, menu);
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

            final Editor editor = pref.edit();
            editor.putInt("Size", 0);
            editor.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri uri = Uri.parse("content://" +FoodContract.CONTENT_AUTHORITY+"/"+ path+ "/"+Table);
        return new CursorLoader(this,uri,columns, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        TheAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        TheAdapter.swapCursor(null);
    }




}
