delete from taco_ingredients;
delete from order_tacos;
delete from tacos;
delete from orders;
delete from ingredients;

insert into ingredients (id, name, type)
    values ('FLTO', 'Flour Tortilla', 'WRAP');
insert into ingredients (id, name, type)
    values ('COTO', 'Corn Tortilla', 'WRAP');
insert into ingredients (id, name, type)
    values ('GRBF', 'Ground Beef', 'PROTEIN');
insert into ingredients (id, name, type)
    values ('CARN', 'Carnitas', 'PROTEIN');
insert into ingredients (id, name, type)
    values ('CHED', 'Cheddar', 'CHEESE');
insert into ingredients (id, name, type)
    values ('JACK', 'Monterrey Jack', 'CHEESE');
insert into ingredients (id, name, type)
    values ('SLSA', 'Salsa', 'SAUCE');
insert into ingredients (id, name, type)
    values ('SRCR', 'Sour Cream', 'SAUCE');