CREATE TABLE casbin_rule
(
    ptype VARCHAR(100) not NULL,
    v0 VARCHAR(100),
    v1 VARCHAR(100),
    v2 VARCHAR(100),
    v3 VARCHAR(100),
    v4 VARCHAR(100),
    v5 VARCHAR(100)
)
;

alter table casbin_rule add constraint casbin_rule_pk primary key (ptype)
;
