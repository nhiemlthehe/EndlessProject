/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.KhachHang;

/**
 *
 * @author 12345
 */
public class KhachHangDAO extends EndlessDAO<KhachHang, String> {

    String INSERT_SQL = "INSERT INTO KhachHang(MaKH, TenKH, SDT, DiaChi) VALUES(?,?,?,?)";
    String UPDATE_SQL = "UPDATE KhachHang SET TenKH=?, SDT=?, DiaChi=? WHERE MaKH=?";
    String DELETE_SQL = "DELETE FROM KhachHang WHERE MaKH=?";
    String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
    String SELECT_DESC = "SELECT * FROM KhachHang order by maKH DESC";
    String SELECT_BY_PHONE_NUMBER = "SELECT * FROM KhachHang where SDT like ?";
    String SELECT_BY_ID_SQL = "SELECT * FROM KhachHang WHERE MaKH=?";
     
    public List<KhachHang> selectDESC() {
        return this.selectBySQL(SELECT_DESC);
    }
    
    
    @Override
    public void insert(KhachHang kh) {
        ConnectDB.executeUpdate(INSERT_SQL,
                kh.getMaKH(),
                kh.getTenKH(),
                kh.getSDT(),
                kh.getDiaChi());
    }

    @Override
    public void update(KhachHang kh) {
        ConnectDB.executeUpdate(UPDATE_SQL,
                kh.getTenKH(),
                kh.getSDT(),
                kh.getDiaChi(),
                kh.getMaKH());
    }

    @Override
    public void delete(String id) {
        ConnectDB.executeUpdate(DELETE_SQL, id);
    }

    public KhachHang selectByID(String id) {
        List<KhachHang> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public List<KhachHang> selectByMaHD(int id) {
        List<KhachHang> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }
    
    public KhachHang selectBySDT(String sdt) {
        List<KhachHang> list = this.selectBySQL(SELECT_BY_PHONE_NUMBER, sdt);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<KhachHang> selectBySQL(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getString("MaKH"));
                kh.setTenKH(rs.getNString("TenKH"));
                kh.setSDT(rs.getString("SDT"));
                kh.setDiaChi(rs.getNString("DiaChi"));
                list.add(kh);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<KhachHang> selectByKeyword(String keyword) {
        String SQL = "SELECT * FROM KhachHang WHERE TenKH LIKE ?";
        return this.selectBySQL(SQL, "%" + keyword + "%");
    }

    @Override
    public KhachHang selectById(String id) {
        List<KhachHang> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
