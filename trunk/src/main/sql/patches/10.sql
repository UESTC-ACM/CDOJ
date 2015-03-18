SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

START TRANSACTION;
USE `uestcoj`;
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (19, 'UoG-UESTC Joint School', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (20, 'School of Resources and Environment', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (21, 'School of Medicine', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (22, 'Institute of Fundamental and Frontier Sciences', 0);
INSERT INTO `uestcoj`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (23, 'School of Energy Science and Engineering', 0);

COMMIT;