package com.koby.EazyVacation.activities;


import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;
import com.koby.EazyVacation.model.Ticket;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class represents the Maintnance Activity which is the screen that will appear when you press the Maintenance service in the front desk screen(reception activity).
 */
public class Maintnance extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    Ticket ticket;
    String summary;
    String type;
    String ticketTime;
    String ticketDate;
    String guest;
    String room;
    int tHour;
    int tMin;
    int tYear;
    int tMonth;
    int tDay;
    CheckBox tv;
    CheckBox ac;
    CheckBox wc;
    CheckBox lights;
    EditText specialInfo;
    Spinner urgency;
    String hotelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance2);
        getActionBar().hide();

        spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.urgency, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        final Button submit = (Button) findViewById(R.id.submit_button_maintnance);
        tv = (CheckBox) findViewById(R.id.checktv);
        ac = (CheckBox) findViewById(R.id.checkac);
        wc = (CheckBox) findViewById(R.id.checkbath);
        lights = (CheckBox) findViewById(R.id.checkLights);
        specialInfo = (EditText) findViewById(R.id.special_info_maintnance);
        urgency = (Spinner) findViewById(R.id.spinner);


/**
 * This listener submits a ticket by the user .
 * The ticket is Maintnance.
 */
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guest = Model.instance().getUser().getfName() + " " + Model.instance().getUser().getlName();
                room = Model.instance().getVacation().getRoomNumber();
                type = "Maintnance";
                hotelName = Model.instance().getVacation().getHotelName();
                Calendar calendar = new GregorianCalendar();
                String hour;
                tHour = calendar.get(Calendar.HOUR_OF_DAY); //always 24 HOUR type
                if (tHour < 10)
                    hour = "0" + tHour;
                else
                    hour = tHour + "";
                tMin = calendar.get(Calendar.MINUTE);
                if (tMin < 10)
                    ticketTime = "" + hour + ":" + "0" + tMin;
                else
                    ticketTime = "" + hour + ":" + tMin;
                tYear = calendar.get(Calendar.YEAR);
                tMonth = calendar.get(Calendar.MONTH);
                tDay = calendar.get(Calendar.DAY_OF_MONTH);
                ticketDate = "" + tDay + "/" + (1 + tMonth) + "/" + tYear;
                summary = checkNeedFix() + "urgency : " + urgency.getSelectedItem().toString() + ".   " + specialInfo.getText().toString();
                ticket = new Ticket(ticketDate, ticketTime, type, room, guest, summary);
                Model.instance().addTicket(ticket, hotelName);
                Toast.makeText(getApplicationContext(), "sumbited", Toast.LENGTH_SHORT).show();
                finish();


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //this method chekcs whats needed to be fixed and adds it to the string.
    private String checkNeedFix() {
        String tmp = "need to fix :";
        String str = "none";
        if (tv.isChecked()) {
            str = "tv,";
        }
        if (wc.isChecked()) {
            str = " wc,";
        }
        if (ac.isChecked()) {
            str = " ac,";
        }
        if (lights.isChecked()) {
            str = "lights,";
        }
        return "need to fix :" + str;
    }

}
