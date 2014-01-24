CREATE TABLE AC_ACD
(
	COID                 VARCHAR(4) NOT NULL ,
	ACCD                 VARCHAR(10) NOT NULL ,
	HACCD                VARCHAR(10) NULL ,
	ACNM                 VARCHAR(100) NOT NULL ,
	DCDV                 VARCHAR(1) NOT NULL ,
	WRTYN                VARCHAR(1) NOT NULL ,
	CONYN                VARCHAR(1) NOT NULL ,
	USEYN                VARCHAR(1) DEFAULT  'Y'  NOT NULL ,
	INSDT                DATETIME DEFAULT  SYSDATE  NULL ,
	INSID                VARCHAR(18) NULL ,
	UPDDT                DATETIME DEFAULT  SYSDATE  NULL ,
	UPDID                VARCHAR(18) NULL ,
CONSTRAINT  XPKAC_ACD PRIMARY KEY (COID,ACCD)
);

CREATE TABLE AC_SLDPT
(
	COID                 VARCHAR(4) NOT NULL ,
	DPTCD                VARCHAR(6) NOT NULL ,
	WRTYN                VARCHAR(1) NULL ,
	OPRT_DT              DATE NULL ,
	OPRT_ID              VARCHAR(18) NULL ,
CONSTRAINT  XPKAC_SLDPT PRIMARY KEY (COID,DPTCD)
);

CREATE TABLE CO_CD
(
	COID                 VARCHAR(4) NOT NULL ,
	CDID                 VARCHAR(10) NOT NULL ,
	CD                   VARCHAR(10) NOT NULL ,
	HCD                  VARCHAR(10) NULL ,
	SEQ                  NUMERIC(4) NULL ,
	CDNM                 VARCHAR(50) NOT NULL ,
	MGTITM1              VARCHAR(50) NULL ,
	MGTITM2              VARCHAR(50) NULL ,
	MGTITM3              VARCHAR(50) NULL ,
	MGTITM4              VARCHAR(50) NULL ,
	MGTITM5              VARCHAR(50) NULL ,
	USEYN                VARCHAR(1) DEFAULT  'Y'  NULL ,
	INSDT                DATE DEFAULT  SYSDATE  NULL ,
	INSID                VARCHAR(12) NULL ,
	UPDDT                DATE DEFAULT  SYSDATE  NULL ,
	UPDID                VARCHAR(12) NULL ,
CONSTRAINT  XPKCO_CD PRIMARY KEY (COID,CDID,CD)
);

CREATE TABLE CO_MNU
(
	COID                 VARCHAR(4) NOT NULL ,
	MNUDV                VARCHAR(1) NOT NULL ,
	MNUID                VARCHAR(12) NOT NULL ,
	HMNUID               VARCHAR(12) NULL ,
	SEQ                  NUMERIC(3) NULL ,
	MNUNM                VARCHAR(100) NULL ,
	MNUFNC               VARCHAR(1) NOT NULL ,
	MNUDIR               VARCHAR(100) NULL ,
	MNUCNTN              VARCHAR(1000) NULL ,
	LNKPARM              VARCHAR(100) NULL ,
	MNUED                VARCHAR(8) NOT NULL ,
	INSDT                DATETIME DEFAULT  SYSDATE  NULL ,
	INSID                VARCHAR(12) NULL ,
	UPDDT                DATETIME DEFAULT  SYSDATE  NULL ,
	UPDID                VARCHAR(12) NULL ,
CONSTRAINT  XPKCO_MNU PRIMARY KEY (COID,MNUDV,MNUID)
);

CREATE TABLE CO_USR
(
	COID                 VARCHAR(4) NOT NULL ,
	USRID                VARCHAR(12) NOT NULL ,
	EMPNO                VARCHAR(12) NULL ,
	USRNM                VARCHAR(50) NOT NULL ,
	PWD                  VARCHAR(50) NOT NULL ,
	DPTCD                VARCHAR(6) NULL ,
	ROLCD                VARCHAR(4) NULL ,
	ROLID                VARCHAR(4) NULL ,
	GRPID                VARCHAR(6) NULL ,
	TELNO                VARCHAR(20) NULL ,
	HPNNO                VARCHAR(20) NULL ,
	USEYN                VARCHAR(1) DEFAULT  'Y'  NOT NULL ,
	INSDT                DATETIME DEFAULT  SYSDATE  NULL ,
	INSID                VARCHAR(12) NULL ,
	UPDDT                DATETIME DEFAULT  SYSDATE  NULL ,
	UPDID                VARCHAR(12) NULL ,
CONSTRAINT  XPKCO_USR PRIMARY KEY (COID,USRID)
);

CREATE TABLE HR_DPT
(
	COID                 VARCHAR(4) NOT NULL ,
	DPTCD                VARCHAR(6) NOT NULL ,
	HDPTCD               VARCHAR(6) NULL ,
	SEQ                  NUMERIC(3) NULL ,
	DPTNM                VARCHAR(50) NOT NULL ,
	DPTSD                VARCHAR(8) NOT NULL ,
	DPTED                VARCHAR(8) NULL ,
	INSDT                DATE DEFAULT  SYSDATE  NULL ,
	INSID                VARCHAR(18) NULL ,
	UPDDT                DATE DEFAULT  SYSDATE  NULL ,
	UPDID                VARCHAR(18) NULL ,
CONSTRAINT  PKHR_DPT PRIMARY KEY (COID,DPTCD)
);