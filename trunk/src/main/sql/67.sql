SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

ALTER TABLE `uestcoj`.`article` DROP FOREIGN KEY `FK_article_parentId_on_article` , DROP FOREIGN KEY `FK_article_problemId_on_problem` , DROP FOREIGN KEY `FK_article_contestId_on_contest` , DROP FOREIGN KEY `FK_article_userId_on_user` ;

ALTER TABLE `uestcoj`.`status` DROP FOREIGN KEY `FK_status_contestId_on_contest` , DROP FOREIGN KEY `FK_status_compileInfoId_on_compileInfo` ;

ALTER TABLE `uestcoj`.`user` ADD COLUMN `motto` VARCHAR(255) NOT NULL DEFAULT '这个人很屌，什么都没写。。。'  AFTER `OPTLOCK` ;

ALTER TABLE `uestcoj`.`problem` CHANGE COLUMN `dataCount` `dataCount` INT(11) NOT NULL DEFAULT 1  ;

ALTER TABLE `uestcoj`.`article` DROP COLUMN `author` , CHANGE COLUMN `isNotice` `type` INT(11) NOT NULL DEFAULT 0  , CHANGE COLUMN `parentId` `parentId` INT(11) NULL DEFAULT NULL  , CHANGE COLUMN `problemId` `problemId` INT(11) NULL DEFAULT NULL  , CHANGE COLUMN `contestId` `contestId` INT(11) NULL DEFAULT NULL  , CHANGE COLUMN `userId` `userId` INT(11) NULL DEFAULT NULL  , 
  ADD CONSTRAINT `FK_article_parentId_on_article`
  FOREIGN KEY (`parentId` )
  REFERENCES `uestcoj`.`article` (`articleId` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `FK_article_problemId_on_problem`
  FOREIGN KEY (`problemId` )
  REFERENCES `uestcoj`.`problem` (`problemId` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `FK_article_contestId_on_contest`
  FOREIGN KEY (`contestId` )
  REFERENCES `uestcoj`.`contest` (`contestId` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION, 
  ADD CONSTRAINT `FK_article_userId_on_user`
  FOREIGN KEY (`userId` )
  REFERENCES `uestcoj`.`user` (`userId` )
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `uestcoj`.`status` CHANGE COLUMN `contestId` `contestId` INT(11) NULL DEFAULT NULL  , CHANGE COLUMN `compileInfoId` `compileInfoId` INT(11) NULL DEFAULT NULL  , 
  ADD CONSTRAINT `FK_status_contestId_on_contest`
  FOREIGN KEY (`contestId` )
  REFERENCES `uestcoj`.`contest` (`contestId` )
  ON DELETE CASCADE
  ON UPDATE CASCADE, 
  ADD CONSTRAINT `FK_status_compileInfoId_on_compileInfo`
  FOREIGN KEY (`compileInfoId` )
  REFERENCES `uestcoj`.`compileInfo` (`compileInfoId` )
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `uestcoj`.`code` ADD COLUMN `share` TINYINT(1) NOT NULL DEFAULT false  AFTER `OPTLOCK` ;

UPDATE `uestcoj`.`trainingUser` SET `ratingVary` = 0 WHERE `ratingVary` IS NULL;
UPDATE `uestcoj`.`trainingUser` SET `volatilityVary` = 0 WHERE `volatilityVary` IS NULL;
ALTER TABLE `uestcoj`.`trainingUser` CHANGE COLUMN `ratingVary` `ratingVary` DOUBLE NOT NULL DEFAULT 0  , CHANGE COLUMN `volatilityVary` `volatilityVary` DOUBLE NOT NULL DEFAULT 0 ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `uestcoj` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `uestcoj` ;

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

USE `uestcoj` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `uestcoj`.`article`
-- -----------------------------------------------------
START TRANSACTION;
USE `uestcoj`;
INSERT INTO `uestcoj`.`article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (1, 'Frequently Asked Questions', '###CDOJ支持的语言 \nCDOJ支持的语言有C、C++和Java。\n\n它们所对应的编辑器参照[Arch Linux软件源](https://www.archlinux.org/packages/)中的**最新**版本。\n\n***\n###在CDOJ上允许使用的输入输出方式 \nCDOJ只支持标准输入标准输出，也就是我们使用的键盘输入输出。注意CDOJ不支持文件操作的函数，如果在您的代码中使用 了文件操作，您将得到`Restricted Function`的结果。 \n\n例如要通过[A+B Problem](/problem/show/1)这道题目，我们可以使用以下几种形式的代码：\n\n####For GNU C\n```\n#include <stdio.h>\n\nint main() \n{\n    int a, b;\n    scanf(\"%d %d\",&a, &b);\n    printf(\"%d\", a+b);\n    return 0;\n}\n```\n\n####For GNU C++\n```\n#include <iostream>\nusing namespace std;  \n\nint main()\n{ \n    int a,b;  \n    cin >> a >> b; \n    cout << a+b << endl; \n    return 0;  \n}\n```\n\n####For Java\n```\nimport java.io.*; \nimport java.util.*;  \n\npublic class Main { \n\n    public static void main(String[] args) throws Exception {  \n        Scanner cin=new Scanner(System.in); \n        int a=cin.nextInt(), b=cin.nextInt();  \n        System.out.println (a + b); \n    }  \n}\n```\n\n***\n###关于C和C++的一些说明 \n1. `main`函数须返回`int`类型，不能使用`void main`，在`main`函数的最后需要加上`return 0`。 \n2. 不要使用系统函数，比如system函数和所有的API函数，如果您在提交的题目中包含了这些函数，您将得到`Restricted Function`的结果。 \n3. 如果用到64位整型，请使用**`long long`**定义，格式化输出符号用**`%lld`**和**`%llu`**来输出`long long`和`unsigned long long`类型变量。 \n4. G++/GCC的编译参数： \n  * `g++ -static -w -O2 -DONLINE_JUDGE -o a.out main.cc`\n  * `gcc -static -w -O2 -DONLINE_JUDGE -o a.out main.c` \n\n***\n###关于Java的一些说明 \n1. 您提交的Java程序必须是单个文件的，也就是说所有的类须写在同一个文件里，要保证您写的文件中，主类名称为`Main`, 在主类 中有且仅有一个静态的`main`函数。 \n2. 您须保证您写的主类是公有类。。\n3. 考虑到Java解释运行的特点，Java的时限和其他语言分开，单独说明。 \n4. Java的编译参数：\n  * `javac Main.java -d temp/` \n\n***\n###CDOJ常见的评测结果 \n1. `Wating`：系统正在对之前的用户代码进行评测。 \n2. `Compiling`：系统正在编译您的代码。 \n3. `Judging`：系统正在运行并评测您的程序 \n4. `Compile Error`：您的代码没有编译成功，请查看自己的代码是否符合标准，或者检查自己是否选择了正确的语言。 \n5. `Wrong Answer`：您的程序返回了错误的结果。 \n6. `Runtime Error`：您的程序在运行的时候发生了错误导致程序中途停止了运行，常见的问题有数组越界或者递归调用过深等。 \n7. `Time Limit Exceeded`：您的程序的运行时间超过的题目限制的时限，遇到此类问题的原因有可能是您的算法时间复杂度太高 或者程序中出现了死循环。 \n8. `Output Limit Exceeded`：您的程序输出了过多的信息，遇到此类问题的原因有可能是出现死循环或者调试时的调试代码没有注释。 \n9. `Memory Limit Exceeded`：您的程序申请的内存空间超出了题目的限制范围，请您优化空间复杂度。 \n10. `Restricted Function`：您的程序使用了不允许使用的函数，比如system函数和API函数，注意数组越界也有可能返回`Restricted Function`。 \n11. `Presentation Error`：您的程序运行出了正确的结果，但是没有正确地按照输出格式输出，可能多或少输出了空格和回车，请 您再次查看自己的输出和题目的输出规范是否符合。 \n12. `Accepted`：您的程序得到了正确的结果。 \n\n***\n###关于C++中cin/cout和printf/scanf的取舍问题 \n在C++中，`cin`和`cout`输入输出流确实为我们提供了非常大的便利，但是`cin`和`cout`有各自的缓冲区，而这个也导致了`cin`和`cout`相比`scanf`、`printf`来说，速度慢了非常多的现象。而且大部分情况下，题目中并不会有太多的提示，所以在此建议大家习惯使用`scanf`和`printf`进行输入输出，C++也支持这两个函数，只要包含`cstdio`或者`stdio.h`头文件即可。', '2013-07-11 22:28:01', 0, 0, 0, 1, NULL, NULL, NULL, 1, 16);
INSERT INTO `uestcoj`.`article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (2, 'Markdown syntax cheatsheet', '我们使用[marked](https://github.com/chjj/marked)作为新OJ所有内容文本的渲染引擎。\n\n关于标准markdown语法规则可以参照[http://markdown.tw](http://markdown.tw)，下面给出一些常用的基本规则。\n\n***\n##HEADERS\n```\n# 一级标题\n## 二级标题\n### 三级标题\n#### 四级标题\n##### 五级标题\n```\n# 一级标题\n## 二级标题\n### 三级标题\n#### 四级标题\n##### 五级标题\n\n***\n##BLOCKQUOTES\n```\n> 这是引用\n\n> 这也是引用\n  啊\n\n> 还可以嵌套引用\n>>  喵\n```\n\n> 这是引用\n\n> 还可以嵌套引用\n>>  喵\n\n***\n##LISTS\n```\n1. 有序列表\n2. 有序列表\n  * 无序列表\n    * 嵌套\n    * 嵌套\n  * 无序列表\n```\n1. 有序列表\n2. 有序列表\n  * 无序列表\n    * 嵌套\n    * 嵌套\n  * 无序列表\n\n***\n##CODE BLOCKS\n    在一行内插入`代码`是很容易的\n\n        插入一段代码也很方便（四个空格或一个tab）\n    \n    ```\n    还有一种方法（适合用来框代码）\n    ````\n\n在一行内插入`代码`是很容易的\n\n    插入一段代码也很方便（四个空格或一个tab）\n\n```\n还有一种方法（适合用来框代码）\n```\n\n***\n##HORIZONTAL RULES\n```\n***\n```\n\n***\n\n***\n##LINKS\n```\n[超链接](http://acm.uestc.edu.cn:8080/)\n```\n[超链接](http://acm.uestc.edu.cn:8080/)\n\n***\n##EMPHASIS\n```\n*斜体*\n\n**粗体**\n\n***粗斜体***\n\n~~删除线~~\n```\n*斜体*\n\n**粗体**\n\n***粗斜体***\n\n~~删除线~~\n\n***\n##IMAGES\n```\n![Title](/images/logo/banner.png)\n```\n![Title](/images/logo/banner.png)\n\n***\n##EpicEditor\n新OJ使用的编辑器是[EpicEditor](http://oscargodson.github.com/EpicEditor/)，它也用marked作为渲染引擎。\n\n除此之外，自己尝试在[EpicEditor]()中整合了一个图片上传功能\n\n![Title](/images/article/2/201307022026484821.png)\n\n支持多张上传\n\n![Title](/images/article/2/201307022027379352.png)\n\n选择需要插入的图片。\n\n![Title](/images/article/2/201307022027550133.png)\n\n就能得到对应代码，复制到需要插入的位置即可。（暂时没找到自动插入的方法）', '2013-07-02 21:27:07', 0, 0, 0, 1, NULL, NULL, NULL, 1, 12);
INSERT INTO `uestcoj`.`article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (3, 'Training system', '###集训系统简介\n集训系统是电子科技大学ACM-ICPC集训队用来统计队员寒暑假训练成绩以及度量训练成绩使用的系统，它采用了TopCoder的[Rating System](http://apps.topcoder.com/wiki/display/tc/Algorithm+Competition+Rating+System)来度量成绩。\n\n***\n###队员注册\n队员分为两种，一种是固定组队的老队员，和个人赛为主的新队员。\n\n老队员账号注册时`name`为自己常用的队名，需要和后面比赛时用的账号的`nick name`相同，便于成绩导入。`member`为队员名称\n\n新队员注册时`name`和`member`统一使用自己的真实姓名，日后比赛时账号的`nick name`也需要为自己的真实姓名。\n\n比较特殊的是新队员组队赛，此时建议注册一个新账号，`nick name`为三个队员的姓名，用英文逗号`,`隔开，方便成绩统计。\n\n***\n###比赛类型\n现在暂时有`normal`、`adjust`、`cf`、`tc`、`team`五种类型。\n\n1. `normal` 一般类型比赛，现在支持导入Virutal Judge、HDU和PC^2的ranklist。\n2. `adjust` 此类为后期调整分数用（例如扣分）。\n3. `cf`和`tc` 考虑到这两类比赛无法直接导出ranklist，采用手工统计的方法，只保留得分信息。\n  * 对于`CF`和`TC`的`Div1`用户，这里采取得分三倍处理（包括挂负）。\n  * 未参赛用户做零分处理。\n4. `team` 这个对应暑假集训的随机组队赛，队员享有同样的排名，但是在计算Rating时权值会降低一些（weight值减半）。\n\n***\n###相关链接\n[Rating System](http://apps.topcoder.com/wiki/display/tc/Algorithm+Competition+Rating+System)实现细节在[这里](https://gitcafe.com/UESTC_ACM/cdoj/blob/master/trunk/src/main/java/cn/edu/uestc/acmicpc/util/RatingUtil.java)。', '2013-07-22 20:48:56', 0, 0, 0, 1, NULL, NULL, NULL, 1, 4);
INSERT INTO `uestcoj`.`article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (4, 'About', '[Project home](https://gitcafe.com/UESTC_ACM/cdoj)\n\n***\n###Features\n* Use markdown to edit problem and article\n* Manager problem data in convenient way\n* Full support of formula(use MathJax)\n\n***\n###TODO\n* Discuss\n* Contest clarification\n* Blog and BBS\n* Links manage\n* Problem tags manage\n* Related documents\n* Attachments manage\n* Message and bookmarks\n* Optimize database and use cache to speed up\n* Backup\n* Upload contest in a zip package\n* VJ(maybe..)\n\n***\n###Copyright\ncdoj, UESTC ACMICPC Online Judge\n\nCopyright (c) 2013 fish [lyhypacm@gmail.com](mailto:lyhypacm@gmail.com),\n mzry1992 [muziriyun@gmail.com](mailto:muziriyun@gmail.com)\n\nThis program is free software; you can redistribute it and/or\nmodify it under the terms of the GNU General Public License\nas published by the Free Software Foundation; either version 2\nof the License, or (at your option) any later version.\n\nThis program is distributed in the hope that it will be useful,\nbut WITHOUT ANY WARRANTY; without even the implied warranty of\nMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the\nGNU General Public License for more details.\n\nYou should have received a copy of the GNU General Public License\nalong with this program; if not, write to the Free Software\nFoundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.', '2013-07-17 23:59:14', 0, 0, 0, 1, NULL, NULL, NULL, 1, 6);

COMMIT;

