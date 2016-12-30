package com.koby.EazyVacation.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class is responsible for getting the time from editText .
 */
public class TimeEditText extends EditText {

    int tHour;
    int tMin;
    boolean hourType;
    OnTimeSetListener onTimeSetListener;
    String str;

    public interface OnTimeSetListener{
        public void  timeSet(int tHour, int tMin, boolean hourType);
    }

    public void init(){

        Calendar calendar = new GregorianCalendar();
        tHour = calendar.get(Calendar.HOUR_OF_DAY); //always 24 HOUR type
        tMin = calendar.get(Calendar.MINUTE);
        if(tHour<10)
             setText("0" + tHour + ":" + tMin );
        else
        {
            setText("" + tHour + ":" + tMin );
        }

    }


    public TimeEditText(Context context) {
        super(context);
        init();
    }

    public TimeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void OnTimeSetListener(OnTimeSetListener listener) {
        this.onTimeSetListener = listener;
    }

    public void setOnTimeSetListener(OnTimeSetListener listener) {
        this.onTimeSetListener = listener;
    }


    public int gettHour() {
        return tHour;
    }

    public void settHour(int tHour) {
        this.tHour = tHour;
    }

    public int gettMin() {
        return tMin;
    }

    public void settMin(int tMin) {
        this.tMin = tMin;
    }

    public boolean isHourType() {
        return hourType;
    }

    public void setHourType(boolean hourType) {
        this.hourType = hourType;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            InnerTimePicker timePicker = new InnerTimePicker();
            timePicker.init(tHour,tMin,true);
            timePicker.setListener(new InnerTimePicker.Listener() {
                @Override
                public void done(int hour, int minute, boolean is24Hour) {
                    TimeEditText.this.tHour = hour;
                    TimeEditText.this.tMin = minute;

                    if (tMin < 10) {
                        str ="0" +tMin ;
                    }


                    else
                    {
                        str = "" + tMin;
                    }
                    TimeEditText.this.hourType = checkHourType(hour);
                    if (tHour < 10)
                        setText("0" + tHour + ":" + str);
                    else {
                        setText("" + tHour + ":" + str);
                    }
                    if (onTimeSetListener != null) {
                        onTimeSetListener.timeSet(hour, minute, hourType);
                    }
                }
            });
            timePicker.show(((Activity) getContext()).getFragmentManager(), "TimeChanged");
        }
        return super.onTouchEvent(event);
    }

    public boolean checkHourType(int hour){
        if( (hour >= 00) && (hour <=12) ){
            return false; //AM
        }
        else{
            return true; //PM
        }
    }

    public static class InnerTimePicker extends DialogFragment {
        int hour;
        int min;
        boolean is24HourView;

        public void init(int hour,int minute,boolean is24HourView) {
            this.hour = hour;
            this.min = minute;
            this.is24HourView = is24HourView;
        }

        interface Listener{
            public void done(int hour, int minute, boolean is24HourView);
        }

        Listener listener;

        public void setListener(Listener listener){
            this.listener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            TimePickerDialog dialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    hour = hourOfDay;
                    min = minute;
                    is24HourView = checkHourType(hour);
                    listener.done(hour,min,is24HourView);
                }
            },hour,min,is24HourView);
            return dialog;
        }

        public boolean checkHourType(int hour){
            if( (hour >= 00) && (hour <=12) ){
                return true;
            }
            else{
                return false;
            }
        }
    }
}
