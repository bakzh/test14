drop table product;
create table product(
    product_id  number(10),
    pname       varchar(30),
    count       number(10),
    price       number(10)
);
--기본키
alter table product add constraint product_product_id_pk primary key(product_id);

--시퀀스생성
drop sequence product_product_id_seq;
create sequence product_product_id_seq;

commit;

select * from product;