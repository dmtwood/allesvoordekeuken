insert into artikels(naam, aankoopprijs, verkoopprijs) values('test', 100, 120);
insert into artikels(naam, aankoopprijs, verkoopprijs, houdbaarheid, soort)
VALUES('testfood',100, 120, 7, 'F');
insert into artikels(naam, aankoopprijs, verkoopprijs, garantie, soort)
values('testnonfood', 100, 120, 30, 'NF');
insert into kortingen(artikelid, vanafAantal, percentage)
VALUES((select id from artikels where naam='testfood'), 1, 10 );