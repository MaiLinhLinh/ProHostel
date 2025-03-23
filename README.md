### QUẢN LÍ KHÁCH SẠN - PROHOSTEL
---
#### Mô tả

ProHostel là ứng dụng quản lý khách sạn giúp tối ưu hóa việc đặt phòng, thanh toán và quản lý thông tin khách hàng. Với giao diện thân thiện và tính năng linh hoạt, ProHostel hỗ trợ vận hành hiệu quả và nâng cao trải nghiệm cho cả người quản lý và khách hàng.

#### Tác giả
- Văn Thị Mai Linh

---
#### 📚 Nội dung
- [QUẢN LÍ KHÁCH SẠN - PROHOSTEL](#quản-lí-khách-sạn---prohostel)
  - [Mô tả](#mô-tả)
  - [Tác giả](#tác-giả)
  - [📚 Nội dung](#-nội-dung)
  - [1. Cấu trúc thư mục](#1-cấu-trúc-thư-mục)
  - [2. Tính năng](#2-tính-năng)
  - [3. Ảnh chụp màn hình](#3-ảnh-chụp-màn-hình)
  - [4. Video demo sản phẩm](#4-video-demo-sản-phẩm)
  - [5. Môi trường](#5-môi-trường)
  - [6. Cài đặt dự án](#6-cài-đặt-dự-án)
---
#### 1. Cấu trúc thư mục
    ProHostel/
    ├── src
    |    └── main
    |        ├── java
    |        |    └── org.example.prohostel
    |        |        ├── Model
    |        |        |    ├── Booking.java
    |        |        |    ├── GuestManager.java
    |        |        |    ├── Invoice.java
    |        |        |    └──....
    |        |        ├── AdminHome.java
    |        |        ├── BookingRoom.java
    |        |        ├── CheckoutPayment.java
    |        |        ├── GuestHome.java
    |        |        └──...
    |        └── resources
    |            ├── Image
    |            └── org.example.prohostel
    |                ├── AdminHome.fxml
    |                ├── BookingRoom.fxml      
    |                ├── GuestHome.fxml
    |                └──...
    ├── Accounts.dat
    ├── avatar_sources.txt
    ├── Guests.dat
    ├── Invoices.dat
    └── Rooms.dat

#### 2. Tính năng
- Đối với tài khoản admin:
    + Đặt phòng.
    + Thanh toán.
    + Xem danh sách khách hàng.
    + Xem danh sách hoá đơn.
    + Xem danh sách phòng, thêm, sửa, xoá phòng.
    + Cấp quyền, huỷ quyền admin cho tài khoản khác.
- Đối với tài khoản khách hàng:
    + Đặt phòng
    + Thanh toán online
    + Xem lịch sử đặt phòng của tài khoản
#### 3. Ảnh chụp màn hình
a) Đăng nhập

![alt text](image.png)

b) Đăng kí

![alt text](image-1.png)

c) Màn hình tài khoản Admin

![alt text](image-2.png)

d) Mà hình tài khoản khách hàng

![alt text](image-3.png)

#### 4. Video demo sản phẩm
https://youtu.be/M0plYF6yOdg

#### 5. Môi trường
- Inteliji
- Ngôn ngữ: JavaFX
- Công cụ hỗ trợ: Scene Builder
#### 6. Cài đặt dự án
- Clone dự án
- Mở terminal và chạy lệnh: mvn clean install
- Hoặc sử dụng inteliji
- Chạy ứng dụng

