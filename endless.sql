-- Tạo CSDL EndlessDB
CREATE DATABASE Endless;
GO
USE Endless;
GO

-- Tạo bảng LoaiGiay
CREATE TABLE LoaiGiay (
    MaLoaiGiay VARCHAR(10) PRIMARY KEY,
    TenLoaiGiay NVARCHAR(60) NOT NULL,
    Mota NVARCHAR(255)
);
GO

-- Tạo bảng MauSac
CREATE TABLE MauSac (
    MaMauSac VARCHAR(10) PRIMARY KEY,
    TenMauSac NVARCHAR(255) NOT NULL,
    Mota NVARCHAR(255)
);
GO

-- Tạo bảng KichThuoc
CREATE TABLE KichThuoc (
    MaKichThuoc VARCHAR(10) PRIMARY KEY,
    TenKichThuoc NVARCHAR(20) NOT NULL,
    Mota NVARCHAR(255)
);
GO

-- Tạo bảng KhuyenMai
CREATE TABLE KhuyenMai (
    MaKM VARCHAR(10) PRIMARY KEY,
    TenKM NVARCHAR(255) NOT NULL,
    MucGiamGia INT NOT NULL,
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE NOT NULL
);
GO

-- Tạo bảng NhanVien
CREATE TABLE NhanVien (
    MaNV VARCHAR(10) PRIMARY KEY,
    TenNV NVARCHAR(255) NOT NULL,
	VaiTro bit NOT NULL,
    MaTK VARCHAR(10) UNIQUE NOT NULL,
    MatKhau VARCHAR(150) NOT NULL,
	GioiTinh bit NOT NULL,
    NgaySinh DATE NOT NULL,
    SDT VARCHAR(10) NOT NULL UNIQUE,
    Email VARCHAR(50) NOT NULL UNIQUE,
    DiaChi NVARCHAR(255) NOT NULL,
	Hinh NVARCHAR(50)
);
GO

-- Tạo bảng KhachHang
CREATE TABLE KhachHang (
    MaKH VARCHAR(10) PRIMARY KEY,
    TenKH NVARCHAR(255) NOT NULL,
    SDT VARCHAR(10) NOT NULL UNIQUE,
    DiaChi NVARCHAR(255)
);
GO

-- Tạo bảng NhaCungCap
CREATE TABLE NhaCungCap (
    MaNCC VARCHAR(10) PRIMARY KEY,
    TenNCC NVARCHAR(255) NOT NULL,
    SDT VARCHAR(10) NOT NULL UNIQUE,
    Email VARCHAR(50) UNIQUE,
    DiaChi NVARCHAR(255) NOT NULL
);
GO

-- Tạo bảng SanPham
CREATE TABLE SanPham (
    MaSP VARCHAR(10) PRIMARY KEY,	
    MaKM VARCHAR(10) REFERENCES KhuyenMai(MaKM) ON DELETE SET NULL,
    TenSP NVARCHAR(60) NOT NULL,
    MaLoaiGiay VARCHAR(10) NOT NULL,
	MaVach VARCHAR(10) UNIQUE,
    DonGiaNhap DECIMAL(18, 2) NOT NULL,
    DonGiaBan DECIMAL(18, 2) NOT NULL,
	GiaKhuyenMai DECIMAL(18, 2),
	HinhAnh VARCHAR(50),
    FOREIGN KEY (MaLoaiGiay) REFERENCES LoaiGiay(MaLoaiGiay) ON DELETE CASCADE
);
GO

-- Tạo bảng ChiTietSanPham
CREATE TABLE ChiTietSanPham (
    MaCTSP VARCHAR(15) PRIMARY KEY,
    MaSP VARCHAR(10) REFERENCES SanPham(MaSP) ON DELETE CASCADE,
    MaMau VARCHAR(10) REFERENCES MauSac(MaMauSac) ON DELETE SET NULL,
    MaKT VARCHAR(10) REFERENCES KichThuoc(MaKichThuoc) ON DELETE SET NULL,
    SoLuong INT NOT NULL,
    Mota NVARCHAR(255)
);
GO

-- Tạo bảng NhapHang
CREATE TABLE NhapHang (
    MaDN VARCHAR(10) PRIMARY KEY,
	MaNCC VARCHAR(10) REFERENCES NhaCungCap(MaNCC) ON DELETE CASCADE,
    MaNV VARCHAR(10) REFERENCES NhanVien(MaNV) ON DELETE CASCADE,
    NgayNhap DATE,
    GhiChu NVARCHAR(100)
);
GO

CREATE TABLE ChiTietNhapHang (
    MaCTDN VARCHAR(15) PRIMARY KEY,
    MaDN VARCHAR(10) REFERENCES NhapHang(MaDN) ON DELETE CASCADE,
    MaCTSP VARCHAR(15) REFERENCES ChiTietSanPham(MaCTSP) ON DELETE CASCADE,
    SoLuong INT NOT NULL
);
GO

-- Tạo bảng HoaDon
CREATE TABLE HoaDon (
    MaHD VARCHAR(10) PRIMARY KEY,
    MaKH VARCHAR(10) REFERENCES KhachHang(MaKH) ON DELETE SET NULL DEFAULT 'KH000',
    MaNV VARCHAR(10) REFERENCES NhanVien(MaNV) ON DELETE SET NULL,
    NgayBan DATE,
    TongTien FLOAT,
    HTThanhToan NVARCHAR(20),
    TienThanhToan FLOAT
);
GO

-- Tạo bảng HoaDonChiTiet
CREATE TABLE HoaDonChiTiet (
    MaHDCT VARCHAR(15) PRIMARY KEY,
    MaHD VARCHAR(10) REFERENCES HoaDon(MaHD) ON DELETE CASCADE,
    MaCTSP VARCHAR(15) REFERENCES ChiTietSanPham(MaCTSP) ON DELETE CASCADE,
    SoLuong INT NOT NULL,
	ThanhTien FLOAT
);
GO


INSERT INTO LoaiGiay (MaLoaiGiay,TenLoaiGiay, Mota)
VALUES 
    ('LG001',N'Sneakers', N'Phổ biến và thoải mái, giày sneakers thường được sử dụng cho mọi hoạt động hằng ngày.'),
    ('LG002',N'Running Shoes', N'Chuyên dụng cho việc chạy bộ, giày này cung cấp sự đàn hồi và hỗ trợ cho đôi chân khi chạy.'),
    ('LG003',N'Basketball Shoes', N'Được thiết kế với đế chống trượt và hỗ trợ cổ chân, giày bóng rổ giúp cầu thủ linh hoạt và ổn định trên sân.'),
    ('LG004',N'Soccer Cleats', N'Tích hợp đinh để cung cấp độ bám trên sân cỏ, giày đá banh giúp cầu thủ chuyền bóng và chạy nhanh hơn.'),
    ('LG005',N'Football Cleats', N'Được tối ưu hóa cho cầu thủ đá bóng, giày này có đinh để cung cấp độ bám trên sân đá.'),
    ('LG006',N'Tennis Shoes', N'Thiết kế với đế phẳng và độ bám tốt, giày tennis giúp cầu thủ di chuyển linh hoạt trên sân.'),
	('LG007',N'Cross Training Shoes', N'Được thiết kế cho nhiều hoạt động tập luyện, giày này cung cấp sự ổn định và hỗ trợ cho đôi chân.'),
    ('LG008',N'Skate Shoes', N'Với đế phẳng và chống trượt, giày trượt patin giúp người đi trượt dễ dàng thực hiện các động tác.'),
    ('LG009',N'Golf Shoes', N'Với đinh để đảm bảo độ ổn định trên sân cỏ, giày golf hỗ trợ golf thủ thực hiện các cú đánh.'),
    ('LG010',N'Hiking Shoes', N'Thiết kế chống nước và có đế chống trượt, giày leo núi giúp người leo núi vượt qua địa hình khó khăn.'),
    ('LG011',N'Trail Running Shoes', N'Thiết kế cho việc chạy trên đường mòn, giày này cung cấp độ bám và bảo vệ chân trước các điều kiện đường đi khác nhau.'),
    ('LG012',N'Wrestling Shoes', N'Nhẹ và ôm chân, giày đấu vật giúp đô vật linh hoạt và nhanh nhẹn trong đấu trường.'),
    ('LG013',N'Cycling Shoes', N'Thiết kế với kẽm để kết nối với bàn đạp, giày đạp xe tăng cường sức mạnh và hiệu suất khi đạp xe.'),
    ('LG014',N'Weightlifting Shoes', N'Với đế cứng và độ nghiêng, giày này giúp tăng sức mạnh và ổn định trong khi tập luyện nâng tạ.'),
    ('LG015',N'Retro or Vintage Athletic Shoes', N'Giày thể thao cổ điển hoặc vintage, mang đến phong cách thời trang hồi xưa cho người sử dụng.');
GO

INSERT INTO MauSac (MaMauSac,TenMauSac, Mota)
VALUES 
    ('MS001',N'Đen', N'Màu cơ bản, tạo sự sang trọng và tinh tế.'),
    ('MS002',N'Trắng', N'Màu trung tính, phù hợp với nhiều phong cách và dễ kết hợp với các màu khác.'),
    ('MS003',N'Đỏ', N'Màu nổi bật, thường được sử dụng để tạo điểm nhấn và sự cuốn hút.'),
    ('MS004',N'Xanh Dương', N'Màu tươi sáng, mang đến cảm giác tươi mới và năng động.'),
    ('MS005',N'Hồng', N'Màu nữ tính, thường được ưa chuộng trong các thiết kế dành cho phụ nữ.'),
	('MS006',N'Xám', N'Màu trung tính, tạo cảm giác mềm mại và hiện đại.'),
    ('MS007',N'Vàng', N'Màu ấm áp, thường được sử dụng để tạo điểm nhấn và sự ấm cúng.'),
    ('MS008',N'Xanh Lá Cây', N'Màu tươi sáng, liên quan đến tự nhiên và môi trường.'),
    ('MS009',N'Cam', N'Màu nổi bật, mang lại cảm giác năng động và tươi mới.'),
    ('MS010',N'Nâu', N'Màu ổn định, thường được sử dụng trong thiết kế đất đai và sang trọng.'),
    ('MS011',N'Hồng Đậm', N'Màu nữ tính và quyến rũ, thường được sử dụng trong thời trang và trang điểm.'),
    ('MS012',N'Xanh Lục', N'Màu dịu dàng và thanh lịch, thường liên quan đến sự bình yên và tĩnh lặng.'),
    ('MS013',N'Đỏ Đậm', N'Màu đam mê và quyền lực, thường được sử dụng để tạo sự cuốn hút.'),
    ('MS014',N'Tím', N'Màu quý phái và lãng mạn, thường xuất hiện trong các bối cảnh sang trọng.'),
    ('MS015',N'Ghi Bạch', N'Màu trung tính, tạo cảm giác sạch sẽ và tinh tế.');
GO

INSERT INTO KichThuoc(MaKichThuoc,TenKichThuoc, Mota)
VALUES 
('KT001','30', N'Phù hợp cho chân có kích thước 160mm'),
('KT002','31', N'Phù hợp cho chân có kích thước 165mm'),
('KT003','32', N'Phù hợp cho chân có kích thước 170mm'),
('KT004','33', N'Phù hợp cho chân có kích thước 175mm'),
('KT005','34', N'Phù hợp cho chân có kích thước 180mm'),
('KT006','35', N'Phù hợp cho chân có kích thước 185mm'),
('KT007','36', N'Phù hợp cho chân có kích thước 190mm'),
('KT008','37', N'Phù hợp cho chân có kích thước 195mm'),
('KT009','38', N'Phù hợp cho chân có kích thước 200mm'),
('KT010','39', N'Phù hợp cho chân có kích thước 205mm'),
('KT011','40', N'Phù hợp cho chân có kích thước 210mm'),
('KT012','41', N'Phù hợp cho chân có kích thước 215mm'),
('KT013','42', N'Phù hợp cho chân có kích thước 220mm'),
('KT014','43', N'Phù hợp cho chân có kích thước 225mm'),
('KT015','44', N'Phù hợp cho chân có kích thước 230mm'),
('KT016','45', N'Phù hợp cho chân có kích thước 235mm');
GO

INSERT INTO KhuyenMai (MaKM,TenKM, MucGiamGia, NgayBatDau, NgayKetThuc)
VALUES 
    ('KM001',N'Khuyến mãi mùa hè', 20, '2023-06-01', '2023-08-31'),
    ('KM002',N'Khuyến mãi cuối năm', 15, '2023-11-01', '2023-12-31'),
    ('KM003',N'Khuyến mãi đặc biệt', 30, '2023-03-15', '2023-03-31'),
    ('KM004',N'Khuyến mãi Black Friday', 25, '2023-11-25', '2023-11-27'),
    ('KM005',N'Khuyến mãi Valentine', 10, '2023-02-01', '2023-02-14');
GO

INSERT INTO NhanVien (MaNV,TenNV, VaiTro, MaTK, MatKhau, GioiTinh, NgaySinh, SDT, Email, DiaChi, Hinh)
VALUES 
    ('NV001',N'Lý Tính Nhiệm',1, 'admin', '$2a$12$V00YlbPVb1witx/cbj79DeuOoTK9UjLZdNjsgLavi3Sij41DjbvMm', 1, '1990-01-01', '0787833283', 'lytinhnhiem@gmail.com', N'123 Đường ABC, Q1, TP.HCM', 'nva.jpg'),
    ('NV002',N'Võ Thị Thảo Nguyên',0, 'vttN456', 'Nguyen1020', 0,  '1995-05-02', '0987654245', 'nguyenvttpc05404@fpt.edu.vn', N'456 Đường XYZ, Q2, TP.HCM', 'nv1.jpg'),
    ('NV003',N'Hà Thanh Nhẹ', 0,'htN6813', 'Nhe8437', 1, '1988-08-10', '0123456799', 'nhehtpc05420@fpt.edu.vn', N'789 Đường UVW, Q3, TP.HCM', 'nv2.jpg'),
    ('NV004',N'Nguyễn Thanh Nhả', 0,'ntN794', 'Nha9270', 1, '1993-03-12', '0987654321', 'nhantpc06089@fpt.edu.vn', N'012 Đường DEF, Q4, TP.HCM', 'nv2.jpg'),
	('NV005',N'Phan Minh Sang', 0,'pmS992', 'Sang7921', 1, '1993-03-02', '0987654361', 'sangpmpc05409@fpt.edu.vn', N'012 Đường DEF, Q4, TP.HCM', 'nv3.jpg'),
	('NV006',N'Trần Y Mỹ', 0,'tyM990', 'My9102', 0, '1993-03-12', '0987658907', 'mytypc05159@fpt.edu.vn', N'012 Đường DEF, Q4, TP.HCM', 'nv4.jpg'),
    ('NV007',N'Võ Khách Toàn',0, 'vkTE345', 'Toan7292', 1, '1997-07-05', '0123455468', 'toanvkpc05433@fpt.edu.vn', N'345 Đường GHI, Q5, TP.HCM', 'nv3.jpg');
GO

INSERT INTO KhachHang (MaKH,TenKH, SDT, DiaChi)
VALUES 
	('KH000',N'Khách vãng lai', '', ''),
    ('KH001',N'Nguyễn Thị Hồng Vân', '0123456789', N'123 Đường ABC, Q1, TP.HCM'),
    ('KH002',N'Lê Thị Quỳnh Anh', '0987654323', N'456 Đường XYZ, Q2, TP.HCM'),
    ('KH003',N'Lưu Trọng Trí', '0123456788', N'789 Đường UVW, Q3, TP.HCM'),
    ('KH004',N'Trần Tuấn Kiệt', '0987654321', N'012 Đường DEF, Q4, TP.HCM'),
    ('KH005',N'Huỳnh Chí Thiện', '0123456787', N'345 Đường GHI, Q5, TP.HCM'),
    ('KH006',N'Nguyễn Thị Phương Anh', '0901111452', N'123 Đường ABC, Q1, TP.HCM'),
    ('KH007',N'Lê Thị Mai Anh', '0913339444', N'456 Đường XYZ, Q2, TP.HCM'),
    ('KH008',N'Lưu Thị Lan Anh', '0925655666', N'789 Đường UVW, Q3, TP.HCM'),
    ('KH009',N'Trần Thị Hồng Anh', '0937707888', N'012 Đường DEF, Q4, TP.HCM'),
    ('KH010',N'Huỳnh Thị Hoa', '0949899000', N'345 Đường GHI, Q5, TP.HCM'),
    ('KH011',N'Võ Thị Hương', '0955555111', N'678 Đường JKL, Q6, TP.HCM'),
    ('KH012',N'Đặng Thị Hồng Ngọc', '0966166222', N'901 Đường MNO, Q7, TP.HCM'),
    ('KH013',N'Phạm Thị Thùy Trang', '0977777332', N'234 Đường PQR, Q8, TP.HCM'),
    ('KH014',N'Trần Thị Hải Yến', '0988488444', N'567 Đường STU, Q9, TP.HCM'),
    ('KH015',N'Nguyễn Thị Thảo Vy', '0990999555', N'890 Đường VWX, Q10, TP.HCM'),
    ('KH016',N'Lê Thị Thu Hà', '0911112122', N'111 Đường ABC, Q1, TP.HCM'),
    ('KH017',N'Nguyễn Thị Thùy Dung', '0922122333', N'2222 Đường XYZ, Q2, TP.HCM'),
    ('KH018',N'Phan Thị Kim Ngân', '0933033444', N'3333 Đường UVW, Q3, TP.HCM'),
    ('KH019',N'Bùi Thị Ngọc Lan', '0945444555', N'4444 Đường DEF, Q4, TP.HCM'),
    ('KH020',N'Trần Thị Kim Anh', '0953555666', N'5555 Đường GHI, Q5, TP.HCM'),
    ('KH021',N'Vũ Thị Thu Hương', '0966866777', N'6666 Đường JKL, Q6, TP.HCM'),
    ('KH022',N'Lê Thị Bích Ngọc', '0977977888', N'7777 Đường MNO, Q7, TP.HCM'),
    ('KH023',N'Đinh Thị Mai Anh', '0981888999', N'8888 Đường PQR, Q8, TP.HCM'),
    ('KH024',N'Ngô Thị Hoài Linh', '0999229000', N'9999 Đường STU, Q9, TP.HCM'),
    ('KH025',N'Lý Thị Diệu Linh', '0901234876', N'1000 Đường VWX, Q10, TP.HCM'),
    ('KH026',N'Mai Thị Ngọc Trâm', '0901134222', N'1111 Đường ABC, Q1, TP.HCM'),
    ('KH027',N'Nguyễn Thị Thanh Thủy', '0955333444', N'2222 Đường XYZ, Q2, TP.HCM'),
    ('KH028',N'Trần Thị Thùy Linh', '0925525666', N'3333 Đường UVW, Q3, TP.HCM'),
    ('KH029',N'Phạm Thị Thanh Hằng', '0939077888', N'4444 Đường DEF, Q4, TP.HCM'),
    ('KH030',N'Bùi Thị Thảo Nhi', '0949239000', N'5555 Đường GHI, Q5, TP.HCM'),
    ('KH031',N'Lê Thị Minh Thu', '0955125111', N'6666 Đường JKL, Q6, TP.HCM'),
    ('KH032',N'Nguyễn Thị Thùy Dương', '0966126222', N'7777 Đường MNO, Q7, TP.HCM'),
    ('KH033',N'Phan Thị Hồng Loan', '0990777733', N'8888 Đường PQR, Q8, TP.HCM'),
    ('KH034',N'Đặng Thị Hà My', '0989888844', N'9999 Đường STU, Q9, TP.HCM'),
    ('KH035',N'Trần Thị Kim Chi', '0991199555', N'1010 Đường VWX, Q10, TP.HCM');
GO

INSERT INTO NhaCungCap (MaNCC,TenNCC, SDT, Email, DiaChi)
VALUES 
    ('NCC01',N'Trần Minh Đạt', '0023456789', 'ncc1@email.com', N'123 Đường ABC, Q1, TP.HCM'),
    ('NCC02',N'Nguyễn Thị Hoài', '0387654321', 'ncc2@email.com', N'456 Đường XYZ, Q2, TP.HCM'),
    ('NCC03',N'Lê Văn Tâm', '0123456789', 'ncc3@email.com', N'789 Đường UVW, Q3, TP.HCM'),
    ('NCC04',N'Phạm Thị Thủy', '0987654321', 'ncc4@email.com', N'012 Đường DEF, Q4, TP.HCM'),
    ('NCC05',N'Trương Minh Tuấn', '0123556789', 'ncc5@email.com', N'345 Đường GHI, Q5, TP.HCM'),
    ('NCC06',N'Đỗ Thị Hương', '0987653321', 'ncc6@email.com', N'678 Đường JKL, Q6, TP.HCM'),
    ('NCC07',N'Vũ Minh Hoàng', '0143456789', 'ncc7@email.com', N'901 Đường MNO, Q7, TP.HCM'),
    ('NCC08',N'Lý Thị Mai', '0989654321', 'ncc8@email.com', N'234 Đường PQR, Q8, TP.HCM'),
    ('NCC09',N'Trần Minh Quang', '0122456789', 'ncc9@email.com', N'567 Đường STU, Q9, TP.HCM'),
    ('NCC10',N'Nguyễn Thị Ngọc', '0997654321', 'ncc10@email.com', N'890 Đường VWX, Q10, TP.HCM'),
    ('NCC11',N'Trần Văn Đức', '0902311222', 'ncc11@email.com', N'111 Đường ABC, Q1, TP.HCM'),
    ('NCC12',N'Nguyễn Thị Lan', '0916733444', 'ncc12@email.com', N'222 Đường XYZ, Q2, TP.HCM'),
    ('NCC13',N'Hoàng Minh Tâm', '0928955666', 'ncc13@email.com', N'333 Đường UVW, Q3, TP.HCM'),
    ('NCC14',N'Lê Thị Thu', '0911777888', 'ncc14@email.com', N'444 Đường DEF, Q4, TP.HCM'),
    ('NCC15',N'Phan Thanh Hải', '0949769000', 'ncc15@email.com', N'555 Đường GHI, Q5, TP.HCM'),
    ('NCC16',N'Đặng Thị Hà', '0959855111', 'ncc16@email.com', N'666 Đường JKL, Q6, TP.HCM'),
    ('NCC17',N'Nguyễn Văn Phú', '0961266222', 'ncc17@email.com', N'777 Đường MNO, Q7, TP.HCM'),
    ('NCC18',N'Trần Thị Ngân', '0971277333', 'ncc18@email.com', N'888 Đường PQR, Q8, TP.HCM'),
    ('NCC19',N'Lê Thanh Tùng', '0982488444', 'ncc19@email.com', N'999 Đường STU, Q9, TP.HCM'),
    ('NCC20',N'Phạm Thị Kim', '0907999555', 'ncc20@email.com', N'1010 Đường VWX, Q10, TP.HCM'),
    ('NCC21',N'Võ Văn Hòa', '0901122222', 'ncc21@email.com', N'1111 Đường ABC, Q1, TP.HCM'),
    ('NCC22',N'Lưu Thị Thuận', '0913345444', 'ncc22@email.com', N'2222 Đường XYZ, Q2, TP.HCM'),
    ('NCC23',N'Nguyễn Thị Trâm', '0925897666', 'ncc23@email.com', N'3333 Đường UVW, Q3, TP.HCM'),
    ('NCC24',N'Trần Minh Hiếu', '0932457888', 'ncc24@email.com', N'4444 Đường DEF, Q4, TP.HCM'),
    ('NCC25',N'Phạm Văn Phương', '0949009000', 'ncc25@email.com', N'5555 Đường GHI, Q5, TP.HCM'),
    ('NCC26',N'Lê Thị Thu Hà', '0955925111', 'ncc26@email.com', N'6666 Đường JKL, Q6, TP.HCM'),
    ('NCC27',N'Hoàng Thị Loan', '0963686222', 'ncc27@email.com', N'7777 Đường MNO, Q7, TP.HCM'),
    ('NCC28',N'Lý Văn Dũng', '0971077333', 'ncc28@email.com', N'8888 Đường PQR, Q8, TP.HCM'),
    ('NCC29',N'Nguyễn Thị Hương', '0976388444', 'ncc29@email.com', N'9999 Đường STU, Q9, TP.HCM'),
    ('NCC30',N'Đinh Văn Hiển', '0909299555', 'ncc30@email.com', N'10101 Đường VWX, Q10, TP.HCM');
GO


INSERT INTO SanPham (MaSP, MaKM, TenSP, MaLoaiGiay, MaVach, DonGiaNhap, DonGiaBan, GiaKhuyenMai, HinhAnh)
VALUES 
	('SP001',null, N'Giày thể thao', 'LG001', '000001', 900000, 1100000, null, 'Sneaker1.jpg'),
    ('SP002',null, N'Giày chạy bộ', 'LG002', '000002', 500000, 700000,null, 'RunningShoes1.jpg'),
    ('SP003',null, N'Giày bóng rổ', 'LG003', '000003', 700000, 1000000, null, 'BasketballShoes1.jpg'),
	('SP004',null, N'Giày đá banh', 'LG004', '000008', 200000, 400000, null, 'SoccerCleats1.jpg'),
    ('SP005',null, N'Giày tập luyện đa năng', 'LG007', '000004', 400000, 600000, null, 'CrossTrainingShoes2.jpg'),
    ('SP006',null, N'Giày golf', 'LG009', '000005', 600000, 800000, null, 'GolfShoes2.jpg'),
	('SP007',null, N'Giày trượt patin', 'LG008', '000009', 600000, 900000, null, 'SkateShoes3.jpg'),
	('SP008',null, N'Giày chạy đường mòn', 'LG011', '000010', 800000, 1000000, null, 'TrailRunningShoes2.jpg'),
	('SP009',null, N'Giày đấu vật', 'LG012', '000006', 300000, 500000, null, 'WrestlingShoes1.jpg'),
    ('SP010',null, N'Giày leo núi', 'LG010', '000007', 300000, 500000, null, 'HikingShoes1.jpg');
GO

INSERT INTO ChiTietSanPham (MaCTSP, MaSP, MaMau, MaKT, SoLuong, Mota)
VALUES 
    ('SP0001-1','SP001', 'MS006', 'KT011', 100, N'Size 40- Màu trắng xám'),
    ('SP0002-1','SP002', 'MS004', 'KT006', 50, N'Size 35- Màu xanh dương'),
    ('SP0003-1','SP003', 'MS008', 'KT015', 80, N'Size 44 - Màu xanh lá'),
    ('SP0004-1','SP006', 'MS014', 'KT001', 120, N'Size 30 - Màu tím trắng'),
    ('SP0005-1','SP010', 'MS010', 'KT003', 60, N'Size 32 - Màu nâu trắng');
GO

INSERT INTO NhapHang (MaDN,MaNCC, MaNV, NgayNhap, GhiChu)
VALUES 
    ('DN001','NCC01', 'NV001', '2023-11-10', N'Nhập hàng giày Sneaker'),
    ('DN002','NCC02', 'NV001', '2023-11-11', N'Nhập hàng giày Running Shoes'),
    ('DN003','NCC03', 'NV001', '2023-11-12', N'Nhập hàng giày Basketball Shoes'),
    ('DN004','NCC04', 'NV001', '2023-11-13', N'Nhập hàng giày Cross Training Shoes'),
    ('DN005','NCC05', 'NV001', '2023-11-14', N'Nhập hàng giày Golf Shoes');
GO

INSERT INTO ChiTietNhapHang (MaCTDN,MaDN, MaCTSP, SoLuong)
VALUES 
    ('DN0001-1','DN001', 'SP0001-1', 300),
    ('DN0002-1','DN002', 'SP0002-1', 230),
    ('DN0003-1','DN003', 'SP0003-1', 140),
    ('DN0004-1','DN004', 'SP0004-1', 57),
    ('DN0005-1','DN005', 'SP0005-1', 89);
GO

INSERT INTO HoaDon (MaHD, MaKH, MaNV, NgayBan, TongTien, HTThanhToan, TienThanhToan)
VALUES 
    ('HD001','KH001', 'NV002', '2023-11-15', 5500000, N'Chuyển khoản', 5500000),
	('HD002','KH002', 'NV001', '2023-11-15', 2100000, N'Chuyển khoản', 2100000),
	('HD003','KH003', 'NV001', '2023-11-15', 2000000, N'Chuyển khoản', 2000000),
	('HD004','KH004', 'NV005', '2023-11-16', 2000000, N'Chuyển khoản', 2000000),
	('HD005','KH005', 'NV005', '2023-11-16', 400000, N'Chuyển khoản', 400000),
	('HD006','KH006', 'NV001', '2023-11-16', 500000, N'Chuyển khoản', 500000),
	('HD007','KH007', 'NV003', '2023-11-17', 1200000, N'Chuyển khoản', 1200000),
	('HD008','KH008', 'NV005', '2023-11-17', 5000000, N'Chuyển khoản', 5000000),
	('HD009','KH009', 'NV001', '2023-11-18', 3500000, N'Chuyển khoản', 3500000),
	('HD010','KH010', 'NV002', '2023-11-19', 4400000, N'Chuyển khoản', 4400000),
	('HD011','KH011', 'NV004', '2023-11-20', 4000000, N'Chuyển khoản', 4000000),
	('HD012','KH012', 'NV003', '2023-11-20', 2000000, N'Chuyển khoản', 2000000);
GO

-- Thêm dữ liệu vào bảng HoaDonChiTiet
INSERT INTO HoaDonChiTiet (MaHDCT, MaHD, MaCTSP, SoLuong, ThanhTien)
VALUES 
    ('HD0001-1','HD001', 'SP0001-1', 5, 50000),
    ('HD0002-1','HD002', 'SP0002-1', 3, 100000),
	('HD0003-1','HD003', 'SP0003-1', 2, 2000000),
    ('HD0004-1','HD004', 'SP0003-1', 2, 7000000),
	('HD0005-1','HD005', 'SP0004-1', 1, 91200000),
	('HD0006-1','HD006', 'SP0005-1', 1, 67000000),
	('HD0007-1','HD007', 'SP0004-1', 3, 2000000),
    ('HD0008-1','HD008', 'SP0003-1', 5, 400000),
	('HD0009-1','HD009', 'SP0002-1', 5, 2000000)
GO


CREATE PROCEDURE ThongKeSanPham
    @LoaiHang NVARCHAR(60) = NULL,
    @HinhThucThongKe NVARCHAR(50) = NULL
AS
BEGIN
    SELECT
        LG.TenLoaiGiay AS 'Tên loại hàng',
        SP.TenSP AS 'Tên sản phẩm',
        ISNULL(SUM(CTSanPham.SoLuong), 0) AS 'Số lượng trong kho',
        ISNULL(SUM(HDCT.SoLuong), 0) AS 'Số lượng đã bán',
        ISNULL(SUM(HDCT.ThanhTien), 0) AS 'Doanh thu'
    FROM 
        SanPham SP 
        INNER JOIN LoaiGiay LG ON LG.MaLoaiGiay = SP.MaLoaiGiay
        LEFT JOIN ChiTietSanPham CTSanPham ON SP.MaSP = CTSanPham.MaSP
        LEFT JOIN (
            SELECT CTSanPham.MaSP, SUM(HDCT.SoLuong) AS SoLuong, SUM(HDCT.ThanhTien) AS ThanhTien
            FROM HoaDonChiTiet HDCT
            INNER JOIN ChiTietSanPham CTSanPham ON HDCT.MaCTSP = CTSanPham.MaCTSP
            GROUP BY CTSanPham.MaSP
        ) HDCT ON SP.MaSP = HDCT.MaSP
    WHERE
        (@LoaiHang IS NULL OR LG.TenLoaiGiay = @LoaiHang)
    GROUP BY LG.TenLoaiGiay, SP.TenSP
    ORDER BY
        CASE
            WHEN @HinhThucThongKe = N'Top sản phẩm có doanh thu cao nhất' THEN ISNULL(SUM(HDCT.SoLuong * SP.DonGiaBan), 0)
            WHEN @HinhThucThongKe = N'Top sản phẩm được mua nhiều nhất' THEN ISNULL(SUM(HDCT.SoLuong), 0)
            WHEN @HinhThucThongKe = N'Top sản phẩm tồn kho nhiều nhất' THEN ISNULL(SUM(CTSanPham.SoLuong), 0)
        END DESC, 
		CASE
            WHEN @HinhThucThongKe = N'Top sản phẩm có doanh thu thấp nhất' THEN ISNULL(SUM(HDCT.SoLuong * SP.DonGiaBan), 0)
            WHEN @HinhThucThongKe = N'Top sản phẩm được mua ít nhất' THEN ISNULL(SUM(HDCT.SoLuong), 0)
            WHEN @HinhThucThongKe = N'Top sản phẩm tồn kho ít nhất' THEN ISNULL(SUM(CTSanPham.SoLuong), 0)
        END ASC;
END;
GO



CREATE PROCEDURE ThongKeDoanhThu
    @NgayBatDau DATE,
    @NgayKetThuc DATE,
    @CheDoXem NVARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON;

    IF @CheDoXem = N'Theo năm'
    BEGIN
        SELECT YEAR(NgayBan) AS ThoiGian,
               MAX(TongTien) AS DoanhThuCaoNhat,
               MIN(TongTien) AS DoanhThuThapNhat,
               AVG(TongTien) AS DoanhThuTrungBinh,
               SUM(TongTien) AS TongDoanhThu
        FROM HoaDon
        WHERE NgayBan BETWEEN @NgayBatDau AND @NgayKetThuc
        GROUP BY YEAR(NgayBan)
        ORDER BY YEAR(NgayBan);
    END
    ELSE IF @CheDoXem = N'Theo tháng'
    BEGIN
        SELECT FORMAT(NgayBan, 'MM/yyyy') AS ThoiGian,
               MAX(TongTien) AS DoanhThuCaoNhat,
               MIN(TongTien) AS DoanhThuThapNhat,
               AVG(TongTien) AS DoanhThuTrungBinh,
               SUM(TongTien) AS TongDoanhThu
        FROM HoaDon
        WHERE NgayBan BETWEEN @NgayBatDau AND @NgayKetThuc
        GROUP BY FORMAT(NgayBan, 'MM/yyyy')
        ORDER BY FORMAT(NgayBan, 'MM/yyyy');
    END
END

