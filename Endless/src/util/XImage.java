/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author VoThiThaoNguyen
 */
public class XImage {

    public static ImageIcon getAppIcon() {
        URL url = XImage.class.getResource("/image/");
        return new ImageIcon(url);
    }

    public static void save(String parent, File src) {
        File dir = new File(parent, src.getName());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            Path source = Paths.get(src.getAbsolutePath());
            Path destination = Paths.get(dir.getAbsolutePath());
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static ImageIcon read(String parent, String filename) {
        File path = new File(parent, filename);
        return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT));
    }

    public static ImageIcon readPerson(String parent, String filename) {
        File path = new File(parent, filename);
        return new ImageIcon(new ImageIcon(path.getAbsolutePath()).getImage());
    }

    public static BufferedImage processImage(String imagePath) {
        try {
            // Read the original image
            BufferedImage originalImage = ImageIO.read(new File(imagePath));

            if (originalImage == null) {
                // Log an error message
                System.err.println("Failed to read the original image: " + imagePath);
                return null;
            }

            // Resize the image to 200x200 with better quality
            int width = 200;
            int height = 200;
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Create a buffered image with an alpha channel
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // Get the graphics context of the buffered image
            Graphics2D g2d = bufferedImage.createGraphics();

            // Set rendering hints for better quality
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw the resized image onto the buffered image
            g2d.drawImage(resizedImage, 0, 0, null);

            // Dispose of the graphics context
            g2d.dispose();

            return bufferedImage;
        } catch (IOException e) {
            // Log the exception
            e.printStackTrace();
            return null;
        }
    }
}
