-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 19, 2023 lúc 08:18 AM
-- Phiên bản máy phục vụ: 10.4.25-MariaDB
-- Phiên bản PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ecommerce`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `category`
--

CREATE TABLE `category` (
  `id` bigint(20) NOT NULL,
  `is_actived` bit(1) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `category_name` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `category`
--

INSERT INTO `category` (`id`, `is_actived`, `is_deleted`, `category_name`) VALUES
(1, b'1', b'0', 'Coca Cola'),
(2, b'1', b'0', 'Pepsi'),
(3, b'1', b'0', 'Reb Bull'),
(4, b'1', b'0', 'Sprite'),
(5, b'1', b'0', 'Fanta'),
(6, b'1', b'0', 'Nước Khoáng');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `cus_address` varchar(255) DEFAULT NULL,
  `cus_email` varchar(255) DEFAULT NULL,
  `cus_full_name` varchar(255) DEFAULT NULL,
  `cus_note` varchar(255) DEFAULT NULL,
  `cus_phone` varchar(255) DEFAULT NULL,
  `order_code` varchar(255) DEFAULT NULL,
  `payments` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`id`, `create_date`, `cus_address`, `cus_email`, `cus_full_name`, `cus_note`, `cus_phone`, `order_code`, `payments`, `status`, `user_id`) VALUES
(1, '2023-03-17 02:14:32.000000', 'Hung Yen , Viet Nam', 'quoctruong11tv@gmail.com', 'Đào Trượng', 'dep thi lay', '0342162155', 'No_2023-03-17_02:14:34', 1, 0, 2),
(2, '2023-03-17 02:29:06.000000', 'Hung Yen , Viet Nam', 'truong@gmail.com', 'đào alo', '', '0342162155', 'No_2023-03-17_02:29:06', 2, 0, 2),
(3, '2023-03-17 02:30:53.000000', 'Hung Yen , Viet Nam', 'quoctruong11tv@gmail.com', 'Đào Trượng', '3131312', '0342162155', 'No_2023-03-17_02:30:53', 2, 0, 2),
(4, '2023-03-17 03:50:55.000000', 'Hung Yen , Viet Nam', 'truong.dq.1978@aptechlearning.edu.vn', 'Đào Trượng', 'Hàng đẹp oke', '0342162155', 'No_2023-03-17_03:50:55', 1, 0, 2),
(5, '2023-03-17 04:06:19.000000', 'Hung Yen , Viet Nam', 'truong.dq.1978@aptechlearning.edu.vn', 'Đào 1 3 5 7 alo 500 anh em', 'đến là đón\r\nđánh là chạy', '0342162155', 'No_2023-03-17_04:06:19', 2, 0, 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_detail`
--

CREATE TABLE `order_detail` (
  `id` bigint(20) NOT NULL,
  `amount` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `order_detail`
--

INSERT INTO `order_detail` (`id`, `amount`, `quantity`, `order_id`, `product_id`) VALUES
(1, 33440, 2, 1, 1),
(2, 13650, 1, 1, 2),
(3, 17010, 1, 1, 3),
(4, 36480, 2, 2, 6),
(5, 8820, 1, 2, 10),
(6, 36480, 2, 3, 6),
(7, 23760, 1, 3, 8),
(8, 17010, 1, 4, 3),
(9, 36480, 2, 4, 6),
(10, 16720, 1, 5, 1),
(11, 36480, 2, 5, 6);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `cost` bigint(20) DEFAULT NULL,
  `description` longtext DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `is_actived` bit(1) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` bigint(20) DEFAULT NULL,
  `sale` int(11) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`id`, `cost`, `description`, `image`, `is_actived`, `is_deleted`, `name`, `price`, `sale`, `title`, `category_id`) VALUES
(1, 16720, 'Coca Cola 350ml', '1678896697523_coca1.jpg', b'1', b'0', 'Coca Cola 350ml', 19000, 12, 'Coca Cola 350ml', 1),
(2, 13650, 'Pepsi 250ml', '1678896729661_pepsi1.jpg', b'1', b'0', 'Pepsi 250ml', 15000, 9, 'Pepsi 250ml', 2),
(3, 17010, 'Coca 175ml', '1678896955955_coca2.jpg', b'1', b'0', 'Coca 175ml', 21000, 19, 'Coca 175ml', 1),
(4, 14910, 'Reb bull đỏ', '1678896990329_rebbull4.jpg', b'1', b'0', 'Reb bull đỏ', 21000, 29, 'Reb bull đỏ', 3),
(5, 15980, 'Sprite 1500ml', '1678897029719_sprite2.jpg', b'1', b'0', 'Sprite 1500ml', 17000, 6, 'Sprite 1500ml', 4),
(6, 18240, 'Fanta cụ', '1678897061360_fanta5.jpg', b'1', b'0', 'Fanta cụ', 19000, 4, 'Fanta cụ', 1),
(7, 18630, 'Pepsi cụ', '1678897097845_pepsi4.jpg', b'1', b'0', 'Pepsi cụ', 23000, 19, 'Pepsi cụ', 2),
(8, 23760, 'Bò Húc đen', '1678897135285_rebbull2.jpg', b'1', b'0', 'Bò Húc đen', 27000, 12, 'Bò Húc đen', 3),
(9, 11550, 'Reb Bull nhỏ', '1678897168345_rebbull4.jpg', b'1', b'0', 'Reb Bull nhỏ', 15000, 23, 'Reb Bull nhỏ', 3),
(10, 8820, 'Fanta lon ', '1678897204524_fanta4.jpg', b'1', b'0', 'Fanta lon ', 9000, 2, 'Fanta lon ', 5),
(11, 9790, 'Nước lọc', '1678897234432_nuockhoang4.png', b'1', b'0', 'Nước lọc', 11000, 11, 'Nước lọc', 6),
(12, 12690, 'Nước lọc nguyên chất', '1678897307494_nuockhoang3.png', b'1', b'0', 'Nước lọc nguyên chất', 13500, 6, 'Nước lọc nguyên chất', 6),
(13, 8245, 'Nước bổ sung vitamin', '1678897336908_nuockhoang1.png', b'1', b'0', 'Nước bổ sung vitamin', 8500, 3, 'Nước bổ sung vitamin', 6),
(14, 209790, 'Coca nguyên két', '1678897365031_coca5.jpg', b'1', b'0', 'Coca nguyên két', 259000, 19, 'Coca nguyên két', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(45) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `full_name` varchar(20) NOT NULL,
  `is_actived` bit(1) DEFAULT NULL,
  `is_deleted` bit(1) DEFAULT NULL,
  `password` varchar(64) NOT NULL,
  `reset_password_token` varchar(30) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `verification_code` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `email`, `enabled`, `full_name`, `is_actived`, `is_deleted`, `password`, `reset_password_token`, `role`, `verification_code`) VALUES
(1, 'truong.dq.1978@aptechlearning.edu.vn', b'1', 'Đào Quốc Trượng', NULL, NULL, '12345678', NULL, 'ADMIN', NULL),
(2, 'quoctruong11tv@gmail.com', b'1', 'Đào Alo Alo', NULL, NULL, '12345678', NULL, 'USER', NULL);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`);

--
-- Chỉ mục cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrws2q0si6oyd6il8gqe2aennc` (`order_id`),
  ADD KEY `FKb8bg2bkty0oksa3wiq5mp5qnc` (`product_id`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `category`
--
ALTER TABLE `category`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Các ràng buộc cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  ADD CONSTRAINT `FKb8bg2bkty0oksa3wiq5mp5qnc` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FKrws2q0si6oyd6il8gqe2aennc` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

--
-- Các ràng buộc cho bảng `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
