CSV into Joomla K2 SQL and Files
==========

CSV Articles format:
� ������ � ���������|24 ���� 2012 ���� � ��������� ���������� �1 (�������������,13) � ������ ��������� �� ������ ��� ������� ������ ������������ ����� �� ������ � ��������� �� ������� �������������� ������ �� ��������� ������  ����� �37. � ���� ������  ������ ������������� � ������������ �� ���������� ��������� �������� ������������ �. ��������, �������� �� ������� ��������� � ������� �� ����. ������������ ���� ����� ���������� ������� ���������� ������� ������ ���� �������� ������������. � ��������� ����  ���������� ��������� ������� �������� �� ������ � ������� ������.|<h2><span style="font-family: &quot;times new roman&quot;, &quot;times&quot;; font-size: medium;">� ������ � ���������</span></h2><p><span style="font-family: &quot;times new roman&quot;, &quot;times&quot;; font-size: medium;">24 ���� 2012 ���� � ��������� ���������� �1 (�������������,13) � ������ ��������� &laquo;� ������ ��� �������&raquo; ������ ������������ ����� &laquo;� ������ � ���������&raquo; �� ������� �������������� ������ �� ��������� ������&nbsp; ����� �37. � ���� ������&nbsp; ������ ������������� � ������������ �� ���������� ��������� �������� ������������ �. ��������, �������� �� ������� ��������� � ������� �� ����. ������������ ���� ����� ���������� ������� ���������� ������� ������ ���� �������� ������������. � ��������� ���� &nbsp;���������� ��������� ������� �������� &laquo;� ������ � ������� ������&raquo;.</span></p><p>&nbsp;</p><p><img src="{CCM:FID_352}" alt="P1050310.JPG" width="400" height="300" /><img src="{CCM:FID_353}" alt="P1050317.JPG" width="400" height="300" /></p><p><span><br /></span></p>

SQL:
SELECT ci.cvName,ci.cvDescription,btc.content FROM CollectionVersionBlocks cvb RIGHT JOIN CollectionVersions ci ON ci.cID=cvb.cID RIGHT JOIN btContentLocal btc ON btc.bID=cvb.bID LEFT JOIN Pages pgs ON cvb.cID=pgs.cID WHERE pgs.cParentID=90 GROUP BY btc.content

CSV Files format:
"1";"1";"inneroptics_dot_net_aspens.jpg";"371285369023";"1";"108199";"inneroptics_dot_net_aspens.jpg";;;"1";"2012-03-16 00:02:24";"1";"1";"2012-03-16 00:02:24";"1";"1";"0";"jpg";"1"

