-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 15, 2015 at 05:52 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `project4`
--

-- --------------------------------------------------------

--
-- Table structure for table `courseavailibility`
--

CREATE TABLE IF NOT EXISTS `courseavailibility` (
  `CourseAvailibilityID` int(11) NOT NULL AUTO_INCREMENT,
  `CourseID` int(11) NOT NULL,
  `SemesterID` varchar(20) NOT NULL,
  `professorAvailibilityID` int(11) NOT NULL,
  `CourseLimit` int(11) NOT NULL,
  PRIMARY KEY (`CourseAvailibilityID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE IF NOT EXISTS `courses` (
  `CourseID` int(11) NOT NULL,
  `CourseName` varchar(50) NOT NULL,
  `Major` varchar(10) NOT NULL,
  `PreReqs` varchar(250) NOT NULL,
  `CoReqs` varchar(250) NOT NULL,
  `Availibility` enum('All','Spring','Summer','Fall','Spring/Fall') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='Pre/CoReqs are CourseID''s comma separated';

-- --------------------------------------------------------

--
-- Table structure for table `coursestaken`
--

CREATE TABLE IF NOT EXISTS `coursestaken` (
  `CoursesTakenIndex` int(11) NOT NULL AUTO_INCREMENT,
  `StudentID` int(11) NOT NULL,
  `SemesterID` varchar(20) NOT NULL,
  `CourseID` int(11) NOT NULL,
  `Grade` enum('P','W') NOT NULL,
  PRIMARY KEY (`CoursesTakenIndex`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `logintracking`
--

CREATE TABLE IF NOT EXISTS `logintracking` (
  `TrackingID` int(11) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(30) NOT NULL,
  `LoginTime` date NOT NULL,
  PRIMARY KEY (`TrackingID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `professoravailibility`
--

CREATE TABLE IF NOT EXISTS `professoravailibility` (
  `ProfessorAvailibilityID` int(11) NOT NULL AUTO_INCREMENT,
  `ProfessorID` varchar(30) NOT NULL COMMENT 'same as userID of a professor',
  `CourseID` int(11) NOT NULL,
  `SemesterID` varchar(20) NOT NULL,
  `Competency` varchar(200) NOT NULL,
  PRIMARY KEY (`ProfessorAvailibilityID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `recommededsolution`
--

CREATE TABLE IF NOT EXISTS `recommededsolution` (
  `RecommededSolutionID` int(11) NOT NULL AUTO_INCREMENT,
  `StudentID` varchar(20) NOT NULL,
  `professorAvailibilityID` int(11) NOT NULL,
  `taavailibilityID` int(11) NOT NULL,
  `date` date NOT NULL,
  `recommendedCourses` varchar(250) NOT NULL COMMENT 'comma delimited courseIDs',
  PRIMARY KEY (`RecommededSolutionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `semesters`
--

CREATE TABLE IF NOT EXISTS `semesters` (
  `SemesterID` varchar(20) NOT NULL,
  `Year` int(11) NOT NULL,
  `Season` enum('Fall','Spring','Summer') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='SemesterID is year+season';

-- --------------------------------------------------------

--
-- Table structure for table `studentpreferences`
--

CREATE TABLE IF NOT EXISTS `studentpreferences` (
  `StudentPreferenceID` int(11) NOT NULL AUTO_INCREMENT,
  `StudentID` varchar(30) NOT NULL COMMENT 'matches ID of user student',
  `SemesterID` varchar(20) NOT NULL,
  `numberOfCourses` int(11) NOT NULL,
  `CourseList` varchar(250) NOT NULL COMMENT 'Comma delimited?',
  PRIMARY KEY (`StudentPreferenceID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `taavailibility`
--

CREATE TABLE IF NOT EXISTS `taavailibility` (
  `taavailibilityID` int(11) NOT NULL AUTO_INCREMENT,
  `studentID` int(11) NOT NULL,
  `semesterID` int(11) NOT NULL,
  PRIMARY KEY (`taavailibilityID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `StudentID` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `firstName` varchar(30) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `UserType` enum('Student','Professor','Administrator') NOT NULL,
  UNIQUE KEY `StudentID` (`StudentID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
