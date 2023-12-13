create table account (
    id serial primary key not null,
    name varchar(100) not null,
    email varchar(255) not null unique,
    password varchar(255) not null,
    created_at timestamp default now(),
    updated_at timestamp
);

create table platform (
    id serial primary key not null,
    name varchar(100) not null unique,
    enabled boolean default true,
    created_at timestamp default now(),
    updated_at timestamp,
    account_id int not null references account (id) on delete cascade
);

create table product (
    id serial primary key not null,
    name varchar(100) not null unique,
    price money not null,
    created_at timestamp default now(),
    updated_at timestamp,
    platform_id int not null references platform (id) on delete cascade
);

create table sale (
    id serial primary key not null,
    created_at timestamp default now(),
    updated_at timestamp,
    platform_id int not null references platform (id) on delete cascade
);

create table sale_item (
    id serial primary key not null,
    sold_price money not null,
    amount int not null,
    sale_id int not null references sale (id) on delete cascade,
    product_id int not null references product (id) on delete cascade,
    unique (sale_id, product_id)
);

create table report (
    id serial primary key not null,
    type varchar not null,
    start_at timestamp not null,
    end_at timestamp not null,
	platform_id int,
    filename varchar not null,
    status varchar not null,
    created_at timestamp default now(),
    updated_at timestamp,
    account_id int not null references account (id) on delete cascade,
    unique (type, start_at, end_at, platform_id)
);

create table history (
    id serial primary key not null,
    signature text not null,
    arguments text not null,
    created_at timestamp default now(),
    updated_at timestamp,
    account_id int not null references account (id) on delete cascade
);
