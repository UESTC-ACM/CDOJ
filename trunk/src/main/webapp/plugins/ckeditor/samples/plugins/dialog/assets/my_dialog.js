/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */
CKEDITOR.dialog.add("myDialog", function () {
    return{title:"My Dialog", minWidth:400, minHeight:200, contents:[
        {id:"tab1", label:"First Tab", title:"First Tab", elements:[
            {id:"input1", type:"text", label:"Text Field"},
            {id:"select1", type:"select", label:"Select Field", items:[
                ["option1", "value1"],
                ["option2", "value2"]
            ]}
        ]},
        {id:"tab2", label:"Second Tab", title:"Second Tab", elements:[
            {id:"button1", type:"button", label:"Button Field"}
        ]}
    ]}
});