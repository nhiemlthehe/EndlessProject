/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.HoaDonChiTiet;

/**
 *
 * @author 12345
 */
public class HoaDonChiTietDAO extends EndlessDAO<HoaDonChiTiet, Integer> {

    String SELECT_ALL_SQL = "SELECT * FROM HoaDonChiTiet";
    String SELECT_DESC = "SELECT * FROM HoaDonChiTiet order by MaHDCT";
    String SELECT_ID_DESC = "SELECT * FROM HoaDonChiTiet where MaHD = ? order by MaHDCT DESC";
    String SELECT_BY_ID_SQL = "SELECT * FROM HoaDonChiTiet WHERE MaHDCT=?";
    String SELECT_BY_MAHD = "SELECT * FROM HoaDonChiTiet WHERE MaHD = ?";
    String INSERT_SQL = "INSERT INTO HoaDonChiTiet(MaHDCT, MaHD, MaCTSP, SoLuong, ThanhTien) VALUES(?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE HoaDonChiTiet SET MaHD=?, MaCTSP=?, SoLuong=?, ThanhTien =? WHERE MaHDCT=?";
    String DELETE_SQL = "DELETE FROM HoaDonChiTiet WHERE MaHDCT=?";

    public List<HoaDonChiTiet> selectDESC() {
        return this.selectBySQL(SELECT_DESC);
    }

    public List<HoaDonChiTiet> selectHoaDonDESC(String maHD) {
        return this.selectBySQL(SELECT_ID_DESC, maHD);
    }

    @Override
    public List<HoaDonChiTiet> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    public List<HoaDonChiTiet> selectByMaHD(String id) {
        return this.selectBySQL(SELECT_BY_MAHD, id);
    }

    public List<HoaDonChiTiet> selectByMaHDCT(String id) {
        return this.selectBySQL(SELECT_BY_ID_SQL, id);
    }

    @Override
    protected List<HoaDonChiTiet> selectBySQL(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            var rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                var hdct = new HoaDonChiTiet();
                hdct.setMaHDCT(rs.getString("MaHDCT"));
                hdct.setMaHD(rs.getString("MaHD"));
                hdct.setMaCTSP(rs.getString("MaCTSP"));
                hdct.setSoLuong(rs.getInt("SoLuong"));
                hdct.setThanhTien(rs.getFloat("ThanhTien"));
                list.add(hdct);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(HoaDonChiTiet hdct) {
        ConnectDB.executeUpdate(INSERT_SQL,
                hdct.getMaHDCT(),
                hdct.getMaHD(),
                hdct.getMaCTSP(),
                hdct.getSoLuong(),
                hdct.getThanhTien()
        );
    }

    @Override
    public void update(HoaDonChiTiet hdct) {
        ConnectDB.executeUpdate(UPDATE_SQL,
                hdct.getMaHD(),
                hdct.getMaCTSP(),
                hdct.getSoLuong(),
                hdct.getThanhTien(),
                hdct.getMaHDCT());
    }

    @Override
    public void delete(Integer id) {
        ConnectDB.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public HoaDonChiTiet selectById(Integer id) {
        List<HoaDonChiTiet> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
