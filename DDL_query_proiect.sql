use wedding_vibe;

create database wedding_vibe;

CREATE TABLE `users`  (
	`id` INT NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(50) UNIQUE,
	`email` VARCHAR(100) UNIQUE NOT NULL,
	`first_name` VARCHAR(100),
	`name` VARCHAR(100),
	`password` VARCHAR(255) NOT NULL,
	`created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `vendors` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL, -- Owner of vendor profile after claiming
  `company_name` VARCHAR(255) NOT NULL,
  `company_email` VARCHAR(255),
  `location` VARCHAR(255),
  `description` TEXT,
  `rating` DOUBLE,
  `profile_picture` TEXT, -- --Base64 stored
  `website_url` VARCHAR(255) UNIQUE,
  `phone_number` VARCHAR(20) UNIQUE,
  `claimed` BOOLEAN DEFAULT FALSE,
  `created_by_userid` INT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_fk_idx` (`user_id`),
  KEY `rating_idx` (`rating`),
  CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `createdBy_user_fk` FOREIGN KEY (`created_by_userid`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE service_reviews;
DROP TABLE users_to_services;
DROP TABLE services;
DROP TABLE portfolio_items;
DROP TABLE vendors;
DROP table users_to_roles;
DROP TABLE users; 
DROP TABLE roles;
DROP TABLE roles_seq;


CREATE TABLE `services` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `service_name` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `category` VARCHAR(100) NOT NULL,
  `vendor_id` INT NOT NULL,
  `rating` DOUBLE,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `service_to_vendor_idx` (`vendor_id`),
  KEY `category_idx` (`category`),
  KEY `rating_idx` (`rating`),
  CONSTRAINT `service_to_vendor_fk` FOREIGN KEY (`vendor_id`) REFERENCES `vendors`(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

 
CREATE TABLE `users_to_services` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `service_id` INT NOT NULL,
  `price` DECIMAL,
  `date_of_service` TIMESTAMP,
  `status` ENUM('BOOKED', 'USED', 'CANCELLED'),
  PRIMARY KEY (`id`),
  KEY `user_fk_idx` (`user_id`),
  KEY `service_fk_idx` (`service_id`),
  CONSTRAINT `users_to_services_user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `users_to_services_service_fk` FOREIGN KEY (`service_id`) REFERENCES `services` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) UNIQUE NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Insert role values into the role table
INSERT INTO `roles` (`name`) VALUES ('ADMIN'), ('VENDOR'), ('USER');

CREATE TABLE `users_to_roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_fk_idx` (`user_id`),
  KEY `role_fk_idx` (`role_id`),
  CONSTRAINT `user_to_roles_user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `user_to_roles_role_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `service_reviews` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `service_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `title` VARCHAR(255),
  `body` TEXT NOT NULL,
  `rating` INT CHECK (rating BETWEEN 1 AND 5),
  `status` ENUM('PENDING', 'CONFIRMED', 'CANCELLED') DEFAULT 'PENDING',  -- 'pending', 'confirmed', 'cancelled',
  `deleted` BOOLEAN DEFAULT FALSE, -- -- for soft delete
  `upvotes` INT DEFAULT 0,
  `downvotes` INT DEFAULT 0,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `review_to_service_id_fk` FOREIGN KEY(`service_id`) REFERENCES `services` (`id`),
  CONSTRAINT `review_to_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `attachments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `file_data` TEXT NOT NULL, -- --Base64 encoded
  `file_type` VARCHAR(50) NOT NULL,
  `original_file_name` VARCHAR(255),
  `attachment_type` ENUM('PROOF_OF_USE', 'VENDOR_PORTFOLIO', 'USER_UPLOADED_IMAGE') NOT NULL,
  `vendor_service_id` INT, -- -- vendor portfolio attachment
  `user_to_services_id` INT, -- -- proof of use attachment
  `service_review_id` INT, -- -- user uploaded image (review attachment)
  `user_id` INT NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `description` TEXT,
  `file_size` BIGINT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `attachments_vendor_service_fk_idx` (`vendor_service_id`),
  CONSTRAINT `attachments_vendor_service_fk` FOREIGN KEY (`vendor_service_id`) REFERENCES `services` (`id`) ON DELETE CASCADE,
  CONSTRAINT `attachments_user_to_services_fk`  FOREIGN KEY (`user_to_services_id`) REFERENCES users_to_services(id) ON DELETE CASCADE,
  CONSTRAINT `attachments_service_review_fk` FOREIGN KEY (`service_review_id`) REFERENCES service_reviews(id) ON DELETE CASCADE,
  CONSTRAINT `attachments_user_id_fk`FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



SELECT * FROM users_to_services;
SELECT * FROM services;
SELECT * FROM users;
SELECT * FROM vendors;
SELECT * FROM service_reviews;

SELECT * FROM roles;
SELECT * FROM users_to_roles;

SELECT * FROM attachments;

DELETE FROM users_to_services where id=5;

DELETE FROM users where id=16;
DELETE FROM services where id=5;
ALTER TABLE `users`


CHANGE COLUMN firstName first_name  VARCHAR(100);