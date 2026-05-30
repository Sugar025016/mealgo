-- MealGo 測試資料
-- 放置位置：src/main/resources/data.sql
-- 注意：如果重啟會重複執行，這份使用 INSERT IGNORE 避免主鍵重複錯誤。

SET FOREIGN_KEY_CHECKS = 0;

-- 先清除舊資料，可避免測試資料混亂
-- 如果你不想清空資料，可以把 DELETE 區塊註解掉
DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM cart_items;
DELETE FROM carts;
DELETE FROM shop_category;
DELETE FROM products;
DELETE FROM shops;
DELETE FROM addresses;
DELETE FROM categories;
DELETE FROM users;

SET FOREIGN_KEY_CHECKS = 1;

-- =========================
-- Users
-- =========================
INSERT INTO users
(id, name, phone, email, password, role, email_verified_at, verify_token, verify_expire_at, create_time, update_time)
VALUES
(1, '測試使用者', '0911111111', 'user@test.com', '$2a$10$yB1VTQW2oB6tKpcdULD0gOdXvfy/YO39Y8b5gSOZkuMCYwMrQhQ5K', 'USER', NOW(), NULL, NULL, NOW(), NOW()),
(2, '店家管理員', '0922222222', 'shop@test.com', '$2a$10$yB1VTQW2oB6tKpcdULD0gOdXvfy/YO39Y8b5gSOZkuMCYwMrQhQ5K', 'ADMIN', NOW(), NULL, NULL, NOW(), NOW());
-- =========================
-- Addresses
-- =========================
INSERT INTO addresses
(id, city, area, street, detail, lat, lng, user_id, create_time, update_time)
VALUES
(1, '台南市', '東區', '大學路', '1號', 22.996900, 120.216800, 1, NOW(), NOW()),
(2, '台南市', '中西區', '民生路一段', '88號', 22.994800, 120.196700, 1, NOW(), NOW());

-- =========================
-- Categories
-- =========================
INSERT INTO categories
(id, name)
VALUES
(1, '便當'),
(2, '飲料'),
(3, '早餐'),
(4, '炸物');

-- =========================
-- Shops
-- =========================
INSERT INTO shops
(id, brand, branch, phone, description, delivery_km, delivery_price, is_open, is_orderable,
 image_path, city, area, street, detail, lat, lng, user_id, create_time, update_time)
VALUES
(1, 'MealGo 便當店', '成大店', '062222222', '提供雞腿飯、排骨飯、滷肉飯等餐點', 5.0, 50, 1, 1,
 '/images/shops/shop-1.jpg', '台南市', '東區', '大學路', '1號', 22.996900, 120.216800, 2, NOW(), NOW()),
(2, 'MealGo 飲料店', '民族店', '063333333', '茶飲、鮮奶茶、果茶', 4.0, 35, 1, 1,
 '/images/shops/shop-2.jpg', '台南市', '中西區', '民族路二段', '100號', 22.997100, 120.202100, 2, NOW(), NOW());

-- =========================
-- Shop Category
-- =========================
INSERT INTO shop_category
(shop_id, category_id)
VALUES
(1, 1),
(1, 4),
(2, 2);

-- =========================
-- Products
-- =========================
INSERT INTO products
(id, name, description, price, is_shelve, image_path, shop_id, create_time, update_time)
VALUES
(1, '招牌雞腿飯', '酥炸雞腿搭配三樣配菜', 120, 1, '/images/products/chicken-leg-rice.jpg', 1, NOW(), NOW()),
(2, '滷排骨飯', '滷排骨搭配白飯與配菜', 110, 1, '/images/products/pork-rib-rice.jpg', 1, NOW(), NOW()),
(3, '滷肉飯', '經典滷肉飯', 65, 1, '/images/products/braised-pork-rice.jpg', 1, NOW(), NOW()),
(4, '紅茶', '古早味紅茶', 30, 1, '/images/products/black-tea.jpg', 2, NOW(), NOW()),
(5, '鮮奶茶', '紅茶搭配鮮奶', 55, 1, '/images/products/milk-tea.jpg', 2, NOW(), NOW());

-- =========================
-- Carts
-- user_id + shop_id 有唯一限制，所以同一個 user 對同一間 shop 只能有一筆 cart
-- =========================
INSERT INTO carts
(id, user_id, shop_id, create_time, update_time)
VALUES
(1, 1, 1, NOW(), NOW());

-- =========================
-- Cart Items
-- =========================
INSERT INTO cart_items
(id, qty, remark, cart_id, product_id, create_time, update_time)
VALUES
(1, 2, '不要香菜', 1, 1, NOW(), NOW()),
(2, 1, '飯少一點', 1, 2, NOW(), NOW());

-- =========================
-- Orders
-- pay_method：1 現金、2 信用卡、3 LINE Pay
-- status：1 待確認、2 已接單、3 製作中、4 待取餐、5 配送中、6 已完成、7 使用者取消、8 店家未接單、9 店家拒接單
-- =========================
INSERT INTO orders
(id, order_number, order_note, delivery_price, subtotal, total_price, pay_method, status,
 city, area, street, detail, lat, lng, user_id, shop_id, create_time, update_time)
VALUES
(1, 'MG202605290001', '請打電話通知', 50, 230, 280, 1, 1,
 '台南市', '東區', '大學路', '1號', 22.996900, 120.216800, 1, 1, NOW(), NOW());

-- =========================
-- Order Items
-- 如果你的 OrderItem 欄位還是 customer_note，請把 remark 改成 customer_note
-- =========================
INSERT INTO order_items
(id, qty, price, remark, product_id, order_id, create_time, update_time)
VALUES
(1, 1, 120, '不要香菜', 1, 1, NOW(), NOW()),
(2, 1, 110, '飯少一點', 2, 1, NOW(), NOW());
