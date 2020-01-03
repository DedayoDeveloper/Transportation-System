-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 04, 2019 at 01:44 PM
-- Server version: 5.1.53
-- PHP Version: 5.3.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `travelinsurance`
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
(12);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_tripbooking`
--

CREATE TABLE IF NOT EXISTS `tbl_tripbooking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `boarding` varchar(50) NOT NULL,
  `destination` varchar(50) NOT NULL,
  `departuredate` date NOT NULL,
  `price` varchar(20) NOT NULL,
  `numberofseats` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `tbl_tripbooking`
--

INSERT INTO `tbl_tripbooking` (`id`, `boarding`, `destination`, `departuredate`, `price`, `numberofseats`) VALUES
(6, 'GIG MOTORS JIBOWU', 'KEBBI', '2019-11-30', '50000', '2'),
(9, 'GIG MOTORS JIBOWU', 'KEBBI', '2019-11-30', '40000', '1'),
(11, 'GIG MOTORS JIBOWU', 'KEBBI', '2019-11-30', '40000', '1');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_trips`
--

CREATE TABLE IF NOT EXISTS `tbl_trips` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `departure` varchar(50) NOT NULL,
  `destination` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `price` varchar(20) NOT NULL,
  `seats` varchar(10) NOT NULL,
  `transportcompany` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `tbl_trips`
--

INSERT INTO `tbl_trips` (`id`, `departure`, `destination`, `date`, `price`, `seats`, `transportcompany`) VALUES
(1, 'lagos', 'kebbi', '2019-11-30', '50000', '50', 'GIG'),
(2, 'lagos', 'kano', '2019-11-29', '50000', '50', 'GIG');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(50) NOT NULL,
  `phonenumber` varchar(20) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `kinname` varchar(50) NOT NULL,
  `kinphonenumber` varchar(20) NOT NULL,
  `kinemail` varchar(30) DEFAULT NULL,
  `kinaddress` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `fullname`, `phonenumber`, `email`, `password`, `kinname`, `kinphonenumber`, `kinemail`, `kinaddress`) VALUES
(8, 'olaleye adetunde', '08034765517', 'olaleye.adetunde@gmail.com', '$2a$10$w5KunpCHNoMCRgpGkJOfFeRg9I7DDeSVAL6wNMu1xFH9D4QZCXDQ6', 'tola davies', '08034662217', 'tola@yahoo.com', 'lagos nigeria'),
(5, 'olaleye adeola', '08023009636', 'olaleye.adeola@gmail.com', '$2a$10$yVIsoK4yFEOuPpsAGlZ5LuY5Jm4rh5m9mJz7D0w4e8RZXYZXP/SbC', 'tunde odeyemi', '09094657833', 'deyemi@yahoo.com', 'ojodu berger');
