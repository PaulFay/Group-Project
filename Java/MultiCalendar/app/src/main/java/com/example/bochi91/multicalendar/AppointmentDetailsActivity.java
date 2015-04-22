package com.example.bochi91.multicalendar;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


public class AppointmentDetailsActivity extends ActionBarActivity {

    String type, location, start, end, date, details;

    TextView txt, txl, txs, txe, txd, txde;

    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        Intent i = getIntent();

        type = i.getStringExtra("type");
        location = i.getStringExtra("location");
        start = i.getStringExtra("start");
        end = i.getStringExtra("end");
        date = i.getStringExtra("date");
        details = i.getStringExtra("details");

        txt = (TextView) findViewById(R.id.txat);
        txl = (TextView) findViewById(R.id.txlt);
        txs = (TextView) findViewById(R.id.txst);
        txe = (TextView) findViewById(R.id.txet);
        txd = (TextView) findViewById(R.id.txdt);
        txde = (TextView) findViewById(R.id.txdet);

        txt.setText(type);
        txl.setText(location);
        txs.setText(start + "0");
        txe.setText(end + "0");
        txd.setText(date);
        txde.setText(details);

        exit = (Button) findViewById(R.id.exbut);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AppointmentDetailsActivity.this, MainActivity.class);
                startActivity(intent);


            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appointment_details, menu);
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
}
