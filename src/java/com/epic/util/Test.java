 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tharaka
 */
public class Test {
    
    public static String formatToTwoDecimalPlaces(String in) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(Double.parseDouble(in));
    }

    public static void main(String[] arg) throws ParseException, Exception {
        
//        try {
//            Util.userADValidation("lk03337", "qwe@123");
//        } catch (Exception ex) {
//           ex.printStackTrace();
//        }
        
//       String test = "Please use Ord #: 4135131& Secret Code 3551(valid 12hrs) Amount Rs 300.0 on Sep 13, 2016 11:45:17 - Com Bank";
       String test = "Please send Recipient +94767804455 Ord #:1015708(valid 12hrs) Amount Rs 100.0. Did not perform? Call +94112353353 Sep 13, 2016 11:10:00 - Com Bank";
//       String test = "Received fund transfer from +94767804455 Secret Code: 1091(valid 12hrs) Sep 13, 2016 11:10:00 - Com Bank";
//       String refNum = test.substring(test.indexOf(":")+1, test.indexOf("&")).trim();
//       String accNum = test.substring(test.lastIndexOf("Code")+4, test.indexOf("(")).trim();
//       String mackRef = ""; 
//       String mackAcc = ""; 
//       for(int i=0;i<refNum.length();i++){
//           mackRef += "*";
//       }
//       for(int i=0;i<accNum.length();i++){
//           mackAcc += "*";
//       }
//       
//       test = test.replace(refNum, mackRef);
//       test = test.replace(accNum, mackAcc);
//        System.out.println("ref "+refNum);
//        System.out.println("acc "+accNum);
//        System.out.println(test);
//       test= Util.maskSmsDescription(test);
        System.out.println(formatToTwoDecimalPlaces("10011"));
    }

}
