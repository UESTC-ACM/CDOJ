<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="cdoj" uri="/WEB-INF/cdoj.tld" %>
<%--
  ~ /*
  ~  * cdoj, UESTC ACMICPC Online Judge
  ~  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
  ~  * 	mzry1992 <@link muziriyun@gmail.com>
  ~  *
  ~  * This program is free software; you can redistribute it and/or
  ~  * modify it under the terms of the GNU General Public License
  ~  * as published by the Free Software Foundation; either version 2
  ~  * of the License, or (at your option) any later version.
  ~  *
  ~  * This program is distributed in the hope that it will be useful,
  ~  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~  * GNU General Public License for more details.
  ~  *
  ~  * You should have received a copy of the GNU General Public License
  ~  * along with this program; if not, write to the Free Software
  ~  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  ~  */
  --%>

<%--
 Home page

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<s:url value="/scripts/cdoj/cdoj.home.js"/>"></script>
    <title>CDOJ</title>
</head>
<body>

<div id="activityCarousel" class="carousel slide">
    <!-- Carousel items -->
    <div class="carousel-inner">
        <div class="item active">
            <img src="<s:url value="/images/test/test001.png"/>">

            <div class="carousel-caption">
                <h4>趣味赛</h4>

                <p>这里可以写一些小介绍之类的东西。要注意图片大小必须严格是1170*450。做海报的时候可以顺便做了</p>
            </div>
        </div>

        <div class="item">
            <img src="<s:url value="/images/test/test002.png"/>">

            <div class="carousel-caption">
                <h4>四维较量</h4>

                <p>想想数据库和后台怎么维护吧</p>
            </div>
        </div>

    </div>
    <!-- Carousel nav -->
    <a class="carousel-control left" href="#activityCarousel" data-slide="prev">&lsaquo;</a>
    <a class="carousel-control right" href="#activityCarousel" data-slide="next">&rsaquo;</a>
</div>

<div class="row">
    <div class="span12">
        <div class="hero-unit">
            <h2>UESTC Online Judge</h2>

            <div>
                <ul id="tab" class="nav nav-pills">
                    <li class="active"><a href="#tab-todo-list" data-toggle="tab">TODO</a></li>
                    <li class=""><a href="#tab-test" data-toggle="tab">测试</a></li>
                    <li class=""><a href="#tab-test-prettify" data-toggle="tab">代码高亮</a></li>
                    <li class=""><a href="#tab-test-mathjax" data-toggle="tab">公式测试</a></li>
                </ul>
                <div id="TabContent" class="tab-content">
                    <div class="tab-pane fade active in" id="tab-todo-list">
                        <p>
                            现在缺的东西：
                            <br/>
                            网站背景可以有。
                            <br/>
                            logo和favicon和banner都是随手乱做的。。求大神做个好看的。。
                            <br/>
                            页面的最小宽度需要仔细考虑，现在暂时设置为940px
                            <br/>
                            主页slide展示的图片为1170*450，或则和其宽高比例一样的图片！最小宽度不能小于1028！（这个只是暂时的规定，后面可以修改，但是一定要保证图片的宽高比例都一样，不然效果会很呵呵的）。
                            <br/>
                            favicon的大小是128*128的png，当然，其它尺寸也可以备几个。
                            banner的大小是234*60。
                            logo的大小是88*31
                            <br/>
                            公式的话现在已经能正常使用了。
                            <br/>
                            平时的话用CDN的mathjax.js吧，内网的时候再到本地架。
                            <br/>
                            哦，对文档需要转义么？公式里面可能用到大于小于号呢。不过代码是必须要转的了！可以看看CF是怎么处理的！
                        </p>
                    </div>
                    <div class="tab-pane fade" id="tab-test">
                        <p>
                            这里可以用作介绍。
                            <br/>
                            小朋友们大家好，还记得我是谁吗？
                            <br/>
                            对了，我就是葛炮！
                        </p>

                        <p><a class="btn btn-primary btn-large" href="#">More »</a></p>
                    </div>
                    <div class="tab-pane fade" id="tab-test-prettify">
                        <p>注意，后台需要对其先转义，然后注意&lt;/pre&gt;的位置</p>

                        <p>TODO:代码全选功能</p>
                        <h4>C</h4>
										<pre class="prettyprint linenums">
#include &lt;stdio.h&gt;
int main()
{
	int a, b;
	scanf("%d %d",&a, &b);
	printf("%d", a+b);
	return 0;
}</pre>
                        <h4>C++</h4>
										<pre class="prettyprint linenums">
#include  &lt;iostream&gt;
using namespace std;
int main()
{
	int a,b;
	cin &gt;&gt; a &gt;&gt; b;
	cout &lt;&lt; a+b &lt;&lt; endl;
	return 0;
}</pre>
                        <h4>java</h4>
										<pre class="prettyprint linenums"><code>import java.io.*;
import java.util.*;
public class Main
{
	public static void main(String[] args) throws Exception
	{
		Scanner cin=new Scanner(System.in);
		int a=cin.nextInt(), b=cin.nextInt();
		System.out.println (a + b);
	}
}</code></pre>
                    </div>

                    <div class="tab-pane fade" id="tab-test-mathjax">
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<div class="row">
    <div class="span8">
        <h2>Contests
            <!-- TODO p tag in h2 -->
            <p class="pull-right"><a class="btn btn-primary" href="./contest.html">More »</a></p>
        </h2>

        <table class="table table-striped table-bordered">
            <tbody>
            <tr>
                <td>2012英才院赛</td>
                <td class="info-time">2012-12-18 20:00:00</td>
                <td class="info-contest-state"><span class="label label-info">pending</span></td>
            </tr>
            <tr>
                <td>微固学院&计算机学院兴趣小组每周一练（2）</td>
                <td class="info-time">2012-12-18 20:00:00</td>
                <td class="info-contest-state"><span class="label label-important">running</span></td>
            </tr>
            <tr>
                <td>第四届ACM趣味程序设计竞赛第二场（热身赛，陈题）</td>
                <td class="info-time">2012-12-18 20:00:00</td>
                <td class="info-contest-state"><span class="label label-success">ended</span></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="span4">
        <h2>Problems
            <!-- TODO p tag in h2 -->
            <p class="pull-right"><a class="btn btn-primary" href="<s:url action="page/1" namespace="/problemset"/>">More
                »</a></p>
        </h2>

        <table class="table table-striped table-bordered">
            <tbody>
            <tr>
                <td>A+B Problem</td>
                <td class="info-problem-difficulty"><i class="icon-star"></i><i class="icon-star"></i><i
                        class="icon-star"></i><i class="icon-star"></i><i class="icon-star"></i></td>
            </tr>
            <tr>
                <td>扫雷</td>
                <td class="info-problem-difficulty"><i class="icon-star"></i><i class="icon-star"></i><i
                        class="icon-star"></i><i class="icon-star-empty"></i></td>
            </tr>
            <tr>
                <td>One million ways to make love</td>
                <td class="info-problem-difficulty"><i class="icon-star"></i><i class="icon-star"></i><i
                        class="icon-star"></i><i class="icon-star-empty"></i></td>
            </tr>
            </tbody>
        </table>

        <!-- 快速跳转题目 -->
        <form class="form-horizontal">
            <!-- TODO idea warning -->
            <input type="text" class="span3" placeholder="Problem ID...">
            <button type="submit" class="btn btn-primary">GO!</button>
        </form>

    </div>

</div>

</body>
</html>