SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `uestcoj` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `uestcoj` ;

-- -----------------------------------------------------
-- Table `uestcoj`.`department`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`department` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`department` (
  `departmentId` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL COMMENT 'department\'s name' ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`departmentId`) ,
  UNIQUE INDEX `departmentId_UNIQUE` (`departmentId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`user` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`user` (
  `userId` INT NOT NULL AUTO_INCREMENT ,
  `userName` VARCHAR(24) NOT NULL ,
  `studentId` VARCHAR(50) NOT NULL ,
  `departmentId` INT NOT NULL ,
  `password` VARCHAR(40) NOT NULL ,
  `school` VARCHAR(100) NOT NULL ,
  `nickName` VARCHAR(50) NOT NULL ,
  `email` VARCHAR(100) NOT NULL ,
  `solved` INT NOT NULL DEFAULT 0 ,
  `tried` INT NOT NULL DEFAULT 0 ,
  `type` INT NOT NULL DEFAULT 0 ,
  `lastLogin` DATETIME NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  `motto` VARCHAR(255) NOT NULL DEFAULT 'Hello world!' ,
  `name` VARCHAR(50) NOT NULL ,
  `sex` INT NOT NULL ,
  `grade` INT NOT NULL ,
  `phone` VARCHAR(45) NOT NULL ,
  `size` INT NOT NULL ,
  PRIMARY KEY (`userId`) ,
  UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) ,
  UNIQUE INDEX `userName_UNIQUE` (`userName` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) ,
  INDEX `FK_departmentId_on_department_idx` (`departmentId` ASC) ,
  CONSTRAINT `FK_user_departmentId_on_department`
    FOREIGN KEY (`departmentId` )
    REFERENCES `uestcoj`.`department` (`departmentId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`problem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`problem` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`problem` (
  `problemId` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(50) NOT NULL ,
  `description` TEXT NOT NULL ,
  `input` TEXT NOT NULL ,
  `output` TEXT NOT NULL ,
  `sampleInput` TEXT NOT NULL ,
  `sampleOutput` TEXT NOT NULL ,
  `hint` TEXT NOT NULL ,
  `source` VARCHAR(100) NOT NULL ,
  `timeLimit` INT NOT NULL DEFAULT 1000 ,
  `memoryLimit` INT NOT NULL DEFAULT 65535 ,
  `solved` INT NOT NULL DEFAULT 0 ,
  `tried` INT NOT NULL DEFAULT 0 ,
  `isSPJ` TINYINT(1) NOT NULL DEFAULT 0 ,
  `isVisible` TINYINT(1) NOT NULL DEFAULT 0 ,
  `outputLimit` INT NOT NULL DEFAULT 8000 ,
  `javaTimeLimit` INT NOT NULL DEFAULT 3000 ,
  `javaMemoryLimit` INT NOT NULL DEFAULT 65535 ,
  `dataCount` INT NOT NULL DEFAULT 0 ,
  `difficulty` INT NOT NULL DEFAULT 1 ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`problemId`) ,
  UNIQUE INDEX `problemId_UNIQUE` (`problemId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`contest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`contest` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`contest` (
  `contestId` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(50) NOT NULL ,
  `description` TEXT NOT NULL ,
  `type` TINYINT NOT NULL DEFAULT 0 ,
  `time` DATETIME NOT NULL ,
  `length` INT NOT NULL ,
  `isVisible` TINYINT(1) NOT NULL DEFAULT 0 ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  `password` VARCHAR(40) NULL ,
  `parentId` INT NULL ,
  PRIMARY KEY (`contestId`) ,
  UNIQUE INDEX `contestId_UNIQUE` (`contestId` ASC) ,
  INDEX `FK_parentId_on_contest_idx_idx` (`parentId` ASC) ,
  CONSTRAINT `FK_parentId_on_contest_idx`
    FOREIGN KEY (`parentId` )
    REFERENCES `uestcoj`.`contest` (`contestId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`article`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`article` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`article` (
  `articleId` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(50) NOT NULL ,
  `content` TEXT NOT NULL ,
  `time` DATETIME NOT NULL ,
  `clicked` INT NOT NULL DEFAULT 0 ,
  `order` INT NOT NULL DEFAULT 0 ,
  `type` INT NOT NULL DEFAULT 0 ,
  `isVisible` TINYINT(1) NOT NULL DEFAULT 0 ,
  `parentId` INT NULL DEFAULT NULL ,
  `problemId` INT NULL DEFAULT NULL ,
  `contestId` INT NULL DEFAULT NULL ,
  `userId` INT NULL DEFAULT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`articleId`) ,
  UNIQUE INDEX `noticeId_UNIQUE` (`articleId` ASC) ,
  INDEX `FK_parentId_on_article_idx` (`parentId` ASC) ,
  INDEX `FK_problemId_on_problem_idx` (`problemId` ASC) ,
  INDEX `FK_contestId_on_contest_idx` (`contestId` ASC) ,
  INDEX `FK_userId_on_user_idx` (`userId` ASC) ,
  CONSTRAINT `FK_article_parentId_on_article`
    FOREIGN KEY (`parentId` )
    REFERENCES `uestcoj`.`article` (`articleId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_article_problemId_on_problem`
    FOREIGN KEY (`problemId` )
    REFERENCES `uestcoj`.`problem` (`problemId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_article_contestId_on_contest`
    FOREIGN KEY (`contestId` )
    REFERENCES `uestcoj`.`contest` (`contestId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_article_userId_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcoj`.`user` (`userId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`message` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`message` (
  `messageId` INT NOT NULL AUTO_INCREMENT ,
  `senderId` INT NOT NULL ,
  `receiverId` INT NOT NULL ,
  `title` VARCHAR(50) NOT NULL ,
  `content` TEXT NOT NULL ,
  `time` DATETIME NOT NULL ,
  `isOpened` TINYINT(1) NOT NULL DEFAULT 0 ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`messageId`) ,
  UNIQUE INDEX `messageId_UNIQUE` (`messageId` ASC) ,
  INDEX `FK_senderId_on_user_idx` (`senderId` ASC) ,
  INDEX `FK_receiverId_on_user_idx` (`receiverId` ASC) ,
  CONSTRAINT `FK_message_senderId_on_user`
    FOREIGN KEY (`senderId` )
    REFERENCES `uestcoj`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_message_receiverId_on_user`
    FOREIGN KEY (`receiverId` )
    REFERENCES `uestcoj`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`contestUser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`contestUser` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`contestUser` (
  `contestUserId` INT NOT NULL AUTO_INCREMENT ,
  `contestId` INT NOT NULL ,
  `userId` INT NOT NULL ,
  `status` TINYINT(4) NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  `comment` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`contestUserId`) ,
  UNIQUE INDEX `contestUserId_UNIQUE` (`contestUserId` ASC) ,
  INDEX `FK_contestId_on_contest_idx` (`contestId` ASC) ,
  INDEX `FK_userId_on_user_idx` (`userId` ASC) ,
  CONSTRAINT `FK_contestUser_contestId_on_contest`
    FOREIGN KEY (`contestId` )
    REFERENCES `uestcoj`.`contest` (`contestId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_contestUser_userId_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcoj`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`contestProblem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`contestProblem` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`contestProblem` (
  `contestProblemId` INT NOT NULL AUTO_INCREMENT ,
  `contestId` INT NOT NULL ,
  `problemId` INT NOT NULL ,
  `order` INT NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`contestProblemId`) ,
  UNIQUE INDEX `contestProblemId_UNIQUE` (`contestProblemId` ASC) ,
  INDEX `FK_contestId_on_contest_idx` (`contestId` ASC) ,
  INDEX `FK_problemId_on_problem_idx` (`problemId` ASC) ,
  CONSTRAINT `FK_contestProblem_contestId_on_contest`
    FOREIGN KEY (`contestId` )
    REFERENCES `uestcoj`.`contest` (`contestId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_contestProblem_problemId_on_problem`
    FOREIGN KEY (`problemId` )
    REFERENCES `uestcoj`.`problem` (`problemId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`tag` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`tag` (
  `tagId` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`tagId`) ,
  UNIQUE INDEX `tagId_UNIQUE` (`tagId` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`problemTag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`problemTag` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`problemTag` (
  `problemTagId` INT NOT NULL AUTO_INCREMENT ,
  `problemId` INT NOT NULL ,
  `tagId` INT NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`problemTagId`) ,
  UNIQUE INDEX `problemTagId_UNIQUE` (`problemTagId` ASC) ,
  INDEX `FK_problemId_on_problem_idx` (`problemId` ASC) ,
  INDEX `FK_tagId_on_tag_idx` (`tagId` ASC) ,
  CONSTRAINT `FK_problemTag_problemId_on_problem`
    FOREIGN KEY (`problemId` )
    REFERENCES `uestcoj`.`problem` (`problemId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_problemTag_tagId_on_tag`
    FOREIGN KEY (`tagId` )
    REFERENCES `uestcoj`.`tag` (`tagId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`language`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`language` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`language` (
  `languageId` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL ,
  `extension` VARCHAR(10) NOT NULL ,
  `param` TEXT NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`languageId`) ,
  UNIQUE INDEX `languageId_UNIQUE` (`languageId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`code` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`code` (
  `codeId` INT NOT NULL AUTO_INCREMENT ,
  `content` TEXT NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  `share` TINYINT(1) NOT NULL DEFAULT false ,
  PRIMARY KEY (`codeId`) ,
  UNIQUE INDEX `codeId_UNIQUE` (`codeId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`compileInfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`compileInfo` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`compileInfo` (
  `compileInfoId` INT NOT NULL AUTO_INCREMENT ,
  `content` TEXT NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`compileInfoId`) ,
  UNIQUE INDEX `compileInfoId_UNIQUE` (`compileInfoId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`status` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`status` (
  `statusId` INT NOT NULL AUTO_INCREMENT ,
  `userId` INT NOT NULL ,
  `problemId` INT NOT NULL ,
  `result` INT NOT NULL ,
  `memoryCost` INT NOT NULL ,
  `timeCost` INT NOT NULL ,
  `languageId` INT NOT NULL ,
  `length` INT NOT NULL ,
  `time` DATETIME NOT NULL ,
  `contestId` INT NULL DEFAULT NULL ,
  `caseNumber` INT NOT NULL DEFAULT 0 ,
  `codeId` INT NOT NULL ,
  `compileInfoId` INT NULL DEFAULT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`statusId`) ,
  UNIQUE INDEX `statusId_UNIQUE` (`statusId` ASC) ,
  INDEX `FK_userID_on_user_idx` (`userId` ASC) ,
  INDEX `FK_problemId_on_problem_idx` (`problemId` ASC) ,
  INDEX `FK_languageId_on_language_idx` (`languageId` ASC) ,
  INDEX `FK_contestId_on_contst_idx` (`contestId` ASC) ,
  INDEX `FK_codeId_on_code_idx` (`codeId` ASC) ,
  INDEX `FK_compileInfoId_on_compileInfo_idx` (`compileInfoId` ASC) ,
  CONSTRAINT `FK_status_userID_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcoj`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_status_problemId_on_problem`
    FOREIGN KEY (`problemId` )
    REFERENCES `uestcoj`.`problem` (`problemId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_status_languageId_on_language`
    FOREIGN KEY (`languageId` )
    REFERENCES `uestcoj`.`language` (`languageId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_status_contestId_on_contest`
    FOREIGN KEY (`contestId` )
    REFERENCES `uestcoj`.`contest` (`contestId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_status_codeId_on_code`
    FOREIGN KEY (`codeId` )
    REFERENCES `uestcoj`.`code` (`codeId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_status_compileInfoId_on_compileInfo`
    FOREIGN KEY (`compileInfoId` )
    REFERENCES `uestcoj`.`compileInfo` (`compileInfoId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`userSerialKey`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`userSerialKey` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`userSerialKey` (
  `userSerialKeyId` INT NOT NULL AUTO_INCREMENT ,
  `userId` INT NOT NULL ,
  `serialKey` VARCHAR(128) NOT NULL ,
  `time` DATETIME NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`userSerialKeyId`) ,
  UNIQUE INDEX `userSerialKeyId_UNIQUE` (`userSerialKeyId` ASC) ,
  INDEX `FK_userId_on_user_idx` (`userId` ASC) ,
  CONSTRAINT `FK_userSerialKey_userId_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcoj`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`trainingContest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`trainingContest` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`trainingContest` (
  `trainingContestId` INT NOT NULL AUTO_INCREMENT ,
  `isPersonal` TINYINT(1) NOT NULL ,
  `title` VARCHAR(150) NOT NULL DEFAULT '' ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  `type` VARCHAR(45) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`trainingContestId`) ,
  UNIQUE INDEX `traningContestId_UNIQUE` (`trainingContestId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`trainingUser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`trainingUser` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`trainingUser` (
  `trainingUserId` INT NOT NULL AUTO_INCREMENT ,
  `rating` DOUBLE NOT NULL ,
  `volatility` DOUBLE NOT NULL ,
  `type` INT NOT NULL ,
  `userId` INT NOT NULL ,
  `OPTLOCK` INT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `allow` TINYINT(1) NOT NULL ,
  `ratingVary` DOUBLE NOT NULL DEFAULT 0 ,
  `volatilityVary` DOUBLE NOT NULL DEFAULT 0 ,
  `competitions` INT NOT NULL ,
  `member` VARCHAR(128) NOT NULL ,
  PRIMARY KEY (`trainingUserId`) ,
  UNIQUE INDEX `trainingUserId_UNIQUE` (`trainingUserId` ASC) ,
  INDEX `FK_trainingUser_userId_on_user_idx` (`userId` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) ,
  CONSTRAINT `FK_trainingUser_userId_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcoj`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`trainingStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`trainingStatus` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`trainingStatus` (
  `trainingStatusId` INT NOT NULL AUTO_INCREMENT ,
  `trainingContestId` INT NOT NULL ,
  `trainingUserId` INT NOT NULL ,
  `rating` DOUBLE NOT NULL ,
  `volatility` DOUBLE NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  `rank` INT NOT NULL ,
  `solve` INT NOT NULL ,
  `penalty` INT NOT NULL ,
  `ratingVary` DOUBLE NOT NULL ,
  `volatilityVary` DOUBLE NOT NULL ,
  `summary` TEXT NOT NULL ,
  PRIMARY KEY (`trainingStatusId`) ,
  UNIQUE INDEX `trainingStatusId_UNIQUE` (`trainingStatusId` ASC) ,
  INDEX `FK_trainingStatus_trainingContestId_on_trainingContest_idx` (`trainingContestId` ASC) ,
  INDEX `FK_trainingStatus_trainingUserId_on_user_idx` (`trainingUserId` ASC) ,
  CONSTRAINT `FK_trainingStatus_trainingContestId_on_trainingContest`
    FOREIGN KEY (`trainingContestId` )
    REFERENCES `uestcoj`.`trainingContest` (`trainingContestId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_trainingStatus_trainingUserId_on_trainingUser`
    FOREIGN KEY (`trainingUserId` )
    REFERENCES `uestcoj`.`trainingUser` (`trainingUserId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`team`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`team` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`team` (
  `teamId` INT NOT NULL AUTO_INCREMENT ,
  `teamName` VARCHAR(45) NOT NULL ,
  `leaderId` INT NOT NULL ,
  PRIMARY KEY (`teamId`) ,
  INDEX `leaderId_idx` (`leaderId` ASC) ,
  UNIQUE INDEX `teamName_UNIQUE` (`teamName` ASC) ,
  UNIQUE INDEX `teamId_UNIQUE` (`teamId` ASC) ,
  CONSTRAINT `FK_team_leaderId_on_user`
    FOREIGN KEY (`leaderId` )
    REFERENCES `uestcoj`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`teamUser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`teamUser` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`teamUser` (
  `teamUserId` INT NOT NULL AUTO_INCREMENT ,
  `userId` INT NOT NULL ,
  `teamId` INT NOT NULL ,
  `allow` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`teamUserId`) ,
  INDEX `teamId_idx` (`teamId` ASC) ,
  INDEX `userId_idx` (`userId` ASC) ,
  UNIQUE INDEX `teamUserId_UNIQUE` (`teamUserId` ASC) ,
  CONSTRAINT `FK_team_teamId_on_team`
    FOREIGN KEY (`teamId` )
    REFERENCES `uestcoj`.`team` (`teamId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_team_userId_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcoj`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`contestTeam`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`contestTeam` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`contestTeam` (
  `contestTeamId` INT NOT NULL AUTO_INCREMENT ,
  `contestId` INT NOT NULL ,
  `teamId` INT NOT NULL ,
  `status` TINYINT(4) NOT NULL ,
  `comment` VARCHAR(255) NOT NULL ,
  PRIMARY KEY (`contestTeamId`) ,
  INDEX `FK_contestTeam_contestId_on_contest_idx` (`contestId` ASC) ,
  INDEX `FK_contestTeam_teamId_on_team_idx` (`teamId` ASC) ,
  UNIQUE INDEX `contestTeamId_UNIQUE` (`contestTeamId` ASC) ,
  CONSTRAINT `FK_contestTeam_contestId_on_contest`
    FOREIGN KEY (`contestId` )
    REFERENCES `uestcoj`.`contest` (`contestId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_contestTeam_teamId_on_team`
    FOREIGN KEY (`teamId` )
    REFERENCES `uestcoj`.`team` (`teamId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`settings`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`settings` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`settings` (
  `settingId` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(255) NOT NULL DEFAULT '',
  `description` VARCHAR(255) NOT NULL DEFAULT '',
  `value` LONGTEXT NOT NULL,
  PRIMARY KEY (`settingId`) ,
  UNIQUE INDEX `settingId_UNIQUE` (`settingId` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `uestcoj`.`settings`
-- -----------------------------------------------------
START TRANSACTION;
USE `uestcoj`;
INSERT INTO `settings` (`settingId`, `name`, `description`, `value`)
VALUES
	(1, X'686F7374', X'4F4A20686F7374', X'687474703A2F2F61636D2E75657374632E6564752E636E'),
	(2, X'656E636F64696E67', X'44656661756C7420656E636F64696E67', X'5554462D38'),
	(3, X'75706C6F6164466F6C646572', X'55706C6F61642066696C65732073746F726520666F6C646572', X'7E2F75706C6F6164732F'),
	(4, X'70696374757265466F6C646572', X'506963747572657320666F6C646572', X'7E2F696D616765732F'),
	(5, X'6A75646765436F7265', X'4A7564676520636F7265206E616D65', X'70796C6F6E636F7265'),
	(6, X'6461746150617468', X'446174612066696C652070617468', X'7E2F646174612F'),
	(7, X'776F726B50617468', X'4A7564676520776F726B2070617468', X'7E2F776F726B2F'),
	(8, X'6A7564676573', X'4A756467652074687265616473', X'5B7B226E616D65223A2266697368227D2C7B226E616D65223A226D7A727931393932227D2C7B226E616D65223A22676F6E6762616F61227D2C7B226E616D65223A226B656E6E657468736E6F77227D5D'),
	(9, X'656D61696C', X'456D61696C207365727665722073657474696E6773', X'7B2261646472657373223A2263646F6A5F74657374403136332E636F6D222C22757365726E616D65223A2263646F6A5F74657374403136332E636F6D222C2270617373776F7264223A22313335363738393432353730222C22736D7470536572766572223A22736D74702E3136332E636F6D227D');

COMMIT;

-- -----------------------------------------------------
-- Data for table `uestcoj`.`department`
-- -----------------------------------------------------
START TRANSACTION;
USE `uestcoj`;
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (1, 'Others', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (2, 'School of Information and Software Engineering', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (3, 'School of Mathematical Sciences', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (4, 'School of Management and Economics', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (5, 'School of Automation Engineering', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (6, 'School of Mechatronics Engineering', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (7, 'School of Optoelectronic Information', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (8, 'School of Computer Science & Engineering', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (9, 'School of Life Science and Technology', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (10, 'School of Foreign Languages', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (11, 'School of Energy Science and Engineering', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (12, 'School of Marxism Education', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (13, 'School of Political Science and Public Administrat', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (14, 'School of Microelectronics and Solid-State Electro', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (15, 'School of Electronic Engineering', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (16, 'School of Physical Electronics', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (17, 'School of Communication & Information Engineering', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (18, 'Yingcai Experimental School', 0);

COMMIT;

-- -----------------------------------------------------
-- Data for table `uestcoj`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `uestcoj`;
INSERT INTO `uestcoj`.`user` (`userId`, `userName`, `studentId`, `departmentId`, `password`, `school`, `nickName`, `email`, `solved`, `tried`, `type`, `lastLogin`, `OPTLOCK`, `motto`, `name`, `sex`, `grade`, `phone`, `size`) VALUES (1, 'administrator', '2010013100008', 1, '3669a3b6618e9b27d641666d764432e025fc5be7', 'UESTC', 'administrator', 'acm@uestc.edu.cn', 0, 0, 1, '2013-01-30 13:17:26', 0, 'Hello world!', 'Admin', 0, 0, '123456', 0);

COMMIT;

-- -----------------------------------------------------
-- Data for table `uestcoj`.`article`
-- -----------------------------------------------------
START TRANSACTION;
USE `uestcoj`;
INSERT INTO `uestcoj`.`article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (1, 'Frequently Asked Questions', '***\n###CDOJ支持的语言 \nCDOJ支持的语言有C、C++和Java。\n\n它们所对应的编辑器参照[Arch Linux软件源](https://www.archlinux.org/packages/)中的**最新**版本。\n\n***\n###在CDOJ上允许使用的输入输出方式 \nCDOJ只支持标准输入标准输出，也就是我们使用的键盘输入输出。注意CDOJ不支持文件操作的函数，如果在您的代码中使用 了文件操作，您将得到`Restricted Function`的结果。 \n\n例如要通过[A+B Problem](/problem/show/1)这道题目，我们可以使用以下几种形式的代码：\n\n####For GNU C\n```\n#include <stdio.h>\n\nint main() \n{\n    int a, b;\n    scanf(\"%d %d\",&a, &b);\n    printf(\"%d\", a+b);\n    return 0;\n}\n```\n\n####For GNU C++\n```\n#include <iostream>\nusing namespace std;  \n\nint main()\n{ \n    int a,b;  \n    cin >> a >> b; \n    cout << a+b << endl; \n    return 0;  \n}\n```\n\n####For Java\n```\nimport java.io.*; \nimport java.util.*;  \n\npublic class Main { \n\n    public static void main(String[] args) throws Exception {  \n        Scanner cin=new Scanner(System.in); \n        int a=cin.nextInt(), b=cin.nextInt();  \n        System.out.println (a + b); \n    }  \n}\n```\n\n***\n###关于C和C++的一些说明 \n1. `main`函数须返回`int`类型，不能使用`void main`，在`main`函数的最后需要加上`return 0`。 \n2. 不要使用系统函数，比如system函数和所有的API函数，如果您在提交的题目中包含了这些函数，您将得到`Restricted Function`的结果。 \n3. 如果用到64位整型，请使用**`long long`**定义，格式化输出符号用**`%lld`**和**`%llu`**来输出`long long`和`unsigned long long`类型变量。 \n4. G++/GCC的编译参数： \n  * `g++ -static -w -O2 -DONLINE_JUDGE -o a.out main.cc`\n  * `gcc -static -w -O2 -DONLINE_JUDGE -o a.out main.c` \n\n***\n###关于Java的一些说明 \n1. 您提交的Java程序必须是单个文件的，也就是说所有的类须写在同一个文件里，要保证您写的文件中，主类名称为`Main`, 在主类 中有且仅有一个静态的`main`函数。 \n2. 您须保证您写的主类是公有类。。\n3. 考虑到Java解释运行的特点，Java的时限和其他语言分开，单独说明。 \n4. Java的编译参数：\n  * `javac Main.java -d temp/` \n\n***\n###CDOJ常见的评测结果 \n1. `Wating`：系统正在对之前的用户代码进行评测。 \n2. `Compiling`：系统正在编译您的代码。 \n3. `Judging`：系统正在运行并评测您的程序 \n4. `Compile Error`：您的代码没有编译成功，请查看自己的代码是否符合标准，或者检查自己是否选择了正确的语言。 \n5. `Wrong Answer`：您的程序返回了错误的结果。 \n6. `Runtime Error`：您的程序在运行的时候发生了错误导致程序中途停止了运行，常见的问题有数组越界或者递归调用过深等。 \n7. `Time Limit Exceeded`：您的程序的运行时间超过的题目限制的时限，遇到此类问题的原因有可能是您的算法时间复杂度太高 或者程序中出现了死循环。 \n8. `Output Limit Exceeded`：您的程序输出了过多的信息，遇到此类问题的原因有可能是出现死循环或者调试时的调试代码没有注释。 \n9. `Memory Limit Exceeded`：您的程序申请的内存空间超出了题目的限制范围，请您优化空间复杂度。 \n10. `Restricted Function`：您的程序使用了不允许使用的函数，比如system函数和API函数，注意数组越界也有可能返回`Restricted Function`。 \n11. `Presentation Error`：您的程序运行出了正确的结果，但是没有正确地按照输出格式输出，可能多或少输出了空格和回车，请 您再次查看自己的输出和题目的输出规范是否符合。 \n12. `Accepted`：您的程序得到了正确的结果。 \n\n***\n###关于C++中cin/cout和printf/scanf的取舍问题 \n在C++中，`cin`和`cout`输入输出流确实为我们提供了非常大的便利，但是`cin`和`cout`有各自的缓冲区，而这个也导致了`cin`和`cout`相比`scanf`、`printf`来说，速度慢了非常多的现象。而且大部分情况下，题目中并不会有太多的提示，所以在此建议大家习惯使用`scanf`和`printf`进行输入输出，C++也支持这两个函数，只要包含`cstdio`或者`stdio.h`头文件即可。', '2013-07-11 22:28:01', 0, 0, 0, 1, NULL, NULL, NULL, 1, 16);
INSERT INTO `uestcoj`.`article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (2, 'Markdown syntax cheatsheet', '我们使用[marked](https://github.com/chjj/marked)作为新OJ所有内容文本的渲染引擎。\n\n关于标准markdown语法规则可以参照[http://markdown.tw](http://markdown.tw)，下面给出一些常用的基本规则。\n\n***\n##HEADERS\n```\n# 一级标题\n## 二级标题\n### 三级标题\n#### 四级标题\n##### 五级标题\n```\n# 一级标题\n## 二级标题\n### 三级标题\n#### 四级标题\n##### 五级标题\n\n***\n##BLOCKQUOTES\n```\n> 这是引用\n\n> 这也是引用\n  啊\n\n> 还可以嵌套引用\n>>  喵\n```\n\n> 这是引用\n\n> 还可以嵌套引用\n>>  喵\n\n***\n##LISTS\n```\n1. 有序列表\n2. 有序列表\n  * 无序列表\n    * 嵌套\n    * 嵌套\n  * 无序列表\n```\n1. 有序列表\n2. 有序列表\n  * 无序列表\n    * 嵌套\n    * 嵌套\n  * 无序列表\n\n***\n##CODE BLOCKS\n    在一行内插入`代码`是很容易的\n\n        插入一段代码也很方便（四个空格或一个tab）\n    \n    ```\n    还有一种方法（适合用来框代码）\n    ````\n\n在一行内插入`代码`是很容易的\n\n    插入一段代码也很方便（四个空格或一个tab）\n\n```\n还有一种方法（适合用来框代码）\n```\n\n***\n##HORIZONTAL RULES\n```\n***\n```\n\n***\n\n***\n##LINKS\n```\n[超链接](http://acm.uestc.edu.cn:8080/)\n```\n[超链接](http://acm.uestc.edu.cn:8080/)\n\n***\n##EMPHASIS\n```\n*斜体*\n\n**粗体**\n\n***粗斜体***\n\n~~删除线~~\n```\n*斜体*\n\n**粗体**\n\n***粗斜体***\n\n~~删除线~~\n\n***\n##IMAGES\n```\n![Title](/images/logo/banner.png)\n```\n![Title](/images/logo/banner.png)\n\n***\n##EpicEditor\n新OJ使用的编辑器是[EpicEditor](http://oscargodson.github.com/EpicEditor/)，它也用marked作为渲染引擎。\n\n除此之外，自己尝试在[EpicEditor]()中整合了一个图片上传功能\n\n![Title](/images/article/2/201307022026484821.png)\n\n支持多张上传\n\n![Title](/images/article/2/201307022027379352.png)\n\n选择需要插入的图片。\n\n![Title](/images/article/2/201307022027550133.png)\n\n就能得到对应代码，复制到需要插入的位置即可。（暂时没找到自动插入的方法）', '2013-07-02 21:27:07', 0, 0, 0, 1, NULL, NULL, NULL, 1, 12);
INSERT INTO `uestcoj`.`article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (3, 'Training system', '***\n###集训系统简介\n集训系统是电子科技大学ACM-ICPC集训队用来统计队员寒暑假训练成绩以及度量训练成绩使用的系统，它采用了TopCoder的[Rating System](http://apps.topcoder.com/wiki/display/tc/Algorithm+Competition+Rating+System)来度量成绩。\n\n***\n###队员注册\n队员分为两种，一种是固定组队的老队员，和个人赛为主的新队员。\n\n老队员账号注册时`name`为自己常用的队名，需要和后面比赛时用的账号的`nick name`相同，便于成绩导入。`member`为队员名称\n\n新队员注册时`name`和`member`统一使用自己的真实姓名，日后比赛时账号的`nick name`也需要为自己的真实姓名。\n\n比较特殊的是新队员组队赛，此时建议注册一个新账号，`nick name`为三个队员的姓名，用英文逗号`,`隔开，方便成绩统计。\n\n***\n###比赛类型\n现在暂时有`normal`、`adjust`、`cf`、`tc`、`team`五种类型。\n\n1. `normal` 一般类型比赛，现在支持导入Virutal Judge、HDU和PC^2的ranklist。\n2. `adjust` 此类为后期调整分数用（例如扣分）。\n3. `cf`和`tc` 考虑到这两类比赛无法直接导出ranklist，采用手工统计的方法，只保留得分信息。\n  * 对于`CF`和`TC`的`Div1`用户，这里采取得分三倍处理（包括挂负）。\n  * 未参赛用户做零分处理。\n4. `team` 这个对应暑假集训的随机组队赛，队员享有同样的排名，但是在计算Rating时权值会降低一些（weight值减半）。\n\n***\n###相关链接\n[Rating System](http://apps.topcoder.com/wiki/display/tc/Algorithm+Competition+Rating+System)实现细节在[这里](https://gitcafe.com/UESTC_ACM/cdoj/blob/master/trunk/src/main/java/cn/edu/uestc/acmicpc/util/RatingUtil.java)。', '2013-07-22 20:48:56', 0, 0, 0, 1, NULL, NULL, NULL, 1, 4);
INSERT INTO `uestcoj`.`article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (4, 'About', '***\n[Project home](https://gitcafe.com/UESTC_ACM/cdoj)\n\n***\n###Features\n* Use markdown to edit problem and article\n* Manager problem data in convenient way\n* Full support of formula(use MathJax)\n\n***\n###TODO\n* Discuss\n* Contest clarification\n* Blog and BBS\n* Links manage\n* Problem tags manage\n* Related documents\n* Attachments manage\n* Message and bookmarks\n* Optimize database and use cache to speed up\n* Backup\n* Upload contest in a zip package\n* VJ(maybe..)\n\n***\n###Copyright\ncdoj, UESTC ACMICPC Online Judge\n\nCopyright (c) 2013 fish [lyhypacm@gmail.com](mailto:lyhypacm@gmail.com),\n mzry1992 [muziriyun@gmail.com](mailto:muziriyun@gmail.com)\n\nThis program is free software; you can redistribute it and/or\nmodify it under the terms of the GNU General Public License\nas published by the Free Software Foundation; either version 2\nof the License, or (at your option) any later version.\n\nThis program is distributed in the hope that it will be useful,\nbut WITHOUT ANY WARRANTY; without even the implied warranty of\nMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the\nGNU General Public License for more details.\n\nYou should have received a copy of the GNU General Public License\nalong with this program; if not, write to the Free Software\nFoundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.', '2013-07-17 23:59:14', 0, 0, 0, 1, NULL, NULL, NULL, 1, 6);
INSERT INTO `uestcoj`.`article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (5, '', '', '2013-08-01 10:57:37', 0, 0, 0, 0, NULL, NULL, NULL, NULL, 0);

COMMIT;

-- -----------------------------------------------------
-- Data for table `uestcoj`.`language`
-- -----------------------------------------------------
START TRANSACTION;
USE `uestcoj`;
INSERT INTO `uestcoj`.`language` (`languageId`, `name`, `extension`, `param`, `OPTLOCK`) VALUES (1, 'C', '.c', '', 0);
INSERT INTO `uestcoj`.`language` (`languageId`, `name`, `extension`, `param`, `OPTLOCK`) VALUES (2, 'C++', '.cc', '', 0);
INSERT INTO `uestcoj`.`language` (`languageId`, `name`, `extension`, `param`, `OPTLOCK`) VALUES (3, 'Java', '.java', '', 0);

COMMIT;

