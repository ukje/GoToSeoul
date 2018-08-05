/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.21-log : Database - gotoseoul
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`gotoseoul` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `gotoseoul`;

/*Table structure for table `거래_일` */

DROP TABLE IF EXISTS `거래_일`;

CREATE TABLE `거래_일` (
  `종목코드` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `종목명` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `일자` char(8) COLLATE utf8_unicode_ci NOT NULL,
  `외인_보유수` bigint(20) DEFAULT NULL,
  `외인_지분율` decimal(7,2) DEFAULT NULL,
  `외인_순매수` bigint(20) DEFAULT NULL,
  `기관_순매수` bigint(20) DEFAULT NULL,
  `종가` bigint(20) DEFAULT NULL,
  `전일비` bigint(20) DEFAULT NULL,
  `등락률` decimal(7,2) DEFAULT NULL,
  `생성시간` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`종목코드`,`일자`),
  FULLTEXT KEY `IDX_거래_일_01` (`일자`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `기업개요` */

DROP TABLE IF EXISTS `기업개요`;

CREATE TABLE `기업개요` (
  `종목코드` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `종목명` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `개요` text COLLATE utf8_unicode_ci NOT NULL,
  `생성시간` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`종목코드`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `실적_분기` */

DROP TABLE IF EXISTS `실적_분기`;

CREATE TABLE `실적_분기` (
  `종목코드` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `종목명` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FY_End` char(6) COLLATE utf8_unicode_ci NOT NULL,
  `실적연도` char(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `실적분기` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FY구분` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `주재무제표` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `매출액` bigint(20) DEFAULT NULL,
  `YoY` decimal(7,2) DEFAULT NULL,
  `영업이익` bigint(20) DEFAULT NULL,
  `당기순이익` bigint(20) DEFAULT NULL,
  `EPS` bigint(20) DEFAULT NULL,
  `BPS` bigint(20) DEFAULT NULL,
  `PER` decimal(7,2) DEFAULT NULL,
  `PBR` decimal(7,2) DEFAULT NULL,
  `ROE` decimal(7,2) DEFAULT NULL,
  `EV_EBITDA` decimal(7,2) DEFAULT NULL,
  `시총` bigint(20) DEFAULT NULL,
  `발행주식수` bigint(20) DEFAULT NULL,
  `생성시간` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`종목코드`,`FY_End`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `실적_분기_01` */

DROP TABLE IF EXISTS `실적_분기_01`;

CREATE TABLE `실적_분기_01` (
  `종목코드` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `종목명` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FY_End` char(6) COLLATE utf8_unicode_ci NOT NULL,
  `실적연도` char(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `실적분기` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FY구분` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `주재무제표` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `매출액` bigint(20) DEFAULT NULL,
  `YoY` decimal(7,2) DEFAULT NULL,
  `영업이익` bigint(20) DEFAULT NULL,
  `당기순이익` bigint(20) DEFAULT NULL,
  `EPS` bigint(20) DEFAULT NULL,
  `BPS` bigint(20) DEFAULT NULL,
  `PER` decimal(7,2) DEFAULT NULL,
  `PBR` decimal(7,2) DEFAULT NULL,
  `ROE` decimal(7,2) DEFAULT NULL,
  `EV_EBITDA` decimal(7,2) DEFAULT NULL,
  `시총` bigint(20) DEFAULT NULL,
  `발행주식수` bigint(20) DEFAULT NULL,
  `생성시간` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`종목코드`,`FY_End`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `실적_연간` */

DROP TABLE IF EXISTS `실적_연간`;

CREATE TABLE `실적_연간` (
  `종목코드` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `종목명` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FY_End` char(6) COLLATE utf8_unicode_ci NOT NULL,
  `실적연도` char(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FY구분` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `주재무제표` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `매출액` bigint(20) DEFAULT NULL,
  `YoY` decimal(7,2) DEFAULT NULL,
  `영업이익` bigint(20) DEFAULT NULL,
  `당기순이익` bigint(20) DEFAULT NULL,
  `EPS` bigint(20) DEFAULT NULL,
  `BPS` bigint(20) DEFAULT NULL,
  `PER` decimal(7,2) DEFAULT NULL,
  `PBR` decimal(7,2) DEFAULT NULL,
  `ROE` decimal(7,2) DEFAULT NULL,
  `EV_EBITDA` decimal(7,2) DEFAULT NULL,
  `시총` bigint(20) DEFAULT NULL,
  `발행주식수` bigint(20) DEFAULT NULL,
  `생성시간` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`종목코드`,`FY_End`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `실적_연간_01` */

DROP TABLE IF EXISTS `실적_연간_01`;

CREATE TABLE `실적_연간_01` (
  `종목코드` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `종목명` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FY_End` char(6) COLLATE utf8_unicode_ci NOT NULL,
  `실적연도` char(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FY구분` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `주재무제표` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `매출액` bigint(20) DEFAULT NULL,
  `YoY` decimal(7,2) DEFAULT NULL,
  `영업이익` bigint(20) DEFAULT NULL,
  `당기순이익` bigint(20) DEFAULT NULL,
  `EPS` bigint(20) DEFAULT NULL,
  `BPS` bigint(20) DEFAULT NULL,
  `PER` decimal(7,2) DEFAULT NULL,
  `PBR` decimal(7,2) DEFAULT NULL,
  `ROE` decimal(7,2) DEFAULT NULL,
  `EV_EBITDA` decimal(7,2) DEFAULT NULL,
  `시총` bigint(20) DEFAULT NULL,
  `발행주식수` bigint(20) DEFAULT NULL,
  `생성시간` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`종목코드`,`FY_End`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `재무` */

DROP TABLE IF EXISTS `재무`;

CREATE TABLE `재무` (
  `종목코드` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `종목명` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `재무구분` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `연간1` bigint(20) DEFAULT NULL,
  `연간2` bigint(20) DEFAULT NULL,
  `연간3` bigint(20) DEFAULT NULL,
  `연간4` bigint(20) DEFAULT NULL,
  `분기1` bigint(20) DEFAULT NULL,
  `분기2` bigint(20) DEFAULT NULL,
  `분기3` bigint(20) DEFAULT NULL,
  `분기4` bigint(20) DEFAULT NULL,
  `생성시간` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`종목코드`,`재무구분`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `종목` */

DROP TABLE IF EXISTS `종목`;

CREATE TABLE `종목` (
  `종목코드` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `종목명` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `종목_상세` */

DROP TABLE IF EXISTS `종목_상세`;

CREATE TABLE `종목_상세` (
  `종목코드` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `종목명` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `상장구분` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `업종` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WICS` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EPS` bigint(20) DEFAULT NULL,
  `BPS` bigint(20) DEFAULT NULL,
  `PER` decimal(10,2) DEFAULT NULL,
  `업종PER` decimal(10,2) DEFAULT NULL,
  `PBR` decimal(7,2) DEFAULT NULL,
  `현금배당수익률` decimal(7,2) DEFAULT NULL,
  `결산기` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `설립일` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `상장일` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `계열` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `종업원수` bigint(20) DEFAULT NULL,
  `발행주식수` bigint(20) DEFAULT NULL,
  `개발비1_회계연도` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `개발비1_지출총액` bigint(20) DEFAULT NULL,
  `개발비1_매출비율` decimal(7,2) DEFAULT NULL,
  `개발비2_회계연도` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `개발비2_지출총액` bigint(20) DEFAULT NULL,
  `개발비2_매출비율` decimal(7,2) DEFAULT NULL,
  `개발비3_회계연도` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `개발비3_지출총액` bigint(20) DEFAULT NULL,
  `개발비3_매출비율` decimal(7,2) DEFAULT NULL,
  `A종가` bigint(20) DEFAULT NULL,
  `APBR` decimal(7,2) DEFAULT NULL,
  `APER` decimal(7,2) DEFAULT NULL,
  `생성시간` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`종목코드`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `종목_상세_01` */

DROP TABLE IF EXISTS `종목_상세_01`;

CREATE TABLE `종목_상세_01` (
  `종목코드` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `종목명` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `상장구분` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `업종` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `WICS` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EPS` bigint(20) DEFAULT NULL,
  `BPS` bigint(20) DEFAULT NULL,
  `PER` decimal(10,2) DEFAULT NULL,
  `업종PER` decimal(10,2) DEFAULT NULL,
  `PBR` decimal(7,2) DEFAULT NULL,
  `현금배당수익률` decimal(7,2) DEFAULT NULL,
  `결산기` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `설립일` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `상장일` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `계열` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `종업원수` bigint(20) DEFAULT NULL,
  `발행주식수` bigint(20) DEFAULT NULL,
  `개발비1_회계연도` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `개발비1_지출총액` bigint(20) DEFAULT NULL,
  `개발비1_매출비율` decimal(7,2) DEFAULT NULL,
  `개발비2_회계연도` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `개발비2_지출총액` bigint(20) DEFAULT NULL,
  `개발비2_매출비율` decimal(7,2) DEFAULT NULL,
  `개발비3_회계연도` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `개발비3_지출총액` bigint(20) DEFAULT NULL,
  `개발비3_매출비율` decimal(7,2) DEFAULT NULL,
  `생성시간` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`종목코드`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
