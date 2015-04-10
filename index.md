---
layout: default
title: Home
---
### Welcome to CDOJ Home page.

This is a online judge system for `ACM/ICPC`, and it currently runs at [UESTC ACM Online Judge](http://acm.uestc.edu.cn/).

### How to run the project in your own computer?

First of all, you need a `i686 Linux` and install `jdk8`, `gradle 2.2+` and `mysql5.5+` in your PC.

Then you can fetch sources from our repository, and run the commond below.

{% highlight bash %}
cd path/to/the/sources
    
# Build judge core
cd branches/oldJudge
make
    
# Go to main directory of project.
cd ../../trunk
    
# Set up database
# Note: please change the user name if needed
mysql -uroot -p < src/main/sql/uestcoj.sql
{% endhighlight %}
    
Edit `gradle.properties`

{% highlight properties %}
dbUser = your_database_user_name
dbPassword = your_database_password

# Static web resource path
staticResourcePath = static/ 

# Image directory location
imagesPath = static/images/  

# Problem data path
dataPath = data/data/          

# Path to save temporary files upload from client  
uploadPath = data/upload/    

# Judge core working path
judgeWorkPath = work/       
{% endhighlight %}

Build project
    
{% highlight bash %}
# Note: You can setup database user name and password in command line
gradle -PdbUser=user -PdbPassword=password build
{% endhighlight %}
    
Run jetty server

{% highlight bash %}
gradle jettyRun
{% endhighlight %}

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

NOTE: **DON''T** work on `master` branch, please checkout a new branch with a name that can simply describe your working purpose for your working.

#### Project tools

##### DTO builder

###### Tool path

 `/branches/tools/gen_dto.py`

###### Usage

User command line:

{% highlight bash %}
./gen_dto.py --entity=<entity name> --package=<package name> --class=<class name> \
--fields=<fields> --types=<types>
{% endhighlight %}

Or you can also use this command line:

{% highlight bash %}
./gen_dto.py -e <entity name> -p <package name> -c <class name> -f <fields> -t <types>
{% endhighlight %}

###### Example

{% highlight bash %}
./gen_dto.py -e User -p user -c UserDTO -f userId,userName -t Integer,String
{% endhighlight %}

###### Be careful

the `types` and `fields` are joined by `,` and there are no white spaces in `types` and `fields`.

### Authors and Contributors

Project owners: Ruins He(@lyhypacm) and Yun Li(@mzry1992).

