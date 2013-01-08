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

editAreaLoader.load_syntax["c"] = {
    'DISPLAY_NAME':'C', 'COMMENT_SINGLE':{1:'//'}, 'COMMENT_MULTI':{'/*':'*/'}, 'QUOTEMARKS':{1:"'", 2:'"'}, 'KEYWORD_CASE_SENSITIVE':true, 'KEYWORDS':{
        'constants':[
            'NULL', 'false', 'stdin', 'stdout', 'stderr', 'true'
        ], 'types':[
            'FILE', 'auto', 'char', 'const', 'double',
            'extern', 'float', 'inline', 'int', 'long', 'register',
            'short', 'signed', 'size_t', 'static', 'struct',
            'time_t', 'typedef', 'union', 'unsigned', 'void',
            'volatile'
        ], 'statements':[
            'do', 'else', 'enum', 'for', 'goto', 'if', 'sizeof',
            'switch', 'while'
        ], 'keywords':[
            'break', 'case', 'continue', 'default', 'delete',
            'return'
        ]
    }, 'OPERATORS':[
        '+', '-', '/', '*', '=', '<', '>', '%', '!', '?', ':', '&'
    ], 'DELIMITERS':[
        '(', ')', '[', ']', '{', '}'
    ], 'REGEXPS':{
        'precompiler':{
            'search':'()(#[^\r\n]*)()', 'class':'precompiler', 'modifiers':'g', 'execute':'before'
        }
        /*		,'precompilerstring' : {
         'search' : '(#[\t ]*include[\t ]*)([^\r\n]*)([^\r\n]*[\r\n])'
         ,'class' : 'precompilerstring'
         ,'modifiers' : 'g'
         ,'execute' : 'before'
         }*/
    }, 'STYLES':{
        'COMMENTS':'color: #AAAAAA;', 'QUOTESMARKS':'color: #6381F8;', 'KEYWORDS':{
            'constants':'color: #EE0000;', 'types':'color: #0000EE;', 'statements':'color: #60CA00;', 'keywords':'color: #48BDDF;'
        }, 'OPERATORS':'color: #FF00FF;', 'DELIMITERS':'color: #0038E1;', 'REGEXPS':{
            'precompiler':'color: #009900;', 'precompilerstring':'color: #994400;'
        }
    }
};
