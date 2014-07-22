# CREATE TABLESPACE BELTPP
# DATAFILE 'beltpp.dbf' SIZE 5M
# AUTOEXTEND ON;

CREATE USER  BELTPP
    IDENTIFIED BY 123456
    DEFAULT TABLESPACE Beltpp;
    
GRANT CREATE SESSION TO BELTPP;

GRANT CREATE TABLE TO BELTPP;
GRANT CREATE PROCEDURE TO BELTPP;
GRANT CREATE TRIGGER TO BELTPP;
GRANT CREATE VIEW TO BELTPP;
GRANT CREATE SEQUENCE TO BELTPP;
GRANT ALTER ANY TABLE TO BELTPP;
GRANT ALTER ANY PROCEDURE TO BELTPP;
GRANT ALTER ANY TRIGGER TO BELTPP;
GRANT ALTER PROFILE TO BELTPP
;
GRANT DELETE ANY TABLE TO BELTPP;  
GRANT DROP ANY TABLE TO BELTPP;
GRANT DROP ANY PROCEDURE TO BELTPP;
GRANT DROP ANY TRIGGER TO BELTPP;
GRANT DROP ANY VIEW TO BELTPP;
GRANT DROP PROFILE TO BELTPP;

GRANT ALTER TABLESPACE TO BELTPP:
GRANT MANAGE TABLESPACE	TO BELTPP:
GRANT UNLIMITED TABLESPACE TO BELTPP:


ALTER TABLE BELTPP.C_COUNTRY
 DROP PRIMARY KEY CASCADE;
 
DROP TABLE BELTPP.C_COUNTRY CASCADE CONSTRAINTS;

CREATE TABLE BELTPP.C_COUNTRY
(
  COUNTRY_ID    VARCHAR2(3 BYTE),
  COUNTRY_NAME  VARCHAR2(255 BYTE)
)
TABLESPACE  BELTPP
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

ALTER TABLE BELTPP.C_COUNTRY ADD (
  PRIMARY KEY
 (COUNTRY_ID)
    USING INDEX 
    TABLESPACE BELTPP
    PCTFREE    10
    INITRANS   2
    MAXTRANS   255
    STORAGE    (
                INITIAL          64K
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       UNLIMITED
                PCTINCREASE      0
                FREELISTS        1
                FREELIST GROUPS  1
               ));


ALTER TABLE BELTPP.C_OTD
 DROP PRIMARY KEY CASCADE;

DROP TABLE BELTPP.C_OTD CASCADE CONSTRAINTS;

CREATE TABLE C_OTD
(
  OTD_ID             NUMBER,
  OTD_NAME           VARCHAR2(255 BYTE),
  OTD_NAME_SYN       VARCHAR2(100 BYTE),
  OTD_ADDRESS_INDEX  VARCHAR2(15 BYTE),
  OTD_ADDRESS_CITY   VARCHAR2(15 BYTE),
  OTD_ADDRESS_LINE   VARCHAR2(50 BYTE),
  OTD_ADDRESS_HOME   VARCHAR2(10 BYTE),
  OTD_ADDRESS_EXP    DATE
)
TABLESPACE  BELTPP
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


ALTER TABLE BELTPP.C_OTD ADD (
  PRIMARY KEY
 (OTD_ID)
    USING INDEX 
    TABLESPACE BELTPP
    PCTFREE    10
    INITRANS   2
    MAXTRANS   255
    STORAGE    (
                INITIAL          64K
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       UNLIMITED
                PCTINCREASE      0
                FREELISTS        1
                FREELIST GROUPS  1
               ));
               



ALTER TABLE BELTPP.C_CERT
 DROP PRIMARY KEY CASCADE;
 
ALTER TABLE BELTPP.C_CERT
 DROP FOREIGN KEY CASCADE;

 
DROP TABLE BELTPP.C_CERT CASCADE CONSTRAINTS;

CREATE TABLE BELTPP.C_CERT
(
  CERT_ID     NUMBER,
  FORMS       VARCHAR2(1000 BYTE),
  UNN         VARCHAR2(1000 BYTE),
  KONTRP      VARCHAR2(1000 BYTE),
  KONTRS      VARCHAR2(1000 BYTE),
  ADRESS      VARCHAR2(1000 BYTE),
  POLUCHAT    VARCHAR2(1000 BYTE),
  ADRESSPOL   VARCHAR2(1000 BYTE),
  DATACERT    VARCHAR2(1000 BYTE),
  NOMERCERT   VARCHAR2(1000 BYTE),
  EXPERT      VARCHAR2(1000 BYTE),
  NBLANKA     VARCHAR2(1000 BYTE),
  RUKOVOD     VARCHAR2(1000 BYTE),
  TRANSPORT   VARCHAR2(1000 BYTE),
  MARSHRUT    VARCHAR2(1000 BYTE),
  OTMETKA     VARCHAR2(1000 BYTE),
  STRANAV     VARCHAR2(1000 BYTE),
  STRANAPR    VARCHAR2(1000 BYTE),
  STATUS      VARCHAR2(1000 BYTE),
  KOLDOPLIST  VARCHAR2(1000 BYTE),
  FLEXP       VARCHAR2(1000 BYTE),
  UNNEXP      VARCHAR2(1000 BYTE),
  EXPP        VARCHAR2(1000 BYTE),
  EXPS        VARCHAR2(1000 BYTE),
  EXPADRESS   VARCHAR2(1000 BYTE),
  FLIMP       VARCHAR2(1000 BYTE),
  IMPORTER    VARCHAR2(1000 BYTE),
  ADRESSIMP   VARCHAR2(1000 BYTE),
  FLSEZ       VARCHAR2(1000 BYTE),
  SEZ         VARCHAR2(1000 BYTE),
  FLSEZREZ    VARCHAR2(1000 BYTE),
  STRANAP     VARCHAR2(1000 BYTE)
)
TABLESPACE  BELTPP
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

ALTER TABLE BELTPP.C_CERT ADD (
  PRIMARY KEY
 (CERT_ID)
    USING INDEX 
    TABLESPACE BELTPP
    PCTFREE    10
    INITRANS   2
    MAXTRANS   255
    STORAGE    (
                INITIAL          64K
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       UNLIMITED
                PCTINCREASE      0
                FREELISTS        1
                FREELIST GROUPS  1
               ));

ALTER TABLE C_CERT 
   ADD OTD_ID number ;
 
ALTER TABLE C_CERT
    ADD  FOREIGN KEY
    (otd_id)
    REFERENCES C_OTD (otd_id);


ALTER TABLE C_CERT ADD ( 
   parentnumber varchar2(50 byte),
   parentstatus varchar2(80 byte)
   );
   
               
               
DROP TABLE BELTPP.C_FILES_IN CASCADE CONSTRAINTS;

CREATE TABLE BELTPP.C_FILES_IN
(
  FILE_IN_ID    NUMBER,
  FILE_IN_NAME  VARCHAR2(255 BYTE),
  CERT_ID       NUMBER,
  DATE_LOAD     DATE
)
TABLESPACE  BELTPP
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


DROP TABLE BELTPP.C_FILES_OUT CASCADE CONSTRAINTS;

CREATE TABLE BELTPP.C_FILES_OUT
(
  FILE_IN_ID    NUMBER,
  FILE_IN_NAME  VARCHAR2(255 BYTE),
  CERT_ID       NUMBER,
  DATE_LOAD     DATE
)
TABLESPACE  BELTPP
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;



ALTER TABLE BELTPP.C_PRODUCT
 DROP PRIMARY KEY CASCADE;

DROP TABLE BELTPP.C_PRODUCT CASCADE CONSTRAINTS;

CREATE TABLE BELTPP.C_PRODUCT
(
  PRODUCT_ID  NUMBER,
  CERT_ID     NUMBER,
  NUMERATOR   VARCHAR2(1000 BYTE),
  TOVAR       VARCHAR2(2500 BYTE),
  VIDUP       VARCHAR2(1000 BYTE),
  KRITER      VARCHAR2(1000 BYTE),
  VES         VARCHAR2(1000 BYTE),
  SCHET       VARCHAR2(1000 BYTE)
)
TABLESPACE  BELTPP
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


ALTER TABLE BELTPP.C_PRODUCT ADD (
  PRIMARY KEY
 (PRODUCT_ID)
    USING INDEX 
    TABLESPACE BELTPP
    PCTFREE    10
    INITRANS   2
    MAXTRANS   255
    STORAGE    (
                INITIAL          64K
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       UNLIMITED
                PCTINCREASE      0
                FREELISTS        1
                FREELIST GROUPS  1
               ));
               
ALTER TABLE BELTPP.C_PRODUCT
    ADD  FOREIGN KEY
    (CERT_ID)
    REFERENCES C_CERT(CERT_ID);
    

DROP VIEW CERT_CHILD_VIEW;

CREATE view cert_child_view as
  select tt.*, bb.nomercert chldnumber, bb.CERT_ID child_id from c_cert tt left join c_cert bb on tt.nomercert = bb.parentnumber;
    
    
DROP VIEW  CERT_VIEW;   
    
CREATE VIEW CERT_VIEW AS 
  select a.*, B.OTD_NAME, B.OTD_ADDRESS_INDEX, B.OTD_ADDRESS_CITY, B.OTD_ADDRESS_LINE, B.OTD_ADDRESS_HOME 
  from CERT_CHILD_VIEW a left join C_OTD b on A.OTD_ID = B.OTD_ID;

 
delete from beltpp.c_otd; 
INSERT INTO "BELTPP"."C_OTD" (OTD_ID, OTD_NAME, OTD_NAME_SYN) VALUES ('1', '������� ��������� ������', 'minsk');
INSERT INTO "BELTPP"."C_OTD" (OTD_ID, OTD_NAME, OTD_NAME_SYN) VALUES ('2', '�������� ��������� ������', 'brest');
INSERT INTO "BELTPP"."C_OTD" (OTD_ID, OTD_NAME, OTD_NAME_SYN) VALUES ('3', '��������� ��������� ������', 'vitebsk');
INSERT INTO "BELTPP"."C_OTD" (OTD_ID, OTD_NAME, OTD_NAME_SYN) VALUES ('4', '���������� ��������� ������', 'gomel');
INSERT INTO "BELTPP"."C_OTD" (OTD_ID, OTD_NAME, OTD_NAME_SYN) VALUES ('6', '����������� ��������� ������', 'mogilev');


DROP INDEX CERT_PARENTNUMBER_IDX;
  CREATE INDEX CERT_PARENTNUMBER_IDX ON C_CERT (PARENTNUMBER ASC);

DROP INDEX CERT_NUMBER_IDX;
  CREATE UNIQUE INDEX CERT_NUMBER_IDX ON C_CERT (NOMERCERT ASC);

  
  