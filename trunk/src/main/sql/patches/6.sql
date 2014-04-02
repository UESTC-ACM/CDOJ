-- Add settings table
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE  TABLE IF NOT EXISTS `uestcoj`.`setting` (
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
-- Data for table `uestcoj`.`setting`
-- -----------------------------------------------------
START TRANSACTION;
USE `uestcoj`;
INSERT INTO `setting` (`settingId`, `name`, `description`, `value`)
VALUES
  (1, 'host', 'OJ host', 'http://127.0.0.1:8080'),
  (2, 'encoding', 'Default encoding', 'UTF-8'),
  (3, 'uploadFolder', 'Upload files store folder', 'uploads/'),
  (4, 'pictureFolder', 'Pictures folder', 'images/'),
  (5, 'judgeCore', 'Judge core name', 'pyloncore'),
  (6, 'dataPath', 'Data file path', 'data/'),
  (7, 'workPath', 'Judge work path', 'work/'),
  (8, 'judges', 'Judge threads', '[{"name":"judge1"},{"name":"judge2"},{"name":"judge3"},{"name":"judge4"}]'),
  (9, 'email', 'Email server settings', '{"address":"example@mail.com","userName":"user name","password":"password","smtpServer":"smtp.mail.com"}'),
  (10, 'recordPerPage', 'Number of records per page', 20);
COMMIT;

