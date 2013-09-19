USE `uestcojtest` ;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (1, 'Others', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (2, 'School of Information and Software Engineering', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (3, 'School of Mathematical Sciences', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (4, 'School of Management and Economics', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (5, 'School of Automation Engineering', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (6, 'School of Mechatronics Engineering', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (7, 'School of Optoelectronic Information', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (8, 'School of Computer Science & Engineering', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (9, 'School of Life Science and Technology', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (10, 'School of Foreign Languages', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (11, 'School of Energy Science and Engineering', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (12, 'School of Marxism Education', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (13, 'School of Political Science and Public Administrat', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (14, 'School of Microelectronics and Solid-State Electro', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (15, 'School of Electronic Engineering', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (16, 'School of Physical Electronics', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (17, 'School of Communication & Information Engineering', 0);
INSERT INTO `uestcojtest`.`department` (`departmentId`, `name`, `OPTLOCK`) VALUES (18, 'Yingcai Experimental School', 0);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `uestcojtest`.`user` (`userId`, `userName`, `studentId`, `departmentId`, `password`, `school`, `nickName`, `email`, `solved`, `tried`, `type`, `lastLogin`, `OPTLOCK`) VALUES (1, 'administrator', '2010013100008', 1, '3669a3b6618e9b27d641666d764432e025fc5be7', 'UESTC', 'administrator', 'acm@uestc.edu.cn', 0, 0, 1, '2013-01-30 13:17:26', 0);
INSERT INTO `uestcojtest`.`user` (`userId`, `userName`, `studentId`, `departmentId`, `password`, `school`, `nickName`, `email`, `solved`, `tried`, `type`, `lastLogin`, `OPTLOCK`) VALUES (2, 'admin', '2010013100008', 1, '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'UESTC', 'admin', 'acm_admin@uestc.edu.cn', 0, 0, 1, '2013-01-30 13:17:26', 0);
INSERT INTO `uestcojtest`.`user` (`userId`, `userName`, `studentId`, `departmentId`, `password`, `school`, `nickName`, `email`, `solved`, `tried`, `type`, `lastLogin`, `OPTLOCK`) VALUES (3, 'user_id3', '2010013100008', 2, '3669a3b6618e9b27d641666d764432e025fc5be7', 'UESTC', 'user_id3', 'user_id3@uestc.edu.cn', 0, 0, 1, '2013-01-30 13:17:26', 0);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `uestcojtest`.`language` (`languageId`, `name`, `extension`, `param`, `OPTLOCK`) VALUES (1, 'C', '.c', '', 0);
INSERT INTO `uestcojtest`.`language` (`languageId`, `name`, `extension`, `param`, `OPTLOCK`) VALUES (2, 'C++', '.cc', '', 0);
INSERT INTO `uestcojtest`.`language` (`languageId`, `name`, `extension`, `param`, `OPTLOCK`) VALUES (3, 'Java', '.java', '', 0);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `uestcojtest`.`problem` (`problemId`, `title`, `description`, `input`, `output`, `sampleInput`, `sampleOutput`, `hint`, `source`, `timeLimit`, `memoryLimit`, `solved`, `tried`, `isSPJ`, `isVisible`, `outputLimit`, `javaTimeLimit`, `javaMemoryLimit`, `dataCount`, `difficulty`, `OPTLOCK`) VALUES (1, 'a+b problem', 'description', 'input', 'output', 'sampleinput', 'sampleoutput', 'hint', 'source', 1000, 65535, 0, 0, 0, 1, 8192, 3000, 65535, 1, 1, NULL);
INSERT INTO `uestcojtest`.`problem` (`problemId`, `title`, `description`, `input`, `output`, `sampleInput`, `sampleOutput`, `hint`, `source`, `timeLimit`, `memoryLimit`, `solved`, `tried`, `isSPJ`, `isVisible`, `outputLimit`, `javaTimeLimit`, `javaMemoryLimit`, `dataCount`, `difficulty`, `OPTLOCK`) VALUES (2, 'a+b problem', 'description', 'input', 'output', 'sampleinput', 'sampleoutput', 'hint', 'source', 1000, 65535, 0, 0, 1, 1, 8192, 3000, 65535, 1, 1, NULL);
INSERT INTO `uestcojtest`.`problem` (`problemId`, `title`, `description`, `input`, `output`, `sampleInput`, `sampleOutput`, `hint`, `source`, `timeLimit`, `memoryLimit`, `solved`, `tried`, `isSPJ`, `isVisible`, `outputLimit`, `javaTimeLimit`, `javaMemoryLimit`, `dataCount`, `difficulty`, `OPTLOCK`) VALUES (3, 'a+b problem', 'description', 'input', 'output', 'sampleinput', 'sampleoutput', 'hint', 'source', 1000, 65535, 0, 0, 0, 1, 8192, 3000, 65535, 1, 1, NULL);
INSERT INTO `uestcojtest`.`problem` (`problemId`, `title`, `description`, `input`, `output`, `sampleInput`, `sampleOutput`, `hint`, `source`, `timeLimit`, `memoryLimit`, `solved`, `tried`, `isSPJ`, `isVisible`, `outputLimit`, `javaTimeLimit`, `javaMemoryLimit`, `dataCount`, `difficulty`, `OPTLOCK`) VALUES (4, 'a+b problem', 'description', 'input', 'output', 'sampleinput', 'sampleoutput', 'hint', 'source', 1000, 65535, 0, 0, 1, 1, 8192, 3000, 65535, 1, 1, NULL);
INSERT INTO `uestcojtest`.`problem` (`problemId`, `title`, `description`, `input`, `output`, `sampleInput`, `sampleOutput`, `hint`, `source`, `timeLimit`, `memoryLimit`, `solved`, `tried`, `isSPJ`, `isVisible`, `outputLimit`, `javaTimeLimit`, `javaMemoryLimit`, `dataCount`, `difficulty`, `OPTLOCK`) VALUES (5, '', 'description', 'input', 'output', 'sampleinput', 'sampleoutput', 'hint', 'source', 1000, 65535, 0, 0, 0, 1, 8192, 3000, 65535, 1, 1, NULL);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `uestcojtest`.`code` (`codeId`, `content`, `OPTLOCK`) VALUES (1, 'code', NULL);
INSERT INTO `uestcojtest`.`code` (`codeId`, `content`, `OPTLOCK`) VALUES (2, 'code', NULL);
INSERT INTO `uestcojtest`.`code` (`codeId`, `content`, `OPTLOCK`) VALUES (3, 'code', NULL);
INSERT INTO `uestcojtest`.`code` (`codeId`, `content`, `OPTLOCK`) VALUES (4, 'code', NULL);
INSERT INTO `uestcojtest`.`code` (`codeId`, `content`, `OPTLOCK`) VALUES (5, 'code', NULL);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `uestcojtest`.`compileInfo` (`compileInfoId`, `content`, `OPTLOCK`) VALUES (1, 'compile info', NULL);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `uestcojtest`.`status` (`statusId`, `userId`, `problemId`, `result`, `memoryCost`, `timeCost`, `languageId`, `length`, `time`, `contestId`, `caseNumber`, `codeId`, `compileInfoId`, `OPTLOCK`) VALUES (1, 1, 1, 1, 1000, 15, 1, 1000, '2013-07-07 00:00:00', NULL, 1, 1, NULL, NULL);
INSERT INTO `uestcojtest`.`status` (`statusId`, `userId`, `problemId`, `result`, `memoryCost`, `timeCost`, `languageId`, `length`, `time`, `contestId`, `caseNumber`, `codeId`, `compileInfoId`, `OPTLOCK`) VALUES (2, 1, 1, 2, 1000, 15, 1, 1000, '2013-07-07 00:00:00', NULL, 1, 2, NULL, NULL);
INSERT INTO `uestcojtest`.`status` (`statusId`, `userId`, `problemId`, `result`, `memoryCost`, `timeCost`, `languageId`, `length`, `time`, `contestId`, `caseNumber`, `codeId`, `compileInfoId`, `OPTLOCK`) VALUES (3, 1, 1, 3, 1000, 15, 1, 1000, '2013-07-07 00:00:00', NULL, 1, 3, NULL, NULL);
INSERT INTO `uestcojtest`.`status` (`statusId`, `userId`, `problemId`, `result`, `memoryCost`, `timeCost`, `languageId`, `length`, `time`, `contestId`, `caseNumber`, `codeId`, `compileInfoId`, `OPTLOCK`) VALUES (4, 2, 1, 4, 1000, 15, 1, 1000, '2013-07-07 00:00:00', NULL, 1, 4, NULL, NULL);
INSERT INTO `uestcojtest`.`status` (`statusId`, `userId`, `problemId`, `result`, `memoryCost`, `timeCost`, `languageId`, `length`, `time`, `contestId`, `caseNumber`, `codeId`, `compileInfoId`, `OPTLOCK`) VALUES (5, 2, 1, 7, 1000, 15, 1, 1000, '2013-07-07 00:00:00', NULL, 1, 5, 1, NULL);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `uestcojtest`.`contest` (`contestId`, `title`, `description`, `type`, `time`, `length`, `isVisible`, `OPTLOCK`) VALUES (1, 'title', 'descrip', 1, '2013-01-01 00:00:00', 300, 1, NULL);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `uestcojtest`.`tag` (`tagId`, `name`, `OPTLOCK`) VALUES (1, 'tag1', NULL);
INSERT INTO `uestcojtest`.`tag` (`tagId`, `name`, `OPTLOCK`) VALUES (2, 'tag2', NULL);
INSERT INTO `uestcojtest`.`tag` (`tagId`, `name`, `OPTLOCK`) VALUES (3, 'tag3', NULL);
INSERT INTO `uestcojtest`.`tag` (`tagId`, `name`, `OPTLOCK`) VALUES (4, 'tag4', NULL);
INSERT INTO `uestcojtest`.`tag` (`tagId`, `name`, `OPTLOCK`) VALUES (5, 'tag5', NULL);
COMMIT;
