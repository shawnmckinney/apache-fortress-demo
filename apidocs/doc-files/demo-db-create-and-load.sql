-- Setup a new MySQL user for fortress demo app:
CREATE USER 'admin'@'localhost' IDENTIFIED BY 'secret';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'localhost' WITH GRANT OPTION;
CREATE USER 'admin'@'%' IDENTIFIED BY 'secret';
-- Set privileges:
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%' WITH GRANT OPTION;
-- Setup demo db:
CREATE DATABASE demoDB;
use demoDB;
CREATE TABLE page1 (ID INT, customer VARCHAR(20), attr_a VARCHAR(20), attr_b VARCHAR(20), attr_c VARCHAR(20), unique key(id) );
ALTER TABLE page1 ADD INDEX customer (customer);
CREATE TABLE page2 (ID INT, customer VARCHAR(20), attr_d VARCHAR(20), attr_e VARCHAR(20), attr_f VARCHAR(20), unique key(id) );
ALTER TABLE page2 ADD INDEX customer (customer);
CREATE TABLE page3 (ID INT, customer VARCHAR(20), attr_g VARCHAR(20), attr_h VARCHAR(20), attr_i VARCHAR(20), unique key(id) );
ALTER TABLE page3 ADD INDEX customer (customer);

-- Load the demo data:
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10001,'123','123,1,a', '123,1,b', '123,1,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10002,'123','123,2,a', '123,2,b', '123,2,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10003,'123','123,3,a', '123,3,b', '123,3,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10004,'123','123,4,a', '123,4,b', '123,4,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10005,'123','123,5,a', '123,5,b', '123,5,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10006,'123','123,6,a', '123,6,b', '123,6,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10007,'123','123,7,a', '123,7,b', '123,7,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10008,'123','123,8,a', '123,8,b', '123,8,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10009,'123','123,9,a', '123,9,b', '123,9,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (100010,'123','123,10,a', '123,10,b', '123,10,c');

INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10011,'456','456,1,a', '456,1,b', '456,1,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10012,'456','456,2,a', '456,2,b', '456,2,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10013,'456','456,3,a', '456,3,b', '456,3,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10014,'456','456,4,a', '456,4,b', '456,4,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10015,'456','456,5,a', '456,5,b', '456,5,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10016,'456','456,6,a', '456,6,b', '456,6,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10017,'456','456,7,a', '456,7,b', '456,7,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10018,'456','456,8,a', '456,8,b', '456,8,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10019,'456','456,9,a', '456,9,b', '456,9,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (100020,'456','456,10,a', '456,10,b', '456,10,c');

INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10021,'789','789,1,a', '789,1,b', '789,1,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10022,'789','789,2,a', '789,2,b', '789,2,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10023,'789','789,3,a', '789,3,b', '789,3,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10024,'789','789,4,a', '789,4,b', '789,4,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10025,'789','789,5,a', '789,5,b', '789,5,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10026,'789','789,6,a', '789,6,b', '789,6,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10027,'789','789,7,a', '789,7,b', '789,7,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10028,'789','789,8,a', '789,8,b', '789,8,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (10029,'789','789,9,a', '789,9,b', '789,9,c');
INSERT INTO page1 (id, customer, attr_a, attr_b, attr_c) VALUES (100030,'789','789,10,a', '789,10,b', '789,10,c');

INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10001,'123','123,1,d', '123,1,e', '123,1,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10002,'123','123,2,d', '123,2,e', '123,2,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10003,'123','123,3,d', '123,3,e', '123,3,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10004,'123','123,4,d', '123,4,e', '123,4,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10005,'123','123,5,d', '123,5,e', '123,5,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10006,'123','123,6,d', '123,6,e', '123,6,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10007,'123','123,7,d', '123,7,e', '123,7,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10008,'123','123,8,d', '123,8,e', '123,8,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10009,'123','123,9,d', '123,9,e', '123,9,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (100010,'123','123,10,d', '123,10,e', '123,10,f');

INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10011,'456','456,1,d', '456,1,e', '456,1,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10012,'456','456,2,d', '456,2,e', '456,2,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10013,'456','456,3,d', '456,3,e', '456,3,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10014,'456','456,4,d', '456,4,e', '456,4,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10015,'456','456,5,d', '456,5,e', '456,5,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10016,'456','456,6,d', '456,6,e', '456,6,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10017,'456','456,7,d', '456,7,e', '456,7,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10018,'456','456,8,d', '456,8,e', '456,8,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10019,'456','456,9,d', '456,9,e', '456,9,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (100020,'456','456,10,d', '456,10,e', '456,10,f');

INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10021,'789','789,1,d', '789,1,e', '789,1,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10022,'789','789,2,d', '789,2,e', '789,2,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10023,'789','789,3,d', '789,3,e', '789,3,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10024,'789','789,4,d', '789,4,e', '789,4,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10025,'789','789,5,d', '789,5,e', '789,5,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10026,'789','789,6,d', '789,6,e', '789,6,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10027,'789','789,7,d', '789,7,e', '789,7,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10028,'789','789,8,d', '789,8,e', '789,8,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (10029,'789','789,9,d', '789,9,e', '789,9,f');
INSERT INTO page2 (id, customer, attr_d, attr_e, attr_f) VALUES (100030,'789','789,10,d', '789,10,e', '789,10,f');

INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10001,'123','123,1,g', '123,1,h', '123,1,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10002,'123','123,2,g', '123,2,h', '123,2,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10003,'123','123,3,g', '123,3,h', '123,3,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10004,'123','123,4,g', '123,4,h', '123,4,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10005,'123','123,5,g', '123,5,h', '123,5,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10006,'123','123,6,g', '123,6,h', '123,6,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10007,'123','123,7,g', '123,7,h', '123,7,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10008,'123','123,8,g', '123,8,h', '123,8,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10009,'123','123,9,g', '123,9,h', '123,9,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (100010,'123','123,10,g', '123,10,h', '123,10,i');

INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10011,'456','456,1,g', '456,1,h', '456,1,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10012,'456','456,2,g', '456,2,h', '456,2,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10013,'456','456,3,g', '456,3,h', '456,3,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10014,'456','456,4,g', '456,4,h', '456,4,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10015,'456','456,5,g', '456,5,h', '456,5,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10016,'456','456,6,g', '456,6,h', '456,6,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10017,'456','456,7,g', '456,7,h', '456,7,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10018,'456','456,8,g', '456,8,h', '456,8,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10019,'456','456,9,g', '456,9,h', '456,9,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (100020,'456','456,10,g', '456,10,h', '456,10,i');

INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10021,'789','789,1,g', '789,1,h', '789,1,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10022,'789','789,2,g', '789,2,h', '789,2,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10023,'789','789,3,g', '789,3,h', '789,3,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10024,'789','789,4,g', '789,4,h', '789,4,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10025,'789','789,5,g', '789,5,h', '789,5,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10026,'789','789,6,g', '789,6,h', '789,6,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10027,'789','789,7,g', '789,7,h', '789,7,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10028,'789','789,8,g', '789,8,h', '789,8,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (10029,'789','789,9,g', '789,9,h', '789,9,i');
INSERT INTO page3 (id, customer, attr_g, attr_h, attr_i) VALUES (100030,'789','789,10,g', '789,10,h', '789,10,i');

commit;