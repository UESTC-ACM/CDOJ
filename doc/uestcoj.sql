CREATE SCHEMA IF NOT EXISTS `uestcoj` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`department` (
  `departmentId` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'department\\\'s name' ,
  PRIMARY KEY (`departmentId`) ,
  UNIQUE INDEX `departmentId_UNIQUE` (`departmentId` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`user` (
  `userId` INT NOT NULL AUTO_INCREMENT ,
  `userName` VARCHAR(24) NOT NULL ,
  `studentId` VARCHAR(50) NOT NULL ,
  `departmentId` INT NOT NULL ,
  `password` VARCHAR(40) NOT NULL COMMENT 'need to validate\\nuse SHA1 encoding' ,
  `school` VARCHAR(50) NOT NULL DEFAULT '' ,
  `nickName` VARCHAR(50) NOT NULL COMMENT 'length >= 3' ,
  `email` VARCHAR(100) NOT NULL COMMENT 'need to validate' ,
  `solved` INT NOT NULL DEFAULT 0 ,
  `tried` INT NOT NULL DEFAULT 0 ,
  `type` INT NOT NULL DEFAULT 0 ,
  `lastLogin` DATETIME NOT NULL ,
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

CREATE  TABLE IF NOT EXISTS `uestcoj`.`article` (
  `articleId` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(50) NOT NULL ,
  `content` TEXT NOT NULL ,
  `author` VARCHAR(50) NOT NULL DEFAULT '' ,
  `time` DATETIME NOT NULL ,
  `clicked` INT NOT NULL DEFAULT 0 ,
  `order` INT NOT NULL DEFAULT 0 COMMENT 'set order to top and move' ,
  `isNotice` TINYINT(1) NOT NULL DEFAULT 0 ,
  `visible` TINYINT(1) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`articleId`) ,
  UNIQUE INDEX `noticeId_UNIQUE` (`articleId` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`code` (
  `codeId` INT NOT NULL AUTO_INCREMENT ,
  `content` TEXT NOT NULL ,
  PRIMARY KEY (`codeId`) ,
  UNIQUE INDEX `codeId_UNIQUE` (`codeId` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`compileInfo` (
  `compileInfoId` INT NOT NULL AUTO_INCREMENT ,
  `content` TEXT NOT NULL ,
  PRIMARY KEY (`compileInfoId`) ,
  UNIQUE INDEX `compileInfoId_UNIQUE` (`compileInfoId` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`problem` (
  `problemId` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(50) NOT NULL ,
  `description` TEXT NOT NULL ,
  `input` TEXT NOT NULL ,
  `output` TEXT NOT NULL ,
  `sampleInput` TEXT NOT NULL ,
  `sampleOutput` TEXT NOT NULL ,
  `hint` TEXT NOT NULL ,
  `source` VARCHAR(100) NOT NULL DEFAULT '' ,
  `timeLimit` INT NOT NULL DEFAULT 1000 ,
  `memoryLimit` INT NOT NULL DEFAULT 65535 ,
  `solved` INT NOT NULL DEFAULT 0 ,
  `tried` INT NOT NULL DEFAULT 0 ,
  `isSPJ` TINYINT(1) NOT NULL ,
  `isVisible` TINYINT(1) NOT NULL ,
  `outputLimit` INT NOT NULL DEFAULT 8000 ,
  `javaTimeLimit` INT NOT NULL DEFAULT 3000 ,
  `javaMemoryLimit` INT NOT NULL DEFAULT 65535 ,
  `dataCount` INT NOT NULL DEFAULT 1 ,
  `difficulty` INT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (`problemId`) ,
  UNIQUE INDEX `problemId_UNIQUE` (`problemId` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`contest` (
  `contestId` INT NOT NULL AUTO_INCREMENT ,
  `title` VARCHAR(50) NOT NULL COMMENT 'length >= 3' ,
  `description` VARCHAR(200) NOT NULL DEFAULT '' ,
  `type` TINYINT NOT NULL DEFAULT 0 ,
  `time` DATETIME NOT NULL ,
  `length` INT NOT NULL ,
  `isVisible` TINYINT(1) NOT NULL ,
  PRIMARY KEY (`contestId`) ,
  UNIQUE INDEX `contestId_UNIQUE` (`contestId` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`contest_team_info` (
  `teamId` INT NOT NULL AUTO_INCREMENT ,
  `userId` INT NOT NULL ,
  `name` VARCHAR(50) NOT NULL DEFAULT '' ,
  `coderName` VARCHAR(150) NOT NULL DEFAULT '' ,
  `sex` VARCHAR(3) NOT NULL DEFAULT '' ,
  `department` VARCHAR(50) NOT NULL DEFAULT '' ,
  `grade` VARCHAR(50) NOT NULL DEFAULT '' ,
  `phone` VARCHAR(100) NOT NULL DEFAULT '' ,
  `size` VARCHAR(50) NOT NULL DEFAULT '' ,
  `email` VARCHAR(300) NOT NULL DEFAULT '' ,
  `school` VARCHAR(50) NOT NULL DEFAULT '' ,
  `state` TINYINT(4) NOT NULL DEFAULT 0 ,
  PRIMARY KEY (`teamId`) ,
  UNIQUE INDEX `teamId_UNIQUE` (`teamId` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`contestProblem` (
  `contestProblemId` INT NOT NULL AUTO_INCREMENT ,
  `contestId` INT NOT NULL ,
  `problemId` INT NOT NULL ,
  `order` INT NOT NULL ,
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

CREATE  TABLE IF NOT EXISTS `uestcoj`.`discuss` (
  `discussId` INT NOT NULL AUTO_INCREMENT ,
  `problemId` INT NOT NULL ,
  `userId` INT NOT NULL ,
  `content` TEXT NOT NULL ,
  `time` DATETIME NOT NULL ,
  `parentId` INT NULL DEFAULT NULL COMMENT 'parent discuss id, if not exists set NULL.' ,
  PRIMARY KEY (`discussId`) ,
  UNIQUE INDEX `discussId_UNIQUE` (`discussId` ASC) ,
  INDEX `FK_problemId_on_problem_idx` (`problemId` ASC) ,
  INDEX `FK_userId_on_user_idx` (`userId` ASC) ,
  INDEX `FK_parentId_on_discuss_idx` (`parentId` ASC) ,
  CONSTRAINT `FK_discuss_problemId_on_problem`
    FOREIGN KEY (`problemId` )
    REFERENCES `uestcoj`.`problem` (`problemId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_discuss_userId_on_user`
    FOREIGN KEY (`userId` )
    REFERENCES `uestcoj`.`user` (`userId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK_disucss_parentId_on_discuss`
    FOREIGN KEY (`parentId` )
    REFERENCES `uestcoj`.`discuss` (`discussId` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`language` (
  `languageId` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL ,
  `extension` VARCHAR(10) NOT NULL ,
  `param` TEXT NOT NULL ,
  PRIMARY KEY (`languageId`) ,
  UNIQUE INDEX `languageId_UNIQUE` (`languageId` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`message` (
  `messageId` INT NOT NULL AUTO_INCREMENT ,
  `senderId` INT NOT NULL ,
  `receiverId` INT NOT NULL ,
  `title` VARCHAR(50) NOT NULL DEFAULT '' ,
  `content` TEXT NOT NULL ,
  `time` DATETIME NOT NULL ,
  `isOpened` TINYINT(1) NOT NULL DEFAULT 0 ,
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

CREATE  TABLE IF NOT EXISTS `uestcoj`.`tag` (
  `tagId` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL ,
  PRIMARY KEY (`tagId`) ,
  UNIQUE INDEX `tagId_UNIQUE` (`tagId` ASC) ,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) )
ENGINE = InnoDB;

CREATE  TABLE IF NOT EXISTS `uestcoj`.`problemTag` (
  `problemTagId` INT NOT NULL AUTO_INCREMENT ,
  `problemId` INT NOT NULL ,
  `tagId` INT NOT NULL ,
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
  `conetstId` INT NULL DEFAULT NULL ,
  `caseNumber` INT NOT NULL DEFAULT 0 ,
  `codeId` INT NOT NULL ,
  `compileInfoId` INT NULL DEFAULT NULL ,
  PRIMARY KEY (`statusId`) ,
  UNIQUE INDEX `statusId_UNIQUE` (`statusId` ASC) ,
  INDEX `FK_userID_on_user_idx` (`userId` ASC) ,
  INDEX `FK_problemId_on_problem_idx` (`problemId` ASC) ,
  INDEX `FK_languageId_on_language_idx` (`languageId` ASC) ,
  INDEX `FK_contestId_on_contst_idx` (`conetstId` ASC) ,
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
    FOREIGN KEY (`conetstId` )
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

CREATE  TABLE IF NOT EXISTS `uestcoj`.`contestUser` (
  `contestUserId` INT NOT NULL AUTO_INCREMENT ,
  `contestId` INT NOT NULL ,
  `userId` INT NOT NULL ,
  `status` TINYINT(4) NOT NULL COMMENT '0 - wait for validating\\n1 - accepted\\n2 - refused' ,
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
