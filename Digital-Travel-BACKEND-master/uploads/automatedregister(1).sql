-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 04, 2019 at 01:43 PM
-- Server version: 5.1.53
-- PHP Version: 5.3.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `automatedregister`
--

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(49);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_employees`
--

CREATE TABLE IF NOT EXISTS `tbl_employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `accountnumber` varchar(10) NOT NULL,
  `bankname` varchar(30) NOT NULL,
  `phonenumber` varchar(20) NOT NULL,
  `address` varchar(100) NOT NULL,
  `location` varchar(100) DEFAULT NULL,
  `enabled` int(10) NOT NULL DEFAULT '0',
  `password` varchar(150) NOT NULL,
  `medicalid` varchar(50) DEFAULT NULL,
  `surgeonid` varchar(50) DEFAULT NULL,
  `nurseid` varchar(50) DEFAULT NULL,
  `pharmacistid` varchar(50) DEFAULT NULL,
  `vehiclenumber` varchar(50) DEFAULT NULL,
  `locationassigned` varchar(100) DEFAULT NULL,
  `usertype` varchar(30) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `dateofbirth` date NOT NULL,
  `emailtoken` varchar(10) NOT NULL,
  `updatepasswordtoken` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=40 ;

--
-- Dumping data for table `tbl_employees`
--

INSERT INTO `tbl_employees` (`id`, `firstname`, `lastname`, `email`, `accountnumber`, `bankname`, `phonenumber`, `address`, `location`, `enabled`, `password`, `medicalid`, `surgeonid`, `nurseid`, `pharmacistid`, `vehiclenumber`, `locationassigned`, `usertype`, `gender`, `dateofbirth`, `emailtoken`, `updatepasswordtoken`) VALUES
(18, 'ademola', 'tokunbo', 'olaleye.davedo@yahoo.com', '0118709099', '', '08034556712', 'ikoyi lagos', '', 1, '$2a$10$yKuixx7WqMrANWp6Uu3Qt.teC.FvI/rkg6TkixUCKLEM074qcUgbC', '0', '0', '0', '0', '0', '0', '', '', '2019-11-12', '', NULL),
(20, 'adeola', 'olaleye', 'olaleye.deola@yahoo.com', '0118709099', '', '08034556712', 'ikoyi lagos', '', 1, '$2a$10$InZiXrjrN9rbr97qQWe1UO3pTFNnlBP4tzBNxdsu6RzFqX/PQa0iy', '0', '0', '0', '0', '0', '0', '', '', '2019-11-12', '', NULL),
(25, 'tony', 'adamse', 'adams.tony@yahoo.com', '0118709099', 'first bank', '08034556712', 'ikoyi lagos', 'ikoyi primary health', 1, '$2a$10$w96UwLO3xNmO36ZL0Lj2Y.dAnca1YB9TlcGC/yi.69pXmybDXuIq2', '001892675', NULL, NULL, NULL, NULL, NULL, 'medical doctor', 'male', '1996-11-15', '', NULL),
(31, 'tolani', 'titideko', 'adams.tonydam@yahoo.com', '0118709099', 'gt bank', '08034556712', 'ikeja lagos', 'ikeja primary health', 1, '$2a$10$0szl9DHp53taASb6cxK43.pTx7mV62RT1WkAr/OZ1hVieVJBFxfku', '001892675', NULL, NULL, NULL, NULL, NULL, 'medical doctor', 'male', '1996-11-15', '', NULL),
(32, 'tolani', 'titideko', 'adams@yahoo.com', '0118709099', 'gt bank', '08034556712', 'ikeja lagos', 'ikeja primary health', 1, '$2a$10$rEAM2RTzje1eMwHzTYLXVOc0zvS.BvWUhB/7b6zhi1Cl.1ObbfMBO', '001892675', NULL, NULL, NULL, NULL, NULL, 'medical doctor', 'male', '1996-11-15', '', NULL),
(33, 'tolani', 'titideko', 'diwe@yahoo.com', '0118709099', 'gt bank', '08034556712', 'ikeja lagos', 'ikeja primary health', 1, '$2a$10$bKMpEf3aUuXfoIBre.F9.uZUN8oeAfwI1NR37I/nr4sKeofJh3ia2', '001892675', NULL, NULL, NULL, NULL, NULL, 'medical doctor', 'male', '1996-11-15', '', NULL),
(34, 'tol', 'deko', 'diwe123@yahoo.com', '0118709099', 'heritage bank', '08034556712', 'ikeja lagos', 'ikeja primary health', 1, '$2a$10$4chWfR0sksq6aQ0SA0QYS.xZvGhKRrF5uGQtK7KAujEePKKefsEie', '001892675', NULL, NULL, NULL, NULL, NULL, 'medical doctor', 'male', '1996-11-15', '', NULL),
(36, 'tol', 'deko', 'olaleye.adeda1234555555@yahoo.com', '0118709099', 'heritage bank', '08034556712', 'ikeja lagos', 'ikeja primary health', 1, '$2a$10$sPUeBadj2/sZsnUCXH9Z5unGHaxleQ./.5zUhzMHJ8UZzD9z49v9y', '001892675', NULL, NULL, NULL, NULL, NULL, 'medical doctor', 'male', '1996-11-15', '', NULL),
(38, 'new', 'dayo', 'olaleye.adedayo@yahoo.com', '0118709099', 'heritage bank', '08034556712', 'ikeja lagos', 'ikeja primary health', 1, '$2a$10$l/f6r/XyaOEnu1yZjNPxp.Sit0811IKs2Prd2w0.6ofzvaHEHzGwe', '001892675', NULL, NULL, NULL, NULL, NULL, 'medical doctor', 'male', '1996-11-15', '9247', '1785'),
(39, 'newest', 'diwe', 'adedayosauce60@yahoo.com', '0118709099', 'heritage bank', '08034556712', 'ikeja lagos', 'ikeja primary health', 0, '$2a$10$fDVMYjsIdqgf6kxQwq7qquU5OzISqmMmkVRjsB/wEDlCKXjSTfvvS', '001892675', NULL, NULL, NULL, NULL, NULL, 'medical doctor', 'male', '1996-11-15', '0705', '5453');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_timeregister`
--

CREATE TABLE IF NOT EXISTS `tbl_timeregister` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `timein` time DEFAULT NULL,
  `timeout` time DEFAULT NULL,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `location` varchar(100) NOT NULL,
  `timecount` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=49 ;

--
-- Dumping data for table `tbl_timeregister`
--

INSERT INTO `tbl_timeregister` (`id`, `email`, `date`, `timein`, `timeout`, `firstname`, `lastname`, `location`, `timecount`) VALUES
(20, 'olaleye@yahoo.com', '2019-11-29', '15:10:21', '15:20:43', 'oluwaseyi', 'ogunnusi', 'longbridge united states', '0'),
(21, 'adeoluwa@yahoo.com', '2019-12-03', '12:01:19', '12:21:14', 'oluwaseyi', 'ogunnusi', 'longbridge united states', '0'),
(43, 'chuksokafor@yahoo.com', '2019-12-03', '13:29:41', '13:55:02', 'chukwudi', 'ogunnusi', 'ogunnusi', '0'),
(44, 'tomisin@yahoo.com', '2019-12-03', '13:58:34', '13:59:06', 'tomi', 'ogunnusi', 'ogunnusi', '0'),
(45, 'chudi@yahoo.com', '2019-12-03', '14:14:23', '14:54:02', 'chudi', 'isiendo', 'isiendo', '1'),
(46, 'tobilafia@yahoo.com', '2019-12-03', '14:52:43', '14:54:46', 'tobi', 'alafia', 'alafia', '1'),
(47, 'esterous@yahoo.com', '2019-12-03', '15:04:19', NULL, 'esthereous', 'alafia', 'alafia', NULL),
(48, 'tobilafia@yahoo.com', '2019-12-03', '16:48:35', NULL, 'esthereous', 'alafia', 'longbridge united states', NULL);
