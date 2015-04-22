

package com.example.bochi91.multicalendar;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;


public class CalendarActivity extends ActionBarActivity {


    CalendarView cal;
    Button btnAdd;

    String email, password, id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Intent i = getIntent();

        email = i.getStringExtra("email");
        password = i.getStringExtra("password");

        id = i.getStringExtra("id");
        cal = (CalendarView) findViewById(R.id.calv);

        //Toast.makeText(CalendarActivity.this, id, Toast.LENGTH_LONG ).show();

        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // TODO Auto-generated method stub

                /*Toast.makeText(getBaseContext(), "Selected Date is\n\n"
                                + dayOfMonth + " : " + month + " : " + year,
                        Toast.LENGTH_LONG).show();*/

                Intent intent = new Intent(CalendarActivity.this, AppointmentActivity.class);

                intent.putExtra("year", Integer.toString(year));
                intent.putExtra("month", Integer.toString(month));
                intent.putExtra("day", Integer.toString(dayOfMonth));
                intent.putExtra("id",id);
                intent.putExtra("email", email);
                intent.putExtra("password", password);

                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
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
