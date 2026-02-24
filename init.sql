-- user-service DB는 MySQL 기본 설정으로 db01 생성됨
-- order-service 전용 DB 추가 생성
CREATE DATABASE IF NOT EXISTS order_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
