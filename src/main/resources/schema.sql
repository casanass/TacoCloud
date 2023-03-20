create table if not exists ingredients (
    id varchar(4) primary key,
    name varchar(25) not null,
    type varchar(10) not null
);

create table if not exists tacos (
    id bigint primary key auto_increment,
    name varchar(50) not null,
    createDt timestamp not null
);

create table if not exists orders (
    id bigint primary key auto_increment,
    createDt timestamp not null,
    orderName varchar(50) not null,
    orderStreet varchar(50) not null,
    orderCity varchar(50) not null,
    orderState varchar(10) not null,
    orderCcNumber varchar(2) not null
);

create table if not exists taco_ingredients (
    tacoId bigint not null,
    ingreId varchar(4) not null,
    foreign key (tacoId) references tacos(id),
    foreign key (ingreId) references ingredients(id)
);

create table if not exists order_tacos (
    orderId bigint not null,
    tacoId bigint not null,
    foreign key (orderId) references orders(id),
    foreign key (tacoId) references tacos(id)
);
