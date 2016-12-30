package com.koby.EazyVacation.model;

import android.content.Context;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.koby.EazyVacation.activities.ImageItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is responsible for  storing data on the Firebase cloud database
 * and retrieving data.
 * Created by RaZr0 on 06/06/2016.
 */
public class ModelFirebase {

    Firebase myFirebaseRef;
    List<Hotel> hotelList;
    ArrayList<ImageItem> imageList;

    boolean flag = false;

    public ModelFirebase(Context context) {
        Firebase.setAndroidContext(context);
        myFirebaseRef = new Firebase("https://easyvacation.firebaseio.com/");

    }

    public void getAllHotelsAsynch(final Model.GetHotelsListener listener, String lastUpdateDate) {

        Firebase htRef = myFirebaseRef.child("hotel");
        Query queryRef = htRef.orderByChild("lastUpdated").startAt(lastUpdateDate);
        // Attach an listener to read the data at our posts reference
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                hotelList = new LinkedList<Hotel>();
                Log.d("TAG", "There are " + snapshot.getChildrenCount() + " hotels");
                for (DataSnapshot stSnapshot : snapshot.getChildren()) {
                    Hotel hotel = stSnapshot.getValue(Hotel.class);
                    Log.d("TAG", hotel.getHotelName() + " - " + hotel.getId());
                    hotelList.add(hotel);
                }
                listener.onResult(hotelList);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

                listener.onCancel();
            }
        });
    }

    public void getAllMealsAsynch(String mealType, String hotelName, String lastUpdateDate, final Model.GetMealsListener listener) {
        Firebase mealsRef = myFirebaseRef.child(hotelName + " Room Service").child(mealType);
        Query queryRef = mealsRef.orderByChild("lastUpdated").startAt(lastUpdateDate);

        Log.d("Tag", mealsRef.toString());
        Log.d("Tag", queryRef.toString());

        // Attach an listener to read the data at our posts reference
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Meal> mealsList;
                mealsList = new LinkedList<Meal>();
                Log.d("TAG", "There are " + snapshot.getChildrenCount() + " meals");
                for (DataSnapshot stSnapshot : snapshot.getChildren()) {
                    Meal meal = stSnapshot.getValue(Meal.class);
                    Log.d("TAG", meal.getMealName());
                    mealsList.add(meal);
                }
                listener.onResult(mealsList);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

                listener.onCancel();
            }
        });

    }


    public void getAllImagesAsynch(final Model.GetImagesListener listener) {

        Firebase htRef = myFirebaseRef.child("ImageGallery");
        // Attach an listener to read the data at our posts reference
        htRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                imageList = new ArrayList<ImageItem>();
                Log.d("TAG", "There are " + snapshot.getChildrenCount() + " imagess");
                for (DataSnapshot stSnapshot : snapshot.getChildren()) {
                    String imgName = stSnapshot.getValue(String.class);
                    Log.d("TAG", imgName);
                    ImageItem imageItem = new ImageItem();
                    imageItem.setTitle(imgName);
                    imageList.add(imageItem);
                }
                listener.onResult(imageList);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

                listener.onCancel();
            }
        });
    }

    public void getHotelById(String id, final Model.GetHotel listener) {


        Firebase htRef = myFirebaseRef.child("hotel").child(id);
        htRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Hotel hotel = snapshot.getValue(Hotel.class);
                Log.d("TAG", hotel.getHotelName() + " - " + hotel.getId());
                listener.onResult(hotel);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

                listener.onCancel();
            }
        });
    }

    public void getInfoById(String id, String hotelName, final Model.GetInfoListener listener) {


        Firebase infoRef = myFirebaseRef.child(hotelName + " Information2").child(id);
        infoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Info info = snapshot.getValue(Info.class);
                info.setLastUpdated(Model.instance().getDate());
                listener.onResult(info);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

                listener.onCancel();
            }
        });
    }

    public void getUserById(String id, final Model.GetUser listener) {

        Firebase userRef = myFirebaseRef.child("clients").child(id);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                Log.d("TAG", user.getEmail() + " - " + user.getId());
                listener.onResult(user);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("onCancel", firebaseError.getMessage());
                listener.onCancel();
            }
        });

    }

    public void getVacationById(String id, final Model.GetVacation listener) {
        Log.d("useid", id);
        Firebase userRef = myFirebaseRef.child("hotelsGuests").child(id);
        Log.d("useid", userRef.toString());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.d("getVcationByid", "b");
                Vacation vacation = snapshot.getValue(Vacation.class);
                if (vacation != null) {
                    listener.onResult(vacation);
                    Log.d("getVcationByid", "bbb");
                } else {
                    listener.onCancel();
                }

                // Model.instance().setVacation(vacation);
                // Log. d("TAG", vacation.getRoomNumber() + " - " + vacation.getHotelName());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("TAG", "No vacation was found by user id");
                listener.onCancel();
            }
        });
    }

    public void getWifi(String hotelName, final Model.GetWifi listener) {
        final Firebase wifiRef = myFirebaseRef.child(hotelName + " Information").child("wifi").child("password");
        wifiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listener.onResult(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("TAG", "No wifi password found");
                listener.onCancel();
            }
        });

    }

    public void getInfo(String hotelName, final Model.GetStringListener listener) {
        final Firebase wifiRef = myFirebaseRef.child(hotelName + " Information");
        wifiRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object obj = snapshot.getValue();
                listener.onResult(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("TAG", "No wifi password found");
                listener.onCancel();
            }
        });
    }

    public void getHotelEvent(String hotelName, final Model.GetEvent listener) {

        final Firebase eventRef = myFirebaseRef.child(hotelName + " Events").child("Sent");

        eventRef.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (!flag)
                    return;
                else {
                    HotelEvent hotelEvent = dataSnapshot.getValue(HotelEvent.class);
                    listener.onResult(hotelEvent);
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("gethotelvent", "error");
            }
        });
        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                flag = true;

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    public void addHotel(Hotel hotel) {

        hotel.setLastUpdated(Model.instance().getDate());
        Firebase htRef = myFirebaseRef.child("hotel").child(hotel.getId());
        htRef.setValue(hotel);
    }

    public void addUser(User user, String uid) {
        user.setLastUpdated(Model.instance().getDate());
        Firebase userRef = myFirebaseRef.child("clients").child(uid);
        userRef.setValue(user);
    }

    public void addTicket(Ticket ticket, String hotelName)

    {
        Firebase ticketRef = myFirebaseRef.child(hotelName + " Tickets").child("Pending").push();
        ticketRef.setValue(ticket);
    }

    public void addReview(Review review, String hotelName)

    {
        Firebase reviewRef = myFirebaseRef.child(hotelName + " Reviews").push();
        reviewRef.setValue(review);
    }

    public void addMeal(Meal meal, String hotelName, String mealType) {
        meal.setLastUpdated(Model.instance().getDate());
        Firebase mealRef = myFirebaseRef.child(hotelName + " Room Service").child(mealType).push();
        mealRef.setValue(meal);
    }

    public void addInfo(Info info, String hotelName) {
        info.setLastUpdated(Model.instance().getDate());
        Firebase infoRef = myFirebaseRef.child(hotelName + " Information2").child(info.getId());
        infoRef.setValue(info);
    }

    public void addRoomServiceOrder(OrderRoomService order, String hotelName) {
        order.setLastUpdated(Model.instance().getDate());
        Firebase orderRef = myFirebaseRef.child(hotelName + " Room Service Orders").push();
        orderRef.setValue(order);
    }

    public void addImageToGallery(String imageName) {

        Firebase imgRef = myFirebaseRef.child("ImageGallery").child(imageName);
        imgRef.setValue(imageName);
    }

    public void resetPw(String email, final Model.ResetPasswordListener listenr) {
        myFirebaseRef.resetPassword(email, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                listenr.success();
                Log.d("TAG", "Check your mail with the new password");
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                listenr.fail(firebaseError.getMessage().toString());

            }
        });
    }

    public void signeup(final String email, final String pwd, final Model.SignupListener listener) {

        myFirebaseRef.createUser(email, pwd, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                listener.success();
            }

            @Override
            public void onError(FirebaseError firebaseError) {

                listener.fail(firebaseError.getMessage());
            }
        });

    }

    public void login(String email, String pwd, final Model.LoginListener listener) {
        myFirebaseRef.authWithPassword(email, pwd, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                listener.success(authData.getUid());
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                listener.fail(firebaseError.getMessage());
            }
        });

    }

    public void logout() {
        myFirebaseRef.unauth();

    }

    //check the user's authentication state by calling the synchronous getAuth() function:
    public String getUserId() {
        AuthData authData = myFirebaseRef.getAuth();
        if (authData != null) {
            return authData.getUid();
        }
        return null;
    }


}



