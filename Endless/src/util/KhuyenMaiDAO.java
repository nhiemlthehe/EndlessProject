/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.KhuyenMai;

/**
 *
 * @author 12345
 */
public class KhuyenMaiDAO extends EndlessDAO<KhuyenMai, String> {

    String INSERT_SQL = "INSERT INTO KhuyenMai(MaKM, TenKM, MucGiamGia, NgayBatDau, NgayKetThuc) VALUES(?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE KhuyenMai SET TenKM=?, MucGiamGia=?, NgayBatDau=?, NgayKetThuc=? WHERE MaKM=?";
    String DELETE_SQL = "DELETE FROM KhuyenMai WHERE MaKM=?";
    String SELECT_ALL_SQL = "SELECT * FROM KhuyenMai";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhuyenMai WHERE MaKM=?";
    String SELECT_DESC = "SELECT * FROM KHUYENMAI ORDER BY MAKM DESC";

    @Override
    public void insert(KhuyenMai km) {
        ConnectDB.executeUpdate(INSERT_SQL,
                km.getMaKM(),
                km.getTenKM(),
                km.getMucGiamGia(),
                km.getNgayBatDau(),
                km.getNgayKetThuc());
    }

    @Override
    public void update(KhuyenMai km) {
        ConnectDB.executeUpdate(UPDATE_SQL,
                km.getTenKM(),
                km.getMucGiamGia(),
                km.getNgayBatDau(),
                km.getNgayKetThuc(),
                km.getMaKM());
    }

    @Override
    public void delete(String id) {
        ConnectDB.executeUpdate(DELETE_SQL, id);
    }

    public KhuyenMai selectByID(String id) {
        List<KhuyenMai> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhuyenMai> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }
    
    public List<KhuyenMai> selectDESC() {
        return this.selectBySQL(SELECT_DESC);
    }

    @Override
    protected List<KhuyenMai> selectBySQL(String sql, Object... args) {
        List<KhuyenMai> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                KhuyenMai km = new KhuyenMai();
                km.setMaKM(rs.getString("MaKM"));
                km.setTenKM(rs.getNString("TenKM"));
                km.setMucGiamGia(rs.getInt("MucGiamGia"));
                km.setNgayBatDau(rs.getDate("NgayBatDau"));
                km.setNgayKetThuc(rs.getDate("NgayKetThuc"));
                list.add(km);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public KhuyenMai selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

    }
}
