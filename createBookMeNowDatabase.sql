CREATE SCHEMA `datasource` ;
USE datasource;

CREATE TABLE `users` (
  `id` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `date_created` date NOT NULL,
  `is_admin` tinyint(4) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `bookmarked_data` (
  `summary` varchar(500) NOT NULL,
  `title` varchar(500) NOT NULL,
  `tags` varchar(500) DEFAULT NULL,
  `url` varchar(500) NOT NULL,
  `email` varchar(85) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

