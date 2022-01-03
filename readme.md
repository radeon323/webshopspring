# Web-shop
##Simple webshop based on Spring

### Installation guide

#### 1. Run docker command to start postgreSQL database:
``docker run --name=main-pg --env=POSTGRES_USER=postgres --env=POSTGRES_PASSWORD=postgres --env=POSRGRES_DB=postgres -p 5432:5432 postgres``

#### 2. Create database with name `postgres`

#### 3. Create tables
```sql
CREATE TABLE products( 
    id SERIAL not NULL,
    name VARCHAR(50),
    price VARCHAR(50),
    creation_date VARCHAR(50)
);

CREATE TABLE users(
    id SERIAL not NULL,
    email VARCHAR(50),
    password VARCHAR(50),
    gender VARCHAR(50),
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    about TEXT,
    age INT NOT NULL DEFAULT 0
);
```