package util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.LoaiGiay;

public class LoaiGiayDAO extends EndlessDAO<LoaiGiay, String> {

    private final String INSERT_SQL = "INSERT INTO LoaiGiay(MaLoaiGiay, TenLoaiGiay, Mota) VALUES (?,?,?)";           
    private final String UPDATE_SQL = "UPDATE LoaiGiay SET TenLoaiGiay = ?, Mota = ? WHERE MaLoaiGiay = ?";
    private final String DELETE_SQL = "DELETE FROM LoaiGiay WHERE TenLoaiGiay = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM LoaiGiay";
    private final String SELECT_DESC = "Select * from LoaiGiay order by MaLoaiGiay desc";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM LoaiGiay WHERE MaLoaiGiay = ?";
    private final String SELECT_BY_NAME_SQL = "SELECT * FROM LoaiGiay WHERE TenLoaiGiay = ?";

    
    
    @Override
    public void insert(LoaiGiay loaiGiay) {
        ConnectDB.executeUpdate(INSERT_SQL, 
                loaiGiay.getMaLoaiGiay(),
                loaiGiay.getTenLoaiGiay(), 
                loaiGiay.getMoTa());
    }

    @Override
    public void update(LoaiGiay loaiGiay) {
        ConnectDB.executeUpdate(UPDATE_SQL, 
                loaiGiay.getTenLoaiGiay(), 
                loaiGiay.getMoTa(), 
                loaiGiay.getMaLoaiGiay());
    }

    public void delete(Integer id) {
        ConnectDB.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<LoaiGiay> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }
    public List<LoaiGiay> selectDESC() {
        return this.selectBySQL(SELECT_DESC);
    }
 
    public LoaiGiay selectById(String id) {
        List<LoaiGiay> list = selectBySQL(SELECT_BY_ID_SQL, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public LoaiGiay selectByName(String name) {
        List<LoaiGiay> list = selectBySQL(SELECT_BY_NAME_SQL, name);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<LoaiGiay> selectBySQL(String sql, Object... args) {
        List<LoaiGiay> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                LoaiGiay loaiGiay = new LoaiGiay();
                loaiGiay.setMaLoaiGiay(rs.getString("MaLoaiGiay"));
                loaiGiay.setTenLoaiGiay(rs.getString("TenLoaiGiay"));
                loaiGiay.setMoTa(rs.getString("Mota"));
                list.add(loaiGiay);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    
    @Override
    public void delete(String tenkg) {
        ConnectDB.executeUpdate(DELETE_SQL, tenkg);
    }
}
