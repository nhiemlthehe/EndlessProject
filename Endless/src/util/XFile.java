package util;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class XFile {

    private static final String FILE_PATH;

    static {
        URL url = XFile.class.getClassLoader().getResource("/document/");
        FILE_PATH = (url != null) ? url.getPath() : null;
    }

    // Hàm lưu ký tự vào file
    public static void saveToFile(String fileName, String content) {
        if (FILE_PATH != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH + fileName, StandardCharsets.UTF_8))) {
                writer.write(content);
            } catch (IOException e) {
                System.err.println("Lỗi khi lưu vào file: " + e.getMessage());
            }
        } else {
            System.err.println("Lỗi: Thư mục 'document/' không tìm thấy trong resource.");
        }
    }

    // Hàm đọc ký tự từ file
    public static String readFromFile(String fileName) {
        StringBuilder content = new StringBuilder();
        if (FILE_PATH != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH + fileName, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                System.err.println("Lỗi khi đọc từ file: " + e.getMessage());
            }
        } else {
            System.err.println("Lỗi: Thư mục 'document/' không tìm thấy trong resource.");
        }
        return content.toString();
    }
}
