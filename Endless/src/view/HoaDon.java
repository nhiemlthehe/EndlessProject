/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.Auth;
import util.ChiTietSanPhamDAO;
import util.HoaDonChiTietDAO;
import util.HoaDonDAO;
import util.KhachHangDAO;
import util.KichThuocDAO;
import util.MauSacDAO;
import util.NhanVienDAO;
import util.PrintBill;
import util.SanPhamDAO;
import util.XDate;
import util.XNumber;

/**
 *
 * @author Ly Tinh Nhiem
 */
public class HoaDon extends javax.swing.JPanel {

    HoaDonDAO hdDAO = new HoaDonDAO();
    HoaDonChiTietDAO hdctDAO = new HoaDonChiTietDAO();
    NhanVienDAO nvDAO = new NhanVienDAO();
    KhachHangDAO khDAO = new KhachHangDAO();
    SanPhamDAO spDAO = new SanPhamDAO();
    MauSacDAO msDAO = new MauSacDAO();
    ChiTietSanPhamDAO ctspDAO = new ChiTietSanPhamDAO();
    KichThuocDAO ktDAO = new KichThuocDAO();
    DecimalFormat format = new DecimalFormat("###,###.##");
    XNumber xn = new XNumber();
    int row = -1;

    public HoaDon() {
        initComponents();
        dcsNgayBatDau.setDate(hdDAO.declareNgayNhoNhat());
        dcsNgayKetThuc.setDate(hdDAO.declareNgayLonNhat());
        txtTongTienThapNhat.setText(new DecimalFormat("0.#").format(hdDAO.declareTongTienNhoNhat()) + "");
        txtTongTienCaoNhat.setText(new DecimalFormat("0.#").format(hdDAO.declareTongTienLonNhat()) + "");
        fillTableHoaDon();
        initForm();
    }
    
    public void initForm() {
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(49, 0, 158));
        headerRenderer.setForeground(Color.WHITE);
        for (int i = 0; i < tblHoaDon.getColumnCount(); i++) {
            tblHoaDon.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        for (int i = 0; i < tblHoaDonChiTiet.getColumnCount(); i++) {
            tblHoaDonChiTiet.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }

    void fillTableHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            List<model.HoaDon> list = hdDAO.selectAll();
            int i = 0;
            for (model.HoaDon hd : list) {
                i++;
                model.NhanVien nv = nvDAO.selectById(hd.getMaNV() + "");
                String tenNV = nv.getTenNV();
                model.KhachHang kh = khDAO.selectByID(hd.getMaKH());
                String tenKH = kh.getTenKH();
                Object[] rows = {i, hd.getMaHD(), tenKH, tenNV, XDate.toString(hd.getNgayBan(), "dd-MM-yyyy"), hd.getHTThanhToan(), XNumber.formatDecimal(hd.getTongTien()), format.format(hd.getTienThanhToan())};
                model.addRow(rows);
            }
        } catch (Exception e) {

            System.out.println(e);
        }
    }

    public void locHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            List<model.HoaDon> list = hdDAO.customSelect(dcsNgayBatDau.getDate(), dcsNgayKetThuc.getDate(),
                    Float.parseFloat(txtTongTienThapNhat.getText()), Float.parseFloat(txtTongTienCaoNhat.getText()));
            int i = 0;
            for (model.HoaDon hd : list) {
                i++;
                model.NhanVien nv = nvDAO.selectById(hd.getMaNV() + "");
                String tenNV = nv.getTenNV();
                model.KhachHang kh = khDAO.selectByID(hd.getMaKH());
                String tenKH = kh.getTenKH();
                Object[] rows = {i, hd.getMaHD(), tenKH, tenNV, XDate.toString(hd.getNgayBan(), "dd-MM-yyyy"), hd.getHTThanhToan(), format.format(hd.getTongTien()), format.format(hd.getTienThanhToan())};
                model.addRow(rows);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Kiểm tra giá trị lọc !");
            fillTableHoaDon();
        }
    }

    void fillTableHoaDonChiTiet(String id) {
        DefaultTableModel model = (DefaultTableModel) tblHoaDonChiTiet.getModel();
        model.setRowCount(0);
        try {
            List<model.HoaDonChiTiet> list = hdctDAO.selectByMaHD(id);
            for (model.HoaDonChiTiet hd : list) {
                model.SanPham sp = spDAO.selectById(ctspDAO.selectById(hd.getMaCTSP()).getMaSP());
                String tenSP = sp.getTenSP();
                model.ChiTietSanPham ctsp = ctspDAO.selectById(hd.getMaCTSP());
                model.MauSac ms = msDAO.selectByID(ctsp.getMaMau());
                String mau = ms.getTenMauSac();
                model.KichThuoc kt = ktDAO.selectByID(ctsp.getMaKT());
                String kichThuoc = kt.getTenKichThuoc();
                double giaBan = sp.getDonGiaBan();
                double km = 100;
                if (sp.getGiaKhuyenMai() != 0) {
                    km = sp.getGiaKhuyenMai();
                }
                double giaKM = (km * 0.01 * giaBan);
                int soLuong = hd.getSoLuong();
                double thanhTien = (giaKM * soLuong);
                Object[] rows = {hd.getMaHDCT(), tenSP, mau, kichThuoc, format.format(giaBan), format.format(giaKM), soLuong, format.format(thanhTien)};
                model.addRow(rows);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void TimKiem() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        try {
            List<model.HoaDon> list = hdDAO.selectByKH(txtTimKiem.getText());
            if (list == null) {
                return;
            }
            int i = 0;
            for (model.HoaDon hd : list) {
                i++;
                model.NhanVien nv = nvDAO.selectById(hd.getMaNV() + "");
                String tenNV = nv.getTenNV();
                model.KhachHang kh = khDAO.selectByID(hd.getMaKH());
                String tenKH = kh.getTenKH();
                Object[] rows = {i, hd.getMaHD(), tenKH, tenNV, XDate.toString(hd.getNgayBan(), "dd-MM-yyyy"), hd.getHTThanhToan(), format.format(hd.getTongTien()), format.format(hd.getTienThanhToan())};
                model.addRow(rows);
            }
        } catch (Exception e) {

            System.out.println(e);
        }
    }

    public void xuatDanhSach(JTable table, File file) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");

        // Tạo một kiểu ô định dạng để căn giữa văn bản
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Tạo dòng tiêu đề
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < table.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(table.getColumnName(i));
            cell.setCellStyle(cellStyle);
        }

        // Tạo dòng dữ liệu
        for (int i = 0; i < table.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < table.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                Object value = table.getValueAt(i, j);
                if (j == 6 || j == 7) {
                    value = String.valueOf(value).replaceAll(",", "");
                    value = Integer.parseInt(value + "");
                }
                if (value instanceof Number) {
                    // Nếu giá trị là số, sử dụng kiểu ô dữ liệu NUMERIC
                    cell.setCellValue(((Number) value).doubleValue());
                    cell.setCellStyle(cellStyle);
                    cell.setCellType(CellType.NUMERIC);
                } else {
                    cell.setCellValue(value.toString());
                    cell.setCellStyle(cellStyle);
                }
            }
        }

        // Tự động điều chỉnh chiều rộng của cột dựa trên nội dung
        for (int i = 0; i < table.getColumnCount(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Lưu file
        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();

        // Hiển thị thông báo
        int open = JOptionPane.showConfirmDialog(this, "Xuát file thành công, bạn muốn mở nó không?");
        if (open == JOptionPane.YES_OPTION) {
            Desktop.getDesktop().browse(file.toURI());
        }
    }

    public void Xoa() {
        if (!Auth.isManager()) {
            JOptionPane.showMessageDialog(this, "Bạn kmông có quyền xóa hóa đơn!!");
        } else if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đối tượng cần xóa");
        } else {
            String maHD = (String) tblHoaDon.getValueAt(row, 1);
            JOptionPane.showConfirmDialog(this, "Bạn thực sự muốn xóa hóa đơn này?");
            try {
                hdDAO.delete(maHD);
                this.fillTableHoaDon();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
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

        jPanel1 = new javax.swing.JPanel();
        txtTongTienThapNhat = new javax.swing.JTextField();
        txtTongTienCaoNhat = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dcsNgayBatDau = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        dcsNgayKetThuc = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        btnLoc = new javax.swing.JButton();
        btnXuatHoaDon = new javax.swing.JButton();
        btnInHoaDon = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txtTimKiem = new swing.TextFieldAnimation();
        blackButton1 = new controller.BlackButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDonChiTiet = new javax.swing.JTable();

        setMaximumSize(new java.awt.Dimension(1280, 730));
        setMinimumSize(new java.awt.Dimension(1280, 730));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(1280, 730));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("-");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setText("-");

        tblHoaDon.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã hóa đơn", "Tên khách hàng", "Tên nhân viên", "Ngày bán", "Hình thức thanh Toán", "Tổng tiền", "Tiền thanh toán"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblHoaDon.setSelectionForeground(new java.awt.Color(0, 0, 51));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setPreferredWidth(50);
            tblHoaDon.getColumnModel().getColumn(0).setMaxWidth(50);
            tblHoaDon.getColumnModel().getColumn(1).setPreferredWidth(100);
            tblHoaDon.getColumnModel().getColumn(1).setMaxWidth(100);
            tblHoaDon.getColumnModel().getColumn(2).setPreferredWidth(300);
            tblHoaDon.getColumnModel().getColumn(2).setMaxWidth(300);
            tblHoaDon.getColumnModel().getColumn(3).setPreferredWidth(250);
            tblHoaDon.getColumnModel().getColumn(3).setMaxWidth(250);
            tblHoaDon.getColumnModel().getColumn(4).setPreferredWidth(120);
            tblHoaDon.getColumnModel().getColumn(4).setMaxWidth(120);
            tblHoaDon.getColumnModel().getColumn(5).setPreferredWidth(190);
            tblHoaDon.getColumnModel().getColumn(5).setMaxWidth(190);
            tblHoaDon.getColumnModel().getColumn(6).setPreferredWidth(120);
            tblHoaDon.getColumnModel().getColumn(6).setMaxWidth(120);
        }

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Tổng tiền :");

        btnLoc.setBackground(new java.awt.Color(80, 199, 255));
        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnLoc.setForeground(new java.awt.Color(255, 255, 255));
        btnLoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/filter.png"))); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.setBorder(null);
        btnLoc.setPreferredSize(new java.awt.Dimension(120, 40));
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        btnXuatHoaDon.setBackground(new java.awt.Color(80, 199, 255));
        btnXuatHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXuatHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/export.png"))); // NOI18N
        btnXuatHoaDon.setText("Xuất excel");
        btnXuatHoaDon.setBorder(null);
        btnXuatHoaDon.setPreferredSize(new java.awt.Dimension(120, 40));
        btnXuatHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatHoaDonActionPerformed(evt);
            }
        });

        btnInHoaDon.setBackground(new java.awt.Color(80, 199, 255));
        btnInHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnInHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnInHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/print.png"))); // NOI18N
        btnInHoaDon.setText("In hóa đơn");
        btnInHoaDon.setBorder(null);
        btnInHoaDon.setPreferredSize(new java.awt.Dimension(120, 40));
        btnInHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInHoaDonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Thời gian :");

        jButton1.setBackground(new java.awt.Color(80, 199, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/btndelete.png"))); // NOI18N
        jButton1.setText("Xoá");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtTimKiem.setSelectedTextColor(new java.awt.Color(0, 0, 0));
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });
        jPanel3.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 300, 40));

        blackButton1.setText("blackButton1");
        blackButton1.setRadius(48);
        jPanel3.add(blackButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 8, 304, 44));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXuatHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addGap(12, 12, 12)
                        .addComponent(dcsNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dcsNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addComponent(txtTongTienThapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTongTienCaoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(4, 4, 4)
                                            .addComponent(jLabel6)
                                            .addGap(8, 8, 8))
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(dcsNgayKetThuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(dcsNgayBatDau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTongTienThapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTongTienCaoNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuatHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hóa đơn chi tiết", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18))); // NOI18N

        tblHoaDonChiTiet.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblHoaDonChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên sản phẩm", "Màu", "Size", "Giá bán", "Giá khuyến mãi", "Số lượng", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDonChiTiet.setPreferredSize(new java.awt.Dimension(300, 60));
        tblHoaDonChiTiet.setSelectionBackground(new java.awt.Color(120, 210, 255));
        tblHoaDonChiTiet.setSelectionForeground(new java.awt.Color(0, 0, 51));
        jScrollPane2.setViewportView(tblHoaDonChiTiet);
        if (tblHoaDonChiTiet.getColumnModel().getColumnCount() > 0) {
            tblHoaDonChiTiet.getColumnModel().getColumn(0).setPreferredWidth(100);
            tblHoaDonChiTiet.getColumnModel().getColumn(0).setMaxWidth(100);
            tblHoaDonChiTiet.getColumnModel().getColumn(1).setPreferredWidth(390);
            tblHoaDonChiTiet.getColumnModel().getColumn(1).setMaxWidth(390);
            tblHoaDonChiTiet.getColumnModel().getColumn(2).setPreferredWidth(160);
            tblHoaDonChiTiet.getColumnModel().getColumn(2).setMaxWidth(160);
            tblHoaDonChiTiet.getColumnModel().getColumn(3).setPreferredWidth(70);
            tblHoaDonChiTiet.getColumnModel().getColumn(3).setMaxWidth(70);
            tblHoaDonChiTiet.getColumnModel().getColumn(4).setPreferredWidth(160);
            tblHoaDonChiTiet.getColumnModel().getColumn(4).setMaxWidth(160);
            tblHoaDonChiTiet.getColumnModel().getColumn(5).setPreferredWidth(130);
            tblHoaDonChiTiet.getColumnModel().getColumn(5).setMaxWidth(130);
            tblHoaDonChiTiet.getColumnModel().getColumn(6).setPreferredWidth(100);
            tblHoaDonChiTiet.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        row = tblHoaDon.getSelectedRow();
        String maHD = "" + tblHoaDon.getValueAt(row, 1);
        fillTableHoaDonChiTiet(maHD);
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnXuatHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatHoaDonActionPerformed

        JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILES", ".xlsx");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Save As");
        int value = chooser.showSaveDialog(null);
        if (value == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".xlsx")) {
                file = new File(file.getAbsolutePath() + ".xlsx");
            }
            try {
                xuatDanhSach(tblHoaDon, file);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        };
    }//GEN-LAST:event_btnXuatHoaDonActionPerformed

    private void btnInHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInHoaDonActionPerformed

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần in");
        } else {
            model.HoaDon hoaDon = hdDAO.selectDESC().get(0);
            List<model.HoaDonChiTiet> chiTietList = hdctDAO.selectByMaHD(hoaDon.getMaHD());
            PrintBill printBill = new PrintBill(hoaDon, chiTietList);
            printBill.printBill();
        }

    }//GEN-LAST:event_btnInHoaDonActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        locHoaDon();
    }//GEN-LAST:event_btnLocActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Xoa();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        TimKiem();
    }//GEN-LAST:event_txtTimKiemKeyPressed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        TimKiem();
    }//GEN-LAST:event_txtTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private controller.BlackButton blackButton1;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnXuatHoaDon;
    private com.toedter.calendar.JDateChooser dcsNgayBatDau;
    private com.toedter.calendar.JDateChooser dcsNgayKetThuc;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblHoaDon;
    public static javax.swing.JTable tblHoaDonChiTiet;
    private swing.TextFieldAnimation txtTimKiem;
    private javax.swing.JTextField txtTongTienCaoNhat;
    private javax.swing.JTextField txtTongTienThapNhat;
    // End of variables declaration//GEN-END:variables

}
