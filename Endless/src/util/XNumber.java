/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class XNumber {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###");

    public static String formatDecimal(double number) {
        return decimalFormat.format(number);
    }

    public static String formatInt(int number) {
        return decimalFormat.format(number);
    }

    public static double parseDecimal(String formattedNumber) {
        try {
            return decimalFormat.parse(formattedNumber).doubleValue();
        } catch (ParseException ex) {
            Logger.getLogger(XNumber.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public static int parseInt(String formattedNumber) {
        try {
            return decimalFormat.parse(formattedNumber).intValue();
        } catch (ParseException ex) {
            Logger.getLogger(XNumber.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
