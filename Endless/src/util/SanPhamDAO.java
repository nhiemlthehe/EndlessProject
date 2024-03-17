/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.EndlessDAO;
import model.SanPham;

/**
 *
 * @author 12345
 */
public class SanPhamDAO extends EndlessDAO<SanPham, String> {

    String INSERT_SQL = "INSERT INTO SanPham(MaSP,MaKM,TenSP,MaLoaiGiay,maVach,DonGiaNhap,DonGiaBan,GiaKhuyenMai,hinhAnh) values (?,?,?,?,?,?,?,?,?)";
    String UPDATE_SQL = "UPDATE SanPham SET MaKM = ?, TenSP= ?, MaLoaiGiay = ?, maVach = ? ,DonGiaNhap = ?, DonGiaBan = ?, GiaKhuyenMai= ?, hinhAnh= ?  WHERE MaSP = ?";
    String DELETE_SQL = "DELETE FROM SanPham WHERE MaSP = ?";
    String SELECT_ALL_SQL = "SELECT * FROM SanPham";
    String SELECT_DESC = "SELECT * FROM SanPham order by MaSP DESC";
    String SELECT_BY_ID_SQL = "SELECT * FROM SanPham where MaSP = ?";
    String SELECT_BY_SHOETYPE = "SELECT * FROM SanPham where maLoaiGiay = ?";
    String SELECT_BY_KM = "SELECT * FROM SanPham where MaKM = ?";

    @Override
    public void insert(SanPham sp) {
        ConnectDB.executeUpdate(INSERT_SQL, 
                sp.getMaSP(),
                sp.getMaKM(),
                sp.getTenSP(),
                sp.getMaLoaiGiay(),
                sp.getMaVach(), 
                sp.getDonGiaNhap(),
                sp.getDonGiaBan(), 
                sp.getGiaKhuyenMai(),
                sp.getHinhAnh());
    }

    @Override
    public void update(SanPham sp) {
        ConnectDB.executeUpdate(UPDATE_SQL,
                sp.getMaKM(),
                sp.getTenSP(),
                sp.getMaLoaiGiay(),
                sp.getMaVach(),
                sp.getDonGiaNhap(),
                sp.getDonGiaBan(),
                sp.getGiaKhuyenMai(),
                sp.getHinhAnh(),
                sp.getMaSP());
    }

    @Override
    public void delete(String id) {
        ConnectDB.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<SanPham> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }
     public List<SanPham> selectDESC() {
        return this.selectBySQL(SELECT_DESC);
    }
     public List<SanPham> selectByKM(String maKM) {
        return selectBySQL(SELECT_BY_KM, maKM);
    }

    @Override
    public SanPham selectById(String id) {
        List<SanPham> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<SanPham> selectBySQL(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getString("MaSP"));
                sp.setMaKM(rs.getString("MaKM"));
                sp.setTenSP(rs.getString("TenSP"));
                sp.setMaLoaiGiay(rs.getString("MaLoaiGiay"));
                sp.setMaVach(rs.getString("MaVach"));
                sp.setDonGiaNhap(rs.getDouble("DonGiaNhap"));
                sp.setDonGiaBan(rs.getDouble("DonGiaBan"));
                sp.setGiaKhuyenMai(rs.getDouble("GiaKhuyenMai"));
                sp.setHinhAnh(rs.getString("hinhAnh"));
                list.add(sp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<model.SanPham> selectByKeyword(String keyword) {
        if (keyword.matches("-?\\d+(\\.\\d+)?")) {
            int key = Integer.parseInt(keyword);
            String SQL = "select * from sanpham where TenSP like ? or MaSP = ? ";
            return this.selectBySQL(SQL, "N" + "'%" + keyword + "%'", key);
        } else {
            String SQL = "select * from sanpham where TenSP like ?";
            return this.selectBySQL(SQL, "%" + keyword + "%");
        }

    }

    public List<SanPham> selectByShoeType(String maLoaiGiay) {
        return selectBySQL(SELECT_BY_SHOETYPE, maLoaiGiay);
    }

    public List selectLoc(Float keywword, Float keyword2) {
        List<SanPham> list = this.selectBySQL("SELECT * FROM SANPHAM WHERE DonGiaBan >= " + keywword + " AND DonGiaBan <= " + keyword2);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }
}
