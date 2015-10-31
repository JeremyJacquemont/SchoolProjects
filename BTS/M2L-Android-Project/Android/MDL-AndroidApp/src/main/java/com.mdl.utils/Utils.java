package com.mdl.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mdl.androidapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Utils class
 */

public class Utils {

    /**
     * Return a format date in string format
     * @param timestamp
     * @param time
     * @return {String} date
     */
    public static String formatDate(long timestamp, boolean time){
        Date date = new Date(timestamp*1000);
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+1"));
        SimpleDateFormat sdf;
        if(time){
            sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        }else{
            sdf = new SimpleDateFormat("dd-MM-yyyy");
        }
        sdf.setCalendar(calendar);
        calendar.setTime(date);
        return sdf.format(date);
    }

    /**
     * Underline a text
     * @param elem
     */
    public static void underLineText(TextView elem){
        SpannableString content = new SpannableString(elem.getText());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        elem.setText(content);
    }

    /**
     * Show a new DateTimeDialog
     * @param button
     * @param context
     * @param date
     */
    public static void createDateTimeDialog(final Button button, Context context, Date date){
        final LayoutInflater factory = LayoutInflater.from(context);
        final View dateTimeDialogView = factory.inflate(
                R.layout.date_time_picker_dialog, null);
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+1"));
        calendar.setTime(date);
        final TimePicker tp = (TimePicker) dateTimeDialogView.findViewById(R.id.t_dialog);
        tp.setIs24HourView(true);
        tp.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        tp.setCurrentMinute(calendar.get(Calendar.MINUTE));
        final DatePicker dp = (DatePicker) dateTimeDialogView.findViewById(R.id.dt_dialog);
        dp.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);

        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        final AlertDialog dateTimeDialog = new AlertDialog.Builder(context).create();
        dateTimeDialog.setContentView(R.layout.date_time_picker_dialog);
        adb.setView(dateTimeDialogView);
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth(), tp.getCurrentHour(), tp.getCurrentMinute());
                button.setText(Utils.formatDate(new Timestamp(calendar.getTime().getTime()/1000).getTime(), true));
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        adb.show();
    }

    /**
     * Return date in date format with a string
     * @param dateInString
     * @return {Date} date
     */
    public static Date convertToDateWithString(String dateInString){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT+1"));
        formatter.setCalendar(calendar);
        Date date = new Date();
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Return date in date format with a string without timestamp
     * @param dateInString
     * @return {Date} date
     */
    public static Date convertToDateWithStrinWithoutTimezone(String dateInString){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        formatter.setCalendar(calendar);
        Date date = new Date();
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
