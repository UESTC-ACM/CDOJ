-- Update training system
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP TABLE IF EXISTS `uestcoj`.`trainingStatus`;
DROP TABLE IF EXISTS `uestcoj`.`trainingContest`;
DROP TABLE IF EXISTS `uestcoj`.`trainingUser`;


-- -----------------------------------------------------
-- Table `uestcoj`.`training`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`training` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`training` (
  `trainingId` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(255) NOT NULL ,
  `description` TEXT NOT NULL ,
  PRIMARY KEY (`trainingId`) ,
  UNIQUE INDEX `trainingId_UNIQUE` (`trainingId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`trainingUser`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`trainingUser` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`trainingUser` (
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
  `ratingHistory` TEXT NOT NULL ,
  PRIMARY KEY (`trainingUserId`) ,
  UNIQUE INDEX `trainingUserId_UNIQUE` (`trainingUserId` ASC) ,
  INDEX `FK_training_user_trainingId_on_training_idx` (`trainingId` ASC) ,
  INDEX `FK_training_user_userId_on_user_idx` (`userId` ASC) ,
  CONSTRAINT `FK_training_user_trainingId_on_training`
    FOREIGN KEY (`trainingId` )
    REFERENCES `uestcoj`.`training` (`trainingId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_training_user_userId_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcoj`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`trainingPlatformInfo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`trainingPlatformInfo` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`trainingPlatformInfo` (
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
    REFERENCES `uestcoj`.`trainingUser` (`trainingUserId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `uestcoj`.`trainingContest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `uestcoj`.`trainingContest` ;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`trainingContest` (
  `trainingContestId` INT NOT NULL AUTO_INCREMENT ,
  `trainingId` INT NOT NULL ,
  `title` VARCHAR(255) NOT NULL ,
  `link` VARCHAR(255) NULL ,
  `rankList` TEXT NOT NULL ,
  `type` INT NOT NULL ,
  `platformType` INT NOT NULL ,
  PRIMARY KEY (`trainingContestId`) ,
  UNIQUE INDEX `trainingContestId_UNIQUE` (`trainingContestId` ASC) ,
  INDEX `FK_training_contest_trainingId_on_training_idx` (`trainingId` ASC) ,
  CONSTRAINT `FK_training_contest_trainingId_on_training`
    FOREIGN KEY (`trainingId` )
    REFERENCES `uestcoj`.`training` (`trainingId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

