--------------------------------------------------------
--  DDL for View CERT_CHILD_VIEW
--------------------------------------------------------

  CREATE OR REPLACE FORCE VIEW "CERT_CHILD_VIEW" ("CERT_ID", "FORMS", "UNN", "KONTRP", "KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT", "ISSUEDATE", "NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD", "TRANSPORT", "MARSHRUT", "OTMETKA", "STRANAV", "STRANAPR", "STATUS", "KOLDOPLIST", "FLEXP", "UNNEXP", "EXPP", "EXPS", "EXPADRESS", "FLIMP", "IMPORTER", "ADRESSIMP", "FLSEZ", "SEZ", "FLSEZREZ", "STRANAP", "OTD_ID", "PARENTNUMBER", "PARENTSTATUS", "DENORM", "CHLDNUMBER", "CHILD_ID") AS 
  select tt."CERT_ID",tt."FORMS",tt."UNN",tt."KONTRP",tt."KONTRS",tt."ADRESS",tt."POLUCHAT",tt."ADRESSPOL",tt."DATACERT", tt."ISSUEDATE", tt."NOMERCERT",tt."EXPERT",tt."NBLANKA",tt."RUKOVOD",tt."TRANSPORT",tt."MARSHRUT",tt."OTMETKA",tt."STRANAV",tt."STRANAPR",tt."STATUS",tt."KOLDOPLIST",tt."FLEXP",tt."UNNEXP",tt."EXPP",tt."EXPS",tt."EXPADRESS",tt."FLIMP",tt."IMPORTER",tt."ADRESSIMP",tt."FLSEZ",tt."SEZ",tt."FLSEZREZ",tt."STRANAP",tt."OTD_ID",tt."PARENTNUMBER",tt."PARENTSTATUS", tt.denorm, bb.nomercert chldnumber, bb.CERT_ID child_id from c_cert tt left join c_cert bb on tt.nomercert = bb.parentnumber;
