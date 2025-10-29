# Hệ Thống Quản Lý Nông Trại Thông Minh

![Farm Management System](https://img.shields.io/badge/Status-Active-success)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.0-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Chart.js](https://img.shields.io/badge/Chart.js-3.7.0-yellow)

Hệ thống giám sát và quản lý nông trại thông minh, cho phép theo dõi nhiệt độ và độ ẩm đất theo thời gian thực.

## 📋 Tính Năng Chính

- 📊 Hiển thị biểu đồ nhiệt độ và độ ẩm đất theo thời gian thực
- ⏱️ Lịch sử dữ liệu theo ngày/tuần/tháng
- 🔔 Cảnh báo khi nhiệt độ hoặc độ ẩm vượt ngưỡng
- 📱 Giao diện đáp ứng, tương thích với mọi thiết bị
- 🔄 Cập nhật dữ liệu tự động mỗi 30 giây

## 🚀 Yêu Cầu Hệ Thống

- Java 17 hoặc cao hơn
- MySQL 8.0 hoặc cao hơn
- Maven 3.6.3 hoặc cao hơn
- Node.js và npm (cho frontend)

## 🛠️ Cài Đặt

### 1. Cài đặt cơ sở dữ liệu

1. Tạo cơ sở dữ liệu MySQL:
   ```sql
   CREATE DATABASE farm_management;
   ```

2. Chạy script khởi tạo bảng và dữ liệu mẫu:
   ```bash
   mysql -u root -p farm_management < database/init.sql
   ```

### 2. Cấu hình ứng dụng

Chỉnh sửa file `src/main/resources/application.properties` với thông tin kết nối MySQL của bạn:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/farm_management?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Khởi chạy ứng dụng

1. Biên dịch và chạy ứng dụng bằng Maven:
   ```bash
   mvn spring-boot:run
   ```

2. Mở trình duyệt và truy cập:
   ```
   http://localhost:8080
   ```

## 📊 Sử Dụng

- **Xem dữ liệu hiện tại**: Nhiệt độ và độ ẩm đất hiển thị trên các thẻ ở đầu trang
- **Chọn khoảng thời gian**: Nhấn vào các nút "Ngày", "Tuần", "Tháng" để xem dữ liệu theo từng khoảng thời gian
- **Bật/tắt theo dõi**: Sử dụng công tắc để bật/tắt hiển thị nhiệt độ hoặc độ ẩm trên biểu đồ

## 🗄️ Cấu Trúc Thư Mục

```
farm-management/
├── src/
│   ├── main/
│   │   ├── java/com/farm/
│   │   │   ├── controller/    # Các controller xử lý request
│   │   │   ├── model/         # Các entity và DTO
│   │   │   ├── repository/    # Các interface repository
│   │   │   ├── service/       # Các service xử lý nghiệp vụ
│   │   │   └── FarmManagementApplication.java  # Khởi chạy ứng dụng
│   │   └── resources/
│   │       ├── static/        # Tài nguyên tĩnh (CSS, JS, hình ảnh)
│   │       └── templates/     # Các file template Thymeleaf
│   └── test/                  # Các bài kiểm thử
├── database/                  # Các script SQL
├── pom.xml                   # Cấu hình Maven
└── README.md                 # Tài liệu này
```

## 🔧 Công Nghệ Sử Dụng

- **Backend**:
  - Spring Boot 2.7.0
  - Spring Data JPA
  - Spring Web
  - Thymeleaf

- **Frontend**:
  - Bootstrap 5
  - Chart.js
  - jQuery

- **Cơ sở dữ liệu**:
  - MySQL 8.0

## 📝 Giấy Phép

Dự án này được phát triển bởi [Tên của bạn] và được cấp phép theo giấy phép MIT.

---

<div align="center">
  <p>Được phát triển với ❤️ bởi Đội ngũ phát triển</p>
  <p>© 2025 Hệ Thống Quản Lý Nông Trại Thông Minh</p>
</div>
