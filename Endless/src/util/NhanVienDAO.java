/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import model.HoaDon;
import model.NhanVien;

/**
 *
 * @author VoThiThaoNguyen
 */
public class NhanVienDAO extends EndlessDAO<NhanVien, String> {

    String INSERT_SQL = "INSERT INTO NhanVien(MaNV,TenNV, VaiTro, MaTK, MatKhau, GioiTinh, NgaySinh, SDT, Email, DiaChi, Hinh) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE NhanVien SET TenNV=?, VaiTro=?, MaTK=?, MatKhau=?, GioiTinh=?, NgaySinh=?, SDT=?, Email=?, DiaChi=?, Hinh=? WHERE MaNV=?";
    String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV=?";
    String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV=?";
    String SELECT_BY_USERNMAE_SQL = "SELECT * FROM NhanVien WHERE MaTK=?";
    String SELECT_BY_KEYWORD = "SELECT * FROM NhanVien WHERE TenNV like ? or MaNV like ?";
    String SELECT_BY_EMAIL_SQL = "SELECT * FROM NhanVien WHERE Email=?";
    String SELECT_DESC = "Select * from NhanVien order by MaNV desc";

    @Override
    public void insert(NhanVien entity) {
        ConnectDB.executeUpdate(INSERT_SQL,
                entity.getMaNV(),
                entity.getTenNV(),
                entity.isVaiTro(),
                entity.getMaTK(),
                entity.getMatKhau(),
                entity.isGioiTinh(),
                entity.getNgaySinh(),
                entity.getSDT(),
                entity.getEmail(),
                entity.getDiaChi(),
                entity.getHinh());
    }
    @Override
    public void update(NhanVien entity) {
        ConnectDB.executeUpdate(UPDATE_SQL,
                entity.getTenNV(),
                entity.isVaiTro(),
                entity.getMaTK(),
                entity.getMatKhau(),
                entity.isGioiTinh(),
                entity.getNgaySinh(),
                entity.getSDT(),
                entity.getEmail(),
                entity.getDiaChi(),
                entity.getHinh(),
                entity.getMaNV());
    }

    @Override
    public void delete(String id) {
        ConnectDB.executeUpdate(DELETE_SQL, id);
    }
    
    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    public NhanVien selectByUserName(String userName) {
        List<NhanVien> list = this.selectBySQL(SELECT_BY_USERNMAE_SQL, userName);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List selectByKeyword(String keywword) {
        List<NhanVien> list = this.selectBySQL(SELECT_BY_KEYWORD, "%" + keywword + "%","%" + keywword + "%");
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public NhanVien selectByEmail(String Email) {
        List<NhanVien> list = this.selectBySQL(SELECT_BY_EMAIL_SQL, Email);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<NhanVien> selectDESC() {
        return this.selectBySQL(SELECT_DESC);
    }

    @Override
    protected List<NhanVien> selectBySQL(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setTenNV(rs.getString("TenNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setMaTK(rs.getString("MaTK"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setVaiTro(rs.getBoolean("VaiTro"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setSDT(rs.getString("SDT"));
                entity.setEmail(rs.getString("Email"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setHinh(rs.getString("Hinh"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
