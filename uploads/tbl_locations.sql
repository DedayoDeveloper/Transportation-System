-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 08, 2020 at 01:53 PM
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
-- Database: `automatedregister`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_locations`
--

CREATE TABLE `tbl_locations` (
  `id` int(11) NOT NULL,
  `medicalcenter` varchar(50) NOT NULL,
  `longitude` varchar(45) DEFAULT NULL,
  `latitude` varchar(45) DEFAULT NULL,
  `hospitalname` varchar(255) NOT NULL,
  `locationname` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_locations`
--

INSERT INTO `tbl_locations` (`id`, `medicalcenter`, `longitude`, `latitude`, `hospitalname`, `locationname`) VALUES
(1, 'General Hospital Shomolu', '', '', '', ''),
(2, 'General Hospital Amuwo Odofin', '', '', '', ''),
(3, 'General Hospital Lagos', '', '', '', ''),
(4, 'Onikan Health Center', '3.405323', '6.444787', '', ''),
(5, 'Mushin General Hospital', '3.346372', '6.531153', '', ''),
(6, 'Ikorodu General Hospital', '3.499376', '6.608696', '', ''),
(7, 'Alimosho General Hospital', '3.25181', '6.561801', '', ''),
(8, 'Shomolu Local Government', '', '', '', ''),
(9, 'Mushin Local Government', '', '', '', ''),
(10, 'Alimosho Local Government', '', '', '', ''),
(11, 'Amuwo Odofin Local Government', '', '', '', ''),
(12, 'Ikorodu Local Government', '', '', '', ''),
(13, 'Epe Local Government', '', '', '', ''),
(14, 'Ajara Badagry', '2.890799', '6.434806', '', ''),
(15, 'Akerele - Surulere', '3.352642', '6.506219', '', ''),
(16, 'Iga Iduganran - Lagos Island', '3.389722', '6.465393', '', ''),
(17, 'Epe - Epe', '3.980337', '6.589462', '', ''),
(18, 'Ikotun - Alimosho', '3.269597', '6.552993', '', ''),
(19, 'Onigbongbo - Ikeja', '3.364684', '6.570743', '', ''),
(20, 'Ita-Elewa - Ikorodu', '3.502438', '6.612647', '', ''),
(21, 'General Hospital, Lagos', '', '', '', ''),
(22, 'General Hospital, Badagry', '2.896861', '6.413626', '', ''),
(23, 'Onikan Hospital', '', '', '', ''),
(24, 'Randle General Hospital', '3.357386', '6.508236', '', ''),
(25, 'Gbagada General Hospital', '3.387574', '6.551908', '', ''),
(26, 'Orile Agege', '3.303652', '6.634493', '', ''),
(28, 'Massey Children Hospital', '3.391864', '6.454843', '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_locations`
--
ALTER TABLE `tbl_locations`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_locations`
--
ALTER TABLE `tbl_locations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
