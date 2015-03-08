SET NAMES UTF8;

USE `uestcojtest` ;

START TRANSACTION;
USE `uestcojtest`;
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

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (1, 'Others', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (2, 'School of Information and Software Engineering', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (3, 'School of Mathematical Sciences', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (4, 'School of Management and Economics', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (5, 'School of Automation Engineering', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (6, 'School of Mechatronics Engineering', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (7, 'School of Optoelectronic Information', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (8, 'School of Computer Science & Engineering', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (9, 'School of Life Science and Technology', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (10, 'School of Foreign Languages', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (11, 'School of Energy Science and Engineering', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (12, 'School of Marxism Education', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (13, 'School of Political Science and Public Administrat', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (14, 'School of Microelectronics and Solid-State Electro', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (15, 'School of Electronic Engineering', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (16, 'School of Physical Electronics', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (17, 'School of Communication & Information Engineering', 0);
INSERT INTO `department` (`departmentId`, `name`, `OPTLOCK`) VALUES (18, 'Yingcai Experimental School', 0);

COMMIT;

-- START TRANSACTION;
-- USE `uestcojtest`;
-- INSERT INTO `user` (`userId`, `userName`, `studentId`, `departmentId`, `password`, `school`, `nickName`, `email`, `solved`, `tried`, `type`, `lastLogin`, `OPTLOCK`, `motto`, `name`, `sex`, `grade`, `phone`, `size`)
-- VALUES (1, 'administrator', '2010013100008', 1, '3669a3b6618e9b27d641666d764432e025fc5be7', 'UESTC', 'administrator', 'acm@uestc.edu.cn', 0, 0, 1, '2013-01-30 13:17:26', 0, 'fuck', 'aa', 0, 0, '123', 0);
-- COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `language` (`languageId`, `name`, `extension`, `param`, `OPTLOCK`) VALUES (1, 'C', '.c', '', 0);
INSERT INTO `language` (`languageId`, `name`, `extension`, `param`, `OPTLOCK`) VALUES (2, 'C++', '.cc', '', 0);
INSERT INTO `language` (`languageId`, `name`, `extension`, `param`, `OPTLOCK`) VALUES (3, 'Java', '.java', '', 0);

COMMIT;

