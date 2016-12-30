package com.koby.EazyVacation.activities;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;
import com.koby.EazyVacation.model.User;
import com.koby.EazyVacation.model.Vacation;

/**
 * This class is the Home_screen_fragment which is the fragment the user see in the second screen activity.
 * This fragment is responsible for entering the MainActivity screen via the enter_hotel button and for the entering the HotelList activity via the enter hotel_list button.
 */
public class Home_screen_fragment extends Fragment {
    Delegate delegate;
    ProgressBar progressBar;

    interface Delegate {
        void inVacation(Vacation vacation);

        void viewHotelList();
    }

    public Home_screen_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_screen_frag, container, false);
        Button enter_hotel = (Button) view.findViewById(R.id.enter_hotelButton);
        progressBar = (ProgressBar) view.findViewById(R.id.mainProgressBar);
        progressBar.setVisibility(View.GONE);
        delegate = (Delegate) getActivity();
        Button enterhotel_list = (Button) view.findViewById(R.id.hotels_list_button);

        enter_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Model.instance().getUserById(Model.instance().getUserId(), new Model.GetUser() {
                    @Override
                    public void onResult(final User user) {
                        {
                            Model.instance().setUser(user);
                            Model.instance().getVacationById(user.getId(), new Model.GetVacation() {
                                @Override
                                public void onResult(Vacation vacation) {
                                    Model.instance().setVacation(vacation);
                                    progressBar.setVisibility(View.GONE);
                                    delegate.inVacation(vacation);

                                }

                                @Override
                                public void onCancel() {
                                    progressBar.setVisibility(View.GONE);
                                    Toast myToast = Toast.makeText(getActivity(), "No Vacation for you!", Toast.LENGTH_SHORT);
                                    myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                    myToast.show();
                                }
                            });


                        }


                    }

                    @Override
                    public void onCancel() {
                        Log.d("scend_Screen:", "could not find user by id");

                    }
                });

            }
        });

        enterhotel_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.viewHotelList();
            }
        });
        return view;
    }


}
