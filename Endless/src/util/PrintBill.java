package util;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;
import model.HoaDonChiTiet;

public class PrintBill implements Printable {

    private List<model.HoaDonChiTiet> chiTietList;
    private model.HoaDon hoaDon;
    SanPhamDAO spDAO = new SanPhamDAO();
    ChiTietSanPhamDAO ctspDAO = new ChiTietSanPhamDAO();
    XNumber xn = new XNumber();

    public PrintBill(model.HoaDon hoaDon, List<model.HoaDonChiTiet> chiTietList) {
        this.hoaDon = hoaDon;
        this.chiTietList = chiTietList;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws java.awt.print.PrinterException {

        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        // Bắt đầu vẽ nội dung hóa đơn
        Font fontHeader = new Font("Arial", Font.BOLD, 16);
        Font fontNormal = new Font("Arial", Font.PLAIN, 12);

        int x = 50; // Điểm bắt đầu vẽ theo trục x
        int y = 50; // Điểm bắt đầu vẽ theo trục y

        // Vẽ thông tin cửa hàng
        g2d.setFont(fontHeader);
        g2d.drawString("ENDLESS SHOESHOP", x, y);
        y += 20;
        g2d.setFont(fontNormal);
        g2d.drawString("-Bức phá giới hạn-", x, y);
        y += 20;
        g2d.drawString("ĐC: Toà nhà FPT Polytechnic, Cần Thơ", x, y);
        y += 20;
        g2d.drawString("Email: Endless.shoeshop@gmail.com", x, y);

        // Vẽ thông tin hóa đơn
        KhachHangDAO khDAO = new KhachHangDAO();
        // Thêm thông tin tiền thối lại
// Sắp xếp lại các phần tử trong hóa đơn
        x = 50;
        y += 30;

// Vẽ thông tin hóa đơn
        g2d.setFont(fontNormal);
        g2d.drawString("Mã Hoá Đơn: " + hoaDon.getMaHD(), x, y);
        x += 150;
        g2d.drawString("Ngày Bán: " + hoaDon.getNgayBan(), x, y);
        x += 150;
        g2d.drawString("Khách Hàng: " + khDAO.selectByID(hoaDon.getMaKH()).getTenKH(), x, y);

// Vẽ bảng sản phẩm
        x = 50;
        y += 30;
        g2d.drawLine(x, y, x + 500, y); // Đường ngang đầu bảng

        y += 20;
        g2d.drawString("SL", x, y);
        x += 30;
        g2d.drawString("Tên sản phẩm", x, y);
        x += 150;
        g2d.drawString("Giá bán", x, y);
        x += 100;
        g2d.drawString("Giá khuyến mãi", x, y);
        x += 130;
        g2d.drawString("Thành tiền", x, y);

        y += 20;
        g2d.drawLine(x - 410, y, x + 90, y); // Đường ngang phía dưới tiêu đề cột

// Vẽ sản phẩm
        for (HoaDonChiTiet chiTiet : chiTietList) {
            model.SanPham sp = spDAO.selectById(ctspDAO.selectById(chiTiet.getMaCTSP()).getMaSP() + "");
            x = 50;
            y += 20;
            g2d.drawString(Integer.toString(chiTiet.getSoLuong()), x, y); // số lượng
            x += 30;
            g2d.drawString(sp.getTenSP(), x, y); // tên sản phẩm
            x += 150;
            g2d.drawString(xn.formatDecimal(sp.getDonGiaBan()), x, y); // giá bán
            x += 100;
            g2d.drawString(xn.formatDecimal(sp.getGiaKhuyenMai()), x, y); // Khuyến mãi
            x += 130;
            if (sp.getGiaKhuyenMai() == 0) {
                g2d.drawString(xn.formatDecimal(sp.getDonGiaBan() * chiTiet.getSoLuong()), x, y); // Thành tiền
            } else {
                g2d.drawString(xn.formatDecimal(sp.getGiaKhuyenMai() * chiTiet.getSoLuong()), x, y); // Thành tiền
            }
        }

// Vẽ tổng số tiền
        x = 50;
        y += 30;
        g2d.drawLine(x, y, x + 500, y); // Đường ngang cuối bảng

        y += 20;
        Font fontTongTien = new Font("Arial", Font.BOLD, 14); // Chọn kích thước font mới
        g2d.setFont(fontTongTien);
        g2d.drawString("Tổng Tiền: " + xn.formatDecimal(hoaDon.getTongTien()) + " VNĐ", x, y);
        y += 30;
        g2d.setFont(fontNormal); // Quay về kích thước font bình thường
        g2d.drawString("Hình thức thanh toán: " + hoaDon.getHTThanhToan(), x, y);
        double tienThoiLai = hoaDon.getTienThanhToan() - hoaDon.getTongTien();
        y += 20;
        g2d.drawString("Tiền nhận: " + xn.formatDecimal(hoaDon.getTienThanhToan()) + " VNĐ", x, y);
        y += 20;
        g2d.drawString("Tiền thối lại: " + (tienThoiLai >= 0 ? tienThoiLai : 0) + " VNĐ", x, y);

// Vẽ chân trang
        y += 30;
        x += 120;
        g2d.drawString("Cảm ơn bạn đã tin tưởng và ủng hộ cửa hàng <3", x, y);
        y += 20;
        x -= 80;
        g2d.drawString("Chúc anh sống bình an trong ánh sáng hào quang của 10 phương chư Phật, Amen !", x, y);
        return PAGE_EXISTS;
    }

    public void printBill() {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(this);

            if (job.printDialog()) {
                job.print();
            }
        } catch (PrinterException e) {
            e.printStackTrace();
            System.out.println("Hello ");
        }
    }
}
