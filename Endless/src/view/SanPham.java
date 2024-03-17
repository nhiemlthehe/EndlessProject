/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.ChiTietSanPham;
import util.Auth;
import util.ChiTietSanPhamDAO;
import util.KichThuocDAO;
import util.LoaiGiayDAO;
import util.MauSacDAO;
import util.PrintBill;
import util.SanPhamDAO;
import util.XImage;
import util.XNumber;

/**
 *
 * @author Hi There
 */
public class SanPham extends javax.swing.JPanel {

    SanPhamDAO spDAO = new SanPhamDAO();
    ChiTietSanPhamDAO ctspDAO = new ChiTietSanPhamDAO();
    LoaiGiayDAO lgDAO = new LoaiGiayDAO();
    KichThuocDAO ktDAO = new KichThuocDAO();
    MauSacDAO msDAO = new MauSacDAO();
    int row = -1;
    int row1 = -1;
    int count = 0;
    XNumber xn = new XNumber();
    JFileChooser fileChooser = new JFileChooser("build\\classes\\image\\product");

    /**
     * Creates new form SanPham2
     */
    public SanPham() {
        initComponents();
        initComboboxLoaiGiay();
        fillTableSanPham();
        initComboboxKichThuoc();
        initComboboxMauSac();
        txtGiaBanCaoNhat.setText(10000000 + "");
        txtGiaBanThapNhat.setText(0 + "");
        initTableModel();
    }

    public void initTableModel() {
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(49, 0, 158));
        headerRenderer.setForeground(Color.WHITE);
        for (int i = 0; i < tblSanPham.getColumnCount(); i++) {
            tblSanPham.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        for (int i = 0; i < tblThuocTinh.getColumnCount(); i++) {
            tblThuocTinh.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }

    void initComboboxLoaiGiay() {
        DefaultComboBoxModel cboModel = (DefaultComboBoxModel) cboLoaiGiay.getModel();
        cboLoaiGiay.removeAllItems();
        List<model.LoaiGiay> lg = new LoaiGiayDAO().selectAll();
        for (model.LoaiGiay loaigiay : lg) {
            cboModel.addElement(lgDAO.selectById(loaigiay.getMaLoaiGiay()).getTenLoaiGiay());
        }
    }

    void initComboboxKichThuoc() {
        DefaultComboBoxModel cboModel = (DefaultComboBoxModel) cboKichThuoc.getModel();
        cboKichThuoc.removeAllItems();
        List<model.KichThuoc> kt = new KichThuocDAO().selectAll();
        for (model.KichThuoc kichthuoc : kt) {
            cboModel.addElement(ktDAO.selectByID(kichthuoc.getMaKichThuoc()).getTenKichThuoc());
        }
    }

    void initComboboxMauSac() {
        DefaultComboBoxModel cboModel = (DefaultComboBoxModel) cboMauSac.getModel();
        cboMauSac.removeAllItems();
        List<model.MauSac> ms = new MauSacDAO().selectAll();
        for (model.MauSac mauSac : ms) {
            cboModel.addElement(msDAO.selectByID(mauSac.getMaMauSac()).getTenMauSac());
        }
    }

    void fillTableSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtSearch.getText();
            List<model.SanPham> list = spDAO.selectByKeyword(keyword);
            for (model.SanPham sp : list) {
                Object[] rows = {sp.getMaSP(), sp.getTenSP(), lgDAO.selectById(sp.getMaLoaiGiay()).getTenLoaiGiay(),
                    xn.formatDecimal(sp.getDonGiaNhap()), xn.formatDecimal(sp.getDonGiaBan()), xn.formatDecimal(sp.getGiaKhuyenMai()), sp.getMaVach(), sp.getHinhAnh()};
                model.addRow(rows);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void fillLoc() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        txtSearch.setText("");
        try {
            Float keyword = Float.parseFloat(txtGiaBanThapNhat.getText());
            Float keyword2 = Float.parseFloat(txtGiaBanCaoNhat.getText());
            List<model.SanPham> list = spDAO.selectLoc(keyword, keyword2);
            for (model.SanPham sp : list) {
                Object[] rows = {sp.getMaSP(), sp.getTenSP(), lgDAO.selectById(sp.getMaLoaiGiay()).getTenLoaiGiay(), xn.formatDecimal(sp.getDonGiaNhap()),
                    xn.formatDecimal(sp.getDonGiaBan()), xn.formatDecimal(sp.getGiaKhuyenMai()), sp.getMaVach(), sp.getHinhAnh()};
                model.addRow(rows);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void setForm(model.SanPham sp) {
        txtMaSanPham.setText(sp.getMaSP() + "");
        txtTenSanPham.setText(sp.getTenSP());
        cboLoaiGiay.setSelectedItem(lgDAO.selectById(sp.getMaLoaiGiay()).getTenLoaiGiay());
        txtMaVach.setText(sp.getMaVach());
        txtGiaNhap.setText(sp.getDonGiaNhap() + "");
        txtGiaBan.setText(sp.getDonGiaBan() + "");
        txtGiaKhuyenMai.setText(sp.getGiaKhuyenMai() + "");

        if (sp.getHinhAnh() != null) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/image/product/" + sp.getHinhAnh()));
            lblHinh.setImage(icon);
        }

    }

    public model.SanPham getForm() {
        model.SanPham sp = new model.SanPham();
        sp.setMaSP(txtMaSanPham.getText());
        sp.setTenSP(txtTenSanPham.getText());
        sp.setMaVach(txtMaVach.getText());
        sp.setMaLoaiGiay(lgDAO.selectByName((String) cboLoaiGiay.getSelectedItem()).getMaLoaiGiay());
        sp.setDonGiaNhap(Double.parseDouble(txtGiaNhap.getText()));
        sp.setDonGiaBan(Double.parseDouble(txtGiaBan.getText()));
        sp.setGiaKhuyenMai(Double.parseDouble("0"));
        if (sp.getHinhAnh() != null) {
            lblHinh.setToolTipText(sp.getHinhAnh());
            lblHinh.setImage(XImage.read("/image/product/", sp.getHinhAnh()));
        }
        sp.setHinhAnh(lblHinh.getToolTipText());
        return sp;
    }

    void fillTableChiTietSanPham(String id) {
        DefaultTableModel model = (DefaultTableModel) tblThuocTinh.getModel();

        model.setRowCount(0);
        try {
            List<model.ChiTietSanPham> list;
            if (id.equals("All")) {
                list = ctspDAO.selectAll();
            } else {
                list = ctspDAO.selectByMaSP(id);
            }
            for (model.ChiTietSanPham ctsp : list) {
                Object[] rows1 = {ctsp.getMaCTSP(), msDAO.selectByID(ctsp.getMaMau()).getTenMauSac(), ktDAO.selectByID(ctsp.getMaKT()).getTenKichThuoc(), ctsp.getSoLuong(), ctsp.getMoTa()};
                model.addRow(rows1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public model.ChiTietSanPham getFormCTSP() {
        model.ChiTietSanPham ctsp = new model.ChiTietSanPham();
        ctsp.setMaCTSP(txtMaCTSP.getText());
        ctsp.setMaSP((String) tblSanPham.getValueAt(row, 0));
        ctsp.setMaMau(msDAO.checkMauSac((String) cboMauSac.getSelectedItem()).getMaMauSac());
        ctsp.setMaKT(ktDAO.checkKichThuoc((String) cboKichThuoc.getSelectedItem()).getMaKichThuoc());
        ctsp.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        ctsp.setMoTa(txtMoTa.getText());
        return ctsp;
    }

    void setFormCTSP(model.ChiTietSanPham ctsp) {
        txtMaCTSP.setText(ctsp.getMaCTSP() + "");
        //txtTenSP.setText();
        cboMauSac.setSelectedItem(msDAO.selectByID(ctsp.getMaMau()).getTenMauSac());
        cboKichThuoc.setSelectedItem(ktDAO.selectByID(ctsp.getMaKT()).getTenKichThuoc());
        txtSoLuong.setText(ctsp.getSoLuong() + "");
        txtMoTa.setText(ctsp.getMoTa());

    }

    public String taoMaSP() {
        model.SanPham sp = spDAO.selectDESC().get(0);
        if (sp == null) {
            sp = new model.SanPham();
            sp.setMaSP("SP000");
        }
        String oldID = sp.getMaSP();
        String number = oldID.substring(2, oldID.length());
        String newID = "SP";
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

    public String taoMaCTSP(String maSP) {
        model.ChiTietSanPham ctsp;
        try {
            ctsp = ctspDAO.selectSanPhamDESC(maSP).get(0);
        } catch (Exception e) {
            ctsp = new ChiTietSanPham();
            ctsp.setMaCTSP(maSP + "-0");
        }

        String oldID = ctsp.getMaCTSP();
        String number = oldID.split("-")[1];
        String newID = maSP + "-";
        int idNumber = Integer.parseInt(number) + 1;
        newID += idNumber;
        return newID;
    }

    void Them() {
        if (batLoiSanPham() == true) {
            model.SanPham sp = getForm();
            sp.setMaSP(taoMaSP());
            try {
                spDAO.insert(sp);
                this.fillTableSanPham();
                LamMoi();
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                System.out.println(e);
            }
        }
    }

    void ThemCTSP(String maSP) {
        if (batLoiChiTietSanPham() == true) {
            model.ChiTietSanPham ctsp = getFormCTSP();
            ctsp.setMaCTSP(taoMaCTSP(maSP));
            try {
                ctspDAO.insert(ctsp);
                this.fillTableChiTietSanPham(ctsp.getMaSP());
                LamMoiCTSP();
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                System.out.println(e);
            }
        }
    }

    void CapNhat() {
        if (batLoiSanPham() == true) {
            model.SanPham sp = getForm();

            try {
                spDAO.update(sp);
                this.fillTableSanPham();
                LamMoi();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                System.out.println(e);
            }
        }
    }

    void CapNhatCTSP() {
        if (batLoiChiTietSanPham() == true) {
            model.ChiTietSanPham ctsp = getFormCTSP();
            try {
                ctspDAO.update(ctsp);
                fillTableChiTietSanPham(ctsp.getMaSP());
                LamMoiCTSP();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                System.out.println(e);
            }
        }
    }

    void Xoa() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa sản phẩm này!!");
        } else if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần xóa");
        } else {
            String id = txtMaSanPham.getText();
            int yesNo = JOptionPane.showConfirmDialog(this, "Bạn thực sự muốn xóa thuộc tính sản phẩm này?");
            if (yesNo == JOptionPane.YES_NO_OPTION) {

                try {
                    spDAO.delete(id);
                    this.fillTableSanPham();
                    LamMoi();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");

                }
            }
        }
    }

    void XoaCTSP() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa sản phẩm này!!");
        } else if (row1 == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần xóa");
        } else {

            String id = txtMaCTSP.getText();
            int yesNo = JOptionPane.showConfirmDialog(this, "Bạn thực sự muốn xóa thuộc tính chi tiết sản phẩm này?");
            if (yesNo == JOptionPane.YES_NO_OPTION) {
                try {
                    ctspDAO.delete(id);
                    this.fillTableChiTietSanPham(lblMaSP.getText());
                    LamMoiCTSP();
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                    System.out.println(e);
                }
            }

        }
    }

    void LamMoi() {
        txtMaSanPham.setText("");
        txtTenSanPham.setText("");
        cboLoaiGiay.setSelectedIndex(0);
        txtMaVach.setText("");
        txtGiaNhap.setText("");
        txtGiaBan.setText("");
        txtGiaKhuyenMai.setText("0");
        lblHinh.setImage(null);
        tblSanPham.clearSelection();
        this.row = -1;
    }

    void LamMoiCTSP() {
        this.row1 = -1;
        cboMauSac.setSelectedIndex(0);
        cboKichThuoc.setSelectedIndex(0);
        txtSoLuong.setText("");
        txtMoTa.setText("");
        tblThuocTinh.clearSelection();

    }

    boolean batLoiSanPham() {
        String err = "";
        if (txtTenSanPham.getText().isEmpty()) {
            err += "Tên sản phẩm không được bỏ trống\n";
        } else if (txtTenSanPham.getText().matches("[0-9].*$")) {
            err += "Tên sản phẩm không được viết số\n ";
        }

        if (txtGiaNhap.getText().isEmpty()) {
            err += "Giá nhập không được bỏ trống\n";
        } else if (!txtGiaNhap.getText().matches("^[^-].*")) {
            err += "Giá nhập không được viết số âm\n ";
        } else if (!txtGiaNhap.getText().matches("^[0-9].*")) {
            err += "Giá nhập không được viết chữ và kí tự đặt biệt\n ";
        }

        if (txtGiaBan.getText().isEmpty()) {
            err += "Giá bán không được bỏ trống\n";
        } else if (!txtGiaBan.getText().matches("^[^-].*")) {
            err += "Giá bán không được viết số âm\n ";
        } else if (!txtGiaBan.getText().matches("^[0-9].*")) {
            err += "Giá bán không được viết chữ và kí tự đặt biệt\n ";
        } else if (Double.parseDouble(txtGiaBan.getText()) < Double.parseDouble(txtGiaNhap.getText())) {
            err += "Giá bán phải lớn hơn giá nhập\n";
        }

        if (txtMaVach.getText().isEmpty()) {
            err += "Mã vạch sản phẩm không được bỏ trống\n";
        } else if (!txtMaVach.getText().matches("^[^a-zA-Z\\p{P}]*$")) {
            err += "Mã Vạch không được viết chữ và kí tự đặt biệt\n ";

        }
        if (!err.isEmpty()) {
            JOptionPane.showMessageDialog(this, err);

            return false;
        }
        return true;
    }

    boolean batLoiChiTietSanPham() {
        String err = "";

        if (txtSoLuong.getText().isEmpty()) {
            err += "Số Lượng không được bỏ trống\n";
        } else if (!txtSoLuong.getText().matches("^[^-].*")) {
            err += "Số Lượng không được viết số âm\n ";
        } else if (!txtSoLuong.getText().matches("^[^a-zA-Z\\p{P}]*$")) {
            err += "Số Lượng không được viết chữ và kí tự đặt biệt\n ";
        }
        if (!err.isEmpty()) {
            JOptionPane.showMessageDialog(this, err);
            return false;
        }
        return true;
    }

    void chonAnh() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save("product", file);
            ImageIcon icon = XImage.read("product", file.getName());
            lblHinh.setImage(icon);
            lblHinh.setToolTipText(file.getName());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tpnTab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        btnLoc = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txtSearch = new swing.TextFieldAnimation();
        blackButton1 = new controller.BlackButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        txtGiaBanCaoNhat = new javax.swing.JTextField();
        txtGiaBanThapNhat = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaSanPham = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cboLoaiGiay = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtGiaBan = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTenSanPham = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtGiaNhap = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtGiaKhuyenMai = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        txtMaVach = new javax.swing.JTextField();
        lblHinh = new controller.ImageAvatar();
        btnThemLoai = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cboMauSac = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cboKichThuoc = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtMaCTSP = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        btnThemCTSP = new javax.swing.JButton();
        btnCapNhatCTSP = new javax.swing.JButton();
        btnXoaCTSP = new javax.swing.JButton();
        btnLamMoiCTSP = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThuocTinh = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        lblTenSP = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblMaSP = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(1280, 730));
        setMinimumSize(new java.awt.Dimension(1280, 730));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Giá Bán:");
        jPanel9.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, -1, -1));

        btnLoc.setBackground(new java.awt.Color(80, 199, 255));
        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLoc.setForeground(new java.awt.Color(255, 255, 255));
        btnLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Loc_24x.png"))); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });
        jPanel9.add(btnLoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 30, 105, 30));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel5MouseExited(evt);
            }
        });
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel5.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, 40));

        blackButton1.setText("blackButton1");
        blackButton1.setRadius(48);
        jPanel5.add(blackButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 8, 304, 44));

        jPanel9.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 320, 60));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Loại giày", "Giá nhập", "Giá bán", "Giá khuyến mãi", "Mã vạch", "Hình ảnh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setGridColor(new java.awt.Color(0, 0, 0));
        tblSanPham.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblSanPham);

        jPanel9.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 84, 1160, 253));

        txtGiaBanCaoNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaBanCaoNhatActionPerformed(evt);
            }
        });
        jPanel9.add(txtGiaBanCaoNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 30, 180, 30));
        jPanel9.add(txtGiaBanThapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 172, 30));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("-");
        jPanel9.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 23, 23));

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 1240, 360));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Ảnh sản phẩm:(*)");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 118, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Mã sản phẩm:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 30, 95, -1));

        txtMaSanPham.setEditable(false);
        txtMaSanPham.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        jPanel4.add(txtMaSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 50, 250, 34));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Loại giày:(*)");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 30, 95, -1));

        cboLoaiGiay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboLoaiGiay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoaiGiayActionPerformed(evt);
            }
        });
        jPanel4.add(cboLoaiGiay, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 50, 230, 33));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Giá bán:(*)");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 95, -1));

        txtGiaBan.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtGiaBanCaretUpdate(evt);
            }
        });
        txtGiaBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaBanActionPerformed(evt);
            }
        });
        jPanel4.add(txtGiaBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, 250, 34));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Tên sản phẩm:(*)");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, 120, -1));

        txtTenSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSanPhamActionPerformed(evt);
            }
        });
        jPanel4.add(txtTenSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 50, 250, 34));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setText("Giá nhập:(*)");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, 95, -1));
        jPanel4.add(txtGiaNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, 250, 34));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setText("Giá khuyến mãi:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 100, 111, -1));

        txtGiaKhuyenMai.setEditable(false);
        jPanel4.add(txtGiaKhuyenMai, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 120, 250, 34));

        btnThem.setBackground(new java.awt.Color(80, 199, 255));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        btnThem.setText("Thêm ");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel4.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 110, 30));

        btnCapNhat.setBackground(new java.awt.Color(80, 199, 255));
        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/update.png"))); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setMaximumSize(new java.awt.Dimension(93, 27));
        btnCapNhat.setMinimumSize(new java.awt.Dimension(93, 27));
        btnCapNhat.setPreferredSize(new java.awt.Dimension(93, 27));
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        jPanel4.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 260, 120, 30));

        btnXoa.setBackground(new java.awt.Color(80, 199, 255));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btndelete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setMaximumSize(new java.awt.Dimension(93, 27));
        btnXoa.setMinimumSize(new java.awt.Dimension(93, 27));
        btnXoa.setPreferredSize(new java.awt.Dimension(93, 27));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel4.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 260, 90, 30));

        btnLamMoi.setBackground(new java.awt.Color(80, 199, 255));
        btnLamMoi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setMaximumSize(new java.awt.Dimension(93, 27));
        btnLamMoi.setMinimumSize(new java.awt.Dimension(93, 27));
        btnLamMoi.setPreferredSize(new java.awt.Dimension(93, 27));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });
        jPanel4.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 260, 140, 30));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setText("Mã vạch sản phẩm:(*)");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 170, 140, -1));

        txtMaVach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaVachActionPerformed(evt);
            }
        });
        jPanel4.add(txtMaVach, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 250, 34));

        lblHinh.setGradientColor1(new java.awt.Color(80, 199, 255));
        lblHinh.setGradientColor2(new java.awt.Color(80, 156, 255));
        lblHinh.setMaximumSize(new java.awt.Dimension(180, 180));
        lblHinh.setMinimumSize(new java.awt.Dimension(180, 180));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });
        jPanel4.add(lblHinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 190, 180));

        btnThemLoai.setBackground(new java.awt.Color(80, 199, 255));
        btnThemLoai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        btnThemLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemLoaiActionPerformed(evt);
            }
        });
        jPanel4.add(btnThemLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 50, -1, -1));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 1240, 310));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1280, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1280, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 713, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        tpnTab.addTab("Sản phẩm", jPanel1);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(null);
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Màu sắc:(*)");
        jPanel7.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 80, -1));

        cboMauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trắng", "Đen ", " " }));
        jPanel7.add(cboMauSac, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 110, 31));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Kích thước:(*)");
        jPanel7.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 90, -1));

        cboKichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "37", "38", "39", "40", "41", "42", "43", " " }));
        jPanel7.add(cboKichThuoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 110, 31));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Số lượng:(*)");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 80, -1));

        txtMaCTSP.setEditable(false);
        jPanel7.add(txtMaCTSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 259, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Mô tả:");
        jPanel7.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 50, -1));

        txtMoTa.setColumns(20);
        txtMoTa.setLineWrap(true);
        txtMoTa.setRows(5);
        txtMoTa.setMaximumSize(new java.awt.Dimension(232, 84));
        txtMoTa.setMinimumSize(new java.awt.Dimension(232, 84));
        jScrollPane1.setViewportView(txtMoTa);

        jPanel7.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 30, 510, 150));

        btnThemCTSP.setBackground(new java.awt.Color(80, 199, 255));
        btnThemCTSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThemCTSP.setForeground(new java.awt.Color(255, 255, 255));
        btnThemCTSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/them.png"))); // NOI18N
        btnThemCTSP.setText("Thêm ");
        btnThemCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemCTSPActionPerformed(evt);
            }
        });
        jPanel7.add(btnThemCTSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 110, -1));

        btnCapNhatCTSP.setBackground(new java.awt.Color(80, 199, 255));
        btnCapNhatCTSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCapNhatCTSP.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatCTSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/update.png"))); // NOI18N
        btnCapNhatCTSP.setText("Cập nhật");
        btnCapNhatCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatCTSPActionPerformed(evt);
            }
        });
        jPanel7.add(btnCapNhatCTSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, 120, -1));

        btnXoaCTSP.setBackground(new java.awt.Color(80, 199, 255));
        btnXoaCTSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoaCTSP.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaCTSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btndelete.png"))); // NOI18N
        btnXoaCTSP.setText("Xóa");
        btnXoaCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCTSPActionPerformed(evt);
            }
        });
        jPanel7.add(btnXoaCTSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 200, 110, -1));

        btnLamMoiCTSP.setBackground(new java.awt.Color(80, 199, 255));
        btnLamMoiCTSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLamMoiCTSP.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoiCTSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/reset.png"))); // NOI18N
        btnLamMoiCTSP.setText("Làm mới");
        btnLamMoiCTSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiCTSPActionPerformed(evt);
            }
        });
        jPanel7.add(btnLamMoiCTSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 200, 120, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Mã chi tiết:");
        jPanel7.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 90, -1));
        jPanel7.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 110, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setText("Thuộc Tính Sản Phẩm:");
        jPanel7.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 174, -1));

        jPanel6.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 1140, 250));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setText("Thông Tin Thuộc Tính:");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, 174, -1));

        tblThuocTinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã chi tiết sản phẩm", "Màu sắc", "Kích thước ", "Số lượng", "Mô tả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThuocTinh.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblThuocTinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuocTinhMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblThuocTinh);

        jPanel6.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 377, 1140, 290));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setText("Tên sản phẩm:");
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 20, 100, -1));

        lblTenSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel6.add(lblTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 200, 20));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setText("Mã sản phẩm:");
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 110, -1));

        lblMaSP.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel6.add(lblMaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 130, 20));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1280, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tpnTab.addTab("Chi tiết sản phẩm", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnTab)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tpnTab)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        row = tblSanPham.getSelectedRow();
        String id = tblSanPham.getValueAt(row, 0) + "";
        setForm(spDAO.selectById(id));
        if (evt.getClickCount() == 2) {
            tpnTab.setSelectedIndex(1);
            lblMaSP.setText(id);
            lblTenSP.setText(txtTenSanPham.getText());
            fillTableChiTietSanPham(id);
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void jPanel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseExited

    }//GEN-LAST:event_jPanel5MouseExited

    private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseEntered

    }//GEN-LAST:event_jPanel5MouseEntered

    private void txtSearchKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyTyped

    }//GEN-LAST:event_txtSearchKeyTyped

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        fillTableSanPham();
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        fillTableSanPham();
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtGiaBanCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtGiaBanCaretUpdate

    }//GEN-LAST:event_txtGiaBanCaretUpdate

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        Them();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        CapNhat();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        Xoa();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        LamMoi();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void tblThuocTinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuocTinhMouseClicked
        row1 = tblThuocTinh.getSelectedRow();
        String id = (String) tblThuocTinh.getValueAt(row1, 0);
        setFormCTSP(ctspDAO.selectById(id));
    }//GEN-LAST:event_tblThuocTinhMouseClicked

    private void btnThemCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemCTSPActionPerformed
        ThemCTSP(lblMaSP.getText());
    }//GEN-LAST:event_btnThemCTSPActionPerformed

    private void btnCapNhatCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatCTSPActionPerformed
        CapNhatCTSP();
    }//GEN-LAST:event_btnCapNhatCTSPActionPerformed

    private void btnXoaCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaCTSPActionPerformed
        XoaCTSP();
    }//GEN-LAST:event_btnXoaCTSPActionPerformed

    private void btnLamMoiCTSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiCTSPActionPerformed
        LamMoiCTSP();
    }//GEN-LAST:event_btnLamMoiCTSPActionPerformed

    private void txtTenSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSanPhamActionPerformed
        cboLoaiGiay.requestFocus();
    }//GEN-LAST:event_txtTenSanPhamActionPerformed

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        count++;
        if (count == 2) {
            chonAnh();
            count = 0;
        }
    }//GEN-LAST:event_lblHinhMouseClicked

    private void txtGiaBanCaoNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaBanCaoNhatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaBanCaoNhatActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        fillLoc();
    }//GEN-LAST:event_btnLocActionPerformed

    private void btnThemLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemLoaiActionPerformed
        LoaiGiay loaiGiayForm = new LoaiGiay();
        loaiGiayForm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDeactivated(WindowEvent e) {
                initComboboxLoaiGiay();
                String newLG = lgDAO.selectDESC().get(0).getTenLoaiGiay();
                cboLoaiGiay.setSelectedItem(newLG);
            }
        });

        loaiGiayForm.setVisible(true);
    }//GEN-LAST:event_btnThemLoaiActionPerformed

    private void cboLoaiGiayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiGiayActionPerformed
        txtGiaNhap.requestFocus();
    }//GEN-LAST:event_cboLoaiGiayActionPerformed

    private void txtGiaBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaBanActionPerformed
        txtMaVach.requestFocus();
    }//GEN-LAST:event_txtGiaBanActionPerformed

    private void txtMaVachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaVachActionPerformed
        Them();
    }//GEN-LAST:event_txtMaVachActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private controller.BlackButton blackButton1;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnCapNhatCTSP;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLamMoiCTSP;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemCTSP;
    private javax.swing.JButton btnThemLoai;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaCTSP;
    private javax.swing.JComboBox<String> cboKichThuoc;
    private javax.swing.JComboBox<String> cboLoaiGiay;
    private javax.swing.JComboBox<String> cboMauSac;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private controller.ImageAvatar lblHinh;
    private javax.swing.JLabel lblMaSP;
    private javax.swing.JLabel lblTenSP;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblThuocTinh;
    private javax.swing.JTabbedPane tpnTab;
    private javax.swing.JTextField txtGiaBan;
    private javax.swing.JTextField txtGiaBanCaoNhat;
    private javax.swing.JTextField txtGiaBanThapNhat;
    private javax.swing.JTextField txtGiaKhuyenMai;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JTextField txtMaCTSP;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextField txtMaVach;
    private javax.swing.JTextArea txtMoTa;
    private swing.TextFieldAnimation txtSearch;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSanPham;
    // End of variables declaration//GEN-END:variables
}
