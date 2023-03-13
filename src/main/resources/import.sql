insert into currency(id, name, description ) values (1, 'nuevo sol','Currency in Peru.');
insert into currency(id, name, description ) values (2, 'dollar','Currency in USA.');
insert into currency(id, name, description ) values (3, 'yuan','Currency in China.');
insert into currency(id, name, description ) values (4, 'euro','Currency in European Union.');

insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (1, 1, 1);
insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (1, 2, 0.27);
insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (1, 3, 1.83);
insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (1, 4, 0.25);

insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (2, 2, 1);
insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (2, 1, 3.77);
insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (2, 3, 6.91);
insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (2, 4, 0.94);

insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (3, 3, 1);
insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (3, 1, 0.55);
insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (3, 2, 0.14);
insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (3, 4, 0.14);

insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (4, 4, 1);
insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (4, 1, 4.01);
insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (4, 2, 1.06);
insert into exchange(currencyOrigin, currencyDestiny, exchangeRate) values (4, 3, 7.35);

insert into users(name, email, password) values ('jose', 'josepanta@gmail.com', '$2a$10$BYU19sHVJ3yJ92IaLkTDWO98gwNEsONBjwOjYnHVxWO4ccjtlnKya')