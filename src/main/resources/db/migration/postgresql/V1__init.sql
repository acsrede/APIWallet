CREATE TABLE users(
  id serial,
  name varchar(50),
  password varchar,
  email varchar(100),
  PRIMARY KEY(id)
);

CREATE TABLE wallet(
  id serial,
  name varchar(60),
  value numeric(10,2),
  PRIMARY KEY(id)
);

CREATE TABLE users_wallet(
  id serial,
  wallet integer,
  users integer,
  PRIMARY KEY(id),
  FOREIGN KEY(users) REFERENCES users(id),
  FOREIGN KEY(wallet) REFERENCES wallet(id)
);

CREATE TABLE wallet_items(
  id serial,
  wallet integer,
  date date,
  type varchar(2),
  description varchar(500),
  value numeric(10,2),
  PRIMARY KEY(id),
  FOREIGN KEY(wallet) REFERENCES wallet(id)
);