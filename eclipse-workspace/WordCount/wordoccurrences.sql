use wordOccurrences;
drop table word;
create table word (words varchar(20));
insert into wordOccurrences.word (words) values ('the');
insert into wordOccurrences.word (words) values ('test');
insert into wordOccurrences.word (words) values ('this');
insert into wordOccurrences.word (words) values ('a');
select * from wordOccurrences.word;