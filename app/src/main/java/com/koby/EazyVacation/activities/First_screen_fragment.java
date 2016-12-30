package com.koby.EazyVacation.activities;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;

/**
 * This class is the First_screen_fragment which is the first fragment the user see in the first screen activity.
 * This fragment is responsible for  login button,signUp button and the resetButton.
 */
public class First_screen_fragment extends Fragment {
    interface Delegate {
        void signUp();

        void logIn(String uid);

        void forgotPw();
    }

    Delegate delegate;
    TextView emailAddress;
    TextView password;
    ProgressBar progressBar;
    Button signUp;
    Button logIn;
    Button resetPw;

    public First_screen_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_screen_frag, container, false);
        emailAddress = (TextView) view.findViewById(R.id.email_editText2);
        password = (TextView) view.findViewById(R.id.emailPassword_editText2);
        progressBar = (ProgressBar) view.findViewById(R.id.mainProgressBar);
        progressBar.setVisibility(View.GONE);
        signUp = (Button) view.findViewById(R.id.sign_up_button);
        logIn = (Button) view.findViewById(R.id.login_button2);
        resetPw = (Button) view.findViewById(R.id.forgot_my_pw_button);
        delegate = (Delegate) getActivity();

        /**
         * This listener  catches a user click on the sign up button
         * and calls for the delegate.signUp method which is implemented in the first screen activity.
         */
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.signUp();
            }
        });
        /**
         * This listener works when a user clicks on the logIn button.
         * The listener loges the user Through the server(Firebase).
         * When the response is a success it triggers the delegate.logIn method which is implemented in the first screen activity.
         */
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Model.instance().login(emailAddress.getText().toString(), password.getText().toString(), new Model.LoginListener() {
                    @Override
                    public void success(String uid) {
                        progressBar.setVisibility(View.GONE);
                        delegate.logIn(uid);
                    }

                    @Override
                    public void fail(String msg) {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });


            }
        });
        /**
         * This listenr works when a user clicks on the reset password  button.
         *  The listener triggers the delegate.forgotPw() method which is implemented in the first screen activity.
         */
        resetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delegate.forgotPw();

            }
        });

        return view;
    }


}
