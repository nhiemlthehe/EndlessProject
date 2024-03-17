package util;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.ThongKeDoanhThu;

public class ThongKeDoanhThuDAO {

    private final String COUNT_TONGDOANHTHU = "SELECT SUM(TongTien) FROM HoaDonChiTiet A INNER JOIN HoaDon B ON A.MaHD = B.MaHD";
    private final String COUNT_DOANHTHUNAMNAY = "SELECT SUM(TongTien) FROM HoaDonChiTiet A INNER JOIN HoaDon B ON A.MaHD = B.MaHD WHERE YEAR(NgayBan) = YEAR(GETDATE())";
    private final String COUNT_DOANHTHUTHANGNAY = "SELECT SUM(TongTien) FROM HoaDonChiTiet A INNER JOIN HoaDon B ON A.MaHD = B.MaHD WHERE YEAR(NgayBan) = YEAR(GETDATE()) AND MONTH(NgayBan) = MONTH(GETDATE())";
    private final String COUNT_DOANHTHU7NGAYGANNHAT = "SELECT SUM(TongTien) FROM HoaDonChiTiet A INNER JOIN HoaDon B ON A.MaHD = B.MaHD WHERE B.NgayBan BETWEEN DATEADD(DAY, -7, GETDATE()) AND GETDATE()";
    private final String COUNT_DOANHTHUHOMNAY = "SELECT SUM(TongTien) FROM HoaDonChiTiet A INNER JOIN HoaDon B ON A.MaHD = B.MaHD WHERE CONVERT(DATE, NgayBan) = CONVERT(DATE, GETDATE())";
    private final String EXE_TKDT = "{call ThongKeDoanhThu (?, ?, ?)}";

    public List<ThongKeDoanhThu> execThongKeDoanhThu(String ngayBatDau, String ngayKetThuc, String cheDoXem) {
        List<ThongKeDoanhThu> list = new ArrayList<>();
        try {
            try (CallableStatement cstmt = ConnectDB.prepareCall(EXE_TKDT, ngayBatDau, ngayKetThuc, cheDoXem)) {
                cstmt.execute();
                try (ResultSet rs = cstmt.getResultSet()) {
                    while (rs.next()) {
                        ThongKeDoanhThu tkdt = new ThongKeDoanhThu();
                        tkdt.setThoiGian(rs.getString("ThoiGian")); // Đổi thành tên thực tế của cột
                        tkdt.setTongDoanhThu(rs.getDouble("TongDoanhThu")); // Đổi thành tên thực tế của cột
                        tkdt.setDoanhThuCaoNhat(rs.getDouble("DoanhThuCaoNhat")); // Đổi thành tên thực tế của cột
                        tkdt.setDoanhThuThapNhat(rs.getDouble("DoanhThuThapNhat")); // Đổi thành tên thực tế của cột
                        tkdt.setDoanhThuTrungBinh(rs.getDouble("DoanhThuTrungBinh")); // Đổi thành tên thực tế của cột
                        list.add(tkdt);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private int executeCountQuery(String sql) {
        int result = 0;
        try {
            try (PreparedStatement pstmt = ConnectDB.preparedStatement(sql); ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public int countTongDoanhThu() {
        return executeCountQuery(COUNT_TONGDOANHTHU);
    }

    public int countDoanhThuNamNay() {
        return executeCountQuery(COUNT_DOANHTHUNAMNAY);
    }

    public int countDoanhThuThangNay() {
        return executeCountQuery(COUNT_DOANHTHUTHANGNAY);
    }

    public int countDoanhThu7NgayGanNhat() {
        return executeCountQuery(COUNT_DOANHTHU7NGAYGANNHAT);
    }

    public int countDoanhThuHomNay() {
        return executeCountQuery(COUNT_DOANHTHUHOMNAY);
    }

    // Hàm util để lấy ngày hiện tại trong định dạng SQL Server
    private String getCurrentDateForSqlServer() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDate.format(formatter);
    }

}
