/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.NhaCungCap;

/**
 *
 * @author 12345
 */
public class NhaCungCapDAO extends EndlessDAO<NhaCungCap, String> {
  
    String INSERT_SQL = "INSERT INTO NhaCungCap(MaNCC, TenNCC, SDT, DiaChi, Email) VALUES(?,?, ?, ?, ?)";
    String UPDATE_SQL = "UPDATE NhaCungCap SET TenNCC=?, SDT=?, DiaChi=?, Email=? WHERE MaNCC=?";
    String DELETE_SQL = "DELETE FROM NhaCungCap WHERE MaNCC=?";
    String SELECT_ALL_SQL = "SELECT * FROM NhaCungCap";
    String SELECT_BY_ID_SQL = "SELECT * FROM NhaCungCap WHERE MaNCC=?";
    String SELECT_BY_SDT_OR_Email = "SELECT * FROM NhaCungCap WHERE SDT like ? or Email like ?";
    String SELECT_DESC = "Select * from NhaCungCap order by MaNCC desc";

    @Override
    public void insert(NhaCungCap ncc) {
        ConnectDB.executeUpdate(INSERT_SQL,
                ncc.getMaNCC(),
                ncc.getTenNCC(),
                ncc.getSDT(),
                ncc.getDiaChi(),
                ncc.getEmail());
    }

    @Override
    public void update(NhaCungCap ncc) {
        ConnectDB.executeUpdate(UPDATE_SQL,
                ncc.getTenNCC(),
                ncc.getSDT(),
                ncc.getDiaChi(),
                ncc.getEmail(),
                ncc.getMaNCC());
    }

    @Override
    public void delete(String id) {
        ConnectDB.executeUpdate(DELETE_SQL, id);
    }

    public NhaCungCap selectByID(String maNCC) {
        List<NhaCungCap> list = selectBySQL(SELECT_BY_ID_SQL, maNCC);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public NhaCungCap selectByEmailOrPhone(String key) {
        List<NhaCungCap> list = selectBySQL(SELECT_BY_SDT_OR_Email, key, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
      public List<NhaCungCap> selectDESC() {
        return this.selectBySQL(SELECT_DESC);
    }

    @Override
    public List<NhaCungCap> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }

    @Override
    protected List<NhaCungCap> selectBySQL(String sql, Object... args) {
        List<NhaCungCap> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap();
                ncc.setMaNCC(rs.getString("MaNCC"));
                ncc.setTenNCC(rs.getNString("TenNCC"));
                ncc.setSDT(rs.getString("SDT"));
                ncc.setDiaChi(rs.getNString("DiaChi"));
                ncc.setEmail(rs.getString("Email"));
                list.add(ncc);
            }
            rs.getStatement().close(); // Explicitly close the Statement
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e); // Wrap with custom exception
        }
    }

    public List<NhaCungCap> selectByKeyword(String keyword) {
        String SQL = "SELECT * FROM NhaCungCap WHERE TenNCC LIKE ?";
        return this.selectBySQL(SQL, "%" + keyword + "%");
    }

    @Override
    public NhaCungCap selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
