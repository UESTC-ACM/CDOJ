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

editAreaLoader.load_syntax["brainfuck"] = {
    'DISPLAY_NAME':'Brainfuck', 'COMMENT_SINGLE':{}, 'COMMENT_MULTI':{}, 'QUOTEMARKS':{}, 'KEYWORD_CASE_SENSITIVE':true, 'OPERATORS':[
        '+', '-'
    ], 'DELIMITERS':[
        '[', ']'
    ], 'REGEXPS':{
        'bfispis':{
            'search':'()(\\.)()', 'class':'bfispis', 'modifiers':'g', 'execute':'before'
        }, 'bfupis':{
            'search':'()(\\,)()', 'class':'bfupis', 'modifiers':'g', 'execute':'before'
        }, 'bfmemory':{
            'search':'()([<>])()', 'class':'bfmemory', 'modifiers':'g', 'execute':'before'
        }
    }, 'STYLES':{
        'COMMENTS':'color: #AAAAAA;', 'QUOTESMARKS':'color: #6381F8;', 'OPERATORS':'color: #88AA00;', 'DELIMITERS':'color: #00C138;', 'REGEXPS':{
            'bfispis':'color: #EE0000;', 'bfupis':'color: #4455ee;', 'bfmemory':'color: #DD00DD;'
        }
    }
};

