 INSERT INTO USER (username, password, first_name, last_name, role, telephone, email, address) VALUES ('vanjus', 'sifra123', 'Vanja', 'Draganic', 1, '0612345678', 'draganicv@gmail.com', 'Vojvode Stepe');
 INSERT INTO USER (username, password, first_name, last_name, role, telephone, email, address) VALUES ('lekson', 'andjela123', 'Aleksa', 'Milenovic', 2, '0623456789', 'leksatuga@gmail.com', 'Avijacija negde');
 INSERT INTO USER (username, password, first_name, last_name, role, telephone, email, address) VALUES ('aleksalukac', 'admin', 'Aleksandar', 'Lukac', 0, '0698765432', 'aleksa@lukac.rs', 'Seljackih buna');

 INSERT INTO DELIVERER (id) VALUES (1);
 INSERT INTO CUSTOMER (id) VALUES (2);

 INSERT INTO ARTICLE (name, description, price, amount, category, on_sale) VALUES ('smoki', 'slane grickalice', 125.00, 6, '0', false);
 INSERT INTO ARTICLE (name, description, price, amount, category, on_sale) VALUES ('jafa', 'keks', 149.99, 10, '1', false);
 INSERT INTO ARTICLE (name, description, price, amount, category, on_sale) VALUES ('fanta', 'gazirani sok', 60.00, 6, '2', true);


 INSERT INTO CART (datetime) VALUES ('2018-08-10');
 INSERT INTO CART (datetime) VALUES ('2019-03-11');
 INSERT INTO CART (datetime) VALUES ('2019-09-03');