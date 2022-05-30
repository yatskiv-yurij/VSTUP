-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Май 30 2022 г., 05:17
-- Версия сервера: 5.7.31
-- Версия PHP: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `vstup`
--
CREATE DATABASE IF NOT EXISTS `vstup` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `vstup`;

-- --------------------------------------------------------

--
-- Структура таблицы `data_admin`
--

DROP TABLE IF EXISTS `data_admin`;
CREATE TABLE IF NOT EXISTS `data_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `dvnz` int(11) DEFAULT NULL,
  `faculty` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `dvnz` (`dvnz`),
  KEY `faculty` (`faculty`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `data_admin`
--

INSERT INTO `data_admin` (`id`, `user_id`, `name`, `last_name`, `dvnz`, `faculty`) VALUES
(1, 5, 'Максим', 'Шоляк', NULL, NULL),
(8, 25, 'Юрій', 'Яцьків', 1, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `data_entrant`
--

DROP TABLE IF EXISTS `data_entrant`;
CREATE TABLE IF NOT EXISTS `data_entrant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `image` blob,
  `document_series` varchar(10) DEFAULT NULL,
  `document_number` varchar(10) DEFAULT NULL,
  `document` int(11) NOT NULL,
  `rating` varchar(10) DEFAULT NULL,
  `document_file` mediumblob,
  `document_name` varchar(100) DEFAULT NULL,
  `is_data` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `document` (`document`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `data_specialties`
--

DROP TABLE IF EXISTS `data_specialties`;
CREATE TABLE IF NOT EXISTS `data_specialties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_system` int(11) NOT NULL,
  `education` int(11) NOT NULL,
  `forma` varchar(20) NOT NULL,
  `offer` varchar(50) NOT NULL,
  `document` int(11) NOT NULL,
  `course` varchar(10) NOT NULL,
  `term` varchar(40) NOT NULL,
  `amount` varchar(10) NOT NULL,
  `contract` varchar(10) NOT NULL,
  `price` varchar(10) NOT NULL,
  `mandatory_exams` varchar(200) DEFAULT NULL,
  `choice_exams` varchar(200) DEFAULT NULL,
  `entrance_exams` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_system` (`id_system`),
  KEY `document` (`document`),
  KEY `education` (`education`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `data_specialties`
--

INSERT INTO `data_specialties` (`id`, `id_system`, `education`, `forma`, `offer`, `document`, `course`, `term`, `amount`, `contract`, `price`, `mandatory_exams`, `choice_exams`, `entrance_exams`) VALUES
(7, 5, 2, 'Денна', 'Відкрита, з пріорітетом', 3, '1 курс', '01.09.2022 - 30.06.2026', '140', '20', '38000', 'Українська мова\r\nМатематика', 'Іноземна мова\r\nІсторія України\r\nБіологія\r\nГеографія\r\nФізика\r\nХімія', NULL),
(8, 5, 2, 'Денна', 'Фіксована', 2, '1 курс', '01.09.2022 – 30.06.2025', '13', '5', '38000', 'Українська мова\r\nМатематика', NULL, 'Фахове випробування'),
(10, 20, 2, 'Денна', 'Відкрита, з пріоритетом', 3, '1 курс', '01.09.2022 - 30.06.2026', '70', '44', '20600', 'Українська мова\nМатематика', 'Історія України\nБіологія\nГеографія\nФізика\nХімія\nАнглійська мова\nНімецька мова\nІспанська мова\nФранцузька мова', 'Іноземна мова\nФахове випробування'),
(11, 20, 2, 'Заочна', 'Відкрита, з пріоритетом', 3, '1 курс', '01.09.2022 - 30.06.2027', '40', '29', '15000', 'Українська мова\nМатематика', 'Історія України\nБіологія\nГеографія\nФізика\nХімія\nАнглійська мова\nНімецька мова\nІспанська мова\nФранцузька мова', 'Іноземна мова\nФахове випробування'),
(12, 20, 2, 'Денна', 'Фіксована', 2, '1 курс', '01.09.2022 - 30.06.2024', '20', '5', '20600', 'Українська мова\nМатематика', '', 'Фахове випробування'),
(13, 20, 4, 'Денна', 'Фіксована', 4, '1 курс', '01.09.2022 - 31.12.2023', '40', '20', '25500', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Структура таблицы `document`
--

DROP TABLE IF EXISTS `document`;
CREATE TABLE IF NOT EXISTS `document` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `document` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `document`
--

INSERT INTO `document` (`id`, `document`) VALUES
(2, 'Диплом молодшого спеціаліста'),
(3, 'Повна загальна середня освіта'),
(4, 'Диплом бакалавра'),
(5, 'Диплом спеціаліста'),
(6, 'Диплом магістра'),
(7, 'Диплом молодшого бакалавра'),
(8, 'Диплом фахового молодшого бакалавра');

-- --------------------------------------------------------

--
-- Структура таблицы `education_level`
--

DROP TABLE IF EXISTS `education_level`;
CREATE TABLE IF NOT EXISTS `education_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `education` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `education_level`
--

INSERT INTO `education_level` (`id`, `education`) VALUES
(2, 'Бакалав'),
(4, 'Магістр'),
(5, 'Молодший бакалавр'),
(6, 'Фаховий молодший бакалавр');

-- --------------------------------------------------------

--
-- Структура таблицы `faculty`
--

DROP TABLE IF EXISTS `faculty`;
CREATE TABLE IF NOT EXISTS `faculty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `faculty`
--

INSERT INTO `faculty` (`id`, `name`) VALUES
(1, 'Факультет інформаційних технологій'),
(2, 'Економічний факультет'),
(3, 'Юридичний факультет'),
(4, 'Механіко-математичний факультет');

-- --------------------------------------------------------

--
-- Структура таблицы `request`
--

DROP TABLE IF EXISTS `request`;
CREATE TABLE IF NOT EXISTS `request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `id_specialty` int(11) NOT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`),
  KEY `id_specialty` (`id_specialty`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `specialties`
--

DROP TABLE IF EXISTS `specialties`;
CREATE TABLE IF NOT EXISTS `specialties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) NOT NULL,
  `name` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `specialties`
--

INSERT INTO `specialties` (`id`, `code`, `name`) VALUES
(1, '121', 'Інженерія програмного забезпечення'),
(2, '122', 'Комп\'ютерні науки'),
(3, '125', 'Кібербезпека'),
(4, '051', 'Економіка'),
(5, '071', 'Облік і оподаткування'),
(6, '111', 'Математика'),
(7, '12.00.03', 'Цивільне право і цивільний'),
(9, '124', 'Системний аналіз'),
(11, '126', 'Інформаційні системи та технології');

-- --------------------------------------------------------

--
-- Структура таблицы `system`
--

DROP TABLE IF EXISTS `system`;
CREATE TABLE IF NOT EXISTS `system` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `university_id` int(11) NOT NULL,
  `faculty_id` int(11) NOT NULL,
  `specialty_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `university_id` (`university_id`),
  KEY `faculty_id` (`faculty_id`),
  KEY `specialty_id` (`specialty_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `system`
--

INSERT INTO `system` (`id`, `university_id`, `faculty_id`, `specialty_id`) VALUES
(4, 1, 3, 7),
(5, 2, 1, 1),
(7, 2, 1, 3),
(8, 2, 4, 6),
(9, 2, 2, 4),
(10, 2, 2, 5),
(15, 2, 1, 2),
(17, 1, 1, 11),
(20, 1, 1, 1),
(21, 1, 1, 9),
(22, 1, 2, 5);

-- --------------------------------------------------------

--
-- Структура таблицы `university`
--

DROP TABLE IF EXISTS `university`;
CREATE TABLE IF NOT EXISTS `university` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `university`
--

INSERT INTO `university` (`id`, `name`) VALUES
(1, 'Ужгородський національний університет'),
(2, 'Київський національний університет імені Тараса Шевченка');

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(100) NOT NULL,
  `password` varchar(256) NOT NULL,
  `role` varchar(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`id`, `login`, `password`, `role`) VALUES
(5, 'maxim@gmail.com', '16d49f4863b21430d617e6259375a00397600124964b9c691b827dde92811451', 'admin'),
(25, 'yurij.yatskiv.work@gmail.com', '86220050a61a32d07fbb65408ae5f037c2ae8f85c1fb024aa9cbcc1f1def0833', 'worker');

-- --------------------------------------------------------

--
-- Структура таблицы `zno`
--

DROP TABLE IF EXISTS `zno`;
CREATE TABLE IF NOT EXISTS `zno` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  `certificate` int(11) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `pincode` varchar(10) NOT NULL,
  `rating` varchar(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `data_admin`
--
ALTER TABLE `data_admin`
  ADD CONSTRAINT `data_admin_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `data_admin_ibfk_2` FOREIGN KEY (`dvnz`) REFERENCES `university` (`id`),
  ADD CONSTRAINT `data_admin_ibfk_3` FOREIGN KEY (`faculty`) REFERENCES `faculty` (`id`);

--
-- Ограничения внешнего ключа таблицы `data_entrant`
--
ALTER TABLE `data_entrant`
  ADD CONSTRAINT `data_entrant_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `data_entrant_ibfk_2` FOREIGN KEY (`document`) REFERENCES `document` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `data_specialties`
--
ALTER TABLE `data_specialties`
  ADD CONSTRAINT `data_specialties_ibfk_1` FOREIGN KEY (`id_system`) REFERENCES `system` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `data_specialties_ibfk_2` FOREIGN KEY (`document`) REFERENCES `document` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `data_specialties_ibfk_3` FOREIGN KEY (`education`) REFERENCES `education_level` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `request`
--
ALTER TABLE `request`
  ADD CONSTRAINT `request_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `request_ibfk_2` FOREIGN KEY (`id_specialty`) REFERENCES `data_specialties` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `system`
--
ALTER TABLE `system`
  ADD CONSTRAINT `system_ibfk_1` FOREIGN KEY (`university_id`) REFERENCES `university` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `system_ibfk_2` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `system_ibfk_3` FOREIGN KEY (`specialty_id`) REFERENCES `specialties` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ограничения внешнего ключа таблицы `zno`
--
ALTER TABLE `zno`
  ADD CONSTRAINT `zno_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
