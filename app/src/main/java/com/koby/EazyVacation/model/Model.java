package com.koby.EazyVacation.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.koby.EazyVacation.activities.ImageItem;
import com.koby.EazyVacation.activities.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * This class represents the model class which is responsible for receiving and sending data from the firebase db and the sql cach db
 * and uploading images and saving them through the cloudinary db.
 * Created by קובי on 02/06/2016.
 */
public class Model {
    private final static Model instance = new Model();
    Context context;
    ModelFirebase firebaseModel;
    ModelCloudinary modelCloudinary;
    ModelSql modelSql;
    User user;
    Vacation vacation;
    Boolean flag = true;

    /**
     * Default constructor which is private because the model is a Singleton
     */
    private Model() {
        context = MyApplication.getContext();
        firebaseModel = new ModelFirebase(context);
        modelCloudinary = new ModelCloudinary();
        modelSql = new ModelSql(MyApplication.getContext());

    }

    /**
     * Static method that returns the instance data member of type Model.
     * Part of the Singleton pattern.
     *
     * @return Model
     */
    public static Model instance() {
        return instance;
    }

    /**
     * This method returns all the hotels  in the firebase db via the  GetHotelsListener .
     *
     * @param listener
     */
    public void getAllHotelsAsynch(final GetHotelsListener listener) {

        final String lastUpdateDate = HotelSql.getLastUpdateDate(modelSql.getReadbleDB());
        firebaseModel.getAllHotelsAsynch(new GetHotelsListener() {
            @Override
            public void onResult(List<Hotel> hotels) {
                if (hotels != null && hotels.size() > 0) {
                    //update the local DB
                    String reacentUpdate = lastUpdateDate;
                    for (Hotel hotel : hotels) {
                        HotelSql.add(modelSql.getWritableDB(), hotel);
                        if (reacentUpdate == null || hotel.getLastUpdated().compareTo(reacentUpdate) > 0) {
                            reacentUpdate = hotel.getLastUpdated();
                        }
                        Log.d("TAG", "updating: " + hotel.toString());
                    }
                    HotelSql.setLastUpdateDate(modelSql.getWritableDB(), reacentUpdate);
                }
                //return the complete student list to the caller
                List<Hotel> res = HotelSql.getAllHotels(modelSql.getReadbleDB());
                listener.onResult(res);
            }

            @Override
            public void onCancel() {
                listener.onCancel();
            }
        }, lastUpdateDate);

    }

    /**
     * This method returns all the meals  in the firebase db via the  GetMealsListener .
     *
     * @param mealType
     * @param hotelName
     * @param listener
     */
    public void getAllmealsAsynch(String mealType, String hotelName, final GetMealsListener listener) {
        final String lastUpdateDate;
        if (mealType.equals("Breakfast")) {
            lastUpdateDate = MealBreakFastSql.getLastUpdateDate(modelSql.getReadbleDB());
            firebaseModel.getAllMealsAsynch(mealType, hotelName, lastUpdateDate, new GetMealsListener() {
                @Override
                public void onResult(List<Meal> meals) {
                    if (meals != null && meals.size() > 0) {
                        //update the local DB
                        String reacentUpdate = lastUpdateDate;
                        for (Meal meal : meals) {
                            MealBreakFastSql.add(modelSql.getWritableDB(), meal);
                            if (reacentUpdate == null || meal.getLastUpdated().compareTo(reacentUpdate) > 0) {
                                reacentUpdate = meal.getLastUpdated();
                            }
                            Log.d("TAG", "updating: " + meal.toString());
                        }
                        MealBreakFastSql.setLastUpdateDate(modelSql.getWritableDB(), reacentUpdate);
                    }
                    //return the complete student list to the caller

                    List<Meal> res = MealBreakFastSql.getAllMeals(modelSql.getReadbleDB());
                    listener.onResult(res);
                }

                @Override
                public void onCancel() {

                }
            });
        } else if (mealType.equals("Lunch")) {
            lastUpdateDate = MealLunchSql.getLastUpdateDate(modelSql.getReadbleDB());
            firebaseModel.getAllMealsAsynch(mealType, hotelName, lastUpdateDate, new GetMealsListener() {
                @Override
                public void onResult(List<Meal> meals) {
                    if (meals != null && meals.size() > 0) {
                        //update the local DB
                        String reacentUpdate = lastUpdateDate;
                        for (Meal meal : meals) {
                            MealLunchSql.add(modelSql.getWritableDB(), meal);
                            if (reacentUpdate == null || meal.getLastUpdated().compareTo(reacentUpdate) > 0) {
                                reacentUpdate = meal.getLastUpdated();
                            }
                            Log.d("TAG", "updating: " + meal.toString());
                        }
                        MealLunchSql.setLastUpdateDate(modelSql.getWritableDB(), reacentUpdate);
                    }
                    //return the complete student list to the caller
                    List<Meal> res = MealLunchSql.getAllMeals(modelSql.getReadbleDB());
                    listener.onResult(res);
                }

                @Override
                public void onCancel() {

                }
            });
        } else {
            lastUpdateDate = MealDinnerSql.getLastUpdateDate(modelSql.getReadbleDB());
            firebaseModel.getAllMealsAsynch(mealType, hotelName, lastUpdateDate, new GetMealsListener() {
                @Override
                public void onResult(List<Meal> meals) {
                    if (meals != null && meals.size() > 0) {
                        //update the local DB
                        String reacentUpdate = lastUpdateDate;
                        for (Meal meal : meals) {
                            MealDinnerSql.add(modelSql.getWritableDB(), meal);
                            if (reacentUpdate == null || meal.getLastUpdated().compareTo(reacentUpdate) > 0) {
                                reacentUpdate = meal.getLastUpdated();
                            }
                            Log.d("TAG", "updating: " + meal.toString());
                        }
                        MealDinnerSql.setLastUpdateDate(modelSql.getWritableDB(), reacentUpdate);
                    }
                    //return the complete student list to the caller
                    List<Meal> res = MealDinnerSql.getAllMeals(modelSql.getReadbleDB());
                    listener.onResult(res);
                }

                @Override
                public void onCancel() {

                }
            });
        }


    }

    /**
     * This method returns all the images  in the firebase db via the GetImagesListener
     * @param listenr
     */
    public void getAllImagesAsynch(GetImagesListener listenr) {
        firebaseModel.getAllImagesAsynch(listenr);
    }

    public void getWifi(String hotelName, final GetWifi listener) {
        firebaseModel.getWifi(hotelName, listener);
    }

    public void getInfo(String hotelName, final GetStringListener listener) {
        firebaseModel.getInfo(hotelName, listener);
    }

    public void getHotelEvent(String hotelName, final GetEvent listener) {
        firebaseModel.getHotelEvent(hotelName, listener);
    }

    public void getHotelById(String id, GetHotel listener) {
        firebaseModel.getHotelById(id, listener);

    }

    public void getInfolById(final String id, String hotelName, final GetInfoListener listener) {
        firebaseModel.getInfoById(id, hotelName, new GetInfoListener() {
            @Override
            public void onResult(Info info) {
                listener.onResult(info);
            }

            @Override
            public void onCancel() {
                listener.onCancel();

            }
        });

    }

    public void getUserById(String id, final GetUser listener) {
        firebaseModel.getUserById(id, listener);
    }

    public void getVacationById(String id, GetVacation listener) {
        firebaseModel.getVacationById(id, listener);
    }

    public void addHotel(Hotel hotel) {
        firebaseModel.addHotel(hotel);
    }

    public void addReview(Review review, String hotelName) {
        firebaseModel.addReview(review, hotelName);
    }

    public void addInfo(Info info, String hotelName) {
        firebaseModel.addInfo(info, hotelName);
    }

    public void addMeal(Meal meal, String hotelName, String mealType) {
        firebaseModel.addMeal(meal, hotelName, mealType);
    }

    public void saveImage(final Bitmap imageBitmap, final String imageName) {
        saveImageToFile(imageBitmap, imageName); // synchronously save image locally
        Thread d = new Thread(new Runnable() {  // asynchronously save image to parse
            @Override
            public void run() {
                modelCloudinary.saveImage(imageBitmap, imageName);
            }
        });
        d.start();
    }

    public interface LoadImageListener {
        public void onResult(Bitmap imageBmp);
    }

    //getImage or download image
    public void loadImage(final String imageName, final LoadImageListener listener) {
        AsyncTask<String, String, Bitmap> task = new AsyncTask<String, String, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bmp = loadImageFromFile(imageName);              //first try to fin the image on the device
                if (bmp == null) {                                      //if image not found - try downloading it from cloudinary
                    bmp = modelCloudinary.loadImage(imageName);
                    if (bmp != null)
                        saveImageToFile(bmp, imageName);    //save the image locally for next time
                }
                return bmp;
            }

            @Override
            protected void onPostExecute(Bitmap result) {
                listener.onResult(result);
            }
        };
        task.execute();
    }

    public void saveImageToFile(Bitmap imageBitmap, String imageFileName) {
        FileOutputStream fos;
        OutputStream out = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!dir.exists()) {
                dir.mkdir();
            }
            Log.d("SaveToFile", imageFileName);
            File imageFile = new File(dir, imageFileName);
            imageFile.createNewFile();

            out = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();


            //add the picture to the gallery so we dont need to manage the cache size
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(imageFile);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
            Log.d("tag", "add image to cache: " + imageFileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap loadImageFromFile(String imageFileName) {
        String str = null;
        Bitmap bitmap = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dir, imageFileName);
            InputStream inputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
            Log.d("tag", "got image from cache: " + imageFileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void signeup(String email, String pwd, final SignupListener listener) {
        firebaseModel.signeup(email, pwd, listener);
    }

    public void login(String email, String pwd, final LoginListener listener) {
        firebaseModel.login(email, pwd, listener);
    }

    public void resetPw(String email, ResetPasswordListener listenr) {
        firebaseModel.resetPw(email, listenr);
    }

    public void addTicket(Ticket ticket, String hotelName) {
        firebaseModel.addTicket(ticket, hotelName);
    }

    public void addRoomServiceOrder(OrderRoomService order, String hotelName) {
        firebaseModel.addRoomServiceOrder(order, hotelName);
    }

    public void addImageToGallery(String imgItem) {
        firebaseModel.addImageToGallery(imgItem);
    }

    public void logout() {
        firebaseModel.logout();
    }

    public String getUserId() {
        return firebaseModel.getUserId();
    }

    public String getDate() {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = null;
        date = dateFormatGmt.format(new Date()).toString();
        return date;
    }

    public interface GetHotelsListener {
        public void onResult(List<Hotel> hotels);

        public void onCancel();
    }

    public interface GetMealsListener {
        public void onResult(List<Meal> meals);

        public void onCancel();
    }

    public interface GetImagesListener {
        public void onResult(ArrayList<ImageItem> imagesList);

        public void onCancel();
    }

    public interface GetHotel {
        public void onResult(Hotel hotel);

        public void onCancel();
    }

    public interface GetUser {
        public void onResult(User user);

        public void onCancel();
    }

    public interface GetVacation {
        public void onResult(Vacation vacation);

        public void onCancel();
    }

    public interface GetWifi {
        public void onResult(String wifi);

        public void onCancel();
    }

    public interface GetInfoListener {
        public void onResult(Info info);

        public void onCancel();
    }

    public interface GetStringListener {
        public void onResult(String string);

        public void onCancel();
    }

    public interface GetEvent {
        public void onResult(HotelEvent event);

        public void onCancel();
    }

    public interface SignupListener {
        public void success();

        public void fail(String msg);
    }

    public interface ResetPasswordListener {
        public void success();

        public void fail(String msg);
    }

    public interface LoginListener {
        public void success(String uid);

        public void fail(String msg);
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ModelFirebase getFirebaseModel() {
        return firebaseModel;
    }

    public void setFirebaseModel(ModelFirebase firebaseModel) {
        this.firebaseModel = firebaseModel;
    }

    public ModelSql getModelSql() {
        return modelSql;
    }

    public void setModelSql(ModelSql modelSql) {
        this.modelSql = modelSql;
    }


}



