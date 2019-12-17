package com.navan.app.alunouesb.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by pedro on 22/05/2017.
 */

public class DateUtils {

    private static final Locale locale = new Locale("pt", "BR");
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy", locale);

    public static String getHoraFromDate(Long date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String formatDateExtenso(Long dateLong) {
        Date date = new Date(dateLong);

        if (date == null) {
            return null;
        }

        String data = df.format(date);
        int mes = Integer.parseInt(data.substring(3, 5));

        if (mes == 1) {
            data = df.format(date).substring(0, 2) + " de jan";
        } else if (mes == 2) {
            data = df.format(date).substring(0, 2) + " de fev";
        } else if (mes == 3) {
            data = df.format(date).substring(0, 2) + " de mar";
        } else if (mes == 4) {
            data = df.format(date).substring(0, 2) + " de abr";
        } else if (mes == 5) {
            data = df.format(date).substring(0, 2) + " de mai";
        } else if (mes == 6) {
            data = df.format(date).substring(0, 2) + " de jun";
        } else if (mes == 7) {
            data = df.format(date).substring(0, 2) + " de jul";
        } else if (mes == 8) {
            data = df.format(date).substring(0, 2) + " de ago";
        } else if (mes == 9) {
            data = df.format(date).substring(0, 2) + " de set";
        } else if (mes == 10) {
            data = df.format(date).substring(0, 2) + " de out";
        } else if (mes == 11) {
            data = df.format(date).substring(0, 2) + " de nov";
        } else {
            data = df.format(date).substring(0, 2) + " de dez";
        }

        if (date.getYear() == new Date().getYear())
            return data;
        else
            return data + " de " + date.getYear();

    }

    public static int getMinutosPassado(Long data) {
        if (data > System.currentTimeMillis())
            return 0;

        return (int) (((System.currentTimeMillis() - data) / 1000) / 60);
    }
}
