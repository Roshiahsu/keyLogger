package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringDateToDate {
    public static void main(String[] args) {
        String year = "2024";
        String month = "09";
        String day = "11";
        String hour = "15";
        String min = "30";
        String sec = "45";


        String dateString = String.format("%s-%s-%sT%s:%s:%s", year, month, day, hour, min, sec);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            Date date = formatter.parse(dateString);
            System.out.println("Date object: " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
