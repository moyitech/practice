CREATE DATABASE IF NOT EXISTS carbon DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE carbon;

-- Core auth/system tables
CREATE TABLE IF NOT EXISTS sys_tenant (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  tenant_name VARCHAR(128),
  contacts_phone VARCHAR(32),
  address VARCHAR(255),
  remarks VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS sys_account (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  account_name VARCHAR(64),
  username VARCHAR(64),
  password VARCHAR(255),
  phone VARCHAR(32),
  avatar VARCHAR(255),
  email VARCHAR(255),
  product_version VARCHAR(64),
  token VARCHAR(255),
  account_type VARCHAR(64),
  account_status VARCHAR(32),
  tenant_id BIGINT,
  remarks VARCHAR(255),
  creator_id BIGINT,
  updated_id BIGINT,
  created_time DATETIME,
  updated_time DATETIME,
  validity_period DATE,
  INDEX idx_sys_account_tenant (tenant_id),
  INDEX idx_sys_account_name (account_name),
  INDEX idx_sys_account_phone (phone)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS sys_role (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  role_code VARCHAR(64),
  role_name VARCHAR(128)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS sys_permission (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  uri VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS sys_account_role (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  account_id BIGINT,
  role_id BIGINT,
  INDEX idx_sys_account_role_account (account_id),
  INDEX idx_sys_account_role_role (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS sys_role_permission (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  role_id BIGINT,
  permission_id BIGINT,
  INDEX idx_sys_role_permission_role (role_id),
  INDEX idx_sys_role_permission_permission (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS data_panel (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  home_data LONGTEXT,
  tenant_id BIGINT,
  INDEX idx_data_panel_tenant (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Carbon project/asset tables
CREATE TABLE IF NOT EXISTS carbon_project (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  project_name VARCHAR(255),
  estimated_reduction DECIMAL(20,4),
  project_status VARCHAR(32)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS carbon_exchange (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  website VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS carbon_methodology (
  dict_code VARCHAR(64) PRIMARY KEY,
  name VARCHAR(255),
  certification_criteria VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS carbon_metaregistry (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  project_name VARCHAR(255),
  methodology_code VARCHAR(64),
  methodology_name VARCHAR(255),
  certified_standard VARCHAR(255),
  project_verifier VARCHAR(255),
  project_owner VARCHAR(255),
  project_scope_code VARCHAR(64),
  project_industry_code VARCHAR(64),
  project_scope_type_code VARCHAR(64),
  project_scope_type VARCHAR(255),
  issuing_date DATE,
  project_status_code VARCHAR(32)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS carbon_metaregistry_project (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  metaregistry_id BIGINT,
  tenant_id BIGINT,
  project_introduction TEXT,
  INDEX idx_cmr_project_meta (metaregistry_id),
  INDEX idx_cmr_project_tenant (tenant_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS carbon_credit_assets (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  carbon_project_id BIGINT,
  carbon_exchange_id BIGINT,
  certified_agency VARCHAR(255),
  issuing_agency VARCHAR(255),
  issuing_certificates VARCHAR(255),
  issuing_date DATE,
  expiry_date DATE,
  assets_status VARCHAR(32),
  transaction_status VARCHAR(32),
  buy_total_price DECIMAL(20,4),
  buy_unit_price DECIMAL(20,4),
  buy_date DATE,
  buy_certificate VARCHAR(255),
  total DECIMAL(20,4),
  available_amount DECIMAL(20,4),
  frozen_amount DECIMAL(20,4),
  locked_amount DECIMAL(20,4),
  valuation DECIMAL(20,4),
  tenant_id BIGINT,
  issuing_certificates_file_name VARCHAR(255),
  buy_certificate_file_name VARCHAR(255),
  creator_id BIGINT,
  updated_id BIGINT,
  created_time DATETIME,
  updated_time DATETIME,
  INDEX idx_cc_assets_project (carbon_project_id),
  INDEX idx_cc_assets_exchange (carbon_exchange_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS carbon_quota_assets (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  agency_name VARCHAR(255),
  carbon_exchange_id BIGINT,
  issuing_agency VARCHAR(255),
  issuing_certificates VARCHAR(255),
  issuing_date DATE,
  expiry_date DATE,
  assets_status VARCHAR(32),
  transaction_status VARCHAR(32),
  buy_total_price DECIMAL(20,4),
  buy_unit_price DECIMAL(20,4),
  buy_date DATE,
  buy_certificate VARCHAR(255),
  total DECIMAL(20,4),
  available_amount DECIMAL(20,4),
  frozen_amount DECIMAL(20,4),
  locked_amount DECIMAL(20,4),
  valuation DECIMAL(20,4),
  tenant_id BIGINT,
  issuing_certificates_file_name VARCHAR(255),
  buy_certificate_file_name VARCHAR(255),
  creator_id BIGINT,
  updated_id BIGINT,
  created_time DATETIME,
  updated_time DATETIME,
  INDEX idx_cq_assets_exchange (carbon_exchange_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Ensure columns exist when tables were created with older schemas
SET @db := DATABASE();

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_tenant' AND COLUMN_NAME='address');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_tenant ADD COLUMN address VARCHAR(255);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_tenant' AND COLUMN_NAME='remarks');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_tenant ADD COLUMN remarks VARCHAR(255);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='username');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN username VARCHAR(64);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='password');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN password VARCHAR(255);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='email');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN email VARCHAR(255);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='product_version');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN product_version VARCHAR(64);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='token');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN token VARCHAR(255);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='account_type');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN account_type VARCHAR(64);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='account_status');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN account_status VARCHAR(32);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='remarks');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN remarks VARCHAR(255);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='creator_id');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN creator_id BIGINT;', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='updated_id');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN updated_id BIGINT;', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='created_time');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN created_time DATETIME;', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='updated_time');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN updated_time DATETIME;', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='sys_account' AND COLUMN_NAME='validity_period');
SET @stmt := IF(@col=0, 'ALTER TABLE sys_account ADD COLUMN validity_period DATE;', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='carbon_metaregistry' AND COLUMN_NAME='project_scope_code');
SET @stmt := IF(@col=0, 'ALTER TABLE carbon_metaregistry ADD COLUMN project_scope_code VARCHAR(64);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='carbon_metaregistry' AND COLUMN_NAME='project_industry_code');
SET @stmt := IF(@col=0, 'ALTER TABLE carbon_metaregistry ADD COLUMN project_industry_code VARCHAR(64);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

SET @col := (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=@db AND TABLE_NAME='carbon_metaregistry' AND COLUMN_NAME='project_scope_type');
SET @stmt := IF(@col=0, 'ALTER TABLE carbon_metaregistry ADD COLUMN project_scope_type VARCHAR(255);', 'SELECT 1;');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

-- Seed data (idempotent)
INSERT IGNORE INTO sys_tenant (id, tenant_name, contacts_phone, address, remarks)
VALUES (1, 'Carbon Tenant', '13800000000', 'Local', 'seed');

INSERT IGNORE INTO sys_role (id, role_code, role_name)
VALUES (9, 'ADMIN', '管理员');

INSERT IGNORE INTO sys_permission (id, uri)
VALUES (1, 'ALL');

INSERT IGNORE INTO sys_role_permission (id, role_id, permission_id)
VALUES (1, 9, 1);

INSERT IGNORE INTO sys_account (
  id, account_name, username, password, phone, email, avatar,
  account_status, account_type, product_version, tenant_id,
  remarks, created_time, updated_time, validity_period
) VALUES (
  1, 'admin', 'admin', MD5('admin123'), '13800000000', 'admin@example.com', '',
  '1', '0380000002', '0400000003', 1,
  'seed', NOW(), NOW(), DATE_ADD(CURDATE(), INTERVAL 365 DAY)
);

INSERT IGNORE INTO sys_account_role (id, account_id, role_id)
VALUES (1, 1, 9);
