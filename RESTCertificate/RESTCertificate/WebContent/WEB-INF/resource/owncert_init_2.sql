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

-- Дамп структуры базы данных owncert
CREATE DATABASE IF NOT EXISTS `owncert` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `owncert`;


-- Дамп структуры для таблица owncert.beltpp
CREATE TABLE IF NOT EXISTS `beltpp` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `address` varchar(120) COLLATE utf8_unicode_ci NOT NULL,
  `unp` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Дамп данных таблицы owncert.beltpp: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `beltpp` DISABLE KEYS */;
INSERT INTO `beltpp` (`id`, `name`, `address`, `unp`) VALUES
	(1, 'Унитарное предприятие по оказанию услуг "Минское отделение торгово-промышленной палаты"', '220133, г.Минск, ул. Я.Колоса, 65, т. 280-04-73', NULL),
	(2, '????????? ??????????? ?? ???????? ????? "??????? ????????? ???????-???????????? ??????"', '220113, ?.?????, ??.?.??????, ?.65, ?. 280-04-73', NULL);
/*!40000 ALTER TABLE `beltpp` ENABLE KEYS */;


-- Дамп структуры для таблица owncert.owncertificate
CREATE TABLE IF NOT EXISTS `owncertificate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_beltpp` int(10) unsigned NOT NULL DEFAULT '0',
  `number` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `blanknumber` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `customername` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `customeraddress` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customerunp` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `factoryaddress` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `branches` varchar(1200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `datecert` date DEFAULT NULL,
  `dateexpire` date DEFAULT NULL,
  `expert` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `signer` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `signerjob` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `datesign` date NOT NULL,
  `dateload` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `number_blanknumber` (`number`,`blanknumber`),
  KEY `FK_owncertificate_beltpp` (`id_beltpp`),
  CONSTRAINT `FK_owncertificate_beltpp` FOREIGN KEY (`id_beltpp`) REFERENCES `beltpp` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='таблица сертификатов собственного производства';

-- Дамп данных таблицы owncert.owncertificate: ~8 rows (приблизительно)
/*!40000 ALTER TABLE `owncertificate` DISABLE KEYS */;
INSERT INTO `owncertificate` (`id`, `id_beltpp`, `number`, `blanknumber`, `customername`, `customeraddress`, `customerunp`, `factoryaddress`, `branches`, `datecert`, `dateexpire`, `expert`, `signer`, `signerjob`, `datesign`, `dateload`) VALUES
	(2, 1, '123455', '455656', '', NULL, '', '', NULL, NULL, NULL, '', '', '', '0000-00-00', '2016-12-19 19:01:03'),
	(3, 2, '0 - 367.1/3860-1', '0037426', '??? "??????????-??????"', '220070, ?.?????, ??.???????, ?.187, ?????? 7', '12345667789', '220070, ?.?????, ??.???????, ?.187, ?????? 7, ?????????? ????????', '1. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 12. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 1', '2014-07-21', '2015-07-21', '?????? ?.?.', '?.?.?????????', '????????? ???? ?1', '2014-07-28', '2016-12-20 00:41:56'),
	(4, 2, '0 - 367.1/3869-1', '0037566', '??? "??????????-??????"', '220070, ?.?????, ??.???????, ?.187, ?????? 7', '12345667789', '220070, ?.?????, ??.???????, ?.187, ?????? 7, ?????????? ????????', '1. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 12. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 1', '2014-07-21', '2015-07-21', '?????? ?.?.', '?.?.?????????', '????????? ???? ?1', '2014-07-28', '2016-12-20 00:42:57'),
	(5, 2, '0 - 377.1/3869-1', '0087566', '??? "??????????-??????"', '220070, ?.?????, ??.???????, ?.187, ?????? 7', '12345667789', '220070, ?.?????, ??.???????, ?.187, ?????? 7, ?????????? ????????', '1. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 12. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 1', '2014-07-21', '2015-07-21', '?????? ?.?.', '?.?.?????????', '????????? ???? ?1', '2014-07-28', '2016-12-20 00:52:43'),
	(7, 2, '0 - 387.1/3869-1', '0087566', '??? "??????????-??????"', '220070, ?.?????, ??.???????, ?.187, ?????? 7', '12345667789', '220070, ?.?????, ??.???????, ?.187, ?????? 7, ?????????? ????????', '1. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 12. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 1', '2014-07-21', '2015-07-21', '?????? ?.?.', '?.?.?????????', '????????? ???? ?1', '2014-07-28', '2016-12-20 00:54:02'),
	(8, 2, '0 - 357.1/3869-1', '0037566', '??? "??????????-??????"', '220070, ?.?????, ??.???????, ?.187, ?????? 7', '12345667789', '220070, ?.?????, ??.???????, ?.187, ?????? 7, ?????????? ????????', '1. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 12. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 1', '2014-07-21', '2015-07-21', '?????? ?.?.', '?.?.?????????', '????????? ???? ?1', '2014-07-28', '2016-12-20 00:56:48'),
	(9, 2, '0 - 457.1/3869-1', '0037566', '??? "??????????-??????"', '220070, ?.?????, ??.???????, ?.187, ?????? 7', '12345667789', '220070, ?.?????, ??.???????, ?.187, ?????? 7, ?????????? ????????', '1. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 12. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 1', '2014-07-21', '2015-07-21', '?????? ?.?.', '?.?.?????????', '????????? ???? ?1', '2014-07-28', '2016-12-20 01:04:04'),
	(10, 2, '0 - 4957.1/3869-1', '0037566', '??? "??????????-??????"', '220070, ?.?????, ??.???????, ?.187, ?????? 7', '12345667789', '220070, ?.?????, ??.???????, ?.187, ?????? 7, ?????????? ????????', '1. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 12. ?????? ??????, 220070, ?.?????, ??.???????, ?.157, ?????? 1', '2014-07-21', '2015-07-21', '?????? ?.?.', '?.?.?????????', '????????? ???? ?1', '2014-07-28', '2016-12-20 01:15:04');
/*!40000 ALTER TABLE `owncertificate` ENABLE KEYS */;


-- Дамп структуры для таблица owncert.ownproduct
CREATE TABLE IF NOT EXISTS `ownproduct` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `id_certificate` int(10) unsigned NOT NULL,
  `number` int(10) unsigned DEFAULT NULL,
  `name` varchar(1024) COLLATE utf8_unicode_ci NOT NULL,
  `code` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_certificate_to_product` (`id_certificate`),
  CONSTRAINT `FK_certificate_to_product` FOREIGN KEY (`id_certificate`) REFERENCES `owncertificate` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Дамп данных таблицы owncert.ownproduct: ~21 rows (приблизительно)
/*!40000 ALTER TABLE `ownproduct` DISABLE KEYS */;
INSERT INTO `ownproduct` (`id`, `id_certificate`, `number`, `name`, `code`) VALUES
	(1, 3, 1, '??????????', '8537 20 910 0'),
	(2, 3, 2, '??????', '8537 20 910 0'),
	(3, 3, 3, '???????????????', '8537 20 910 0'),
	(4, 4, 1, '??????????', '8537 20 910 0'),
	(5, 4, 2, '??????', '8537 20 910 0'),
	(6, 4, 3, '???????????????', '8537 20 910 0'),
	(7, 5, 1, '??????????', '8537 20 910 0'),
	(8, 5, 2, '??????', '8537 20 910 0'),
	(9, 5, 3, '???????????????', '8537 20 910 0'),
	(10, 7, 1, '??????????', '8537 20 910 0'),
	(11, 7, 2, '??????', '8537 20 910 0'),
	(12, 7, 3, '???????????????', '8537 20 910 0'),
	(13, 8, 1, '??????????', '8537 20 910 0'),
	(14, 8, 2, '??????', '8537 20 910 0'),
	(15, 8, 3, '???????????????', '8537 20 910 0'),
	(16, 9, 1, '??????????', '8537 20 910 0'),
	(17, 9, 2, '??????', '8537 20 910 0'),
	(18, 9, 3, '???????????????', '8537 20 910 0'),
	(19, 10, 1, '??????????', '8537 20 910 0'),
	(20, 10, 2, '??????', '8537 20 910 0'),
	(21, 10, 3, '???????????????', '8537 20 910 0');
/*!40000 ALTER TABLE `ownproduct` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;