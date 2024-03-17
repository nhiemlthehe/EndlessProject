/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.MauSac;

/**
 *
 * @author 12345
 */
public class MauSacDAO extends EndlessDAO<MauSac, String> {

    String INSERT_SQL = "INSERT INTO MauSac(MaMauSac, TenMauSac, Mota) VALUES(?,?,?)";
    String UPDATE_SQL = "UPDATE MauSac SET TenMauSac=?, Mota=? WHERE MaMauSac=?";
    String DELETE_SQL = "DELETE FROM MauSac WHERE TenMauSac like ?";
    String SELECT_BY_ID_SQL = "SELECT * FROM MauSac WHERE MaMauSac=?";
    String SELECT_DESC = "Select * from MauSac order by MaMauSac desc";
    String SELECT_ALL_SQL = "SELECT * FROM MauSac";
    String CHECK_MAUDSAC = "select * from mausac where TenMauSac like ?";

    @Override
    public void insert(MauSac ms) {
        ConnectDB.executeUpdate(INSERT_SQL,
                ms.getMaMauSac(),
                ms.getTenMauSac(),
                ms.getMoTa());
    }

    @Override
    public void update(MauSac ms) {
        ConnectDB.executeUpdate(UPDATE_SQL,
                ms.getTenMauSac(),
                ms.getMoTa(),
                ms.getMaMauSac());
    }

    @Override
    public void delete(String tenms) {
        ConnectDB.executeUpdate(DELETE_SQL, tenms);
    }

    public MauSac selectByID(String id) {
        List<MauSac> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public MauSac checkMauSac(String tenMau) {
        List<MauSac> list = this.selectBySQL(CHECK_MAUDSAC, tenMau);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public MauSac selectById(String tenmau) {
        List<MauSac> list = this.selectBySQL(SELECT_BY_ID_SQL, tenmau);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<MauSac> selectAll() {
        return this.selectBySQL(SELECT_ALL_SQL);
    }
     public List<MauSac> selectDESC() {
        return this.selectBySQL(SELECT_DESC);
    }
    
    @Override
    protected List<MauSac> selectBySQL(String sql, Object... args) {
        List<MauSac> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                MauSac ms = new MauSac();
                ms.setMaMauSac(rs.getString("MaMauSac"));
                ms.setTenMauSac(rs.getNString("TenMauSac"));
                ms.setMoTa(rs.getNString("Mota"));
                list.add(ms);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
