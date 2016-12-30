package com.koby.EazyVacation.activities;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;
import com.koby.EazyVacation.model.Vacation;

/**
 * This class is the Vacation_Details_fragment which is the  fragment the user see in the Vacation activity.
 * This fragment is responsible for  viewing the users vacation details,such as:hotel name,room nubmer,checkIn and CheckOut date.
 */
public class Vacation_Details_fragment extends Fragment {
    TextView hotelName;
    TextView roomNumber;
    TextView checkIn;
    TextView checkOut;


    public Vacation_Details_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vacation_details, container, false);
        Vacation vacation = Model.instance().getVacation();
        hotelName = (TextView) view.findViewById(R.id.hotelName_textView);
        roomNumber = (TextView) view.findViewById(R.id.room_number);
        checkIn = (TextView) view.findViewById(R.id.checkIn_date);
        checkOut = (TextView) view.findViewById(R.id.checkOut_date);

        hotelName.setText(vacation.getHotelName());
        roomNumber.setText(vacation.getRoomNumber());
        checkIn.setText(vacation.getCheckIn());
        checkOut.setText(vacation.getCheckOut());
        return view;
    }

}
