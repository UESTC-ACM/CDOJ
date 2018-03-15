SET NAMES UTF8;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP DATABASE IF EXISTS `uestcojtest`;

CREATE SCHEMA IF NOT EXISTS `uestcojtest` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `uestcojtest` ;

-- -----------------------------------------------------
-- Table `uestcojtest`.`department`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`department` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`department` (
  `departmentId` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL COMMENT 'department\'s name' ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`departmentId`) ,
  UNIQUE INDEX `departmentId_UNIQUE` (`departmentId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`user` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`user` (
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
    REFERENCES `uestcojtest`.`department` (`departmentId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`problem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`problem` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`problem` (
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
  `memoryLimit` INT NOT NULL DEFAULT 64 ,
  `solved` INT NOT NULL DEFAULT 0 ,
  `tried` INT NOT NULL DEFAULT 0 ,
  `isSPJ` TINYINT(1) NOT NULL DEFAULT 0 ,
  `isVisible` TINYINT(1) NOT NULL DEFAULT 0 ,
  `outputLimit` INT NOT NULL DEFAULT 64 ,
  `dataCount` INT NOT NULL DEFAULT 0 ,
  `difficulty` INT NOT NULL DEFAULT 1 ,
  `type` INT NOT NULL DEFAULT 0 ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`problemId`) ,
  UNIQUE INDEX `problemId_UNIQUE` (`problemId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`contest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`contest` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`contest` (
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
  `frozenTime` INT(11) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`contestId`) ,
  UNIQUE INDEX `contestId_UNIQUE` (`contestId` ASC) ,
  INDEX `FK_parentId_on_contest_idx_idx` (`parentId` ASC) ,
  CONSTRAINT `FK_parentId_on_contest_idx`
    FOREIGN KEY (`parentId` )
    REFERENCES `uestcojtest`.`contest` (`contestId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`article`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`article` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`article` (
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
    REFERENCES `uestcojtest`.`article` (`articleId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_article_problemId_on_problem`
    FOREIGN KEY (`problemId` )
    REFERENCES `uestcojtest`.`problem` (`problemId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_article_contestId_on_contest`
    FOREIGN KEY (`contestId` )
    REFERENCES `uestcojtest`.`contest` (`contestId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_article_userId_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcojtest`.`user` (`userId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`message` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`message` (
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
    REFERENCES `uestcojtest`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_message_receiverId_on_user`
    FOREIGN KEY (`receiverId` )
    REFERENCES `uestcojtest`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`contestUser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`contestUser` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`contestUser` (
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
    REFERENCES `uestcojtest`.`contest` (`contestId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_contestUser_userId_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcojtest`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`contestProblem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`contestProblem` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`contestProblem` (
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
    REFERENCES `uestcojtest`.`contest` (`contestId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_contestProblem_problemId_on_problem`
    FOREIGN KEY (`problemId` )
    REFERENCES `uestcojtest`.`problem` (`problemId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`tag` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`tag` (
  `tagId` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`tagId`) ,
  UNIQUE INDEX `tagId_UNIQUE` (`tagId` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`problemTag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`problemTag` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`problemTag` (
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
    REFERENCES `uestcojtest`.`problem` (`problemId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_problemTag_tagId_on_tag`
    FOREIGN KEY (`tagId` )
    REFERENCES `uestcojtest`.`tag` (`tagId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`language`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`language` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`language` (
  `languageId` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL ,
  `extension` VARCHAR(10) NOT NULL ,
  `param` TEXT NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`languageId`) ,
  UNIQUE INDEX `languageId_UNIQUE` (`languageId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`code`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`code` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`code` (
  `codeId` INT NOT NULL AUTO_INCREMENT ,
  `content` TEXT NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  `share` TINYINT(1) NOT NULL DEFAULT false ,
  PRIMARY KEY (`codeId`) ,
  UNIQUE INDEX `codeId_UNIQUE` (`codeId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`compileInfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`compileInfo` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`compileInfo` (
  `compileInfoId` INT NOT NULL AUTO_INCREMENT ,
  `content` TEXT NOT NULL ,
  `OPTLOCK` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`compileInfoId`) ,
  UNIQUE INDEX `compileInfoId_UNIQUE` (`compileInfoId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`status` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`status` (
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
    REFERENCES `uestcojtest`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_status_problemId_on_problem`
    FOREIGN KEY (`problemId` )
    REFERENCES `uestcojtest`.`problem` (`problemId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_status_languageId_on_language`
    FOREIGN KEY (`languageId` )
    REFERENCES `uestcojtest`.`language` (`languageId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_status_contestId_on_contest`
    FOREIGN KEY (`contestId` )
    REFERENCES `uestcojtest`.`contest` (`contestId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_status_codeId_on_code`
    FOREIGN KEY (`codeId` )
    REFERENCES `uestcojtest`.`code` (`codeId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_status_compileInfoId_on_compileInfo`
    FOREIGN KEY (`compileInfoId` )
    REFERENCES `uestcojtest`.`compileInfo` (`compileInfoId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`userSerialKey`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`userSerialKey` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`userSerialKey` (
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
    REFERENCES `uestcojtest`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`team`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`team` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`team` (
  `teamId` INT NOT NULL AUTO_INCREMENT ,
  `teamName` VARCHAR(45) NOT NULL ,
  `leaderId` INT NOT NULL ,
  PRIMARY KEY (`teamId`) ,
  INDEX `leaderId_idx` (`leaderId` ASC) ,
  UNIQUE INDEX `teamName_UNIQUE` (`teamName` ASC) ,
  UNIQUE INDEX `teamId_UNIQUE` (`teamId` ASC) ,
  CONSTRAINT `FK_team_leaderId_on_user`
    FOREIGN KEY (`leaderId` )
    REFERENCES `uestcojtest`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`teamUser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`teamUser` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`teamUser` (
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
    REFERENCES `uestcojtest`.`team` (`teamId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_team_userId_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcojtest`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`contestTeam`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`contestTeam` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`contestTeam` (
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
    REFERENCES `uestcojtest`.`contest` (`contestId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_contestTeam_teamId_on_team`
    FOREIGN KEY (`teamId` )
    REFERENCES `uestcojtest`.`team` (`teamId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`setting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`setting` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`setting` (
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
-- Table `uestcojtest`.`training`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`training` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`training` (
  `trainingId` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(255) NOT NULL ,
  `description` TEXT NOT NULL ,
  PRIMARY KEY (`trainingId`) ,
  UNIQUE INDEX `trainingId_UNIQUE` (`trainingId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`trainingUser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`trainingUser` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`trainingUser` (
  `trainingUserId` INT NOT NULL AUTO_INCREMENT ,
  `trainingId` INT NOT NULL ,
  `userId` INT NOT NULL ,
  `trainingUserName` VARCHAR(45) NOT NULL ,
  `type` INT NOT NULL ,
  `currentRating` DOUBLE NOT NULL ,
  `currentVolatility` DOUBLE NOT NULL ,
  `competitions` INT NOT NULL ,
  `rank` INT NOT NULL ,
  `maximumRating` DOUBLE NOT NULL ,
  `minimumRating` DOUBLE NOT NULL ,
  `mostRecentEventId` INT NULL ,
  `mostRecentEventName` VARCHAR(255) NULL ,
  `ratingHistory` MEDIUMTEXT NOT NULL ,
  PRIMARY KEY (`trainingUserId`) ,
  UNIQUE INDEX `trainingUserId_UNIQUE` (`trainingUserId` ASC) ,
  INDEX `FK_training_user_trainingId_on_training_idx` (`trainingId` ASC) ,
  INDEX `FK_training_user_userId_on_user_idx` (`userId` ASC) ,
  CONSTRAINT `FK_training_user_trainingId_on_training`
    FOREIGN KEY (`trainingId` )
    REFERENCES `uestcojtest`.`training` (`trainingId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_training_user_userId_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcojtest`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`trainingPlatformInfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`trainingPlatformInfo` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`trainingPlatformInfo` (
  `trainingPlatformInfoId` INT NOT NULL AUTO_INCREMENT ,
  `trainingUserId` INT NOT NULL ,
  `userName` VARCHAR(255) NULL ,
  `userId` VARCHAR(255) NULL ,
  `type` INT NOT NULL ,
  PRIMARY KEY (`trainingPlatformInfoId`) ,
  UNIQUE INDEX `trainingPlatformInfoId_UNIQUE` (`trainingPlatformInfoId` ASC) ,
  INDEX `FK_training_platform_info_trainingUserId_on_trainingUser_idx` (`trainingUserId` ASC) ,
  CONSTRAINT `FK_training_platform_info_trainingUserId_on_trainingUser`
    FOREIGN KEY (`trainingUserId` )
    REFERENCES `uestcojtest`.`trainingUser` (`trainingUserId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcojtest`.`trainingContest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcojtest`.`trainingContest` ;

CREATE  TABLE IF NOT EXISTS `uestcojtest`.`trainingContest` (
  `trainingContestId` INT NOT NULL AUTO_INCREMENT ,
  `trainingId` INT NOT NULL ,
  `title` VARCHAR(255) NOT NULL ,
  `link` VARCHAR(255) NULL ,
  `rankList` MEDIUMTEXT NOT NULL ,
  `type` INT NOT NULL ,
  `platformType` INT NOT NULL ,
  PRIMARY KEY (`trainingContestId`) ,
  UNIQUE INDEX `trainingContestId_UNIQUE` (`trainingContestId` ASC) ,
  INDEX `FK_training_contest_trainingId_on_training_idx` (`trainingId` ASC) ,
  CONSTRAINT `FK_training_contest_trainingId_on_training`
    FOREIGN KEY (`trainingId` )
    REFERENCES `uestcojtest`.`training` (`trainingId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
