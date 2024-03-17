package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.CallableStatement;
import model.Setting;

public class ConnectDB {
    private static final String FILE_PATH = "document/sever.txt";
    private static final XFile XF = new XFile();
    private static final Setting STG = new Setting();
    private static String SERVER;
    private static String DATABASE;
    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456789";

    static {
        initialize(); // Initialize static variables
    }

    private static void initialize() {
        SERVER = docSeverTuFile();
        DATABASE = docDatabaseTuFile();
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDburl() {
        return "jdbc:sqlserver://" + SERVER + ";databaseName=" + DATABASE + ";encrypt=true;trustServerCertificate=true;";
    }

    public static boolean kiemTraFile(String fileName) {
        // Sử dụng ClassLoader để tải tệp từ resource
        InputStream inputStream = ConnectDB.class.getClassLoader().getResourceAsStream(fileName);

        // Kiểm tra xem InputStream có tồn tại không
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                // Đọc nội dung tệp từ InputStream
                String line;
                while ((line = reader.readLine()) != null) {
                    // Kiểm tra tệp có kích thước lớn hơn 0 không
                    if (line.length() > 0) {
                        return true;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi đọc tệp từ resource.", e);
            }
        }
        // Xử lý trường hợp không tìm thấy tệp trong resource
        return false;
    }

    private static String docSeverTuFile() {
        if (kiemTraFile(FILE_PATH)) {
            InputStream inputStream = ConnectDB.class.getClassLoader().getResourceAsStream(FILE_PATH);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("Sever")) {
                        String[] parts = line.split(":", 2);
                        if (parts.length == 2) {
                            String key = parts[0].trim();
                            String value = parts[1].trim();
                            if ("Sever".equals(key)) {
                                STG.setSever(value);
                                return STG.getSever();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi đọc tệp từ resource.", e);
            }
        }
        return STG.getSever();
    }

    private static String docDatabaseTuFile() {
        if (kiemTraFile(FILE_PATH)) {
            InputStream inputStream = ConnectDB.class.getClassLoader().getResourceAsStream(FILE_PATH);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        if ("Database".equals(key)) {
                            STG.setDatabase(value);
                            return STG.getDatabase();
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("Lỗi khi đọc tệp từ resource.", e);
            }
        }
        return STG.getDatabase();
    }


    public static PreparedStatement preparedStatement(String sql, Object... args) throws SQLException {
        java.sql.Connection conn = DriverManager.getConnection(getDburl(), USERNAME, PASSWORD);
        PreparedStatement pstmt;
        if (sql.trim().startsWith("{")) {
            pstmt = conn.prepareCall(sql); // proc
        } else {
            pstmt = conn.prepareStatement(sql); // SQL
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

    public static ResultSet executeQuery(String sql, Object... args) {
        try {
            PreparedStatement pstmt = preparedStatement(sql, args);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void executeUpdate(String sql, Object... args) {
        try {
            try (PreparedStatement pstmt = preparedStatement(sql, args)) {
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static java.sql.Connection getConnection() {
        try {
            return DriverManager.getConnection(getDburl(), USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static CallableStatement prepareCall(String sql, Object... args) throws SQLException {
        java.sql.Connection conn = DriverManager.getConnection(getDburl(), USERNAME, PASSWORD);
        CallableStatement cstmt = conn.prepareCall(sql);
        for (int i = 0; i < args.length; i++) {
            cstmt.setObject(i + 1, args[i]);
        }
        return cstmt;
    }

    public static ResultSet executeQuery(CallableStatement cstmt) {
        try {
            cstmt.execute();
            return cstmt.getResultSet();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
