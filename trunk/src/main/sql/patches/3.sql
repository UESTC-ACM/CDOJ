-- Add tables used in contest registrer system
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

ALTER TABLE `uestcoj`.`user` 
  ADD COLUMN `name` VARCHAR(50) NOT NULL DEFAULT '' AFTER `motto` , 
  ADD COLUMN `sex` INT(11) NOT NULL DEFAULT 0  AFTER `name` , 
  ADD COLUMN `grade` INT(11) NOT NULL DEFAULT 0  AFTER `sex` , 
  ADD COLUMN `phone` VARCHAR(45) NOT NULL DEFAULT ''  AFTER `grade` , 
  ADD COLUMN `size` INT(11) NOT NULL DEFAULT 0 AFTER `phone` ;

ALTER TABLE `uestcoj`.`contestUser` 
  ADD COLUMN `comment` VARCHAR(255) NOT NULL AFTER `OPTLOCK`;

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
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`teamUser` (
  `teamUserId` INT(11) NOT NULL AUTO_INCREMENT ,
  `userId` INT(11) NOT NULL ,
  `teamId` INT(11) NOT NULL ,
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
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`contestTeam` (
  `contestTeamId` INT(11) NOT NULL AUTO_INCREMENT ,
  `contestId` INT(11) NOT NULL ,
  `teamId` INT(11) NOT NULL ,
  `status` TINYINT(4) NOT NULL,  -- 0 - wait for validating, 1 - accepted, 2 - refused 
  `comment` VARCHAR(255) NOT NULL,
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
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

DROP TABLE IF EXISTS `uestcoj`.`contestTeamInfo` ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
