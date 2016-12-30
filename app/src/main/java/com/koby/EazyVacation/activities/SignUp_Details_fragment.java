package com.koby.EazyVacation.activities;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;
import com.koby.EazyVacation.model.User;

/**
 * This class is the SignUp_Details_fragment which is the second fragment the user see in the first screen activity.
 * This fragment is responsible for showing the registration form in which the user fills in his details.
 */
public class SignUp_Details_fragment extends Fragment {
    TextView emailAddress;
    TextView password;
    TextView confirmPassword;
    TextView firstName;
    TextView lastName;
    TextView userId;
    TextView phoneNumber;
    CheckBox newsAndUpdates;
    Button register;
    Boolean valid;
    String email;
    String pw;
    String rePw;
    String fName;
    String lName;
    String uId;
    String phone;
    Boolean news;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    interface Delegate2 {
        void Register(User user);

    }

    public SignUp_Details_fragment() {
        valid = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_details, container, false);
        emailAddress = (TextView) view.findViewById(R.id.email_editText_profile);
        password = (TextView) view.findViewById(R.id.Password_editText_profile);
        confirmPassword = (TextView) view.findViewById(R.id.confirmPassword_editText_profile);
        firstName = (TextView) view.findViewById(R.id.user_firstName);
        lastName = (TextView) view.findViewById(R.id.user_lastName);
        userId = (TextView) view.findViewById(R.id.user_id);
        phoneNumber = (TextView) view.findViewById(R.id.user_phone);
        newsAndUpdates = (CheckBox) view.findViewById(R.id.user_checkBox_updates);
        register = (Button) view.findViewById(R.id.register_button);

/**
 * This listener is responsible for  the registration of a user in the  servers db
 * and to return via the SignupListener() the new user obj with the relevant data.
 */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailAddress.getText().toString();
                pw = password.getText().toString();
                rePw = confirmPassword.getText().toString();
                fName = firstName.getText().toString();
                lName = lastName.getText().toString();
                uId = userId.getText().toString();
                phone = phoneNumber.getText().toString();
                news = newsAndUpdates.isChecked();
                if (isvaild()) {

                    Model.instance().signeup(email, pw, new Model.SignupListener() {
                        @Override
                        public void success() {

                            User user = new User(email, pw, rePw, fName, lName, uId, phone, news);
                            Delegate2 delegate = (Delegate2) getActivity();
                            delegate.Register(user);
                        }

                        @Override
                        public void fail(String msg) {
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        return view;
    }

    /**
     * This method checks the users input,to see if its valid ot not.
     *
     * @return Boolean
     */
    public Boolean isvaild() {
        if (email != null && email.length() == 0) {
            setValid(false);
            emailAddress.setError("Email address is required !");
        } else {
            setValid(true);
        }
        if (pw != null && pw.length() == 0) {
            setValid(false);
            password.setError("Password is required !");
        } else {
            setValid(true);
        }
        if (rePw != null && (rePw.length() == 0 || (!rePw.equals(pw)))) {

            setValid(false);
            confirmPassword.setError("Passwords dont match or required !");

        } else {
            setValid(true);
        }
        if (uId != null && uId.length() == 0) {
            setValid(false);
            userId.setError("Id is required !");

        } else {
            setValid(true);
        }
        return valid;
    }

}
