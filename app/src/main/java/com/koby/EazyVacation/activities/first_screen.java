package com.koby.EazyVacation.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.koby.EazyVacation.R;
import com.koby.EazyVacation.model.Model;
import com.koby.EazyVacation.model.User;

/**
 * This class represents the first screen Activity and this will be the first screen the user will see.
 * In the activity there are 2 fragments:First_screen_fragment,SignUp_Details_fragment.
 * The activity implements 2 interfaces :Delegate and Delegate2.
 */
public class first_screen extends Activity implements First_screen_fragment.Delegate, SignUp_Details_fragment.Delegate2 {
    First_screen_fragment first_screen_fragment;
    SignUp_Details_fragment signUp_details_fragment;
    FragmentTransaction transaction;
    Intent intent;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        getActionBar().hide();

        first_screen_fragment = new First_screen_fragment();
        signUp_details_fragment = new SignUp_Details_fragment();

        transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.first_screen_frag_container, first_screen_fragment, "y");
        // transaction.add(R.id.first_screen_frag_container, signUp_details_fragment, "y");
        if (Model.instance().getUserId() != null) {
            Log.d("first_Screen activity :", Model.instance().getUserId());
            Intent intent = new Intent(getApplicationContext(), second_screen.class);
            startActivity(intent);
        } else {
            transaction.show(first_screen_fragment);
            // transaction.hide(signUp_details_fragment);
            transaction.commit();
        }

    }

    /**
     * This method shows up the signUp_details_fragment  when the user wants to sign up.
     */
    @Override
    public void signUp() {

        FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
        transaction2.replace(R.id.first_screen_frag_container, signUp_details_fragment);
        getFragmentManager().popBackStack();
        transaction2.addToBackStack(null);
        transaction2.commit();


    }

    /**
     * This method is responsible for logging the user into the db.
     *
     * @param uid
     */
    @Override
    public void logIn(String uid) {
        // Model.instance().getFirebaseModel().createClients(uid);
        if (getIntent().getParcelableExtra("user") != null) {
            this.user = new User((User) getIntent().getParcelableExtra("user"));
            Model.instance().getFirebaseModel().addUser(user, uid);
        }
        Intent intent = new Intent(getApplicationContext(), second_screen.class);
        startActivity(intent);


    }

    /**
     * This method is responsible for opening the ResetPassword screen when the user forgot his password and he wants to reset it.
     */
    @Override
    public void forgotPw() {
        Intent intent = new Intent(getApplicationContext(), ResetPassword.class);
        startActivity(intent);
    }

    /**
     * This method stores the user information that is inside the User obj and stores it inside the intent.
     *
     * @param user
     */
    @Override
    public void Register(User user) {
        this.user = new User(user);
        intent = new Intent(getApplicationContext(), first_screen.class);
        intent.putExtra("user", user);
        Toast.makeText(this, "added successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

}
