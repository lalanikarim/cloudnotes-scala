# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table note (
  id                        integer not null,
  width                     integer,
  height                    integer,
  position                  integer,
  constraint pk_note primary key (id))
;

create table note_acl (
  id                        integer not null,
  aclnote_id                integer,
  acluser_user_id           varchar(255),
  permission                integer,
  constraint ck_note_acl_permission check (permission in (0,1,2)),
  constraint pk_note_acl primary key (id))
;

create table note_item (
  id                        integer not null,
  parent_note_id            integer,
  author_user_id            varchar(255),
  created_on                timestamp,
  note_text                 varchar(1024),
  constraint pk_note_item primary key (id))
;

create table user (
  user_id                   varchar(255) not null,
  password                  varchar(255),
  constraint pk_user primary key (user_id))
;

create sequence note_seq;

create sequence note_acl_seq;

create sequence note_item_seq;

create sequence user_seq;

alter table note_acl add constraint fk_note_acl_ACLNote_1 foreign key (aclnote_id) references note (id) on delete restrict on update restrict;
create index ix_note_acl_ACLNote_1 on note_acl (aclnote_id);
alter table note_acl add constraint fk_note_acl_ACLUser_2 foreign key (acluser_user_id) references user (user_id) on delete restrict on update restrict;
create index ix_note_acl_ACLUser_2 on note_acl (acluser_user_id);
alter table note_item add constraint fk_note_item_ParentNote_3 foreign key (parent_note_id) references note (id) on delete restrict on update restrict;
create index ix_note_item_ParentNote_3 on note_item (parent_note_id);
alter table note_item add constraint fk_note_item_Author_4 foreign key (author_user_id) references user (user_id) on delete restrict on update restrict;
create index ix_note_item_Author_4 on note_item (author_user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists note;

drop table if exists note_acl;

drop table if exists note_item;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists note_seq;

drop sequence if exists note_acl_seq;

drop sequence if exists note_item_seq;

drop sequence if exists user_seq;

