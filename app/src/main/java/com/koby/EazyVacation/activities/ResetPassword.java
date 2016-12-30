package com.koby.EazyVacation.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;

/**
 * This class is ResetPassword Activity which is responsible for restarting a users password.
 * The user will get an email with the new password.
 */
public class ResetPassword extends Activity {
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText email = (EditText) findViewById(R.id.email_resPw);
        Button resPwButton = (Button) findViewById(R.id.resetPwButton);

        //when the reset button was clicked checks if the email is valid and then sends the information to the server.
        resPwButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().length() == 0) {
                    email.setError("Email address is required !");
                    flag = false;
                }
                if (flag) {
                    Model.instance().getFirebaseModel().resetPw(email.getText().toString(), new Model.ResetPasswordListener() {
                        @Override
                        public void success() {
                            Toast.makeText(getApplicationContext(), "Check your mailbox with your new password!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void fail(String msg) {
                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }
        });

    }

}
