package util;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.util.Date;
import model.BD_ThongKeSanPham;
import model.ThongKeSanPham;

public class ThongKeSanPhamDAO {

    private final String EXE_TKSP = "{call ThongKeSanPham (?, ?)}";
    private final String EXE_BD_TKSP = "{call BD_ThongKeSanPham (?, ?, ?)}";
    private final String COUNT_TSSP = "SELECT COUNT(*) FROM SanPham";
    private final String COUNT_CTSP = "SELECT COUNT(*) FROM ChiTietSanPham";
    private final String COUNT_SPDB = "SELECT Sum(SoLuong) FROM HoaDonChiTiet";
    private final String COUNT_SPTK = "SELECT Sum(SoLuong) FROM ChiTietSanPham";
    private final String COUNT_SPHH = "SELECT COUNT(*) FROM ChiTietSanPham where SoLuong = 0";
    private final String COUNT_SPSH = "SELECT COUNT(*) FROM ChiTietSanPham where SoLuong <= 10";

    public int countTongSoSanPham() {
        return executeCountQuery(COUNT_TSSP);
    }
    
    public int countTongSoChiTiet() {
        return executeCountQuery(COUNT_CTSP);
    }

    public int countSoLuongSanPhamDaBan() {
        return executeCountQuery(COUNT_SPDB);
    }

    public int countSoLuongSanPhamTrongKho() {
        return executeCountQuery(COUNT_SPTK);
    }

    public int countSoLuongSanPhamHetHang() {
        return executeCountQuery(COUNT_SPHH);
    }

    public int countSoLuongSanPhamSapHetHang() {
        return executeCountQuery(COUNT_SPSH);
    }

    public List<ThongKeSanPham> execThongKeSanPham(String loaiHang, String hinhThucThongKe) {
        return selectBySQL(EXE_TKSP, loaiHang, hinhThucThongKe);
    }

    public List<BD_ThongKeSanPham> execBD_ThongKeSanPham(Date batDau, Date ketThuc, String hinhThucThongKe) {
        return selectSQL(EXE_BD_TKSP, batDau, ketThuc, hinhThucThongKe);
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

    public List<ThongKeSanPham> selectBySQL(String sql, Object... args) {
        List<ThongKeSanPham> list = new ArrayList<>();
        try {
            try (CallableStatement cstmt = ConnectDB.prepareCall(sql, args)) {
                for (int i = 0; i < args.length; i++) {
                    cstmt.setObject(i + 1, args[i]);
                }
                cstmt.execute();
                try (ResultSet rs = cstmt.getResultSet()) {
                    while (rs.next()) {
                        ThongKeSanPham tksp = new ThongKeSanPham();
                        tksp.setLoaiHang(rs.getString("Tên loại hàng")); // Đổi thành tên thực tế của cột
                        tksp.setTenSP(rs.getString("Tên sản phẩm")); // Đổi thành tên thực tế của cột
                        tksp.setTonKho(rs.getInt("Số lượng trong kho")); // Đổi thành tên thực tế của cột
                        tksp.setDaBan(rs.getInt("Số lượng đã bán")); // Đổi thành tên thực tế của cột
                        tksp.setDoanhThu(rs.getDouble("Doanh thu")); // Đổi thành tên thực tế của cột
                        list.add(tksp);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return list;
    }
    
        public List<BD_ThongKeSanPham> selectSQL(String sql, Object... args) {
        List<BD_ThongKeSanPham> list = new ArrayList<>();
        try {
            try (CallableStatement cstmt = ConnectDB.prepareCall(sql, args)) {
                for (int i = 0; i < args.length; i++) {
                    cstmt.setObject(i + 1, args[i]);
                }
                cstmt.execute();
                try (ResultSet rs = cstmt.getResultSet()) {
                    while (rs.next()) {
                        BD_ThongKeSanPham tksp = new BD_ThongKeSanPham();
                        tksp.setTenSP(rs.getString("TenSP")); // Đổi thành tên thực tế của cột
                        tksp.setSoLuong(rs.getInt("SoLuong")); // Đổi thành tên thực tế của cột
                        tksp.setHinhAnh(rs.getString("HinhAnh")); // Đổi thành tên thực tế của cột
                        list.add(tksp);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
