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
  Created by IntelliJ IDEA.
  User: mzry1992
  Date: 13-1-9
  Time: 下午8:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${problemId}</title>
</head>
<body>

        <div class="row">
            <!-- 题目标题 -->
            <div class="span12">
                <h2>Problem 9999. One million ways to make love</h2>
            </div>

            <s:url action="problem/" namespace="/problemset" id="problemUrl"/>
            <!-- 导航链接 -->
            <div class="span12">
                <ul class="nav nav-pills">
                    <li class="active">
                        <a href="#">Problem</a>
                    </li>
                    <li><a href="${problemUrl}${problemId}/submit">Submit</a></li>
                    <li><a href="${problemUrl}${problemId}/status">Status</a></li>
                    <li><a href="${problemUrl}${problemId}/discuss">Discuss</a></li>
                </ul>
            </div>
        </div>

        <div class="row">
            ${problemId}
            <!-- 题目信息 -->
            <div class="span12">
                <dl class="dl-horizontal">
                    <dt>Time limit</dt>
                    <dd><span>2 / 1 second (Java / others)</span></dd>

                    <dt>Memory limit</dt>
                    <dd><span>128 / 64 megabytes (Java / others)</span></dd>

                    <dt>Total accepted</dt>
                    <dd><span>100</span></dd>

                    <dt>Total submissions</dt>
                    <dd><span>1000</span></dd>

                </dl>
            </div>

            <!-- 描述 -->
            <div class="span12">

                <p>
                    \begin{align}
                    \dot{x} & = \sigma(y-x) \\
                    \dot{y} & = \rho x - y - xz \\
                    \dot{z} & = -\beta z + xy
                    \end{align}
                </p>

                <p>\[
                    \left( \sum_{k=1}^n a_k b_k \right)^{\!\!2} \leq
                    \left( \sum_{k=1}^n a_k^2 \right) \left( \sum_{k=1}^n b_k^2 \right)
                    \]</p>

                <p>\[
                    \mathbf{V}_1 \times \mathbf{V}_2 =
                    \begin{vmatrix}
                    \mathbf{i} & \mathbf{j} & \mathbf{k} \\
                    \frac{\partial X}{\partial u} & \frac{\partial Y}{\partial u} & 0 \\
                    \frac{\partial X}{\partial v} & \frac{\partial Y}{\partial v} & 0 \\
                    \end{vmatrix}
                    \]</p>

                <p>\[P(E) = {n \choose k} p^k (1-p)^{ n-k} \]</p>

                <p>\[
                    \frac{1}{(\sqrt{\phi \sqrt{5}}-\phi) e^{\frac25 \pi}} =
                    1+\frac{e^{-2\pi}} {1+\frac{e^{-4\pi}} {1+\frac{e^{-6\pi}}
                    {1+\frac{e^{-8\pi}} {1+\ldots} } } }
                    \]</p>

                <p>\[
                    1 +  \frac{q^2}{(1-q)}+\frac{q^6}{(1-q)(1-q^2)}+\cdots =
                    \prod_{j=0}^{\infty}\frac{1}{(1-q^{5j+2})(1-q^{5j+3})},
                    \quad\quad \text{for $|q|<1$}.
                    \]</p>

                <p>
                    \begin{align}
                    \nabla \times \vec{\mathbf{B}} -\, \frac1c\, \frac{\partial\vec{\mathbf{E}}}{\partial t} & = \frac{4\pi}{c}\vec{\mathbf{j}} \\
                    \nabla \cdot \vec{\mathbf{E}} & = 4 \pi \rho \\
                    \nabla \times \vec{\mathbf{E}}\, +\, \frac1c\, \frac{\partial\vec{\mathbf{B}}}{\partial t} & = \vec{\mathbf{0}} \\
                    \nabla \cdot \vec{\mathbf{B}} & = 0
                    \end{align}
                </p>

                <p>Finally, while display equations look good for a page of samples, the
                    ability to mix math and text in a paragraph is also important.  This
                    expression \(\sqrt{3x-1}+(1+x)^2\) is an example of an inline equation.  As
                    you see, MathJax equations can be used this way as well, without unduly
                    disturbing the spacing between lines.</p>

                <p>
                    There is no true love falling from the sky.So if you want to get into God Luo's heart,you must take action.As we all know,if you don't try to make love,love will never come into being.There are one million ways to make the love between you and God Luo.If you find one of them, your dream about love will come true.
                </p>
                <p>
                    You know, God Luo always changes his necklace.This time, God Luo gets some magical beads and there are 10 kinds of these beads.We use A to denote the first kind of the beads,B to denote the second and so on.
                </p>
                <p>
                    The beads can do some transformation: You can put some beads in a special vessel and whisper a secret spell(Only God Luo knows it),then these beads will become one new bead(Remember,before whispering the spell there are at least two beads ).
                </p>
                <p>
                    God Luo gives the spell book to you, and this book is about something like this:"BC -&gt; D".It indecates a B-Bead and a C-Bead can become a D-Bead(Only if you have the special vessel and know the spell).
                </p>
                <p>
                    After reading the spell book, you found an important fact: The right part can never be the same in any two spells(that is to say,every right part is distinct).
                </p>
                <p>
                    God Luo wants to make a necklace whose components are known(At least one bead). You think it's a chance to make love,so you plan to tell God Luo the maximum of necklace can be made from the beads which he has. Maybe the right answer to that question is one of the way to make love, so it's worth a try,isn't it?
                </p>
                <p>
                    CODE RIGHT NOW! FOR GOD LUO!
                </p>
            </div>

            <!-- Input -->
            <div class="span12">
                <h2>Input</h2>
            </div>
            <div class="span12">
                <p>
                    There is an integer T(T&lt;=100) in the first line, indecating the number of test cases.
                </p>
                <p>
                    The first line of each test case contains 10 integers(no more than 50)indicating the number of each kind of beads which God Luo has.
                </p>
                <p>
                    The second line contains 10 integers(no more than 50) indicating the need of each kind of beads to compose the necklace.
                </p>
                <p>
                    The third line contains an interger n(0&lt;=n&lt;=10) indicating there are n spell in total.
                </p>
                <p>
                    Then n lines follow,and there are two strings in each line.The first string is the left part of the spell, and the second is the right part.
                </p>
                <p>
                </p>
                <p>
                    Notice:
                </p>
                <p>
                    1.The length of second string is always 1.
                </p>
                <p>
                    2.The length of first string is L which satisfies that 2&lt;=L&lt;=10.
                </p>
                <p>
                    3.The right part of each line is distinct while some left parts can be the same.
                </p>
            </div>

            <!-- Output -->
            <div class="span12">
                <h2>Output</h2>
            </div>
            <div class="span12">
                <p>
                    For each test case, output the maximum number of the necklaces can be made from the initial beads.
                </p>
            </div>

            <!-- Sample -->
            <div class="span12">
                <h2>Sample input and output</h2>
            </div>
            <div class="span12">
                <table class="table table-sample table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>Sample Input</th>
                        <th>Sample Output</th>
                    </tr>
                    </thead>
                    <tbody class="font-code">
                    <tr>
                        <td>
                            <p>
                                2<br />
                                2 2 0 0 0 0 0 0 0 0<br />
                                1 1 0 0 0 0 0 0 0 0<br />
                                0<br />
                                2 2 0 0 0 0 0 0 0 0<br />
                                0 0 1 0 0 0 0 0 0 0<br />
                                3<br />
                                AB C<br />
                                AAA A<br />
                                AAB B<br />
                            </p>
                        </td>
                        <td>
                            <p>
                                2<br />
                                2<br />
                            </p>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <!-- Hint -->
            <div class="span12">
                <h2>Hint</h2>
            </div>
            <div class="span12">
                <p>
                    沈老师太厉害了
                </p>
            </div>

            <!-- Source -->
            <div class="span12">
                <h2>Source</h2>
            </div>
            <div class="span12">
                <p>
                    罗神粉丝俱乐部
                </p>
            </div>

        </div>
</body>
</html>