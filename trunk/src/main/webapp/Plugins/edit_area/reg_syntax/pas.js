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

editAreaLoader.load_syntax["pas"] = {
    'DISPLAY_NAME':'Pascal', 'COMMENT_SINGLE':{}, 'COMMENT_MULTI':{'{':'}', '(*':'*)'}, 'QUOTEMARKS':{1:'"', 2:"'"}, 'KEYWORD_CASE_SENSITIVE':false, 'KEYWORDS':{
        'constants':[
            'Blink', 'Black', 'Blue', 'Green', 'Cyan', 'Red',
            'Magenta', 'Brown', 'LightGray', 'DarkGray',
            'LightBlue', 'LightGreen', 'LightCyan', 'LightRed',
            'LightMagenta', 'Yellow', 'White', 'MaxSIntValue',
            'MaxUIntValue', 'maxint', 'maxLongint', 'maxSmallint',
            'erroraddr', 'errorcode', 'LineEnding'
        ], 'keywords':[
            'in', 'or', 'div', 'mod', 'and', 'shl', 'shr', 'xor',
            'pow', 'is', 'not', 'Absolute', 'And_then', 'Array',
            'Begin', 'Bindable', 'Case', 'Const', 'Do', 'Downto',
            'Else', 'End', 'Export', 'File', 'For', 'Function',
            'Goto', 'If', 'Import', 'Implementation', 'Inherited',
            'Inline', 'Interface', 'Label', 'Module', 'Nil',
            'Object', 'Of', 'Only', 'Operator', 'Or_else',
            'Otherwise', 'Packed', 'Procedure', 'Program',
            'Protected', 'Qualified', 'Record', 'Repeat',
            'Restricted', 'Set', 'Then', 'To', 'Type', 'Unit',
            'Until', 'Uses', 'Value', 'Var', 'Virtual', 'While',
            'With'
        ], 'functions':[
            'Abs', 'Addr', 'Append', 'Arctan', 'Assert', 'Assign',
            'Assigned', 'BinStr', 'Blockread', 'Blockwrite',
            'Break', 'Chdir', 'Chr', 'Close', 'CompareByte',
            'CompareChar', 'CompareDWord', 'CompareWord', 'Concat',
            'Continue', 'Copy', 'Cos', 'CSeg', 'Dec', 'Delete',
            'Dispose', 'DSeg', 'Eof', 'Eoln', 'Erase', 'Exclude',
            'Exit', 'Exp', 'Filepos', 'Filesize', 'FillByte',
            'Fillchar', 'FillDWord', 'Fillword', 'Flush', 'Frac',
            'Freemem', 'Getdir', 'Getmem', 'GetMemoryManager',
            'Halt', 'HexStr', 'Hi', 'High', 'Inc', 'Include',
            'IndexByte', 'IndexChar', 'IndexDWord', 'IndexWord',
            'Insert', 'IsMemoryManagerSet', 'Int', 'IOresult',
            'Length', 'Ln', 'Lo', 'LongJmp', 'Low', 'Lowercase',
            'Mark', 'Maxavail', 'Memavail', 'Mkdir', 'Move',
            'MoveChar0', 'New', 'Odd', 'OctStr', 'Ofs', 'Ord',
            'Paramcount', 'Paramstr', 'Pi', 'Pos', 'Power', 'Pred',
            'Ptr', 'Random', 'Randomize', 'Read', 'Readln',
            'Real2Double', 'Release', 'Rename', 'Reset', 'Rewrite',
            'Rmdir', 'Round', 'Runerror', 'Seek', 'SeekEof',
            'SeekEoln', 'Seg', 'SetMemoryManager', 'SetJmp',
            'SetLength', 'SetString', 'SetTextBuf', 'Sin', 'SizeOf',
            'Sptr', 'Sqr', 'Sqrt', 'SSeg', 'Str', 'StringOfChar',
            'Succ', 'Swap', 'Trunc', 'Truncate', 'Upcase', 'Val',
            'Write', 'WriteLn'
        ], 'types':[
            'Integer', 'Shortint', 'SmallInt', 'Longint',
            'Longword', 'Int64', 'Byte', 'Word', 'Cardinal',
            'QWord', 'Boolean', 'ByteBool', 'LongBool', 'Char',
            'Real', 'Single', 'Double', 'Extended', 'Comp',
            'String', 'ShortString', 'AnsiString', 'PChar'
        ]
    }, 'OPERATORS':[
        '@', '*', '+', '-', '/', '^', ':=', '<', '=', '>'
    ], 'DELIMITERS':[
        '(', ')', '[', ']'
    ], 'STYLES':{
        'COMMENTS':'color: #AAAAAA;', 'QUOTESMARKS':'color: #6381F8;', 'KEYWORDS':{
            'specials':'color: #EE0000;', 'constants':'color: #654321;', 'keywords':'color: #48BDDF;', 'functions':'color: #449922;', 'types':'color: #2B60FF;'
        }, 'OPERATORS':'color: #FF00FF;', 'DELIMITERS':'color: #60CA00;'
    }
};
