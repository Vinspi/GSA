-- team --
insert into team (team_id, team_name) values (1, 'WOCKHARDT LIMITED');
insert into team (team_id, team_name) values (2, 'Newton Laboratories, Inc.');
insert into team (team_id, team_name) values (3, 'Safeway');
insert into team (team_id, team_name) values (4, 'Salix Pharmaceuticals, Inc.');
insert into team (team_id, team_name) values (5, 'Walgreen Company');
insert into team (team_id, team_name) values (6, 'NorthStar Rx LLC');
insert into team (team_id, team_name) values (7, 'West-Ward Pharmaceutical Corp');
insert into team (team_id, team_name) values (8, 'Dr. Fresh, Inc.');
insert into team (team_id, team_name) values (9, 'Boggs Gases div. Boggs Fire Equipment');
insert into team (team_id, team_name) values (10, 'NorthStar Rx LLC');
-- species --
insert into species (species_name) values ('Carpet python');
insert into species (species_name) values ('Blue-faced booby');
insert into species (species_name) values ('Wolf spider');
insert into species (species_name) values ('Baboon, gelada');
insert into species (species_name) values ('Monkey, bleeding heart');
insert into species (species_name) values ('Bird, pied butcher');
-- product --
insert into product (target_pk, source_pk) values ('Carpet python', 'Blue-faced booby');
insert into product (target_pk, source_pk) values ('Wolf spider', 'Baboon, gelada');
insert into product (target_pk, source_pk) values ('Monkey, bleeding heart', 'Bird, pied butcher');
-- aliquots --
insert into aliquot (aliquotnlot, aliquot_expiration_date, aliquot_price, aliquot_quantity, provider, target, source) values (1, '2019-02-11 00:00:00', 0.167879443, 2, 'Dynabox', 'Carpet python', 'Blue-faced booby');
insert into aliquot (aliquotnlot, aliquot_expiration_date, aliquot_price, aliquot_quantity, provider, target, source) values (2, '2019-02-11 00:00:00', 0.4369044297, 2, 'Linktype', 'Carpet python', 'Blue-faced booby');
insert into aliquot (aliquotnlot, aliquot_expiration_date, aliquot_price, aliquot_quantity, provider, target, source) values (3, '2019-02-11 00:00:00', 2.0824829339, 13, 'Flipbug', 'Carpet python', 'Blue-faced booby');