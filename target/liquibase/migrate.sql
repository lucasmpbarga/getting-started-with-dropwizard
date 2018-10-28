--  *********************************************************************
--  Rollback 1 Change(s) Script
--  *********************************************************************
--  Change Log: migrations.xml
--  Ran at: 28/10/18 00:46
--  Against: root@localhost@jdbc:mysql://localhost:3306/dropbookmarks
--  Liquibase version: 3.4.1
--  *********************************************************************

--  Lock Database
UPDATE DATABASECHANGELOGLOCK SET LOCKED = 1, LOCKEDBY = 'fe80:0:0:0:bd9:455b:505c:76f9%enp6s0 (fe80:0:0:0:bd9:455b:505c:76f9%enp6s0)', LOCKGRANTED = '2018-10-28 00:46:29.380' WHERE ID = 1 AND LOCKED = 0;

--  Rolling Back ChangeSet: migrations.xml::3::dima
DELETE FROM users WHERE id=1;

DELETE FROM DATABASECHANGELOG WHERE ID = '3' AND AUTHOR = 'dima' AND FILENAME = 'migrations.xml';

--  Release Database Lock
UPDATE DATABASECHANGELOGLOCK SET LOCKED = 0, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

