/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class TrangChuDAO {

    private final String COUNT_HOA_DON = "SELECT COUNT(*) FROM HoaDon WHERE CONVERT(VARCHAR, NgayBan, 23) = CONVERT(VARCHAR, GETDATE(), 23)";
    private final String SUM_TONG_TIEN = "SELECT ISNULL(SUM(TongTien), 0) FROM HoaDon WHERE CONVERT(VARCHAR, NgayBan, 23) = CONVERT(VARCHAR, GETDATE(), 23)";
    private final String SUM_SO_LUONG = "SELECT ISNULL(SUM(b.SoLuong), 0) "
            + "FROM HoaDon a INNER JOIN HoaDonChiTiet b ON a.MaHD = b.MaHD "
            + "WHERE CONVERT(VARCHAR, NgayBan, 23) = CONVERT(VARCHAR, GETDATE(), 23)";
    private final String COUNT_NHAP_HANG = "SELECT COUNT(*) FROM NhapHang WHERE CONVERT(VARCHAR, NgayNhap, 23) = CONVERT(VARCHAR, GETDATE(), 23)";

    public int countHoaDon() {
        return executeCountQuery(COUNT_HOA_DON);
    }

    public double sumTongTien() {
        return executeSumQuery(SUM_TONG_TIEN);
    }

    public int sumSoLuong() {
        return executeCountQuery(SUM_SO_LUONG);
    }

    public int countNhapHang() {
        return executeCountQuery(COUNT_NHAP_HANG);
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

    private double executeSumQuery(String sql) {
        double result = 0;
        try (Connection connection = ConnectDB.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                result = rs.getDouble(1);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error executing sum query", e);
        }
        return result;
    }
}
