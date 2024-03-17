/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.IOException;
import java.net.URL;
import java.awt.print.PrinterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

public class PrintUtility {

    public static void printFileFromPath(String filePath) {
        try {
            // Tạo một URL từ đường dẫn cung cấp
            URL url = new URL("file:///" + filePath);

            // Lấy tất cả các máy in hiện có
            PrintService[] printServices = javax.print.PrintServiceLookup.lookupPrintServices(null, null);

            if (printServices.length > 0) {
                // Lựa chọn máy in đầu tiên
                PrintService printService = printServices[0];

                // Tạo một công việc in
                DocPrintJob printJob = printService.createPrintJob();

                // Xác định loại dữ liệu của tài liệu
                DocFlavor flavor = DocFlavor.URL.AUTOSENSE;

                // Tạo một đối tượng Doc từ URL
                Doc doc = new SimpleDoc(url, flavor, null);

                // Thiết lập các thuộc tính của công việc in
                PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

                try {
                    // In tài liệu
                    printJob.print(doc, attributeSet);
                } catch (PrintException ex) {
                    Logger.getLogger(PrintUtility.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("No printers available.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
