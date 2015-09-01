DROP TABLE "COMPANY_EMPLOYEE" CASCADE CONSTRAINT PURGE
/
CREATE TABLE "COMPANY_EMPLOYEE"(
  "COMPANY_ID" NUMBER(20,0) NOT NULL,
  "EMPLOYEE_ID" NUMBER(20,0) NOT NULL
)TABLESPACE MASTERSPACE
/
ALTER TABLE "COMPANY_EMPLOYEE" ADD CONSTRAINT "COMPANY_EMPLOYEE_PK" PRIMARY KEY ("COMPANY_ID", "EMPLOYEE_ID")
/
ALTER TABLE "COMPANY_EMPLOYEE" ADD CONSTRAINT "COMPANY_EMPLOYEE_COMPANY_FK" FOREIGN KEY ("COMPANY_ID") REFERENCES COMPANY("ID")
/
ALTER TABLE "COMPANY_EMPLOYEE" ADD CONSTRAINT "COMPANY_EMPLOYEE_EMPLOYEE_FK" FOREIGN KEY ("EMPLOYEE_ID") REFERENCES EMPLOYEE("ID")
/