package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import model.ChiTietNhapHang;
import util.Auth;
import util.ChiTietNhapHangDAO;
import util.ChiTietSanPhamDAO;
import util.KichThuocDAO;
import util.LoaiGiayDAO;
import util.MauSacDAO;
import util.NhaCungCapDAO;
import util.NhanVienDAO;
import util.NhapHangDAO;
import util.SanPhamDAO;
import util.XDate;
import util.XNumber;

/**
 *
 * @author Admin
 */
public class NhapHang extends javax.swing.JPanel {

    LoaiGiayDAO lgDAO = new LoaiGiayDAO();
    KichThuocDAO ktDAO = new KichThuocDAO();
    MauSacDAO msDAO = new MauSacDAO();
    SanPhamDAO spDAO = new SanPhamDAO();
    ChiTietSanPhamDAO ctspDAO = new ChiTietSanPhamDAO();
    NhanVienDAO nvDAO = new NhanVienDAO();
    NhaCungCapDAO nccDAO = new NhaCungCapDAO();
    NhapHangDAO nhDAO = new NhapHangDAO();
    ChiTietNhapHangDAO ctnhDAO = new ChiTietNhapHangDAO();
    int row = -1;
    XDate xd = new XDate();
    XNumber xn = new XNumber();

    public NhapHang() {
        initComponents();
        initForm();
    }

    public void initComBoBox(String text) {
        if (text.isEmpty()) {
            cboSearch.removeAll();
            cboSearch.hidePopup();
        } else {
            fillCombobox();
            cboSearch.showPopup();
        }
    }

    public void showCombobox() {
        if (!txtSearch.getText().isEmpty()) {
            cboSearch.showPopup();
        } else {
            cboSearch.hidePopup();
        }
    }

    public void tinhTien() {
        int tongSL = 0;
        double tongTien = 0;
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            try {
                int a = Integer.parseInt(tblGioHang.getValueAt(i, 6) + "");
            } catch (Exception e) {
                tblGioHang.setValueAt(1, i, 6);
            }
            int soLuong = Integer.parseInt(tblGioHang.getValueAt(i, 6) + "");
            double gia = (xn.parseDecimal(tblGioHang.getValueAt(i, 5) + "")) * soLuong;
            tongSL += soLuong;
            tongTien += gia;
        }
        lblTongSoLuong.setText(tongSL + "");
        lblTongCong.setText(xn.formatDecimal(tongTien));
    }

    void initForm() {
        Date date = new Date();
        txtNgay.setText(xd.toString(date, "dd-MM-yyyy"));
        txtNhanVien.setText(Auth.user.getTenNV());
        txtNhanVien.setToolTipText(Auth.user.getMaNV() + "");
        tblGioHang.getColumnModel().getColumn(7).setCellRenderer(new CircularButtonRenderer());
        tblGioHang.getColumnModel().getColumn(7).setCellEditor(new CircularButtonEditor(new JTextField()));
        tblGioHang.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(SwingConstants.LEFT);
            }
        });
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(49, 0, 158));
        headerRenderer.setForeground(Color.WHITE);

        for (int i = 0; i < tblGioHang.getColumnCount(); i++) {
            tblGioHang.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        for (int i = 0; i < tblDonNhap.getColumnCount(); i++) {
            tblDonNhap.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        tblGioHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                row = tblGioHang.rowAtPoint(p);
                tblGioHang.repaint(); // Yêu cầu vẽ lại để cập nhật màu sắc
            }
        });
        fillToTableDonNhap();
    }

    public model.NhapHang getDonNhap() {
        model.NhapHang nh = new model.NhapHang();
        if (txtTenNCC.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng kiểm tra thông tin nhà cung cấp!");
            return null;
        }
        nh.setMaNCC(nccDAO.selectByEmailOrPhone(txtSDT.getText()).getMaNCC());
        nh.setMaNV(txtNhanVien.getToolTipText());
        nh.setNgayNhap(XDate.toDate(txtNgay.getText(), "dd-MM-yyyy"));
        nh.setGhiChu(txtGhiChu.getText());
        return nh;
    }

    public void lamMoi() {
        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
        model.setRowCount(0);
        lblTongSoLuong.setText("");
        lblTongCong.setText("");
        txtNgay.setText(xd.toString(new Date(), "dd-MM-yyyy"));
        txtNhanVien.setText(Auth.user.getTenNV());
        txtNhanVien.setToolTipText(Auth.user.getMaNV() + "");
        txtTenNCC.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
    }

    public String taoMaDN() {
        model.NhapHang nh = nhDAO.selectDESC().get(0);
        if (nh == null) {
            nh = new model.NhapHang();
            nh.setMaDN("DN000");
        }
        String oldID = nh.getMaDN();
        String number = oldID.substring(2, oldID.length());
        String newID = "DN";
        int idNumber = Integer.parseInt(number) + 1;
        if (idNumber < 10) {
            newID += "00" + idNumber;
        } else if (idNumber < 100) {
            newID += "0" + idNumber;
        } else {
            newID += idNumber;
        }
        return newID;
    }

    public String taoMaCTNH(String maDN) {
        model.ChiTietNhapHang ctnh;
        try {
            ctnh = ctnhDAO.selectNhapHangDESC(maDN).get(0);
        } catch (Exception e) {
            ctnh = new ChiTietNhapHang();
            ctnh.setMaCTDN(maDN + "-0");
        }

        String oldID = ctnh.getMaCTDN();
        String number = oldID.split("-")[1];
        String newID = maDN + "-";
        int idNumber = Integer.parseInt(number) + 1;
        newID += idNumber;
        return newID;
    }

    public boolean ThemChiTietNhapHang(String maDN) {
        if (tblGioHang.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa thêm sản phẩm vào đơn nhập!");
            return false;
        } else {
            for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                model.ChiTietNhapHang ctnh = new ChiTietNhapHang();
                ctnh.setMaCTDN(taoMaCTNH(maDN));
                
                ctnh.setMaDN(maDN);
                ctnh.setMaCTSP(tblGioHang.getValueAt(i, 0) + "");
                ctnh.setSoLuong(Integer.parseInt(tblGioHang.getValueAt(i, 6) + ""));
                ctnhDAO.insert(ctnh);
                model.ChiTietSanPham ctsp = ctspDAO.selectById(ctnh.getMaCTSP());
                int soLuong = ctsp.getSoLuong() + ctnh.getSoLuong();
                ctsp.setSoLuong(soLuong);
                ctspDAO.update(ctsp);
            }
        }
        return true;
    }

    public void themDonNhap() {
        model.NhapHang nh = getDonNhap();
        if (nh==null) {
            return;
        }
        nh.setMaDN(taoMaDN());
        try {
            if (txtTenNCC.getText().isEmpty()) {
                return;
            }
            nhDAO.insert(nh);
            String MaDN = nhDAO.selectDESC().get(0).getMaDN();
            if (ThemChiTietNhapHang(MaDN) == true) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            } else {
                nhDAO.delete(nh.getMaDN() + "");
                return;
            }
            fillToTableDonNhap();
            lamMoi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            System.out.println(e);
        }
    }

    public void fillToTableDonNhap() {
        DefaultTableModel model = (DefaultTableModel) tblDonNhap.getModel();
        model.setRowCount(0);
        try {
            List<model.NhapHang> list = nhDAO.selectDESC();
            for (model.NhapHang nh : list) {
                model.NhanVien nv = nvDAO.selectById(nh.getMaNV() + "");
                String tenNV = nv.getTenNV();
                model.NhaCungCap ncc = nccDAO.selectByID(nh.getMaNCC());
                String tenNCC = ncc.getTenNCC();
                List<ChiTietNhapHang> li = ctnhDAO.selectByDonNhapID(nh.getMaDN());

                double tongTien = 0;
                if (li != null) {
                    for (ChiTietNhapHang ctnh : li) {
                        tongTien += ctnh.getSoLuong() * spDAO.selectById(ctspDAO.selectById(ctnh.getMaCTSP()).getMaSP()).getDonGiaNhap();
                    }
                }

                Object[] rows = {nh.getMaDN(), tenNCC, tenNV, XDate.toString(nh.getNgayNhap(), "dd-MM-yyyy"), xn.formatDecimal(tongTien)};
                model.addRow(rows);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillGioHang() {
        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
        if (cboSearch.getSelectedIndex() == 0) {
            return;
        }
        String input = cboSearch.getSelectedItem() + "";
        String[] parts = input.split(" - ");
        String maCTSP = parts[0].trim();
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            String item = "" + tblGioHang.getValueAt(i, 0);
            if (item.equalsIgnoreCase(maCTSP)) {
                int newSL = (int) tblGioHang.getValueAt(i, 6) + 1;
                tblGioHang.setValueAt(newSL, i, 6);
                return;
            }
        }

        model.ChiTietSanPham ctsp = ctspDAO.selectById(maCTSP);
        if (ctsp == null) {
            return;
        }
        model.SanPham sp = spDAO.selectById(ctsp.getMaSP() + "");
        Object[] rows = {ctsp.getMaCTSP(), sp.getTenSP(), lgDAO.selectById(sp.getMaLoaiGiay()).getTenLoaiGiay(), msDAO.selectByID(ctsp.getMaMau()).getTenMauSac(),
            ktDAO.selectByID(ctsp.getMaKT()).getTenKichThuoc(), xn.formatDecimal(sp.getDonGiaNhap()), sp.getGiaKhuyenMai() == 0 ? null : xn.formatDecimal(sp.getGiaKhuyenMai()), 1};
        model.addRow(rows);
        tinhTien();
    }

    public void timKhachHang() {
        try {
            model.NhaCungCap ncc = nccDAO.selectByEmailOrPhone(txtSDT.getText());
            if (ncc == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin, vui lòng kiểm tra lại");
                txtSDT.setText("");
            } else {
                txtTenNCC.setText(ncc.getMaNCC());
                txtDiaChi.setText(ncc.getDiaChi());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void fillCombobox() {
        String keyWord = (String) txtSearch.getText();
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSearch.getModel();
        model.removeAllElements();
        cboSearch.addItem("Chọn sản phẩm: ");
        List<model.ChiTietSanPham> ctspList = new ArrayList<>();
        List<model.SanPham> spList = spDAO.selectByKeyword(keyWord);
        for (model.SanPham sanPham : spList) {
            List<model.ChiTietSanPham> ctspList2 = ctspDAO.selectByMaSP(sanPham.getMaSP());
            ctspList.addAll(ctspList2);
        }
        for (model.ChiTietSanPham ctsp : ctspList) {
            model.SanPham sp = spDAO.selectById(ctsp.getMaSP() + "");
            cboSearch.addItem(ctsp.getMaCTSP() + " - " + sp.getTenSP() + " - " + msDAO.selectByID(ctsp.getMaMau()).getTenMauSac() + " - " + ktDAO.selectByID(ctsp.getMaKT()).getTenKichThuoc());
        }
    }

    private class CircularButtonRenderer extends JButton implements TableCellRenderer {

        private ImageIcon icon;
        private int selectedRow = -1;

        public CircularButtonRenderer() {
            setOpaque(true);
            setBorderPainted(false);
            setContentAreaFilled(false);

            try {
                // Load the icon from the resources
                icon = new ImageIcon(getClass().getResource("/icon/delete.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (row == selectedRow) {
                setBackground(Color.BLACK);
            } else {
                setBackground(table.getBackground());
            }

            setIcon(icon);
            setText((value == null) ? "" : value.toString());
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
        }
    }

    private class CircularButtonEditor extends DefaultCellEditor {

        private JButton button;

        public CircularButtonEditor(JTextField textField) {
            super(textField);

            button = new JButton();
            try {
                // Load the icon from the resources
                ImageIcon icon = new ImageIcon(getClass().getResource("/icon/delete.png"));
                button.setIcon(icon);
            } catch (Exception e) {
                e.printStackTrace();
            }

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Point clickPoint = MouseInfo.getPointerInfo().getLocation();
                    SwingUtilities.convertPointFromScreen(clickPoint, tblGioHang);

                    int row = tblGioHang.rowAtPoint(clickPoint);

                    if (row >= 0 && row < tblGioHang.getRowCount()) {
                        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();

                        // Kết thúc sự kiện chỉnh sửa nếu đang có
                        if (tblGioHang.isEditing()) {
                            tblGioHang.getCellEditor().stopCellEditing();
                        }

                        model.removeRow(row);
                        tinhTien();
                    }
                }
            });

            button.setBorderPainted(false);
            button.setContentAreaFilled(false);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText((value == null) ? "" : value.toString());
            return button;
        }
    }

    private boolean isMouseOverComboBoxDropDown() {
        Point point = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(point, cboSearch);
        Rectangle bounds = cboSearch.getBounds();
        return bounds.contains(point);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        btnHuyHoaDon = new javax.swing.JButton();
        btnXacNhan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        lblTongCong = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtNgay = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtNhanVien = new javax.swing.JLabel();
        lblTongSoLuong = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        pnlTable = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDonNhap = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtSearch = new swing.TextFieldAnimation();
        blackButton1 = new controller.BlackButton();
        cboSearch = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtTenNCC = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        lblSoDienThoai = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();

        setMaximumSize(new java.awt.Dimension(1280, 730));
        setMinimumSize(new java.awt.Dimension(1280, 730));

        jPanel1.setMaximumSize(new java.awt.Dimension(1280, 730));
        jPanel1.setMinimumSize(new java.awt.Dimension(1280, 730));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 730));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel37.setText("Ghi chú: ");
        jPanel15.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, 30));

        jLabel39.setText("VND");
        jPanel15.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(446, 132, -1, -1));

        jLabel38.setText("VND");
        jPanel15.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(446, 228, -1, -1));

        jLabel40.setText("VND");
        jPanel15.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(446, 276, -1, -1));

        btnHuyHoaDon.setBackground(new java.awt.Color(80, 199, 255));
        btnHuyHoaDon.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnHuyHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnHuyHoaDon.setText("Làm mới");
        btnHuyHoaDon.setBorder(null);
        btnHuyHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHoaDonActionPerformed(evt);
            }
        });
        jPanel15.add(btnHuyHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 110, 50));

        btnXacNhan.setBackground(new java.awt.Color(80, 199, 255));
        btnXacNhan.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/confirm.png"))); // NOI18N
        btnXacNhan.setText("Xác nhận");
        btnXacNhan.setBorder(null);
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });
        jPanel15.add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 430, 210, 50));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Tổng số lượng:");
        jPanel15.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel34.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel34.setText("Tổng cộng:");
        jPanel15.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        lblTongCong.setFont(new java.awt.Font("sansserif", 1, 20)); // NOI18N
        lblTongCong.setText(" ");
        jPanel15.add(lblTongCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 170, 170, -1));
        jPanel15.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 350, 10));

        txtNgay.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        txtNgay.setText(" ");
        jPanel15.add(txtNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 210, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Ngày: ");
        jPanel15.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel36.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel36.setText("Người nhập: ");
        jPanel15.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        txtNhanVien.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        txtNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtNhanVien.setText(" ");
        jPanel15.add(txtNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 64, 210, 30));

        lblTongSoLuong.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        lblTongSoLuong.setText(" ");
        jPanel15.add(lblTongSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, 170, -1));

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        jPanel15.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 258, 350, 130));

        jPanel1.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 230, 390, 500));

        pnlTable.setBackground(new java.awt.Color(255, 255, 255));
        pnlTable.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, java.awt.Color.gray));
        pnlTable.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblDonNhap.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblDonNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nhà cung cấp", "Người nhận", "Ngày nhập", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDonNhap.setRowHeight(20);
        tblDonNhap.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblDonNhap.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(tblDonNhap);
        if (tblDonNhap.getColumnModel().getColumnCount() > 0) {
            tblDonNhap.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblDonNhap.getColumnModel().getColumn(0).setMaxWidth(100);
            tblDonNhap.getColumnModel().getColumn(1).setPreferredWidth(250);
            tblDonNhap.getColumnModel().getColumn(1).setMaxWidth(250);
            tblDonNhap.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblDonNhap.getColumnModel().getColumn(2).setMaxWidth(200);
            tblDonNhap.getColumnModel().getColumn(3).setPreferredWidth(130);
            tblDonNhap.getColumnModel().getColumn(3).setMaxWidth(130);
            tblDonNhap.getColumnModel().getColumn(4).setPreferredWidth(130);
            tblDonNhap.getColumnModel().getColumn(4).setMaxWidth(130);
        }

        pnlTable.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 820, 410));

        tblGioHang.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblGioHang.setForeground(new java.awt.Color(0, 0, 0));
        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên giày", "Loại giày", "Màu sắc", "Size", "Giá nhập", "Số lượng", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGioHang.setToolTipText("");
        tblGioHang.setRowHeight(20);
        tblGioHang.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblGioHang.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblGioHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGioHangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblGioHangMouseEntered(evt);
            }
        });
        tblGioHang.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblGioHangPropertyChange(evt);
            }
        });
        tblGioHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblGioHangKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblGioHang);
        if (tblGioHang.getColumnModel().getColumnCount() > 0) {
            tblGioHang.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblGioHang.getColumnModel().getColumn(0).setMaxWidth(100);
            tblGioHang.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblGioHang.getColumnModel().getColumn(1).setMaxWidth(200);
            tblGioHang.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblGioHang.getColumnModel().getColumn(2).setMaxWidth(150);
            tblGioHang.getColumnModel().getColumn(3).setPreferredWidth(150);
            tblGioHang.getColumnModel().getColumn(3).setMaxWidth(150);
            tblGioHang.getColumnModel().getColumn(4).setPreferredWidth(70);
            tblGioHang.getColumnModel().getColumn(4).setMaxWidth(70);
            tblGioHang.getColumnModel().getColumn(5).setPreferredWidth(100);
            tblGioHang.getColumnModel().getColumn(5).setMaxWidth(100);
            tblGioHang.getColumnModel().getColumn(7).setPreferredWidth(20);
            tblGioHang.getColumnModel().getColumn(7).setMaxWidth(20);
        }

        pnlTable.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 820, 140));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(null);
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel3MouseExited(evt);
            }
        });
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearch.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearchKeyTyped(evt);
            }
        });
        jPanel3.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, 40));

        blackButton1.setText("blackButton1");
        blackButton1.setRadius(48);
        jPanel3.add(blackButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 8, 304, 44));

        pnlTable.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 120));

        cboSearch.setBackground(new java.awt.Color(255, 255, 255));
        cboSearch.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cboSearch.setMaximumRowCount(5);
        cboSearch.setAutoscrolls(true);
        cboSearch.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 255)));
        cboSearch.setMaximumSize(null);
        cboSearch.setMinimumSize(null);
        cboSearch.setName(""); // NOI18N
        cboSearch.setPreferredSize(null);
        cboSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cboSearchFocusGained(evt);
            }
        });
        cboSearch.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cboSearchPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cboSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSearchActionPerformed(evt);
            }
        });
        pnlTable.add(cboSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 280, 30));

        jPanel1.add(pnlTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 840, 720));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        txtTenNCC.setFocusable(false);
        txtTenNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenNCCActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("Tên NCC:");

        jLabel29.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 153, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("THÔNG TIN NHÀ CUNG CẤP");

        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        lblSoDienThoai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSoDienThoai.setText("Email/SDT:");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel31.setText("Địa chỉ:");

        txtDiaChi.setFocusable(false);
        txtDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiaChiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblSoDienThoai)
                        .addGap(60, 60, 60)
                        .addComponent(txtSDT)
                        .addGap(24, 24, 24))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(10, 10, 10))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(55, 55, 55)))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTenNCC, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                            .addComponent(txtDiaChi))
                        .addGap(26, 26, 26))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSoDienThoai))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel28))
                            .addComponent(txtTenNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, 390, 190));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 742, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE))
        );

        jPanel1.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenNCCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenNCCActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        timKhachHang();
    }//GEN-LAST:event_txtSDTActionPerformed

    private void tblGioHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseClicked

    }//GEN-LAST:event_tblGioHangMouseClicked

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped

    }//GEN-LAST:event_txtSearchKeyTyped

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        initComBoBox(txtSearch.getText());
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        initComBoBox(txtSearch.getText());
    }//GEN-LAST:event_txtSearchKeyPressed

    private void jPanel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseEntered
        showCombobox();
    }//GEN-LAST:event_jPanel3MouseEntered

    private void jPanel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseExited

    }//GEN-LAST:event_jPanel3MouseExited

    private void cboSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSearchActionPerformed
        fillGioHang();
        tinhTien();
    }//GEN-LAST:event_cboSearchActionPerformed

    private void tblGioHangPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblGioHangPropertyChange

    }//GEN-LAST:event_tblGioHangPropertyChange

    private void cboSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cboSearchFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSearchFocusGained

    private void cboSearchPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cboSearchPopupMenuWillBecomeInvisible
        if (!isMouseOverComboBoxDropDown()) {
            cboSearch.hidePopup();
        }
    }//GEN-LAST:event_cboSearchPopupMenuWillBecomeInvisible

    private void tblGioHangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGioHangMouseEntered

    }//GEN-LAST:event_tblGioHangMouseEntered

    private void txtDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiaChiActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        themDonNhap();
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnHuyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHoaDonActionPerformed
        lamMoi();
    }//GEN-LAST:event_btnHuyHoaDonActionPerformed

    private void tblGioHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblGioHangKeyReleased
        tinhTien();
    }//GEN-LAST:event_tblGioHangKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private controller.BlackButton blackButton1;
    private javax.swing.JButton btnHuyHoaDon;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JComboBox<String> cboSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblSoDienThoai;
    private javax.swing.JLabel lblTongCong;
    private javax.swing.JLabel lblTongSoLuong;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable tblDonNhap;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JLabel txtNgay;
    private javax.swing.JLabel txtNhanVien;
    private javax.swing.JTextField txtSDT;
    private swing.TextFieldAnimation txtSearch;
    private javax.swing.JTextField txtTenNCC;
    // End of variables declaration//GEN-END:variables
}
