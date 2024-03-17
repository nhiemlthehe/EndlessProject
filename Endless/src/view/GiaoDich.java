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
import java.text.DecimalFormat;
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
import model.HoaDonChiTiet;
import util.Auth;
import util.ChiTietSanPhamDAO;
import util.HoaDonChiTietDAO;
import util.HoaDonDAO;
import util.KhachHangDAO;
import util.KichThuocDAO;
import util.LoaiGiayDAO;
import util.MauSacDAO;
import util.NhanVienDAO;
import util.PrintBill;
import util.SanPhamDAO;
import util.XDate;
import util.XNumber;

/**
 *
 * @author Admin
 */
public class GiaoDich extends javax.swing.JPanel {

    LoaiGiayDAO lgDAO = new LoaiGiayDAO();
    KichThuocDAO ktDAO = new KichThuocDAO();
    MauSacDAO msDAO = new MauSacDAO();
    SanPhamDAO spDAO = new SanPhamDAO();
    ChiTietSanPhamDAO ctspDAO = new ChiTietSanPhamDAO();
    NhanVienDAO nvDAO = new NhanVienDAO();
    KhachHangDAO khDAO = new KhachHangDAO();
    HoaDonDAO hdDAO = new HoaDonDAO();
    HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAO();
    DecimalFormat format = new DecimalFormat("###,###.##");
    int row = -1;
    XDate xd = new XDate();
    XNumber xn = new XNumber();

    public GiaoDich() {
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
        double khuyenMai = 0;
        double giaGoc = 0;
        for (int i = 0; i < tblGioHang.getRowCount(); i++) {
            try {
                int a = Integer.parseInt(tblGioHang.getValueAt(i, 7) + "");
                if (a <= 0) {
                    a = a / 0;
                }
            } catch (Exception e) {
                tblGioHang.setValueAt(1, i, 7);
            }
            int soLuong = Integer.parseInt(tblGioHang.getValueAt(i, 7) + "");
            Object value = tblGioHang.getValueAt(i, 6);
            double gia = ((value != null) ? xn.parseDecimal(value + "") : xn.parseDecimal(tblGioHang.getValueAt(i, 5) + "")) * soLuong;
            double goc = xn.parseDecimal(tblGioHang.getValueAt(i, 5) + "") * soLuong;
            tongSL += soLuong;
            khuyenMai += gia;
            giaGoc += goc;
        }
        txtTienThoi.setText(xn.formatDecimal(tongSL));
        lblTongSoLuong.setText(tongSL + "");
        lblTongCong.setText(xn.formatDecimal(khuyenMai));
        lblKhuyenMai.setText(xn.formatDecimal(giaGoc - khuyenMai));
        txtTienThanhToan.setText(xn.formatDecimal(khuyenMai));
        txtTienThoi.setText("0");
    }

    public void initForm() {
        Date date = new Date();
        txtNgay.setText(xd.toString(date, "dd-MM-yyyy"));
        txtNhanVien.setText(Auth.user.getTenNV());
        txtNhanVien.setToolTipText(Auth.user.getMaNV() + "");
        tblGioHang.getColumnModel().getColumn(8).setCellRenderer(new CircularButtonRenderer());
        tblGioHang.getColumnModel().getColumn(8).setCellEditor(new CircularButtonEditor(new JTextField()));
        tblGioHang.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
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

        for (int i = 0; i < tblHoaDon.getColumnCount(); i++) {
            tblHoaDon.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        tblGioHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                row = tblGioHang.rowAtPoint(p);
                tblGioHang.repaint(); // Yêu cầu vẽ lại để cập nhật màu sắc
            }
        });
        fillToTableHoaDon();
    }

    public String taoMaKH() {
        model.KhachHang kh = khDAO.selectDESC().get(0);
        if (kh == null) {
            kh=new model.KhachHang();
            kh.setMaKH("KH000");
        }
        String oldID = kh.getMaKH();
        String number = oldID.substring(2, oldID.length());
        String newID = "KH";
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

    public void themKhachHang() {
        model.KhachHang kh = new model.KhachHang();
        kh.setMaKH(taoMaKH());
        kh.setTenKH(txtTenKhachHang.getText());
        kh.setSDT(txtSDT.getText());
        kh.setDiaChi(txtDiaChi.getText());
        try {
            khDAO.insert(kh);
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            txtSDT.setFocusable(false);
            txtDiaChi.setFocusable(false);
            btnThemKhachHang.setFocusable(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            System.out.println(e);
        }
    }

    public model.HoaDon getHoaDon() {
        model.HoaDon hd = new model.HoaDon();
        if (khDAO.selectBySDT(txtSDT.getText()) != null) {
            hd.setMaKH(khDAO.selectBySDT(txtSDT.getText()).getMaKH());
        } else {
            hd.setMaKH("KH000");
        }
        hd.setMaNV(txtNhanVien.getToolTipText());
        hd.setNgayBan(XDate.toDate(txtNgay.getText(), "dd-MM-yyyy"));
        hd.setTongTien((float) xn.parseDecimal(lblTongCong.getText()));
        hd.setHTThanhToan(cboHinhThucThanhToan.getSelectedItem() + "");
        hd.setTienThanhToan((float) xn.parseDecimal(txtTienThanhToan.getText()));
        return hd;
    }

    public void lamMoi() {
        DefaultTableModel model = (DefaultTableModel) tblGioHang.getModel();
        model.setRowCount(0);
        txtTienThanhToan.setText("");
        txtTienThoi.setText("");
        lblKhuyenMai.setText("");
        lblTongSoLuong.setText("");
        lblTongCong.setText("");
        txtNgay.setText(xd.toString(new Date(), "dd-MM-yyyy"));
        txtNhanVien.setText(Auth.user.getTenNV());
        txtNhanVien.setToolTipText(Auth.user.getMaNV() + "");
        txtTenKhachHang.setText("");
        txtSDT.setText("");
        txtDiaChi.setText("");
    }

    public boolean themHoaDonChiTiet(String maHD) {
        if (tblGioHang.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa thêm sản phẩm vào hóa đơn!");
            return false;
        } else {
            for (int i = 0; i < tblGioHang.getRowCount(); i++) {
                model.HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaHDCT(taoMaHDCT(maHD));
                hdct.setMaHD(maHD);
                hdct.setMaCTSP(tblGioHang.getValueAt(i, 0) + "");
                hdct.setSoLuong(Integer.parseInt(tblGioHang.getValueAt(i, 7) + ""));
                if (spDAO.selectById(ctspDAO.selectById(hdct.getMaCTSP()).getMaSP()).getGiaKhuyenMai() == 0) {
                    hdct.setThanhTien(Float.parseFloat(hdct.getSoLuong() * spDAO.selectById(ctspDAO.selectById(hdct.getMaCTSP()).getMaSP()).getGiaKhuyenMai() + ""));
                } else {
                    hdct.setThanhTien(Float.parseFloat(hdct.getSoLuong() * spDAO.selectById(ctspDAO.selectById(hdct.getMaCTSP()).getMaSP()).getDonGiaBan() + ""));
                }
                hdctDAO.insert(hdct);
                model.ChiTietSanPham ctsp = ctspDAO.selectById(hdct.getMaCTSP());
                int soLuong = ctsp.getSoLuong() - hdct.getSoLuong();
                if (soLuong < 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng sản phẩm có mã " + ctsp.getMaCTSP() + " chỉ còn " + ctsp.getSoLuong() + " sản phẩm !");
                    return false;
                }
                ctsp.setSoLuong(soLuong);
                ctspDAO.update(ctsp);
            }
        }
        return true;
    }

    public String taoMaHD() {
        model.HoaDon hd = hdDAO.selectDESC().get(0);
        if (hd == null) {
            hd = new model.HoaDon();
            hd.setMaHD("HD000");
        }
        String oldID = hd.getMaHD();
        String number = oldID.substring(2, oldID.length());
        String newID = "HD";
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

    public String taoMaHDCT(String maHD) {
        model.HoaDonChiTiet hdct;
        try {
            hdct = hdctDAO.selectHoaDonDESC(maHD).get(0);
        } catch (Exception e) {
            hdct = new HoaDonChiTiet();
            hdct.setMaHDCT(maHD + "-0");
        }

        String oldID = hdct.getMaHDCT();
        String number = oldID.split("-")[1];
        String newID = maHD + "-";
        int idNumber = Integer.parseInt(number) + 1;
        newID += idNumber;
        return newID;
    }

    public void themHoaDon() {
        model.HoaDon hd = getHoaDon();
        hd.setMaHD(taoMaHD());
        try {
            hdDAO.insert(hd);
            String maHD = hdDAO.selectDESC().get(0).getMaHD();
            if (themHoaDonChiTiet(maHD) == true) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            } else {
                hdDAO.delete(hd.getMaHD() + "");
                return;
            }
            fillToTableHoaDon();

            model.HoaDon hoaDon = hdDAO.selectDESC().get(0);
            List<model.HoaDonChiTiet> chiTietList = hdctDAO.selectByMaHD(hoaDon.getMaHD());
            PrintBill printBill = new PrintBill(hoaDon, chiTietList);
            printBill.printBill();
            lamMoi();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
            System.out.println(e);
        }
    }

    public void fillToTableHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            List<model.HoaDon> list = hdDAO.selectDESC();
            for (model.HoaDon hd : list) {
                model.NhanVien nv = nvDAO.selectById(hd.getMaNV() + "");
                String tenNV = nv.getTenNV();
                model.KhachHang kh = khDAO.selectByID(hd.getMaKH());
                String tenKH = kh.getTenKH();
                Object[] rows = {hd.getMaHD(), tenKH, tenNV, XDate.toString(hd.getNgayBan(), "dd-MM-yyyy"), hd.getHTThanhToan(), xn.formatDecimal(hd.getTongTien())};
                model.addRow(rows);
            }
        } catch (Exception e) {
            System.out.println(e);
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
                int newSL = (int) tblGioHang.getValueAt(i, 7) + 1;
                tblGioHang.setValueAt(newSL, i, 7);
                return;
            }
        }

        model.ChiTietSanPham ctsp = ctspDAO.selectById(maCTSP);
        if (ctsp == null) {
            return;
        }
        model.SanPham sp = spDAO.selectById(ctsp.getMaSP() + "");
        Object[] rows = {ctsp.getMaCTSP(), sp.getTenSP(), lgDAO.selectById(sp.getMaLoaiGiay()).getTenLoaiGiay(), msDAO.selectByID(ctsp.getMaMau()).getTenMauSac(),
            ktDAO.selectByID(ctsp.getMaKT()).getTenKichThuoc(), xn.formatDecimal(sp.getDonGiaBan()), sp.getGiaKhuyenMai() == 0 ? null : xn.formatDecimal(sp.getGiaKhuyenMai()), 1};
        model.addRow(rows);
        tinhTien();
    }

    public void timKhachHang() {
        try {
            model.KhachHang kh = khDAO.selectBySDT(txtSDT.getText());
            if (kh == null) {
                int option = JOptionPane.showConfirmDialog(
                        null,
                        "Không tìm thấy khách hàng phù hợp. Bạn muốn thêm khách hàng mới chứ?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    txtTenKhachHang.setFocusable(true);
                    txtDiaChi.setFocusable(true);
                    btnThemKhachHang.setFocusable(true);
                } else {
                    txtTenKhachHang.setFocusable(false);
                    txtDiaChi.setFocusable(false);
                    btnThemKhachHang.setFocusable(false);
                    return;
                }
            } else {
                txtTenKhachHang.setText(kh.getTenKH());
                txtDiaChi.setText(kh.getDiaChi());
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
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        cboHinhThucThanhToan = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        btnHuyHoaDon = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        lblKhuyenMai = new javax.swing.JLabel();
        txtTienThoi = new javax.swing.JLabel();
        lblTongCong = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtNgay = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtNhanVien = new javax.swing.JLabel();
        txtTienThanhToan = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        lblTongSoLuong = new javax.swing.JLabel();
        pnlTable = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtSearch = new swing.TextFieldAnimation();
        blackButton1 = new controller.BlackButton();
        cboSearch = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnThemKhachHang = new javax.swing.JButton();
        txtTenKhachHang = new javax.swing.JTextField();
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
        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel33.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel33.setText("Khuyến mãi:");
        jPanel15.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, 24));

        jLabel35.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel35.setText("Hình thức thanh toán:");
        jPanel15.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, 30));

        cboHinhThucThanhToan.setBackground(new java.awt.Color(255, 255, 255));
        cboHinhThucThanhToan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tiền mặt", "Chuyển khoản" }));
        cboHinhThucThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboHinhThucThanhToanActionPerformed(evt);
            }
        });
        jPanel15.add(cboHinhThucThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 150, 34));

        jLabel37.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel37.setText("Tiền khách đưa: ");
        jPanel15.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, 30));

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
        jPanel15.add(btnHuyHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 110, 50));

        btnThanhToan.setBackground(new java.awt.Color(80, 199, 255));
        btnThanhToan.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pay.png"))); // NOI18N
        btnThanhToan.setText("Thanh  Toán");
        btnThanhToan.setBorder(null);
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });
        jPanel15.add(btnThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 210, 50));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Tổng số lượng:");
        jPanel15.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel34.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel34.setText("Tổng cộng:");
        jPanel15.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        lblKhuyenMai.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        lblKhuyenMai.setForeground(new java.awt.Color(153, 0, 0));
        lblKhuyenMai.setText(" ");
        jPanel15.add(lblKhuyenMai, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 170, -1));

        txtTienThoi.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        txtTienThoi.setText(" ");
        jPanel15.add(txtTienThoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 350, 170, -1));

        lblTongCong.setFont(new java.awt.Font("sansserif", 1, 20)); // NOI18N
        lblTongCong.setText(" ");
        jPanel15.add(lblTongCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 170, -1));
        jPanel15.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 350, 10));

        txtNgay.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        txtNgay.setText(" ");
        jPanel15.add(txtNgay, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 230, -1));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Ngày: ");
        jPanel15.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel36.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel36.setText("Nhân viên:");
        jPanel15.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        txtNhanVien.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        txtNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtNhanVien.setText(" dasac");
        jPanel15.add(txtNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 65, 230, 30));

        txtTienThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienThanhToanActionPerformed(evt);
            }
        });
        txtTienThanhToan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTienThanhToanKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienThanhToanKeyReleased(evt);
            }
        });
        jPanel15.add(txtTienThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 310, 150, 30));

        jLabel41.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel41.setText("Tiền thối lại:");
        jPanel15.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, 30));

        lblTongSoLuong.setFont(new java.awt.Font("sansserif", 0, 20)); // NOI18N
        lblTongSoLuong.setText(" ");
        jPanel15.add(lblTongSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, 170, -1));

        jPanel1.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 230, 390, 500));

        pnlTable.setBackground(new java.awt.Color(255, 255, 255));
        pnlTable.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, java.awt.Color.gray));
        pnlTable.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHoaDon.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Khách hàng", "Người tạo", "Ngày tạo", "Thanh toán bằng", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setRowHeight(20);
        tblHoaDon.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblHoaDon.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setPreferredWidth(70);
            tblHoaDon.getColumnModel().getColumn(0).setMaxWidth(70);
            tblHoaDon.getColumnModel().getColumn(1).setPreferredWidth(200);
            tblHoaDon.getColumnModel().getColumn(1).setMaxWidth(200);
            tblHoaDon.getColumnModel().getColumn(2).setPreferredWidth(200);
            tblHoaDon.getColumnModel().getColumn(2).setMaxWidth(200);
            tblHoaDon.getColumnModel().getColumn(3).setPreferredWidth(130);
            tblHoaDon.getColumnModel().getColumn(3).setMaxWidth(130);
            tblHoaDon.getColumnModel().getColumn(4).setPreferredWidth(150);
            tblHoaDon.getColumnModel().getColumn(4).setMaxWidth(150);
            tblHoaDon.getColumnModel().getColumn(5).setPreferredWidth(130);
            tblHoaDon.getColumnModel().getColumn(5).setMaxWidth(130);
        }

        pnlTable.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 830, 390));

        tblGioHang.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblGioHang.setForeground(new java.awt.Color(0, 0, 0));
        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên giày", "Loại giày", "Màu sắc", "Size", "Giá bán", "Giá giảm", "SL", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true
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
        jScrollPane3.setViewportView(tblGioHang);
        if (tblGioHang.getColumnModel().getColumnCount() > 0) {
            tblGioHang.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblGioHang.getColumnModel().getColumn(0).setMaxWidth(100);
            tblGioHang.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblGioHang.getColumnModel().getColumn(1).setMaxWidth(150);
            tblGioHang.getColumnModel().getColumn(2).setPreferredWidth(150);
            tblGioHang.getColumnModel().getColumn(2).setMaxWidth(150);
            tblGioHang.getColumnModel().getColumn(3).setPreferredWidth(130);
            tblGioHang.getColumnModel().getColumn(3).setMaxWidth(130);
            tblGioHang.getColumnModel().getColumn(4).setPreferredWidth(80);
            tblGioHang.getColumnModel().getColumn(4).setMaxWidth(80);
            tblGioHang.getColumnModel().getColumn(5).setPreferredWidth(100);
            tblGioHang.getColumnModel().getColumn(5).setMaxWidth(100);
            tblGioHang.getColumnModel().getColumn(6).setPreferredWidth(120);
            tblGioHang.getColumnModel().getColumn(6).setMaxWidth(120);
            tblGioHang.getColumnModel().getColumn(8).setPreferredWidth(20);
            tblGioHang.getColumnModel().getColumn(8).setMaxWidth(20);
        }

        pnlTable.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 830, 160));

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
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
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

        jPanel1.add(pnlTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 850, 720));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnThemKhachHang.setBackground(new java.awt.Color(80, 199, 255));
        btnThemKhachHang.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        btnThemKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnThemKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        btnThemKhachHang.setFocusable(false);
        btnThemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhachHangActionPerformed(evt);
            }
        });

        txtTenKhachHang.setFocusable(false);
        txtTenKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenKhachHangActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel28.setText("Tên khách hàng:");

        jLabel29.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 153, 255));
        jLabel29.setText("THÔNG TIN KHÁCH HÀNG");

        txtSDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSDTActionPerformed(evt);
            }
        });

        lblSoDienThoai.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblSoDienThoai.setText("Số điện thoại:");

        jLabel31.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
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
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                        .addGap(237, 237, 237))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addGap(10, 10, 10)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTenKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                                    .addComponent(txtDiaChi))
                                .addGap(4, 4, 4)
                                .addComponent(btnThemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(lblSoDienThoai)
                                    .addGap(30, 30, 30)
                                    .addComponent(txtSDT))
                                .addComponent(jLabel29)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
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
                            .addComponent(txtTenKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addComponent(jLabel31)))
                .addGap(15, 15, 15))
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

    private void txtTenKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenKhachHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenKhachHangActionPerformed

    private void txtSDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSDTActionPerformed
        timKhachHang();
    }//GEN-LAST:event_txtSDTActionPerformed

    private void cboHinhThucThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboHinhThucThanhToanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboHinhThucThanhToanActionPerformed

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
        tinhTien();
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

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        themHoaDon();
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtTienThanhToanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienThanhToanKeyPressed
        try {
            double tienThoi = xn.parseDecimal(txtTienThanhToan.getText()) - xn.parseDecimal(lblTongCong.getText());
            txtTienThoi.setText(tienThoi + "");
        } catch (Exception e) {
            return;
        }

    }//GEN-LAST:event_txtTienThanhToanKeyPressed

    private void txtTienThanhToanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienThanhToanKeyReleased
        try {
            double tienThoi = xn.parseDecimal(txtTienThanhToan.getText()) - xn.parseDecimal(lblTongCong.getText());
            txtTienThoi.setText(tienThoi + "");
        } catch (Exception e) {
            return;
        }
    }//GEN-LAST:event_txtTienThanhToanKeyReleased

    private void btnThemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhachHangActionPerformed
        themKhachHang();
    }//GEN-LAST:event_btnThemKhachHangActionPerformed

    private void btnHuyHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHoaDonActionPerformed
        lamMoi();
    }//GEN-LAST:event_btnHuyHoaDonActionPerformed

    private void txtTienThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienThanhToanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienThanhToanActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private controller.BlackButton blackButton1;
    private javax.swing.JButton btnHuyHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemKhachHang;
    private javax.swing.JComboBox<String> cboHinhThucThanhToan;
    private javax.swing.JComboBox<String> cboSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblKhuyenMai;
    private javax.swing.JLabel lblSoDienThoai;
    private javax.swing.JLabel lblTongCong;
    private javax.swing.JLabel lblTongSoLuong;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JLabel txtNgay;
    private javax.swing.JLabel txtNhanVien;
    private javax.swing.JTextField txtSDT;
    private swing.TextFieldAnimation txtSearch;
    private javax.swing.JTextField txtTenKhachHang;
    private javax.swing.JTextField txtTienThanhToan;
    private javax.swing.JLabel txtTienThoi;
    // End of variables declaration//GEN-END:variables
}
