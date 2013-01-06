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

editAreaLoader.load_syntax["cpp"] = {
    'DISPLAY_NAME':'CPP', 'COMMENT_SINGLE':{1:'//'}, 'COMMENT_MULTI':{'/*':'*/'}, 'QUOTEMARKS':{1:"'", 2:'"'}, 'KEYWORD_CASE_SENSITIVE':true, 'KEYWORDS':{
        'constants':[
            'NULL', 'false', 'std', 'stdin', 'stdout', 'stderr',
            'true'
        ], 'types':[
            'FILE', 'auto', 'char', 'class', 'const', 'double',
            'extern', 'float', 'friend', 'inline', 'int',
            'iterator', 'long', 'map', 'operator', 'queue',
            'register', 'short', 'signed', 'size_t', 'stack',
            'static', 'string', 'struct', 'time_t', 'typedef',
            'union', 'unsigned', 'vector', 'void', 'volatile'
        ], 'statements':[
            'catch', 'do', 'else', 'enum', 'for', 'goto', 'if',
            'sizeof', 'switch', 'this', 'throw', 'try', 'while'
        ], 'keywords':[
            'break', 'case', 'continue', 'default', 'delete',
            'namespace', 'new', 'private', 'protected', 'public',
            'return', 'using'
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
