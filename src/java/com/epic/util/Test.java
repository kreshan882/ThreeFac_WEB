 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.util;

import com.epic.init.InitConfigValue;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author tharaka
 */
public class Test {
    


    public static void main(String[] args) {
	try{	
                // qrCodeText = "77FC9B8EE567AC5842C5FE9E5BDDA0F8DA77E8E1F6EFE16C1D2E382DC09E9E99E5A3F4D64B83015CDDA35D2AF6FCFC094658793DC3EF60B90A00D22E8E62BDCC2B9DEEE53169F3AC978942026E3500746B57C5AE89A72F8A0713260237B4FEA0B97F31BCA8725D94E7C5DEDDDEDE998D1B19ED5387B03B27F95E34684F5EB9FA76C22D5F153F1AE8A8852190F077A603E2F0DF68B7AA857FDBE7D9C422458447D0CAE104F84951E1F2FEBBB5756975113B8436091D40C69AB090DB3308C262EEE2E37139D627F4889DBE5052C9FE688B78F462EC7668AE11194BD711C534B7D125DBAC17E1C60B0B9BFA26CB7D5DD3D5DB617C1BA7083E494B897DA14F1B108C";
		String qrCodeText = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
                String filePath = InitConfigValue.QR_IMG_PATH+"863163033112758"+".png";
                
		QRHelper.createQRImage(new File(filePath), qrCodeText, 200, "png");
		System.out.println("DONE");
        }catch(Exception e){
            e.printStackTrace();
        }
	}

	

}