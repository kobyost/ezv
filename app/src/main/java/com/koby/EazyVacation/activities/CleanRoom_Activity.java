package com.koby.EazyVacation.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;
import com.koby.EazyVacation.model.Ticket;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class represents the CleanRoom_Activity which is the screen that will appear when you press the clean room service in the front desk screen(reception activity).
 */
public class CleanRoom_Activity extends Activity {
    Ticket ticket;
    String summary;
    String type;
    String ticketTime;
    String ticketDate;
    String guest;
    String room;
    String hotelName;
    int tHour;
    int tMin;
    int tYear;
    int tMonth;
    int tDay;
    TimeEditText cleanRoomTime;
    EditText specialInfo_cleanRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean_room_);
        getActionBar().hide();
        cleanRoomTime = (TimeEditText) findViewById(R.id.cleanRoom_timeEdit);
        specialInfo_cleanRoom = (EditText) findViewById(R.id.special_info_cleanRoom);
        Button submit = (Button) findViewById(R.id.submit_button_cleanRoom);
/**
 * This listener submits a ticket by the user .
 * The ticket is clean room.
 */
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guest = Model.instance().getUser().getfName() + " " + Model.instance().getUser().getlName();
                room = Model.instance().getVacation().getRoomNumber();
                type = "Clean room";
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
                // ticketDate   ="" + tDay + "/" + (1+tMonth) + "/" + tYear;
                ticketDate = "" + (1 + tMonth) + "/" + tDay + "/" + tYear;
                summary = "Clean room at : " + cleanRoomTime.getText() + ".  " + specialInfo_cleanRoom.getText();
                ticket = new Ticket(ticketDate, ticketTime, type, room, guest, summary);
                Model.instance().addTicket(ticket, hotelName);
                Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
