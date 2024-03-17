/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.KichThuoc;

/**
 *
 * @author 12345
 */
public class KichThuocDAO extends EndlessDAO<KichThuoc, String> {

    String INSERT_SQL = "INSERT INTO KichThuoc(MaKichThuoc, TenKichThuoc, Mota) VALUES(?,?,?)";
    String UPDATE_SQL = "UPDATE KichThuoc SET TenKichThuoc=?, Mota=? WHERE MaKichThuoc=?";
    String DELETE_SQL = "DELETE FROM KichThuoc WHERE TenKichThuoc like ?";
    String SELECT_BY_ID_SQL = "SELECT * FROM KichThuoc WHERE MaKichThuoc=?";
    String SELECT_DESC = "Select * from KichThuoc order by MaKichThuoc desc";
    String SELECT_ALL_SQL = "SELECT * FROM KichThuoc";
    String CHECK_KICHTHUOC = "select * from kichthuoc where TenKichThuoc like ?";

    @Override
    public void insert(KichThuoc kt) {
        ConnectDB.executeUpdate(INSERT_SQL,
                kt.getMaKichThuoc(),
                kt.getTenKichThuoc(),
                kt.getMoTa());
    }

    @Override
    public void update(KichThuoc kt) {
        ConnectDB.executeUpdate(UPDATE_SQL,
                kt.getTenKichThuoc(),
                kt.getMoTa(),
                kt.getMaKichThuoc());
    }

    @Override
    public void delete(String tenkt) {
        ConnectDB.executeUpdate(DELETE_SQL, tenkt);
    }

    public KichThuoc selectByID(String id) {
        List<KichThuoc> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public KichThuoc checkKichThuoc(String kichthuoc) {
        List<KichThuoc> list = this.selectBySQL(CHECK_KICHTHUOC, kichthuoc);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KichThuoc> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }
    public List<KichThuoc> selectDESC() {
        return this.selectBySQL(SELECT_DESC);
    }
    
    @Override
    protected List<KichThuoc> selectBySQL(String sql, Object... args) {
        List<KichThuoc> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                KichThuoc kt = new KichThuoc();
                kt.setMaKichThuoc(rs.getString("MaKichThuoc"));
                kt.setTenKichThuoc(rs.getNString("TenKichThuoc"));
                kt.setMoTa(rs.getString("Mota"));
                list.add(kt);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public KichThuoc selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
