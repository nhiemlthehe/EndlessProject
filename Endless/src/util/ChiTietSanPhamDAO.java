package util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ChiTietSanPham;
import util.ConnectDB;
import util.EndlessDAO;

public class ChiTietSanPhamDAO extends EndlessDAO<ChiTietSanPham, String> {

    String INSERT_SQL = "INSERT INTO ChiTietSanPham (MaCTSP, MaSP, MaMau, MaKT, SoLuong, Mota) VALUES (?,?,?,?,?,?)";
            
    String UPDATE_SQL = "UPDATE ChiTietSanPham SET MaSP = ?, MaMau = ?, MaKT = ?, SoLuong = ?, Mota = ? WHERE MaCTSP = ?";
    String DELETE_SQL = "DELETE FROM ChiTietSanPham WHERE MaCTSP = ?";
    String SELECT_ALL_SQL = "SELECT * FROM ChiTietSanPham";
    String SELECT_ALL_BY_MASANPHAM = "SELECT * FROM ChiTietSanPham where MaSP = ?";
    String SELECT_BY_ID_SQL = "SELECT * FROM ChiTietSanPham WHERE MaCTSP = ?";
    String SELECT_DESC = "SELECT * FROM ChiTietSanPham order by MaCTSP";
    String SELECT_ID_DESC = "SELECT * FROM ChiTietSanPham where MaSP = ? order by MaCTSP";
    
    @Override
    public void insert(ChiTietSanPham chiTietSP) {
        ConnectDB.executeUpdate(INSERT_SQL,
                chiTietSP.getMaCTSP(),
                chiTietSP.getMaSP(),
                chiTietSP.getMaMau(),
                chiTietSP.getMaKT(),
                chiTietSP.getSoLuong(),
                chiTietSP.getMoTa());
    }

    @Override
    public void update(ChiTietSanPham chiTietSP) {
        ConnectDB.executeUpdate(UPDATE_SQL,
                chiTietSP.getMaSP(),
                chiTietSP.getMaMau(),
                chiTietSP.getMaKT(),
                chiTietSP.getSoLuong(),
                chiTietSP.getMoTa(),
                chiTietSP.getMaCTSP());

    }

    @Override
    public void delete(String id) {
        ConnectDB.executeUpdate(DELETE_SQL, id);
    }

    @Override
    public List<ChiTietSanPham> selectAll() {
        return selectBySQL(SELECT_ALL_SQL);
    }
    
    public List<ChiTietSanPham> selectDESC() {
        return this.selectBySQL(SELECT_DESC);
    }

    public List<ChiTietSanPham> selectSanPhamDESC(String maSP) {
        return this.selectBySQL(SELECT_ID_DESC, maSP);
    }
    
    public List<ChiTietSanPham> selectByMaSP(String maSP) {
        return selectBySQL(SELECT_ALL_BY_MASANPHAM, maSP);
    }

    public ChiTietSanPham selectById(String id) {
        List<ChiTietSanPham> list = selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ChiTietSanPham> selectBySQL(String sql, Object... args) {
        List<ChiTietSanPham> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                ChiTietSanPham chiTietSP = new ChiTietSanPham();
                chiTietSP.setMaCTSP(rs.getString("MaCTSP"));
                chiTietSP.setMaSP(rs.getString("MaSP"));
                chiTietSP.setMaMau(rs.getString("MaMau"));
                chiTietSP.setMaKT(rs.getString("MaKT"));
                chiTietSP.setSoLuong(rs.getInt("SoLuong"));
                chiTietSP.setMoTa(rs.getString("Mota"));
                list.add(chiTietSP);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
