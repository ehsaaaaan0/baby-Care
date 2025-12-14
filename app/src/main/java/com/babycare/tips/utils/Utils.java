package com.babycare.tips.utils;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.WindowManager;

import com.babycare.tips.R;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Base64;

import java.util.Calendar;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

//BY RECTIFI
//Dated: 17 aug 2022
public class Utils {


    public static String convertDate(String dateInMilliseconds) {
        String dateFormat = "dd/MM/yyyy";
        return DateFormat.format(dateFormat, Long.parseLong(dateInMilliseconds)).toString();
    }

    public static Bitmap base64ToBitmap(String encodedString) {
        byte[] decodedString = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            decodedString = Base64.getDecoder().decode(encodedString);
        }
        Bitmap bitmap= BitmapFactory.decodeByteArray(decodedString , 0,
                decodedString.length);
        return bitmap;
    }

    public static String encodeBitmapTobase64(Bitmap image) {
        if (image.getRowBytes()*image.getHeight()>1048487){
            Log.d(TAG, "encodeBitmapTobase64: "+image.getRowBytes()*image.getHeight());
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 50, os);
        Log.d(TAG, "encodeBitmapTo: "+image.getRowBytes()*image.getHeight());
        byte[] byteArray = os.toByteArray();
        String encodedImageString =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            encodedImageString = Base64.getEncoder().encodeToString(byteArray);
        }
        return encodedImageString ;
    }
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        NetworkCapabilities nc = connMgr.getNetworkCapabilities(connMgr.getActiveNetwork());
                        int downSpeed = (nc.getLinkDownstreamBandwidthKbps());
                        int upSpeed = (nc.getLinkUpstreamBandwidthKbps());
                        if (downSpeed>10&&upSpeed>10){
                            return true;
                        }
                    }
                } else {
                    if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                        //connected to mobileData
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            NetworkCapabilities nc = connMgr.getNetworkCapabilities(connMgr.getActiveNetwork());
                            int downSpeed = (nc.getLinkDownstreamBandwidthKbps());
                            int upSpeed = (nc.getLinkUpstreamBandwidthKbps());
                            if (downSpeed>10&&upSpeed>10){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void disableInteraction(boolean isEnabled, Activity activity){
        if (isEnabled){
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }else {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public static String compress(String data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data.getBytes());
        gzip.close();
        bos.close();
        return bos.toString();
    }
    public static String decompress(String compressed) throws IOException {
        byte[] bytes = compressed.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        GZIPInputStream gis = new GZIPInputStream(bis);
        BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        gis.close();
        bis.close();
        return sb.toString();
    }

    public static int[] getAge(String dob) {
        String[] date = dob.split("/");
        int dayOfMonth = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        int ageDay = 0;
        int ageMonths = 0;
        int ageYears = 0;
        int[] age = new int[3];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ageDay = Period.between(
                    LocalDate.of(year, month, dayOfMonth),
                    LocalDate.now()
            ).getDays();
            ageMonths = Period.between(
                    LocalDate.of(year, month, dayOfMonth),
                    LocalDate.now()
            ).getMonths();
            ageYears = Period.between(
                    LocalDate.of(year, month, dayOfMonth),
                    LocalDate.now()
            ).getYears();
        }
        age[0] = ageDay;
        age[1] = ageMonths;
        age[2] = ageYears;
        return age;
    }

    public static ArrayList<String> readTxtFile(String file, Context context){
        ArrayList<String> RowData = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.getAssets().open(file)));
            String line;
            Log.e("Reader Stuff",reader.readLine());
            while ((line = reader.readLine()) != null) {
                Log.e("code",line);
                RowData.add(line);
                Log.d(TAG, "readTxtFile1: "+RowData.get(0));
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return RowData;
    }
    public static String getDate(long milliSeconds) {
        String dateFormat = "dd/MM/yyyy";
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
