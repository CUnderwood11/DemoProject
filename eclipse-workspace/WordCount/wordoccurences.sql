use wordoccurences;
drop table word;
create table word(words varchar(20));
insert into word (words) values ('the');
insert into word (words) values ('test');
insert into word (words) values ('this');
insert into word (words) values ('a');
select * from word;