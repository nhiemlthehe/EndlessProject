package util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ChiTietNhapHang;
import model.HoaDonChiTiet;

public class ChiTietNhapHangDAO extends EndlessDAO<ChiTietNhapHang, Integer> {

    private static final String SELECT_BY_DN = "SELECT * FROM ChiTietNhapHang WHERE MaDN=?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM ChiTietNhapHang WHERE MaCTDN=?";
    private static final String INSERT_SQL = "INSERT INTO ChiTietNhapHang(MaCTDN, MaDN, MaCTSP, SoLuong) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE ChiTietNhapHang SET MaDN=?, MaCTSP=?, SoLuong=? WHERE MaCTDN=?";
    private static final String DELETE_SQL = "DELETE FROM ChiTietNhapHang WHERE MaCTDN=?";
    private static final String SELECT_ID_DESC = "SELECT * FROM ChiTietNhapHang where MaDN = ? order by MaCTDN DESC";

    public List<ChiTietNhapHang> selectByDonNhapID(String id) {
        List<ChiTietNhapHang> list = this.selectBySQL(SELECT_BY_DN, id);
        if (list.isEmpty()) {
            return null;
        }
        return list;
    }

    public List<ChiTietNhapHang> selectNhapHangDESC(String maDN) {
        return this.selectBySQL(SELECT_ID_DESC, maDN);
    }

    public ChiTietNhapHang selectById(String id) {
        List<ChiTietNhapHang> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ChiTietNhapHang> selectAll() {
        return this.selectBySQL("SELECT * FROM ChiTietNhapHang");
    }

    @Override
    public void insert(ChiTietNhapHang chiTietNhapHang) {
        ConnectDB.executeUpdate(INSERT_SQL,
                chiTietNhapHang.getMaCTDN(),
                chiTietNhapHang.getMaDN(),
                chiTietNhapHang.getMaCTSP(),
                chiTietNhapHang.getSoLuong());
    }

    @Override
    public void update(ChiTietNhapHang chiTietNhapHang) {
        ConnectDB.executeUpdate(UPDATE_SQL,
                chiTietNhapHang.getMaDN(),
                chiTietNhapHang.getMaCTSP(),
                chiTietNhapHang.getSoLuong(),
                chiTietNhapHang.getMaCTDN());
    }

    @Override
    public void delete(Integer id) {
        ConnectDB.executeUpdate(DELETE_SQL, id);
    }

    @Override
    protected List<ChiTietNhapHang> selectBySQL(String sql, Object... args) {
        List<ChiTietNhapHang> list = new ArrayList<>();
        try {
            var rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                ChiTietNhapHang ctnh = new ChiTietNhapHang();
                ctnh.setMaCTDN(rs.getString("MaCTDN"));
                ctnh.setMaDN(rs.getString("MaDN"));
                ctnh.setMaCTSP(rs.getString("MaCTSP"));
                ctnh.setSoLuong(rs.getInt("SoLuong"));
                list.add(ctnh);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ChiTietNhapHang selectById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
