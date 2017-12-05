/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.util;

import com.epic.init.InitConfigValue;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.GregorianCalendar;


public class LogFileCreator {

    private static String path = null;



    public static void writeInfoToLog(String msg) throws Exception {
        String errorpath = InitConfigValue.LOGPATH + "infors";

        BufferedWriter bw = null;
        String newLine = "";
        msg = newLine + "\n" + getTime() + "\n" + msg;

        try {
            String osinfopath = Util.getOSLogPath(errorpath);
            String filename = osinfopath + Util.getLocalDate() + "_EpicCLA_Infor";
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();

        } catch (Exception ioe) {
            ioe.printStackTrace();
            throw ioe;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                }
            }
        }
    }


    public static void writeErrorToLog(Throwable aThrowable) {

        String infopath = InitConfigValue.LOGPATH + "errors";

        BufferedWriter bw = null;
        String msg = "";
        String newLine = "";

        try {
            String osinfopath = Util.getOSLogPath(infopath);
            String filename = osinfopath + Util.getLocalDate() + "_EpicCLA_Error";

            msg = newLine + "\n" + getTime() + "\n" + getStackTrace(aThrowable);
            bw = new BufferedWriter(new FileWriter(filename, true));
            bw.write(msg);
            bw.newLine();
            bw.flush();
        } catch (Exception ioe) {
            ioe.printStackTrace();
            //throw ioe;
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    //throw ioe2;
                }
            }
        }
    }


    private static String getTime() {
        GregorianCalendar now = new GregorianCalendar();
        return now.getTime().toString();
    }


    private static String getStackTrace(Throwable aThrowable) throws Exception {
        String re = "";
        Writer result = null;
        PrintWriter printWriter = null;
        try {
            result = new StringWriter();
            printWriter = new PrintWriter(result);

            aThrowable.printStackTrace(printWriter);
            re = result.toString();
            result.close();
            printWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {

            try {
                if (result != null) {
                    result.close();
                }
                if (printWriter != null) {
                    printWriter.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return re;
    }
}
