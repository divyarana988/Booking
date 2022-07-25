CREATE DATABASE booking;
CREATE USER 'booking'@'localhost' IDENTIFIED BY 'booking';
GRANT ALL PRIVILEGES ON booking.* TO 'booking'@'localhost';
commit;

ALTER DATABASE bookmyshow CHARACTER SET utf8 COLLATE utf8_general_ci;



DROP TABLE IF EXISTS `booking`;

CREATE TABLE `booking` (
                           `booking_id` bigint NOT NULL AUTO_INCREMENT,
                           `created_on` datetime DEFAULT NULL,
                           `deleted` datetime DEFAULT NULL,
                           `movie_id` varchar(255) DEFAULT NULL,
                           `notification_id` binary(255) DEFAULT NULL,
                           `payment_id` binary(255) DEFAULT NULL,
                           `seat_booked` int DEFAULT NULL,
                           `seat_numbers` varchar(255) DEFAULT NULL,
                           `threatre_id` varchar(255) DEFAULT NULL,
                           `total_price` int DEFAULT NULL,
                           `updated_on` datetime DEFAULT NULL,
                           `user_name` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`booking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `cast`;

CREATE TABLE `cast` (
                        `id` bigint NOT NULL,
                        `description` varchar(255) DEFAULT NULL,
                        `character_name` varchar(255) DEFAULT NULL,
                        `cast_id` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `hibernate_sequence`;

CREATE TABLE `hibernate_sequence` (
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (11);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;


DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie` (
                         `movie_id` varchar(255) NOT NULL,
                         `active_date_end` datetime DEFAULT NULL,
                         `active_date_start` datetime DEFAULT NULL,
                         `cast_id` varchar(255) DEFAULT NULL,
                         `created_on` datetime DEFAULT NULL,
                         `deleted` datetime DEFAULT NULL,
                         `director` varchar(255) DEFAULT NULL,
                         `duration` varchar(255) DEFAULT NULL,
                         `language` varchar(255) DEFAULT NULL,
                         `name` varchar(255) DEFAULT NULL,
                         `poster_url` varchar(255) DEFAULT NULL,
                         `rating` varchar(255) DEFAULT NULL,
                         `release_year` varchar(255) DEFAULT NULL,
                         `threatre_id` varchar(255) DEFAULT NULL,
                         `trailer_url` varchar(255) DEFAULT NULL,
                         `type` varchar(255) DEFAULT NULL,
                         `updated_on` datetime DEFAULT NULL,
                         PRIMARY KEY (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
                                `notification_id` binary(16) NOT NULL,
                                `booking_id` bigint DEFAULT NULL,
                                `created_on` datetime DEFAULT NULL,
                                `receiver_email` varchar(255) DEFAULT NULL,
                                `receiver_mobile` varchar(255) DEFAULT NULL,
                                `receiver_type` varchar(255) DEFAULT NULL,
                                `send_timie` datetime DEFAULT NULL,
                                `sender_email` varchar(255) DEFAULT NULL,
                                `status` varchar(255) DEFAULT NULL,
                                `tiny_url` varchar(255) DEFAULT NULL,
                                `updated_on` datetime DEFAULT NULL,
                                PRIMARY KEY (`notification_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `offer`;

CREATE TABLE `offer` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `amount_less` int DEFAULT NULL,
                         `code` varchar(255) DEFAULT NULL,
                         `created_on` datetime DEFAULT NULL,
                         `deleted` datetime DEFAULT NULL,
                         `description` varchar(255) DEFAULT NULL,
                         `updated_on` datetime DEFAULT NULL,
                         `valid_end` datetime DEFAULT NULL,
                         `valid_start` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
                           `transaction_id` binary(16) NOT NULL,
                           `amount` int DEFAULT NULL,
                           `booking_id` bigint DEFAULT NULL,
                           `created_on` datetime DEFAULT NULL,
                           `deleted` datetime DEFAULT NULL,
                           `method` varchar(255) DEFAULT NULL,
                           `source_details` varchar(255) DEFAULT NULL,
                           `status` varchar(255) DEFAULT NULL,
                           `updated_on` datetime DEFAULT NULL,
                           PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `show`;

CREATE TABLE `show` (
                          `movie_id` varchar(255) NOT NULL,
                          `starts_at` varchar(255) NOT NULL,
                          `threatre_id` varchar(255) NOT NULL,
                          `ends_at` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`movie_id`,`starts_at`,`threatre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `seat`;

CREATE TABLE `seat` (
                               `movie_id` varchar(255) NOT NULL,
                               `show_starts_at` varchar(255) NOT NULL,
                               `seat_number` varchar(255) NOT NULL,
                               `threatre_id` varchar(255) NOT NULL,
                               `booked` bit(1) DEFAULT NULL,
                               `created_on` datetime DEFAULT NULL,
                               `deleted` datetime DEFAULT NULL,
                               `seat_price` int DEFAULT NULL,
                               `seat_type` varchar(255) DEFAULT NULL,
                               `updated_on` datetime DEFAULT NULL,
                               PRIMARY KEY (`movie_id`,`show_starts_at`,`seat_number`,`threatre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `threatre`;

CREATE TABLE `threatre` (
                           `threatre_id` varchar(255) NOT NULL,
                           `address` varchar(255) DEFAULT NULL,
                           `city` varchar(255) DEFAULT NULL,
                           `created_on` datetime DEFAULT NULL,
                           `deleted` datetime DEFAULT NULL,
                           `languages` varchar(255) DEFAULT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `updated_on` datetime DEFAULT NULL,
                           `user_name` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`threatre_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `url`;

CREATE TABLE `url` (
                       `url_id` bigint NOT NULL AUTO_INCREMENT,
                       `created_on` datetime DEFAULT NULL,
                       `original_url` varchar(255) DEFAULT NULL,
                       PRIMARY KEY (`url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
                        `user_name` varchar(255) NOT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `created_on` datetime DEFAULT NULL,
                        `deleted` datetime DEFAULT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `first_name` varchar(255) NOT NULL,
                        `last_name` varchar(255) NOT NULL,
                        `mobile_number` varchar(255) NOT NULL,
                        `updated_on` datetime DEFAULT NULL,
                        `user_type` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
