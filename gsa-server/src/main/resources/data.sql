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
insert into species (species_name) values ('MONKEY');
insert into species (species_name) values ('DONKEY');
insert into species (species_name) values ('WOLF');
insert into species (species_name) values ('Spider');
insert into species (species_name) values ('goat');
insert into species (species_name) values ('shark');
-- product --
insert into product (target_pk, source_pk) values ('shark', 'WOLF');
insert into product (target_pk, source_pk) values ('WOLF', 'goat');
insert into product (target_pk, source_pk) values ('Spider', 'WOLF');
-- aliquots --
insert into aliquot (aliquotnlot, aliquot_expiration_date, aliquot_price, aliquot_quantity_hidden_stock, aliquot_quantity_visible_stock, provider, target, source) values (1, '2019-02-11 00:00:00', 0.167879443, 2,2, 'Dynabox', 'shark', 'WOLF');
insert into aliquot (aliquotnlot, aliquot_expiration_date, aliquot_price, aliquot_quantity_hidden_stock, aliquot_quantity_visible_stock, provider, target, source) values (2, '2019-02-11 00:00:00', 0.4369044297, 2,6, 'Linktype', 'shark', 'WOLF');
insert into aliquot (aliquotnlot, aliquot_expiration_date, aliquot_price, aliquot_quantity_hidden_stock, aliquot_quantity_visible_stock, provider, target, source) values (3, '2019-02-11 00:00:00', 2.0824829339, 13,14, 'Flipbug', 'Spider', 'WOLF');