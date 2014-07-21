# ����� ��������� ��������� �� ������ ������������ ������ 
select tt.nomercert, bb.nomercert chld from c_cert tt left join c_cert bb on tt.nomercert = bb.parentnumber;

# �������
select tt.nomercert, bb.nomercert chld from c_cert tt join c_cert bb on tt.nomercert = bb.parentnumber ;

select tt.cert_id, tt.nomercert, bb.nomercert chld, bb.CERT_ID child_id from c_cert tt join c_cert bb on tt.nomercert = bb.parentnumber ;

select tt.*, bb.nomercert chldnumber, bb.CERT_ID child_id from c_cert tt join c_cert bb on tt.nomercert = bb.parentnumber;


# �������� 20 ������� ������� � 80 � �� �������� �� child

SELECT cert.*  FROM (SELECT t.*, ROW_NUMBER() OVER (ORDER BY t.NOMERCERT) rw 
FROM cert_child_view t) cert  
WHERE cert.rw > 80 AND cert.rw <= 100;


# �������� ����������� � ���������
SELECT cert.*  FROM (SELECT t.*, ROW_NUMBER() OVER (ORDER BY t.NOMERCERT) rw 
FROM cert_view t where t.child_id is not null) cert  
WHERE cert.rw >= 1 AND cert.rw <= 100;


select cert_id, nomercert, chldnumber, child_id from CERT_CHILD_VIEW where chldnumber IS NOT null;