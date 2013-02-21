CSV into Joomla K2 SQL and Files
==========

CSV Articles format:
```csv
В гостях у классиков|24 июля 2012 года в городской библиотеке №1 (Кронштадтская,13) в рамках программы «У книжек нет каникул» прошел литературный вечер «В гостях у классиков» на котором присутствовали ребята из школьного лагеря  школы №37. В ходе вечера  ребята познакомились с презентацией по творчеству известной шведской писательницы А. Линдгрен, ответили на вопросы связанные с героями ее книг. Библиотекарь дала детям творческое задание нарисовать любимых героев книг шведской писательницы. В досуговом зале  библиотеки оформлена книжная выставка «В гостях у любимых героев».|<h2><span style="font-family: &quot;times new roman&quot;, &quot;times&quot;; font-size: medium;">В гостях у классиков</span></h2><p><span style="font-family: &quot;times new roman&quot;, &quot;times&quot;; font-size: medium;">24 июля 2012 года в городской библиотеке №1 (Кронштадтская,13) в рамках программы &laquo;У книжек нет каникул&raquo; прошел литературный вечер &laquo;В гостях у классиков&raquo; на котором присутствовали ребята из школьного лагеря&nbsp; школы №37. В ходе вечера&nbsp; ребята познакомились с презентацией по творчеству известной шведской писательницы А. Линдгрен, ответили на вопросы связанные с героями ее книг. Библиотекарь дала детям творческое задание нарисовать любимых героев книг шведской писательницы. В досуговом зале &nbsp;библиотеки оформлена книжная выставка &laquo;В гостях у любимых героев&raquo;.</span></p><p>&nbsp;</p><p><img src="{CCM:FID_352}" alt="P1050310.JPG" width="400" height="300" /><img src="{CCM:FID_353}" alt="P1050317.JPG" width="400" height="300" /></p><p><span><br /></span></p>
```
SQL:
```sql
SELECT ci.cvName,ci.cvDescription,btc.content FROM CollectionVersionBlocks cvb RIGHT JOIN CollectionVersions ci ON ci.cID=cvb.cID RIGHT JOIN btContentLocal btc ON btc.bID=cvb.bID LEFT JOIN Pages pgs ON cvb.cID=pgs.cID WHERE pgs.cParentID=90 GROUP BY btc.content
```

CSV Files format:
```csv
"1";"1";"inneroptics_dot_net_aspens.jpg";"371285369023";"1";"108199";"inneroptics_dot_net_aspens.jpg";;;"1";"2012-03-16 00:02:24";"1";"1";"2012-03-16 00:02:24";"1";"1";"0";"jpg";"1"
```
