-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 23, 2020 at 02:14 PM
-- Server version: 10.4.10-MariaDB
-- PHP Version: 7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `travelinsurance`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_trips`
--

CREATE TABLE `tbl_trips` (
  `id` bigint(20) NOT NULL,
  `date` varchar(255) DEFAULT NULL,
  `departure` varchar(255) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `drivername` varchar(255) DEFAULT NULL,
  `filedownloaduri` varchar(255) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `seats` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  `transportcompany` varchar(255) DEFAULT NULL,
  `vehiclenumber` varchar(255) DEFAULT NULL,
  `vehicletype` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbl_trips`
--

INSERT INTO `tbl_trips` (`id`, `date`, `departure`, `destination`, `drivername`, `filedownloaduri`, `price`, `seats`, `time`, `transportcompany`, `vehiclenumber`, `vehicletype`, `station`) VALUES
(16, '2020-01-21', 'Lagos', 'Abuja', 'mr olafunmi', NULL, '7000', '26', '12:00:00', 'ABC motors', 'eky-23etry', 'toyota hiace', 'jibowu'),
(24, '2020-01-21', 'Lagos', 'Abuja', 'mr adedayo', NULL, '10000', '20', '12:00:00', 'GIG motors', 'eky-23etry', 'toyota hiace', 'jibowu'),
(25, '2020-01-21', 'Lagos', 'Abuja', 'mr oluwole', NULL, '8000', '23', '12:00:00', 'Ekesons motors', 'eky-23etry', 'toyota hiace', 'jibowu');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_trips`
--
ALTER TABLE `tbl_trips`
  ADD PRIMARY KEY (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
