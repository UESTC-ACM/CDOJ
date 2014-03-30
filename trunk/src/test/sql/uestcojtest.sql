/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP DATABASE IF EXISTS `uestcojtest`;
CREATE DATABASE `uestcojtest`;

USE `uestcojtest`;
--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `article` (
  `articleId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) COLLATE utf8_bin NOT NULL,
  `content` text COLLATE utf8_bin NOT NULL,
  `time` datetime NOT NULL,
  `clicked` int(11) NOT NULL DEFAULT '0',
  `order` int(11) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '0',
  `isVisible` tinyint(1) NOT NULL DEFAULT '0',
  `parentId` int(11) DEFAULT NULL,
  `problemId` int(11) DEFAULT NULL,
  `contestId` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `OPTLOCK` int(11) DEFAULT '0',
  PRIMARY KEY (`articleId`),
  UNIQUE KEY `noticeId_UNIQUE` (`articleId`),
  KEY `FK_parentId_on_article_idx` (`parentId`),
  KEY `FK_problemId_on_problem_idx` (`problemId`),
  KEY `FK_contestId_on_contest_idx` (`contestId`),
  KEY `FK_userId_on_user_idx` (`userId`),
  CONSTRAINT `FK_article_contestId_on_contest` FOREIGN KEY (`contestId`) REFERENCES `contest` (`contestId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_article_parentId_on_article` FOREIGN KEY (`parentId`) REFERENCES `article` (`articleId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_article_problemId_on_problem` FOREIGN KEY (`problemId`) REFERENCES `problem` (`problemId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_article_userId_on_user` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `code`
--

DROP TABLE IF EXISTS `code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `code` (
  `codeId` int(11) NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8_bin NOT NULL,
  `OPTLOCK` int(11) DEFAULT '0',
  `share` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`codeId`),
  UNIQUE KEY `codeId_UNIQUE` (`codeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `compileInfo`
--

DROP TABLE IF EXISTS `compileInfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compileInfo` (
  `compileInfoId` int(11) NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8_bin NOT NULL,
  `OPTLOCK` int(11) DEFAULT '0',
  PRIMARY KEY (`compileInfoId`),
  UNIQUE KEY `compileInfoId_UNIQUE` (`compileInfoId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contest`
--

DROP TABLE IF EXISTS `contest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contest` (
  `contestId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'length >= 3',
  `description` text COLLATE utf8_bin NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `time` datetime NOT NULL,
  `length` int(11) NOT NULL,
  `isVisible` tinyint(1) NOT NULL,
  `OPTLOCK` int(11) DEFAULT '0',
  `password` VARCHAR(40) NULL ,
  `parentId` INT NULL ,
  PRIMARY KEY (`contestId`),
  UNIQUE KEY `contestId_UNIQUE` (`contestId`),
  INDEX `FK_parentId_on_contest_idx_idx` (`parentId` ASC) ,
  CONSTRAINT `FK_parentId_on_contest_idx`
    FOREIGN KEY (`parentId` )
    REFERENCES `contest` (`contestId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contestProblem`
--

DROP TABLE IF EXISTS `contestProblem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contestProblem` (
  `contestProblemId` int(11) NOT NULL AUTO_INCREMENT,
  `contestId` int(11) NOT NULL,
  `problemId` int(11) NOT NULL,
  `order` int(11) NOT NULL,
  `OPTLOCK` int(11) DEFAULT '0',
  PRIMARY KEY (`contestProblemId`),
  UNIQUE KEY `contestProblemId_UNIQUE` (`contestProblemId`),
  KEY `FK_contestId_on_contest_idx` (`contestId`),
  KEY `FK_problemId_on_problem_idx` (`problemId`),
  CONSTRAINT `FK_contestProblem_contestId_on_contest` FOREIGN KEY (`contestId`) REFERENCES `contest` (`contestId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_contestProblem_problemId_on_problem` FOREIGN KEY (`problemId`) REFERENCES `problem` (`problemId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contestTeam`
--

DROP TABLE IF EXISTS `contestTeam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contestTeam` (
  `contestTeamId` int(11) NOT NULL AUTO_INCREMENT,
  `contestId` int(11) NOT NULL,
  `teamId` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `comment` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`contestTeamId`),
  UNIQUE KEY `contestTeamId_UNIQUE` (`contestTeamId`),
  KEY `FK_contestTeam_contestId_on_contest_idx` (`contestId`),
  KEY `FK_contestTeam_teamId_on_team_idx` (`teamId`),
  CONSTRAINT `FK_contestTeam_contestId_on_contest` FOREIGN KEY (`contestId`) REFERENCES `contest` (`contestId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_contestTeam_teamId_on_team` FOREIGN KEY (`teamId`) REFERENCES `team` (`teamId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contestUser`
--

DROP TABLE IF EXISTS `contestUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contestUser` (
  `contestUserId` int(11) NOT NULL AUTO_INCREMENT,
  `contestId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL COMMENT '0 - wait for validating\\n1 - accepted\\n2 - refused',
  `OPTLOCK` int(11) DEFAULT '0',
  `comment` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`contestUserId`),
  UNIQUE KEY `contestUserId_UNIQUE` (`contestUserId`),
  KEY `FK_contestId_on_contest_idx` (`contestId`),
  KEY `FK_userId_on_user_idx` (`userId`),
  CONSTRAINT `FK_contestUser_contestId_on_contest` FOREIGN KEY (`contestId`) REFERENCES `contest` (`contestId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_contestUser_userId_on_user` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `departmentId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'department\\''s name',
  `OPTLOCK` int(11) DEFAULT '0',
  PRIMARY KEY (`departmentId`),
  UNIQUE KEY `departmentId_UNIQUE` (`departmentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `language`
--

DROP TABLE IF EXISTS `language`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language` (
  `languageId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `extension` varchar(10) COLLATE utf8_bin NOT NULL,
  `param` text COLLATE utf8_bin NOT NULL,
  `OPTLOCK` int(11) DEFAULT '0',
  PRIMARY KEY (`languageId`),
  UNIQUE KEY `languageId_UNIQUE` (`languageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `messageId` int(11) NOT NULL AUTO_INCREMENT,
  `senderId` int(11) NOT NULL,
  `receiverId` int(11) NOT NULL,
  `title` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `content` text COLLATE utf8_bin NOT NULL,
  `time` datetime NOT NULL,
  `isOpened` tinyint(1) NOT NULL DEFAULT '0',
  `OPTLOCK` int(11) DEFAULT '0',
  PRIMARY KEY (`messageId`),
  UNIQUE KEY `messageId_UNIQUE` (`messageId`),
  KEY `FK_senderId_on_user_idx` (`senderId`),
  KEY `FK_receiverId_on_user_idx` (`receiverId`),
  CONSTRAINT `FK_message_receiverId_on_user` FOREIGN KEY (`receiverId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_message_senderId_on_user` FOREIGN KEY (`senderId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem` (
  `problemId` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) COLLATE utf8_bin NOT NULL,
  `description` text COLLATE utf8_bin NOT NULL,
  `input` text COLLATE utf8_bin NOT NULL,
  `output` text COLLATE utf8_bin NOT NULL,
  `sampleInput` text COLLATE utf8_bin NOT NULL,
  `sampleOutput` text COLLATE utf8_bin NOT NULL,
  `hint` text COLLATE utf8_bin NOT NULL,
  `source` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `timeLimit` int(11) NOT NULL DEFAULT '1000',
  `memoryLimit` int(11) NOT NULL DEFAULT '65535',
  `solved` int(11) NOT NULL DEFAULT '0',
  `tried` int(11) NOT NULL DEFAULT '0',
  `isSPJ` tinyint(1) NOT NULL,
  `isVisible` tinyint(1) NOT NULL,
  `outputLimit` int(11) NOT NULL DEFAULT '8000',
  `javaTimeLimit` int(11) NOT NULL DEFAULT '3000',
  `javaMemoryLimit` int(11) NOT NULL DEFAULT '65535',
  `dataCount` int(11) NOT NULL DEFAULT '1',
  `difficulty` int(11) NOT NULL DEFAULT '1',
  `OPTLOCK` int(11) DEFAULT '0',
  PRIMARY KEY (`problemId`),
  UNIQUE KEY `problemId_UNIQUE` (`problemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `problemTag`
--

DROP TABLE IF EXISTS `problemTag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problemTag` (
  `problemTagId` int(11) NOT NULL AUTO_INCREMENT,
  `problemId` int(11) NOT NULL,
  `tagId` int(11) NOT NULL,
  `OPTLOCK` int(11) DEFAULT '0',
  PRIMARY KEY (`problemTagId`),
  UNIQUE KEY `problemTagId_UNIQUE` (`problemTagId`),
  KEY `FK_problemId_on_problem_idx` (`problemId`),
  KEY `FK_tagId_on_tag_idx` (`tagId`),
  CONSTRAINT `FK_problemTag_problemId_on_problem` FOREIGN KEY (`problemId`) REFERENCES `problem` (`problemId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_problemTag_tagId_on_tag` FOREIGN KEY (`tagId`) REFERENCES `tag` (`tagId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `statusId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `problemId` int(11) NOT NULL,
  `result` int(11) NOT NULL,
  `memoryCost` int(11) NOT NULL,
  `timeCost` int(11) NOT NULL,
  `languageId` int(11) NOT NULL,
  `length` int(11) NOT NULL,
  `time` datetime NOT NULL,
  `contestId` int(11) DEFAULT NULL,
  `caseNumber` int(11) NOT NULL DEFAULT '0',
  `codeId` int(11) NOT NULL,
  `compileInfoId` int(11) DEFAULT NULL,
  `OPTLOCK` int(11) DEFAULT '0',
  PRIMARY KEY (`statusId`),
  UNIQUE KEY `statusId_UNIQUE` (`statusId`),
  KEY `FK_userID_on_user_idx` (`userId`),
  KEY `FK_problemId_on_problem_idx` (`problemId`),
  KEY `FK_languageId_on_language_idx` (`languageId`),
  KEY `FK_contestId_on_contst_idx` (`contestId`),
  KEY `FK_codeId_on_code_idx` (`codeId`),
  KEY `FK_compileInfoId_on_compileInfo_idx` (`compileInfoId`),
  CONSTRAINT `FK_status_codeId_on_code` FOREIGN KEY (`codeId`) REFERENCES `code` (`codeId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_status_compileInfoId_on_compileInfo` FOREIGN KEY (`compileInfoId`) REFERENCES `compileInfo` (`compileInfoId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_status_contestId_on_contest` FOREIGN KEY (`contestId`) REFERENCES `contest` (`contestId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_status_languageId_on_language` FOREIGN KEY (`languageId`) REFERENCES `language` (`languageId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_status_problemId_on_problem` FOREIGN KEY (`problemId`) REFERENCES `problem` (`problemId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_status_userID_on_user` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `tagId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `OPTLOCK` int(11) DEFAULT '0',
  PRIMARY KEY (`tagId`),
  UNIQUE KEY `tagId_UNIQUE` (`tagId`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `teamId` int(11) NOT NULL AUTO_INCREMENT,
  `teamName` varchar(45) COLLATE utf8_bin NOT NULL,
  `leaderId` int(11) NOT NULL,
  PRIMARY KEY (`teamId`),
  UNIQUE KEY `teamName_UNIQUE` (`teamName`),
  UNIQUE KEY `teamId_UNIQUE` (`teamId`),
  KEY `leaderId_idx` (`leaderId`),
  CONSTRAINT `FK_team_leaderId_on_user` FOREIGN KEY (`leaderId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teamUser`
--

DROP TABLE IF EXISTS `teamUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teamUser` (
  `teamUserId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `teamId` int(11) NOT NULL,
  `allow` tinyint(1) NOT NULL,
  PRIMARY KEY (`teamUserId`),
  UNIQUE KEY `teamUserId_UNIQUE` (`teamUserId`),
  KEY `teamId_idx` (`teamId`),
  KEY `userId_idx` (`userId`),
  CONSTRAINT `FK_team_teamId_on_team` FOREIGN KEY (`teamId`) REFERENCES `team` (`teamId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_team_userId_on_user` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trainingContest`
--

DROP TABLE IF EXISTS `trainingContest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainingContest` (
  `trainingContestId` int(11) NOT NULL AUTO_INCREMENT,
  `isPersonal` tinyint(1) NOT NULL,
  `title` varchar(150) COLLATE utf8_bin NOT NULL DEFAULT '',
  `OPTLOCK` int(11) DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`trainingContestId`),
  UNIQUE KEY `traningContestId_UNIQUE` (`trainingContestId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trainingStatus`
--

DROP TABLE IF EXISTS `trainingStatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainingStatus` (
  `trainingStatusId` int(11) NOT NULL AUTO_INCREMENT,
  `trainingContestId` int(11) NOT NULL,
  `trainingUserId` int(11) NOT NULL,
  `rating` double NOT NULL,
  `volatility` double NOT NULL,
  `OPTLOCK` int(11) DEFAULT '0',
  `rank` int(11) NOT NULL,
  `solve` int(11) NOT NULL,
  `penalty` int(11) NOT NULL,
  `ratingVary` double NOT NULL,
  `volatilityVary` double NOT NULL,
  `summary` text COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`trainingStatusId`),
  UNIQUE KEY `trainingStatusId_UNIQUE` (`trainingStatusId`),
  KEY `FK_trainingStatus_trainingContestId_on_trainingContest_idx` (`trainingContestId`),
  KEY `FK_trainingStatus_trainingUserId_on_user_idx` (`trainingUserId`),
  CONSTRAINT `FK_trainingStatus_trainingContestId_on_trainingContest` FOREIGN KEY (`trainingContestId`) REFERENCES `trainingContest` (`trainingContestId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_trainingStatus_trainingUserId_on_trainingUser` FOREIGN KEY (`trainingUserId`) REFERENCES `trainingUser` (`trainingUserId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trainingUser`
--

DROP TABLE IF EXISTS `trainingUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainingUser` (
  `trainingUserId` int(11) NOT NULL AUTO_INCREMENT,
  `rating` double NOT NULL,
  `volatility` double NOT NULL,
  `type` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `OPTLOCK` int(11) DEFAULT NULL,
  `name` varchar(45) COLLATE utf8_bin NOT NULL,
  `allow` tinyint(1) NOT NULL,
  `ratingVary` double NOT NULL DEFAULT '0',
  `volatilityVary` double NOT NULL DEFAULT '0',
  `competitions` int(11) NOT NULL,
  `member` varchar(128) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`trainingUserId`),
  UNIQUE KEY `trainingUserId_UNIQUE` (`trainingUserId`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `FK_trainingUser_userId_on_user_idx` (`userId`),
  CONSTRAINT `FK_trainingUser_userId_on_user` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(24) COLLATE utf8_bin NOT NULL,
  `studentId` varchar(50) COLLATE utf8_bin NOT NULL,
  `departmentId` int(11) NOT NULL,
  `password` varchar(40) COLLATE utf8_bin NOT NULL COMMENT 'need to validate\\nuse SHA1 encoding',
  `school` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '',
  `nickName` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'length >= 3',
  `email` varchar(100) COLLATE utf8_bin NOT NULL COMMENT 'need to validate',
  `solved` int(11) NOT NULL DEFAULT '0',
  `tried` int(11) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '0',
  `lastLogin` datetime NOT NULL,
  `OPTLOCK` int(11) DEFAULT '0',
  `motto` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '这个人很屌，什么都没写。。。',
  `name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '',
  `sex` int(11) NOT NULL DEFAULT '0',
  `grade` int(11) NOT NULL DEFAULT '0',
  `phone` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT '',
  `size` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId_UNIQUE` (`userId`),
  UNIQUE KEY `userName_UNIQUE` (`userName`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `FK_departmentId_on_department_idx` (`departmentId`),
  CONSTRAINT `FK_user_departmentId_on_department` FOREIGN KEY (`departmentId`) REFERENCES `department` (`departmentId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `userSerialKey`
--

DROP TABLE IF EXISTS `userSerialKey`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userSerialKey` (
  `userSerialKeyId` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `serialKey` varchar(128) COLLATE utf8_bin NOT NULL,
  `time` datetime NOT NULL,
  `OPTLOCK` int(11) DEFAULT '0',
  PRIMARY KEY (`userSerialKeyId`),
  UNIQUE KEY `userSerialKeyId_UNIQUE` (`userSerialKeyId`),
  KEY `FK_userId_on_user_idx` (`userId`),
  CONSTRAINT `FK_userSerialKey_userId_on_user` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-03-26  0:16:33
