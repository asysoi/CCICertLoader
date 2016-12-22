-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.1.51-community - MySQL Community Server (GPL)
-- ОС Сервера:                   Win32
-- HeidiSQL Версия:              9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Дамп структуры базы данных owncert_2
CREATE DATABASE IF NOT EXISTS `owncert_2` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `owncert_2`;


-- Дамп структуры для таблица owncert_2.beltpp
CREATE TABLE IF NOT EXISTS `beltpp` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `unp` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Экспортируемые данные не выделены.


-- Дамп структуры для представление owncert_2.certview
-- Создание временной таблицы для обработки ошибок зависимостей представлений
CREATE TABLE `certview` (
	`id` INT(10) UNSIGNED NOT NULL,
	`id_beltpp` INT(10) UNSIGNED NOT NULL,
	`type` VARCHAR(1) NOT NULL COLLATE 'utf8_unicode_ci',
	`number` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`blanknumber` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`customername` VARCHAR(1024) NOT NULL COLLATE 'utf8_unicode_ci',
	`customeraddress` VARCHAR(1024) NULL COLLATE 'utf8_unicode_ci',
	`customerunp` VARCHAR(15) NOT NULL COLLATE 'utf8_unicode_ci',
	`factoryaddress` VARCHAR(255) NULL COLLATE 'utf8_unicode_ci',
	`branches` VARCHAR(1200) NULL COLLATE 'utf8_unicode_ci',
	`additionallists` VARCHAR(80) NOT NULL COLLATE 'utf8_unicode_ci',
	`datestart` DATE NOT NULL,
	`dateexpire` DATE NOT NULL,
	`expert` VARCHAR(25) NOT NULL COLLATE 'utf8_unicode_ci',
	`signer` VARCHAR(25) NOT NULL COLLATE 'utf8_unicode_ci',
	`signerjob` VARCHAR(50) NOT NULL COLLATE 'utf8_unicode_ci',
	`datecert` DATE NOT NULL,
	`dateload` TIMESTAMP NOT NULL,
	`beltppname` VARCHAR(120) NULL COLLATE 'utf8_unicode_ci',
	`beltppaddress` VARCHAR(120) NULL COLLATE 'utf8_unicode_ci',
	`beltppunp` VARCHAR(16) NULL COLLATE 'utf8_unicode_ci'
) ENGINE=MyISAM;


-- Дамп структуры для таблица owncert_2.owncertificate
CREATE TABLE IF NOT EXISTS `owncertificate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_beltpp` int(10) unsigned NOT NULL DEFAULT '0',
  `type` varchar(1) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'P',
  `number` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `blanknumber` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `customername` varchar(1024) COLLATE utf8_unicode_ci NOT NULL,
  `customeraddress` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customerunp` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `factoryaddress` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `branches` varchar(1200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `additionallists` varchar(80) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `datestart` date NOT NULL,
  `dateexpire` date NOT NULL,
  `expert` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `signer` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `signerjob` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `datecert` date NOT NULL,
  `dateload` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `number_blanknumber` (`number`,`blanknumber`),
  KEY `FK_owncertificate_beltpp` (`id_beltpp`),
  CONSTRAINT `FK_owncertificate_beltpp` FOREIGN KEY (`id_beltpp`) REFERENCES `beltpp` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='таблица сертификатов собственного производства';

-- Экспортируемые данные не выделены.


-- Дамп структуры для таблица owncert_2.ownproduct
CREATE TABLE IF NOT EXISTS `ownproduct` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_certificate` int(10) unsigned NOT NULL,
  `number` varchar(5) COLLATE utf8_unicode_ci DEFAULT '',
  `name` varchar(1024) COLLATE utf8_unicode_ci NOT NULL,
  `code` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_certificate_to_product` (`id_certificate`),
  CONSTRAINT `FK_certificate_to_product` FOREIGN KEY (`id_certificate`) REFERENCES `owncertificate` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Экспортируемые данные не выделены.


-- Дамп структуры для представление owncert_2.certview
-- Удаление временной таблицы и создание окончательной структуры представления
DROP TABLE IF EXISTS `certview`;
CREATE ALGORITHM=UNDEFINED DEFINER=`cert`@`localhost` SQL SECURITY DEFINER VIEW `certview` AS select `owncertificate`.`id` AS `id`,`owncertificate`.`id_beltpp` AS `id_beltpp`,`owncertificate`.`type` AS `type`,`owncertificate`.`number` AS `number`,`owncertificate`.`blanknumber` AS `blanknumber`,`owncertificate`.`customername` AS `customername`,`owncertificate`.`customeraddress` AS `customeraddress`,`owncertificate`.`customerunp` AS `customerunp`,`owncertificate`.`factoryaddress` AS `factoryaddress`,`owncertificate`.`branches` AS `branches`,`owncertificate`.`additionallists` AS `additionallists`,`owncertificate`.`datestart` AS `datestart`,`owncertificate`.`dateexpire` AS `dateexpire`,`owncertificate`.`expert` AS `expert`,`owncertificate`.`signer` AS `signer`,`owncertificate`.`signerjob` AS `signerjob`,`owncertificate`.`datecert` AS `datecert`,`owncertificate`.`dateload` AS `dateload`,`beltpp`.`name` AS `beltppname`,`beltpp`.`address` AS `beltppaddress`,`beltpp`.`unp` AS `beltppunp` from (`owncertificate` left join `beltpp` on((`owncertificate`.`id_beltpp` = `beltpp`.`id`)));
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
