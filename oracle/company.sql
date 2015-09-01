DROP TABLE "COMPANY" CASCADE CONSTRAINT PURGE
/
CREATE TABLE "COMPANY"(
  "ID" NUMBER(20,0) NOT NULL,
  "NAME" VARCHAR2(255) NOT NULL,
  "DESCRIPTION" VARCHAR2(255) NULL,
  "STREET" VARCHAR2(255) NOT NULL,
  "CITY" VARCHAR2(50) NOT NULL,
  "ZIP" VARCHAR2(10) NOT NULL,
  "STATE" VARCHAR2(30) NULL,
  "CREATED_AT" TIMESTAMP NOT NULL,
  "CREATED_BY" VARCHAR2(50) NOT NULL,
  "UPDATED_AT" TIMESTAMP NOT NULL,
  "UPDATED_BY" VARCHAR2(50) NOT NULL,
  "VERSION" NUMBER(20,0) NOT NULL
)TABLESPACE MASTERSPACE
/
ALTER TABLE "COMPANY" ADD CONSTRAINT "COMPANY_PK" PRIMARY KEY ("ID")
/
ALTER TABLE "COMPANY" ADD CONSTRAINT "COMPANY_UK" UNIQUE ("NAME")
/
DROP SEQUENCE COMPANY_SEQ
/
CREATE SEQUENCE COMPANY_SEQ
START WITH 1
INCREMENT BY 1
MINVALUE 1
MAXVALUE 999999999999
NOCYCLE
NOCACHE
ORDER
/
CREATE OR REPLACE TRIGGER COMPANY_TRIG
BEFORE INSERT ON COMPANY
FOR EACH ROW
BEGIN
    SELECT COMPANY_SEQ.NEXTVAL INTO :NEW.ID FROM DUAL;
END;
/
PURGE RECYCLEBIN
/