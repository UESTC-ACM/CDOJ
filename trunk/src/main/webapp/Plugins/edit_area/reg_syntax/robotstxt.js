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

editAreaLoader.load_syntax["robotstxt"] = {
    'DISPLAY_NAME':'Robots txt',
    'COMMENT_SINGLE':{1:'#'},
    'COMMENT_MULTI':{},
    'QUOTEMARKS':[],
    'KEYWORD_CASE_SENSITIVE':false,
    'KEYWORDS':{
        'attributes':['User-agent', 'Disallow', 'Allow', 'Crawl-delay'],
        'values':['*'],
        'specials':['*']
    },
    'OPERATORS':[':'],
    'DELIMITERS':[],
    'STYLES':{
        'COMMENTS':'color: #AAAAAA;',
        'QUOTESMARKS':'color: #6381F8;',
        'KEYWORDS':{
            'attributes':'color: #48BDDF;',
            'values':'color: #2B60FF;',
            'specials':'color: #FF0000;'
        },
        'OPERATORS':'color: #FF00FF;',
        'DELIMITERS':'color: #60CA00;'
    }
};
