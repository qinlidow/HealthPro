-- HealthPro 数据库初始化脚本
-- 数据库: healthpro

CREATE DATABASE IF NOT EXISTS healthpro DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE healthpro;

-- 用户表
CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255),
    age INT,
    gender VARCHAR(10),
    avatar VARCHAR(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 身体数据表
CREATE TABLE IF NOT EXISTS body (
    id INT PRIMARY KEY AUTO_INCREMENT,
    height FLOAT,
    weight FLOAT,
    fat_pre FLOAT,
    sugar FLOAT,
    pressure_h FLOAT,
    pressure_l FLOAT,
    date VARCHAR(255),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 饮食记录表
CREATE TABLE IF NOT EXISTS diet (
    id INT PRIMARY KEY AUTO_INCREMENT,
    breakfast VARCHAR(255),
    lunch VARCHAR(255),
    dinner VARCHAR(255),
    remark VARCHAR(255),
    date VARCHAR(255),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 睡眠记录表
CREATE TABLE IF NOT EXISTS sleep (
    id INT PRIMARY KEY AUTO_INCREMENT,
    start_time VARCHAR(255),
    end_time VARCHAR(255),
    duration FLOAT,
    quality INT,
    deep_sleep FLOAT,
    remark VARCHAR(255),
    date VARCHAR(255),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 健身记录表
CREATE TABLE IF NOT EXISTS fitness (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(255),
    duration INT,
    calories INT,
    distance FLOAT,
    heart_rate INT,
    remark VARCHAR(255),
    date VARCHAR(255),
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 社交帖子表
CREATE TABLE IF NOT EXISTS post (
    id INT PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(255),
    title VARCHAR(255) DEFAULT NULL,
    content TEXT,
    image VARCHAR(500) DEFAULT NULL,
    likes INT DEFAULT 0,
    comments INT DEFAULT 0,
    date VARCHAR(255),
    user_id INT,
    user_name VARCHAR(255),
    status INT DEFAULT 0,
    is_top INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 评论表
CREATE TABLE IF NOT EXISTS comment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    user_name VARCHAR(255),
    content TEXT NOT NULL,
    date VARCHAR(255),
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 关注表
CREATE TABLE IF NOT EXISTS follow (
    id INT PRIMARY KEY AUTO_INCREMENT,
    follower_id INT NOT NULL,
    following_id INT NOT NULL,
    date VARCHAR(255),
    UNIQUE KEY uk_follow (follower_id, following_id),
    FOREIGN KEY (follower_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (following_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 收藏表
CREATE TABLE IF NOT EXISTS favorite (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    date VARCHAR(255),
    UNIQUE KEY uk_favorite (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 打卡表
CREATE TABLE IF NOT EXISTS check_in (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    date VARCHAR(255) NOT NULL,
    UNIQUE KEY uk_check_in (user_id, date),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 打卡表
CREATE TABLE IF NOT EXISTS check_in (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    date VARCHAR(255) NOT NULL,
    UNIQUE KEY uk_check_in (user_id, date),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 帖子点赞表
CREATE TABLE IF NOT EXISTS post_like (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    date VARCHAR(255),
    UNIQUE KEY uk_post_like (user_id, post_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
