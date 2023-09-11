create table crypto_currency
(
    amount               float(53),
    price                decimal(38, 2) not null,
    version              integer,
    created_date         datetime(6),
    update_date          datetime(6),
    id                   varchar(36)    not null,
    crypto_currency_name varchar(50)    not null,
    primary key (id)
) engine=InnoDB;
