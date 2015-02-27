SET NAMES UTF8;

USE `uestcojtest` ;

-- -----------------------------------------------------
-- Data for table `uestcoj`.`setting`
-- -----------------------------------------------------
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

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `training` (`trainingId`, `title`, `description`) VALUES(1, 'Training1', 'Description1');
INSERT INTO `training` (`trainingId`, `title`, `description`) VALUES(2, 'Training2', 'Description2');
INSERT INTO `training` (`trainingId`, `title`, `description`) VALUES(3, 'Training3', 'Description3');
INSERT INTO `training` (`trainingId`, `title`, `description`) VALUES(4, 'Training4', 'Description4');
INSERT INTO `training` (`trainingId`, `title`, `description`) VALUES(5, 'keyword1', 'Description5');
INSERT INTO `training` (`trainingId`, `title`, `description`) VALUES(6, 'keyword2', 'Description6');
INSERT INTO `training` (`trainingId`, `title`, `description`) VALUES(7, 'keyword3', 'Description7');

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `user` (`userId`, `userName`, `studentId`, `departmentId`, `password`, `school`, `nickName`, `email`, `solved`, `tried`, `type`, `lastLogin`, `OPTLOCK`, `motto`, `name`, `sex`, `grade`, `phone`, `size`) 
VALUES (1, 'administrator', '2010013100008', 1, '3669a3b6618e9b27d641666d764432e025fc5be7', 'UESTC', 'administrator', 'acm@uestc.edu.cn', 0, 0, 1, '2013-01-30 13:17:26', 0, 'fuck', 'aa', 0, 0, '123', 0);
INSERT INTO `user` (`userId`, `userName`, `studentId`, `departmentId`, `password`, `school`, `nickName`, `email`, `solved`, `tried`, `type`, `lastLogin`, `OPTLOCK`, `motto`, `name`, `sex`, `grade`, `phone`, `size`) 
VALUES (2, 'admin', '2010013100008', 1, '5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8', 'UESTC', 'admin', 'acm_admin@uestc.edu.cn', 0, 0, 1, '2013-01-30 13:17:26', 0, 'fuck', 'aa', 0, 0, '123', 0);
INSERT INTO `user` (`userId`, `userName`, `studentId`, `departmentId`, `password`, `school`, `nickName`, `email`, `solved`, `tried`, `type`, `lastLogin`, `OPTLOCK`, `motto`, `name`, `sex`, `grade`, `phone`, `size`) 
VALUES (3, 'user_id3', '2010013100008', 2, '3669a3b6618e9b27d641666d764432e025fc5be7', 'UESTC', 'user_id3', 'user_id3@uestc.edu.cn', 0, 0, 0, '2013-01-30 13:17:26', 0, 'fuck', 'aa', 0, 0, '123', 0);
INSERT INTO `user` (`userId`, `userName`, `studentId`, `departmentId`, `password`, `school`, `nickName`, `email`, `solved`, `tried`, `type`, `lastLogin`, `OPTLOCK`, `motto`, `name`, `sex`, `grade`, `phone`, `size`) 
VALUES (4, 'user4', '2010013100008', 1, '3669a3b6618e9b27d641666d764432e025fc5be7', 'UESTC', 'user4', 'user4@uestc.edu.cn', 0, 0, 2, '2013-01-30 13:17:26', 0, 'fuck', 'aa', 0, 0, '123', 0);
INSERT INTO `user` (`userId`, `userName`, `studentId`, `departmentId`, `password`, `school`, `nickName`, `email`, `solved`, `tried`, `type`, `lastLogin`, `OPTLOCK`, `motto`, `name`, `sex`, `grade`, `phone`, `size`) 
VALUES (5, 'user5', '2010013100008', 1, '3669a3b6618e9b27d641666d764432e025fc5be7', 'UESTC', 'user5', 'user5@uestc.edu.cn', 0, 0, 1, '2013-01-30 13:17:26', 0, 'fuck', 'aa', 0, 0, '123', 0);
INSERT INTO `user` (`userId`, `userName`, `studentId`, `departmentId`, `password`, `school`, `nickName`, `email`, `solved`, `tried`, `type`, `lastLogin`, `OPTLOCK`, `motto`, `name`, `sex`, `grade`, `phone`, `size`) 
VALUES (6, 'user6', '2010013100008', 1, '3669a3b6618e9b27d641666d764432e025fc5be7', 'UESTC', 'user6', 'user6@uestc.edu.cn', 0, 0, 1, '2013-01-30 13:17:26', 0, 'fuck', 'aa', 0, 0, '123', 0);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `language` (`languageId`, `name`, `extension`, `param`, `OPTLOCK`) VALUES (1, 'C', '.c', '', 0);
INSERT INTO `language` (`languageId`, `name`, `extension`, `param`, `OPTLOCK`) VALUES (2, 'C++', '.cc', '', 0);
INSERT INTO `language` (`languageId`, `name`, `extension`, `param`, `OPTLOCK`) VALUES (3, 'Java', '.java', '', 0);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `problem` (`problemId`, `title`, `description`, `input`, `output`, `sampleInput`, `sampleOutput`, `hint`, `source`, `timeLimit`, `memoryLimit`, `solved`, `tried`, `isSPJ`, `isVisible`, `outputLimit`, `javaTimeLimit`, `javaMemoryLimit`, `dataCount`, `difficulty`, `type`, `OPTLOCK`) VALUES (1, 'a+b problem', 'description', 'input', 'output', 'sampleinput', 'sampleoutput', 'hint', 'source', 1000, 65535, 0, 0, 0, 1, 8192, 3000, 65535, 1, 1, 0, NULL);
INSERT INTO `problem` (`problemId`, `title`, `description`, `input`, `output`, `sampleInput`, `sampleOutput`, `hint`, `source`, `timeLimit`, `memoryLimit`, `solved`, `tried`, `isSPJ`, `isVisible`, `outputLimit`, `javaTimeLimit`, `javaMemoryLimit`, `dataCount`, `difficulty`, `type`, `OPTLOCK`) VALUES (2, 'problem 2', 'description 2', 'input', 'output', 'sampleinput', 'sampleoutput', 'hint', 'source', 1000, 65535, 0, 0, 1, 1, 8192, 3000, 65535, 1, 1, 0, NULL);
INSERT INTO `problem` (`problemId`, `title`, `description`, `input`, `output`, `sampleInput`, `sampleOutput`, `hint`, `source`, `timeLimit`, `memoryLimit`, `solved`, `tried`, `isSPJ`, `isVisible`, `outputLimit`, `javaTimeLimit`, `javaMemoryLimit`, `dataCount`, `difficulty`, `type`, `OPTLOCK`) VALUES (3, 'problem 3', 'description 3', 'input', 'output', 'sampleinput', 'sampleoutput', 'hint', 'source', 1000, 65535, 0, 0, 0, 1, 8192, 3000, 65535, 1, 1, 0, NULL);
INSERT INTO `problem` (`problemId`, `title`, `description`, `input`, `output`, `sampleInput`, `sampleOutput`, `hint`, `source`, `timeLimit`, `memoryLimit`, `solved`, `tried`, `isSPJ`, `isVisible`, `outputLimit`, `javaTimeLimit`, `javaMemoryLimit`, `dataCount`, `difficulty`, `type`, `OPTLOCK`) VALUES (4, 'problem 4', 'description 4', 'input', 'output', 'sampleinput', 'sampleoutput', 'hint', 'source', 1000, 65535, 0, 0, 1, 1, 8192, 3000, 65535, 1, 1, 0, NULL);
INSERT INTO `problem` (`problemId`, `title`, `description`, `input`, `output`, `sampleInput`, `sampleOutput`, `hint`, `source`, `timeLimit`, `memoryLimit`, `solved`, `tried`, `isSPJ`, `isVisible`, `outputLimit`, `javaTimeLimit`, `javaMemoryLimit`, `dataCount`, `difficulty`, `type`, `OPTLOCK`) VALUES (5, 'problem 5', 'description 5', 'input', 'output', 'sampleinput', 'sampleoutput', 'hint', 'source', 1000, 65535, 0, 0, 0, 1, 8192, 3000, 65535, 1, 1, 0, NULL);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `code` (`codeId`, `content`, `OPTLOCK`) VALUES (1, 'code', NULL);
INSERT INTO `code` (`codeId`, `content`, `OPTLOCK`) VALUES (2, 'code', NULL);
INSERT INTO `code` (`codeId`, `content`, `OPTLOCK`) VALUES (3, 'code', NULL);
INSERT INTO `code` (`codeId`, `content`, `OPTLOCK`) VALUES (4, 'code', NULL);
INSERT INTO `code` (`codeId`, `content`, `OPTLOCK`) VALUES (5, 'code', NULL);
INSERT INTO `code` (`codeId`, `content`, `OPTLOCK`) VALUES (6, 'code', NULL);
INSERT INTO `code` (`codeId`, `content`, `OPTLOCK`) VALUES (7, 'code', NULL);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `compileInfo` (`compileInfoId`, `content`, `OPTLOCK`) VALUES (1, 'compile info', NULL);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `contest` (`contestId`, `title`, `description`, `type`, `time`, `length`, `isVisible`, `OPTLOCK`) 
VALUES (1, 'title', 'descrip', 1, '2013-01-01 00:00:00', 300, 1, NULL);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `status` (`statusId`, `userId`, `problemId`, `result`, `memoryCost`, `timeCost`, `languageId`, `length`, `time`, `contestId`, `caseNumber`, `codeId`, `compileInfoId`, `OPTLOCK`) 
VALUES (1, 1, 1, 1, 1000, 15, 1, 1000, '2013-07-07 00:00:00', NULL, 1, 1, NULL, NULL);
INSERT INTO `status` (`statusId`, `userId`, `problemId`, `result`, `memoryCost`, `timeCost`, `languageId`, `length`, `time`, `contestId`, `caseNumber`, `codeId`, `compileInfoId`, `OPTLOCK`) 
VALUES (2, 1, 1, 2, 1000, 15, 1, 1000, '2013-07-07 00:00:00', NULL, 1, 2, NULL, NULL);
INSERT INTO `status` (`statusId`, `userId`, `problemId`, `result`, `memoryCost`, `timeCost`, `languageId`, `length`, `time`, `contestId`, `caseNumber`, `codeId`, `compileInfoId`, `OPTLOCK`) 
VALUES (3, 1, 1, 3, 1000, 15, 1, 1000, '2013-07-07 00:00:00', NULL, 1, 3, NULL, NULL);
INSERT INTO `status` (`statusId`, `userId`, `problemId`, `result`, `memoryCost`, `timeCost`, `languageId`, `length`, `time`, `contestId`, `caseNumber`, `codeId`, `compileInfoId`, `OPTLOCK`) 
VALUES (4, 2, 1, 4, 1000, 15, 1, 1000, '2013-07-07 00:00:00', NULL, 1, 4, NULL, NULL);
INSERT INTO `status` (`statusId`, `userId`, `problemId`, `result`, `memoryCost`, `timeCost`, `languageId`, `length`, `time`, `contestId`, `caseNumber`, `codeId`, `compileInfoId`, `OPTLOCK`) 
VALUES (5, 2, 1, 7, 1000, 15, 1, 1000, '2013-07-07 00:00:00', 1, 1, 5, 1, NULL);
INSERT INTO `status` (`statusId`, `userId`, `problemId`, `result`, `memoryCost`, `timeCost`, `languageId`, `length`, `time`, `contestId`, `caseNumber`, `codeId`, `compileInfoId`, `OPTLOCK`) 
VALUES (6, 3, 1, 2, 1000, 15, 1, 1000, '2014-01-01 00:00:00', 1, 1, 6, NULL, NULL);
INSERT INTO `status` (`statusId`, `userId`, `problemId`, `result`, `memoryCost`, `timeCost`, `languageId`, `length`, `time`, `contestId`, `caseNumber`, `codeId`, `compileInfoId`, `OPTLOCK`) 
VALUES (7, 3, 2, 1, 1000, 15, 1, 1000, '2014-03-26 11:00:00', NULL, 1, 7, NULL, NULL);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `tag` (`tagId`, `name`, `OPTLOCK`) VALUES (1, 'tag1', NULL);
INSERT INTO `tag` (`tagId`, `name`, `OPTLOCK`) VALUES (2, 'tag2', NULL);
INSERT INTO `tag` (`tagId`, `name`, `OPTLOCK`) VALUES (3, 'tag3', NULL);
INSERT INTO `tag` (`tagId`, `name`, `OPTLOCK`) VALUES (4, 'tag4', NULL);
INSERT INTO `tag` (`tagId`, `name`, `OPTLOCK`) VALUES (5, 'tag5', NULL);
COMMIT;

START TRANSACTION;
USE `uestcojtest`;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (1, 'Frequently Asked Questions', '###CDOJ支持的语言 \nCDOJ支持的语言有C、C++和Java。\n\n它们所对应的编辑器参照[Arch Linux软件源](https://www.archlinux.org/packages/)中的**最新**版本。\n\n***\n###在CDOJ上允许使用的输入输出方式 \nCDOJ只支持标准输入标准输出，也就是我们使用的键盘输入输出。注意CDOJ不支持文件操作的函数，如果在您的代码中使用 了文件操作，您将得到`Restricted Function`的结果。 \n\n例如要通过[A+B Problem](/problem/show/1)这道题目，我们可以使用以下几种形式的代码：\n\n####For GNU C\n```\n#include <stdio.h>\n\nint main() \n{\n    int a, b;\n    scanf(\"%d %d\",&a, &b);\n    printf(\"%d\", a+b);\n    return 0;\n}\n```\n\n####For GNU C++\n```\n#include <iostream>\nusing namespace std;  \n\nint main()\n{ \n    int a,b;  \n    cin >> a >> b; \n    cout << a+b << endl; \n    return 0;  \n}\n```\n\n####For Java\n```\nimport java.io.*; \nimport java.util.*;  \n\npublic class Main { \n\n    public static void main(String[] args) throws Exception {  \n        Scanner cin=new Scanner(System.in); \n        int a=cin.nextInt(), b=cin.nextInt();  \n        System.out.println (a + b); \n    }  \n}\n```\n\n***\n###关于C和C++的一些说明 \n1. `main`函数须返回`int`类型，不能使用`void main`，在`main`函数的最后需要加上`return 0`。 \n2. 不要使用系统函数，比如system函数和所有的API函数，如果您在提交的题目中包含了这些函数，您将得到`Restricted Function`的结果。 \n3. 如果用到64位整型，请使用**`long long`**定义，格式化输出符号用**`%lld`**和**`%llu`**来输出`long long`和`unsigned long long`类型变量。 \n4. G++/GCC的编译参数： \n  * `g++ -static -w -O2 -DONLINE_JUDGE -o a.out main.cc`\n  * `gcc -static -w -O2 -DONLINE_JUDGE -o a.out main.c` \n\n***\n###关于Java的一些说明 \n1. 您提交的Java程序必须是单个文件的，也就是说所有的类须写在同一个文件里，要保证您写的文件中，主类名称为`Main`, 在主类 中有且仅有一个静态的`main`函数。 \n2. 您须保证您写的主类是公有类。。\n3. 考虑到Java解释运行的特点，Java的时限和其他语言分开，单独说明。 \n4. Java的编译参数：\n  * `javac Main.java -d temp/` \n\n***\n###CDOJ常见的评测结果 \n1. `Wating`：系统正在对之前的用户代码进行评测。 \n2. `Compiling`：系统正在编译您的代码。 \n3. `Judging`：系统正在运行并评测您的程序 \n4. `Compile Error`：您的代码没有编译成功，请查看自己的代码是否符合标准，或者检查自己是否选择了正确的语言。 \n5. `Wrong Answer`：您的程序返回了错误的结果。 \n6. `Runtime Error`：您的程序在运行的时候发生了错误导致程序中途停止了运行，常见的问题有数组越界或者递归调用过深等。 \n7. `Time Limit Exceeded`：您的程序的运行时间超过的题目限制的时限，遇到此类问题的原因有可能是您的算法时间复杂度太高 或者程序中出现了死循环。 \n8. `Output Limit Exceeded`：您的程序输出了过多的信息，遇到此类问题的原因有可能是出现死循环或者调试时的调试代码没有注释。 \n9. `Memory Limit Exceeded`：您的程序申请的内存空间超出了题目的限制范围，请您优化空间复杂度。 \n10. `Restricted Function`：您的程序使用了不允许使用的函数，比如system函数和API函数，注意数组越界也有可能返回`Restricted Function`。 \n11. `Presentation Error`：您的程序运行出了正确的结果，但是没有正确地按照输出格式输出，可能多或少输出了空格和回车，请 您再次查看自己的输出和题目的输出规范是否符合。 \n12. `Accepted`：您的程序得到了正确的结果。 \n\n***\n###关于C++中cin/cout和printf/scanf的取舍问题 \n在C++中，`cin`和`cout`输入输出流确实为我们提供了非常大的便利，但是`cin`和`cout`有各自的缓冲区，而这个也导致了`cin`和`cout`相比`scanf`、`printf`来说，速度慢了非常多的现象。而且大部分情况下，题目中并不会有太多的提示，所以在此建议大家习惯使用`scanf`和`printf`进行输入输出，C++也支持这两个函数，只要包含`cstdio`或者`stdio.h`头文件即可。', '2013-07-11 22:28:01', 0, 0, 0, 1, NULL, NULL, NULL, 1, 16);
INSERT INTO `article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (2, 'Markdown syntax cheatsheet', '我们使用[marked](https://github.com/chjj/marked)作为新OJ所有内容文本的渲染引擎。\n\n关于标准markdown语法规则可以参照[http://markdown.tw](http://markdown.tw)，下面给出一些常用的基本规则。\n\n***\n##HEADERS\n```\n# 一级标题\n## 二级标题\n### 三级标题\n#### 四级标题\n##### 五级标题\n```\n# 一级标题\n## 二级标题\n### 三级标题\n#### 四级标题\n##### 五级标题\n\n***\n##BLOCKQUOTES\n```\n> 这是引用\n\n> 这也是引用\n  啊\n\n> 还可以嵌套引用\n>>  喵\n```\n\n> 这是引用\n\n> 还可以嵌套引用\n>>  喵\n\n***\n##LISTS\n```\n1. 有序列表\n2. 有序列表\n  * 无序列表\n    * 嵌套\n    * 嵌套\n  * 无序列表\n```\n1. 有序列表\n2. 有序列表\n  * 无序列表\n    * 嵌套\n    * 嵌套\n  * 无序列表\n\n***\n##CODE BLOCKS\n    在一行内插入`代码`是很容易的\n\n        插入一段代码也很方便（四个空格或一个tab）\n    \n    ```\n    还有一种方法（适合用来框代码）\n    ````\n\n在一行内插入`代码`是很容易的\n\n    插入一段代码也很方便（四个空格或一个tab）\n\n```\n还有一种方法（适合用来框代码）\n```\n\n***\n##HORIZONTAL RULES\n```\n***\n```\n\n***\n\n***\n##LINKS\n```\n[超链接](http://acm.uestc.edu.cn:8080/)\n```\n[超链接](http://acm.uestc.edu.cn:8080/)\n\n***\n##EMPHASIS\n```\n*斜体*\n\n**粗体**\n\n***粗斜体***\n\n~~删除线~~\n```\n*斜体*\n\n**粗体**\n\n***粗斜体***\n\n~~删除线~~\n\n***\n##IMAGES\n```\n![Title](/images/logo/banner.png)\n```\n![Title](/images/logo/banner.png)\n\n***\n##EpicEditor\n新OJ使用的编辑器是[EpicEditor](http://oscargodson.github.com/EpicEditor/)，它也用marked作为渲染引擎。\n\n除此之外，自己尝试在[EpicEditor]()中整合了一个图片上传功能\n\n![Title](/images/article/2/201307022026484821.png)\n\n支持多张上传\n\n![Title](/images/article/2/201307022027379352.png)\n\n选择需要插入的图片。\n\n![Title](/images/article/2/201307022027550133.png)\n\n就能得到对应代码，复制到需要插入的位置即可。（暂时没找到自动插入的方法）', '2013-07-02 21:27:07', 0, 0, 0, 1, NULL, NULL, NULL, 1, 12);
INSERT INTO `article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (3, 'Training system', '###集训系统简介\n集训系统是电子科技大学ACM-ICPC集训队用来统计队员寒暑假训练成绩以及度量训练成绩使用的系统，它采用了TopCoder的[Rating System](http://apps.topcoder.com/wiki/display/tc/Algorithm+Competition+Rating+System)来度量成绩。\n\n***\n###队员注册\n队员分为两种，一种是固定组队的老队员，和个人赛为主的新队员。\n\n老队员账号注册时`name`为自己常用的队名，需要和后面比赛时用的账号的`nick name`相同，便于成绩导入。`member`为队员名称\n\n新队员注册时`name`和`member`统一使用自己的真实姓名，日后比赛时账号的`nick name`也需要为自己的真实姓名。\n\n比较特殊的是新队员组队赛，此时建议注册一个新账号，`nick name`为三个队员的姓名，用英文逗号`,`隔开，方便成绩统计。\n\n***\n###比赛类型\n现在暂时有`normal`、`adjust`、`cf`、`tc`、`team`五种类型。\n\n1. `normal` 一般类型比赛，现在支持导入Virutal Judge、HDU和PC^2的ranklist。\n2. `adjust` 此类为后期调整分数用（例如扣分）。\n3. `cf`和`tc` 考虑到这两类比赛无法直接导出ranklist，采用手工统计的方法，只保留得分信息。\n  * 对于`CF`和`TC`的`Div1`用户，这里采取得分三倍处理（包括挂负）。\n  * 未参赛用户做零分处理。\n4. `team` 这个对应暑假集训的随机组队赛，队员享有同样的排名，但是在计算Rating时权值会降低一些（weight值减半）。\n\n***\n###相关链接\n[Rating System](http://apps.topcoder.com/wiki/display/tc/Algorithm+Competition+Rating+System)实现细节在[这里](https://gitcafe.com/UESTC_ACM/cdoj/blob/master/trunk/src/main/java/cn/edu/uestc/acmicpc/util/RatingUtil.java)。', '2013-07-22 20:48:56', 0, 0, 0, 1, NULL, NULL, NULL, 1, 4);
INSERT INTO `article` (`articleId`, `title`, `content`, `time`, `clicked`, `order`, `type`, `isVisible`, `parentId`, `problemId`, `contestId`, `userId`, `OPTLOCK`) VALUES (4, 'About', '[Project home](https://gitcafe.com/UESTC_ACM/cdoj)\n\n***\n###Features\n* Use markdown to edit problem and article\n* Manager problem data in convenient way\n* Full support of formula(use MathJax)\n\n***\n###TODO\n* Discuss\n* Contest clarification\n* Blog and BBS\n* Links manage\n* Problem tags manage\n* Related documents\n* Attachments manage\n* Message and bookmarks\n* Optimize database and use cache to speed up\n* Backup\n* Upload contest in a zip package\n* VJ(maybe..)\n\n***\n###Copyright\ncdoj, UESTC ACMICPC Online Judge\n\nCopyright (c) 2013 fish [lyhypacm@gmail.com](mailto:lyhypacm@gmail.com),\n mzry1992 [muziriyun@gmail.com](mailto:muziriyun@gmail.com)\n\nThis program is free software; you can redistribute it and/or\nmodify it under the terms of the GNU General Public License\nas published by the Free Software Foundation; either version 2\nof the License, or (at your option) any later version.\n\nThis program is distributed in the hope that it will be useful,\nbut WITHOUT ANY WARRANTY; without even the implied warranty of\nMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the\nGNU General Public License for more details.\n\nYou should have received a copy of the GNU General Public License\nalong with this program; if not, write to the Free Software\nFoundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.', '2013-07-17 23:59:14', 0, 0, 0, 1, NULL, NULL, NULL, 1, 6);

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `trainingUser` (`trainingUserId`, `trainingId`, `userId`, `trainingUserName`, `type`, `currentRating`, `currentVolatility`, `competitions`, `rank`, `maximumRating`, `minimumRating`, `mostRecentEventId`, `mostRecentEventName`, `ratingHistory`)
VALUES
	(1, 1, 1, X'7573657241', 0, 1200, 350, 0, 0, 1200, 1200, NULL, NULL, X''),
	(2, 1, 2, X'7573657242', 0, 1200, 350, 0, 0, 1200, 1200, NULL, NULL, X''),
	(3, 1, 3, X'7573657243', 0, 1200, 350, 0, 0, 1200, 1200, NULL, NULL, X''),
	(4, 1, 3, X'757365724332', 0, 1200, 350, 0, 0, 1200, 1200, NULL, NULL, X''),
	(5, 2, 1, X'7573657241', 0, 1200, 350, 0, 0, 1200, 1200, NULL, NULL, X'');

COMMIT;

START TRANSACTION;
USE `uestcojtest`;
INSERT INTO `trainingPlatformInfo` (`trainingPlatformInfoId`, `trainingUserId`, `userName`, `userId`, `type`)
VALUES
	(1, 1, X'5265637461466C6578', X'313233', 0),
	(2, 1, X'6D7A727931393932', X'313233', 1),
	(3, 1, X'6D7A727931393932', X'313233', 2),
	(4, 1, X'55455354435F497A61796F69', X'313233', 3),
	(5, 1, X'55455354435F497A61796F69', X'313233', 4),
	(6, 2, X'676F6E6762616F61', X'313233', 0),
	(7, 3, X'6B656E6E657468736E6F77', X'313233', 0),
	(8, 5, X'6B656E6E657468736E6F77', X'313131', 0);

COMMIT;

