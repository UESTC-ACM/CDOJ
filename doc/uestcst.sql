CREATE SCHEMA IF NOT EXISTS `uestcst`; 

CREATE  TABLE IF NOT EXISTS `uestcst`.`user` (
  `userId` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL DEFAULT '' ,
  `studentId` VARCHAR(50) NOT NULL ,
  `password` VARCHAR(40) NOT NULL ,
  `topcoderId` VARCHAR(50) NOT NULL DEFAULT '' ,
  `codeforcesId` VARCHAR(50) NOT NULL DEFAULT '' ,
  `email` VARCHAR(100) NOT NULL DEFAULT '' ,
  `department` VARCHAR(50) NOT NULL DEFAULT '' ,
  `phone` VARCHAR(20) NOT NULL DEFAULT '' ,
  PRIMARY KEY (`userId`) ,
  UNIQUE INDEX `userId_UNIQUE` (`userId` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `uestcst`.`contest` (
  `contestId` INT NOT NULL AUTO_INCREMENT ,
  `isPersonal` TINYINT(1) NOT NULL ,
  `name` VARCHAR(150) NOT NULL DEFAULT '' ,
  PRIMARY KEY (`contestId`) ,
  UNIQUE INDEX `contestId_UNIQUE` (`contestId` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `uestcst`.`status` (
  `statusId` INT NOT NULL AUTO_INCREMENT ,
  `userId` INT NOT NULL ,
  `contestId` INT NOT NULL ,
  `rating` DOUBLE NOT NULL ,
  `volatility` DOUBLE NOT NULL ,
  PRIMARY KEY (`statusId`) ,
  UNIQUE INDEX `statusId_UNIQUE` (`statusId` ASC) ,
  INDEX `FK_userId_on_user_idx` (`userId` ASC) ,
  INDEX `FK_contestId_on_contest_idx` (`contestId` ASC) ,
  CONSTRAINT `FK_status_userId_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcst`.`user` (`userId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_status_contestId_on_contest`
    FOREIGN KEY (`contestId` )
    REFERENCES `uestcst`.`contest` (`contestId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
