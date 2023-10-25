
create table user_table (
	id identity primary key,  
	alias varchar(25) unique, 
	email varchar(25) unique,
	first_name varchar(25), 
	last_name varchar(25), 
	level varchar(25),
	password varchar(100), 
	role varchar(25)
);

create table attempt (
	id identity primary key,
    answer integer not null, 
    attempt integer not null, 
    factora integer not null, 
    factorb integer not null, 
    is_correct boolean not null, 
    user_id bigint, 
    primary key (id), 
    foreign key(user_id) references user_table(id)
);
