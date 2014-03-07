---
layout: default
title: Home
---
### Welcome to CDOJ Home page.

This is a online judge system for `ACM/ICPC`, and it currently runs at [UESTC ACM Online Judge](http://acm.uestc.edu.cn/).

### How to run the project in your own computer?

First of all, you need a `i686 Linux` and install `openjdk7`, `maven` and `mysql` in your PC.

Then you can fetch sources from our repository, and run the commond below.

    # Go to main directory of project.
    cd path/to/the/sources
    cd trunk
    
    # Set up database
    # Note: please set 'root'(without quotes) as root password of mysql.
    mysql -uroot -p < src/main/sql/uestcoj.sql
    # Then you need patch all patch sql files.
    mysql -uroot -p < src/main/sql/patches/<id>.sql
    
    # Test project
    mvn test
    
    # Run jetty server
    mvn jetty:run

After that, you can visit [localhost:8080](http://localhost:8080/) to get running demo.

### How to help to enhance our project?

#### Version control system

We use `git` to do version control. For **ALL** developers, the first thing you should do is **forking** this project, and you can get the backup of this project in your own project list. Then you should set the upstream url for you own project:

    git remote add upstream https://github.com/UESTC-ACM/CDOJ.git
    git pull upstream master

When you need push your commits, please use `Pull Request` to do that, and we will review the changes.

#### Workflow overview

`Set upstream` -> `Pull newest code from upstream` -> `Modify code` -> `Pull newest code from upstream` -> `Pull request to merge your code to master branch` -> `Admin accepted`.

Before you send out a pull request, please use `git pull upstream master` sync your workspace.

NOTE: **DON'T** work on `master` branch, please checkout a new branch with a name that can simply describe your working purpose for your working.

#### Project tools

##### Pre commit tool

Please use `pre-commit.sh` before you send out a pull request.

    ./pre-commit.sh

##### Plugin install tool
Please use `install-plugin.sh` **after merge from upstream**.

    ./install-plugin.sh

##### DTO builder

###### Tool path

 `/branches/tools/gen_dto.py`

###### Usage

User command line:

    ./gen_dto.py --entity=<entity name> --package=<package name> --class=<class name> \
    --fields=<fields> --types=<types>

Or you can also use this command line:

    ./gen_dto.py -e <entity name> -p <package name> -c <class name> -f <fields> -t <types>

###### Example

    ./gen_dto.py -e User -p user -c UserDTO -f userId,userName -t Integer,String

###### Be careful

the `types` and `fields` are joined by `,` and there are no white spaces in `types` and `fields`.

### Authors and Contributors

Project owners: Ruins He(@lyhypacm), Jianjin Fan(@pfctgeorge) and Yun Li(@mzry1992).

Other contributors: fpy(@iv404).

