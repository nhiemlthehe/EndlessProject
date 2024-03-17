/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.HoaDon;

/**
 *
 * @author 12345
 */
public class HoaDonDAO extends EndlessDAO<HoaDon, String> {

    String INSERT_SQL = "INSERT INTO HoaDon(MaHD, MaKH, MaNV, NgayBan, TongTien, HTThanhToan, TienThanhToan) VALUES(?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE HoaDon SET MaKH=?, MaNV=?, NgayBan=?, TongTien=?, HTThanhToan=?, TienThanhToan=? WHERE MaHD=?";
    String DELETE_SQL = "DELETE FROM HoaDon WHERE MaHD=?";

    String SELECT_BY_KH = "select MaHD, b.TenNV, NgayBan, TongTien from HoaDon a inner Join NhanVien  b on a.MaNV = b.MaNV where MaKH = ?";
    String SELECT_ALL_SQL = "Select * from HoaDon";
    String SELECT_DESC = "Select * from hoadon order by maHD desc";
    String SELECT_BY_ID_SQL = "SELECT * FROM HoaDon WHERE MaHD=?";
    String SELECT_BY_NAME_SQL = "select MaHD, b.TenKH, c.TenNV, NgayBan, HTThanhToan, TongTien, TienThanhToan from HoaDon a inner join KhachHang b on a.MaKH = b.MaKH inner join NhanVien c on a.MaNV = c.MaNV";
    String SELECT_BY_TENKH = "SELECT * FROM hoadon WHERE makh IN (SELECT makh FROM khachhang WHERE tenkh LIKE ?)";
    String SELECT_BY_MAKH = "SELECT * FROM hoadon WHERE makh LIKE ?";
    
    public List<HoaDon> selectByMaKH(String maKH) {
        List<HoaDon> list = this.selectBySQL(SELECT_BY_MAKH, maKH);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }
    
    @Override
    public HoaDon selectById(String id) {
        List<HoaDon> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<HoaDon> selectByKH(String TenKH) {
        List<HoaDon> list = this.selectBySQL(SELECT_BY_TENKH, "%" + TenKH + "%");
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public List<HoaDon> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    public List<HoaDon> selectDESC() {
        return this.selectBySQL(SELECT_DESC);
    }

    @Override
    protected List<HoaDon> selectBySQL(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("MaHD"));
                hd.setMaKH(rs.getString("MaKH"));
                hd.setMaNV(rs.getString("MaNV"));
                hd.setNgayBan(rs.getDate("NgayBan"));
                hd.setTongTien(rs.getFloat("TongTien"));
                hd.setHTThanhToan(rs.getString("HTThanhToan"));
                hd.setTienThanhToan(rs.getFloat("TienThanhToan"));
                list.add(hd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<HoaDon> selectByCutomCode(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getString("MaHD"));
                hd.setMaKH(rs.getString("MaKH"));
                hd.setMaNV(rs.getString("MaNV"));
                hd.setNgayBan(rs.getDate("NgayBan"));
                hd.setTongTien(rs.getFloat("TongTien"));
                hd.setHTThanhToan(rs.getString("HTThanhToan"));
                hd.setTienThanhToan(rs.getFloat("TienThanhToan"));
                list.add(hd);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<HoaDon> selectByKeyword(String keyword) {
        String SQL = "SELECT * FROM KhachHang WHERE TenKH LIKE ?";
        return this.selectBySQL(SQL, "%" + keyword + "%");
    }

    public List<HoaDon> customSelect(Date ngayBatDau, Date ngayKetThuc, double soTienNhapNhat, double soTienCaoNhat) {
        String SQL = "Declare @ngayNhoNhat date = (select top 1 ngayban from hoaDon order by ngayban)\n"
                + "Declare @ngayLonNhat date = (select top 1 ngayban from hoaDon order by ngayban desc)\n"
                + "Declare @tienItNhat float = (select top 1 TongTien from hoadon order by TongTien)\n"
                + "Declare @tienNhieuNhat float = (select top 1 TongTien from hoadon order by TongTien DESC)\n"
                + "SELECT * FROM HOADON WHERE NgayBan >= isnull((?), @ngayNhoNhat) AND NgayBan <= isnull((?), @ngayLonNhat)"
                + " AND TongTien >= isnull(?,  @tienItNhat) AND TongTien <= isnull(?,  @tienNhieuNhat)";
        return this.selectBySQL(SQL, ngayBatDau, ngayKetThuc, soTienNhapNhat, soTienCaoNhat);
    }

    public Date declareNgayNhoNhat() {
        Date result = new Date();
        try {
            try (PreparedStatement pstmt = ConnectDB.preparedStatement("select top 1 ngayban from hoaDon order by ngayban"); ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getDate(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Date declareNgayLonNhat() {
        Date result = new Date();
        try {
            try (PreparedStatement pstmt = ConnectDB.preparedStatement("select top 1 ngayban from hoaDon order by ngayban DESC"); ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getDate(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Float declareTongTienNhoNhat() {
        float result = 0;
        try {
            try (PreparedStatement pstmt = ConnectDB.preparedStatement("select top 1 TongTien from hoadon order by TongTien"); ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getFloat(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Float declareTongTienLonNhat() {
        float result = 0;
        try {
            try (PreparedStatement pstmt = ConnectDB.preparedStatement("select top 1 TongTien from hoadon order by TongTien DESC"); ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getFloat(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public void insert(HoaDon hd) {
        ConnectDB.executeUpdate(INSERT_SQL,
                hd.getMaHD(),
                hd.getMaKH(),
                hd.getMaNV(),
                hd.getNgayBan(),
                hd.getTongTien(),
                hd.getHTThanhToan(),
                hd.getTienThanhToan()
        );
    }

    @Override
    public void update(HoaDon hd) {
        ConnectDB.executeUpdate(UPDATE_SQL,
                hd.getMaKH(),
                hd.getMaNV(),
                hd.getNgayBan(),
                hd.getTongTien(),
                hd.getHTThanhToan(),
                hd.getTienThanhToan(),
                hd.getMaHD()
        );
    }

    @Override
    public void delete(String id) {
        ConnectDB.executeUpdate(DELETE_SQL, id);
    }
}
