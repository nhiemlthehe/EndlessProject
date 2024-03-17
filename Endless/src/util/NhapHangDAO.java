package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.HoaDon;
import model.NhapHang;

public class NhapHangDAO extends EndlessDAO<NhapHang, String> {

    private static final String SELECT_BY_ID_SQL = "SELECT * FROM NhapHang WHERE MaDN=?";
    private static final String INSERT_SQL = "INSERT INTO NhapHang(MaDN, MaNCC, MaNV, NgayNhap, GhiChu) VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE NhapHang SET MaNCC=?, MaNV=?, NgayNhap=?, GhiChu=? WHERE MaDN=?";
    private static final String DELETE_SQL = "DELETE FROM NhapHang WHERE MaDN=?";
    
    public List<NhapHang> selectNguoiNhap(String maNCC) {
        return this.selectBySQL("SELECT * FROM NhapHang where maNCC = ?", maNCC);
    }

    @Override
    public NhapHang selectById(String id) {
        List<NhapHang> list = this.selectBySQL(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhapHang> selectAll() {
        return this.selectBySQL("SELECT * FROM NhapHang");
    }
    
    public List<NhapHang> selectDESC() {
        return this.selectBySQL("SELECT * FROM NhapHang order by MaDN DESC");
    }

    @Override
    public void insert(NhapHang nhapHang) {
        ConnectDB.executeUpdate(INSERT_SQL,
                nhapHang.getMaDN(),
                nhapHang.getMaNCC(),
                nhapHang.getMaNV(),
                nhapHang.getNgayNhap(),
                nhapHang.getGhiChu());
    }

    @Override
    public void update(NhapHang nhapHang) {
        ConnectDB.executeUpdate(UPDATE_SQL,
                nhapHang.getMaNCC(),
                nhapHang.getMaNV(),
                nhapHang.getNgayNhap(),
                nhapHang.getGhiChu(),
                nhapHang.getMaDN());
    }

    @Override
    public void delete(String id) {
        ConnectDB.executeUpdate(DELETE_SQL, id);
    }

    @Override
    protected List<NhapHang> selectBySQL(String sql, Object... args) {
        List<NhapHang> list = new ArrayList<>();
        try {
            ResultSet rs = ConnectDB.executeQuery(sql, args);
            while (rs.next()) {
                NhapHang nh = new NhapHang();
                nh.setMaDN(rs.getString("MaDN"));
                nh.setMaNCC(rs.getString("MaNCC"));
                nh.setMaNV(rs.getString("MaNV"));
                nh.setNgayNhap(rs.getDate("NgayNhap"));
                nh.setGhiChu(rs.getString("GhiChu"));
                list.add(nh);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
