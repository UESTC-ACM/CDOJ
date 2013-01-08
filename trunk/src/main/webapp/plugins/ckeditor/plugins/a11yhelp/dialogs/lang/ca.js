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
CKEDITOR.plugins.setLang("a11yhelp", "ca", {title:"Instruccions d'Accessibilitat", contents:"Continguts de l'Ajuda. Per tancar aquest quadre de diàleg premi ESC.", legend:[
    {name:"General", items:[
        {name:"Editor de barra d'eines", legend:"Press ${toolbarFocus} to navigate to the toolbar. Move to the next and previous toolbar group with TAB and SHIFT-TAB. Move to the next and previous toolbar button with RIGHT ARROW or LEFT ARROW. Press SPACE or ENTER to activate the toolbar button."},
        {name:"Editor de quadre de diàleg",
            legend:"Inside a dialog, press TAB to navigate to next dialog field, press SHIFT + TAB to move to previous field, press ENTER to submit dialog, press ESC to cancel dialog. For dialogs that have multiple tab pages, press ALT + F10 to navigate to tab-list. Then move to next tab with TAB OR RIGTH ARROW. Move to previous tab with SHIFT + TAB or LEFT ARROW. Press SPACE or ENTER to select the tab page."},
        {name:"Editor de menú contextual", legend:"Press ${contextMenu} or APPLICATION KEY to open context-menu. Then move to next menu option with TAB or DOWN ARROW. Move to previous option with SHIFT+TAB or UP ARROW. Press SPACE or ENTER to select the menu option. Open sub-menu of current option with SPACE or ENTER or RIGHT ARROW. Go back to parent menu item with ESC or LEFT ARROW. Close context menu with ESC."},
        {name:"Editor de caixa de llista", legend:"Inside a list-box, move to next list item with TAB OR DOWN ARROW. Move to previous list item with SHIFT + TAB or UP ARROW. Press SPACE or ENTER to select the list option. Press ESC to close the list-box."},
        {name:"Editor de barra de ruta de l'element", legend:"Press ${elementsPathFocus} to navigate to the elements path bar. Move to next element button with TAB or RIGHT ARROW. Move to previous button with  SHIFT+TAB or LEFT ARROW. Press SPACE or ENTER to select the element in editor."}
    ]},
    {name:"Ordres", items:[
        {name:"Desfer ordre", legend:"Premi ${undo}"},
        {name:"Refer ordre", legend:"Premi ${redo}"},
        {name:"Ordre negreta", legend:"Prem ${bold}"},
        {name:"Ordre cursiva", legend:"Prem ${italic}"},
        {name:"Ordre subratllat", legend:"Prem ${underline}"},
        {name:"Ordre enllaç", legend:"Prem ${link}"},
        {name:"Ordre amagar barra d'eines", legend:"Prem ${toolbarCollapse}"},
        {name:"Ajuda d'accessibilitat", legend:"Prem ${a11yHelp}"}
    ]}
]});