package com.example.bochi91.multicalendar;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class AppointmentActivity extends ActionBarActivity
{
    String day, month, year, start, end, type, location, id, details;

    Spinner typeInput;
    TimePicker startInput, endInput;
    EditText locationInput, InputDetails;
    int nid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Intent intent = getIntent();

        day = intent.getStringExtra("day");
        month = intent.getStringExtra("month");
        year = intent.getStringExtra("year");
        id = intent.getStringExtra("id");

        //Toast.makeText(AppointmentActivity.this, id, Toast.LENGTH_LONG ).show();

        TextView dateHeading = (TextView)findViewById(R.id.dateDisplay);
        dateHeading.setText(day + "/" + month + "/" + year);

        String[] types = {"Meeting", "Lecture", "Tutorial", "Lab"};

        typeInput = (Spinner)findViewById(R.id.type);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeInput.setAdapter(adapter);

        startInput = (TimePicker)findViewById(R.id.startTime);
        endInput = (TimePicker)findViewById(R.id.endTime);

        locationInput = (EditText)findViewById(R.id.inputLocation);
        InputDetails = (EditText)findViewById(R.id.inputDetails);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //submitAppointment();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appointment, menu);
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

    public void onAddClicked(View view)
    {
        start = startInput.getCurrentHour() + ":" + startInput.getCurrentMinute();
        end = endInput.getCurrentHour() + ":" + endInput.getCurrentMinute();
        type = typeInput.getSelectedItem().toString();
        location = locationInput.getText().toString();
        details = InputDetails.getText().toString();
        submitAppointment();
    }

    public void submitAppointment()
    {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(9);
        InputStream is = null;
        nameValuePairs.add(new BasicNameValuePair("date", day + "/" + month + "/" + year));
        nameValuePairs.add(new BasicNameValuePair("start", start));
        nameValuePairs.add(new BasicNameValuePair("end", end));
        nameValuePairs.add(new BasicNameValuePair("type", type));
        nameValuePairs.add(new BasicNameValuePair("location", location));
        nameValuePairs.add(new BasicNameValuePair("details", details));
        nameValuePairs.add(new BasicNameValuePair("id", id));

        //ALSO ADD THE LECTURER ID !

        //setting up the connection inside the try catch block
        try{
            //Toast.makeText(getApplicationContext(), "Beginning the try block", Toast.LENGTH_SHORT).show();

            //setting up the default http client
            HttpClient httpClient = new DefaultHttpClient();

            //Setting up the http post method and passing the url in case
            //of online database and the ip address in case of localhost database.
            //And the php file which serves as the link between the android app
            //and the database.

            HttpPost httpPost = new HttpPost("http://teammacro.byethost12.com/appointment.php");
            //Passing the nameValuePairs inside the httpPost
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            //Getting the response
            HttpResponse response = httpClient.execute(httpPost);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            //setting up the entity
            HttpEntity entity = response.getEntity();

            //setting up the content in an input stream reader
            //lets define the input stream reader
            String responseBody = httpClient.execute(httpPost, responseHandler);

            is = entity.getContent();

            //Displaying a toast message if the data is entered successfully
            String msg = responseBody;

            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

            Intent i = new Intent(AppointmentActivity.this, AppointmentDetailsActivity.class);

            i.putExtra("type", type);
            i.putExtra("location", location);
            i.putExtra("start", start);
            i.putExtra("end", end);
            i.putExtra("date", day + "/" + month + "/" + year );
            i.putExtra("details", details);
            startActivity(i);


            //Toast.makeText(getApplicationContext(), "Hello Again", Toast.LENGTH_SHORT).show();

            /*if(msg.matches("match")) {

                msg = "Login successful";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, CalendarActivity.class);
                startActivity(i);
            }
            else
            {
                msg = "Error: incorrect credentials";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }*/


        }//write the catch blocks to handle the exceptions
        catch(ClientProtocolException e){
            Log.e("ClientProtocol", "Log_tag");
            e.printStackTrace();
        } catch (IOException e)
        {
            Log.e("Log_tag", "IOException");
            e.printStackTrace();
        }

    }

}