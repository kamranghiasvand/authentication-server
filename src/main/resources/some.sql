delete
from tbl_reg_role_permission;
delete
from tbl_reg_user_role;

delete
from tbl_permission;
insert into tbl_permission (domain, method, name, url)
VALUES ('app', 'GET', 'per_get', '/api/test');
insert into tbl_permission (domain, method, name, url)
VALUES ('app', 'PUT', 'per_put', '/api/test');
select pk_id
into @per_put
from tbl_permission
where name = 'per_put';
select pk_id
into @per_get
from tbl_permission
where name = 'per_get';

delete
from tbl_reg_role;
insert into tbl_reg_role (domain, name)
VALUES ('app', 'test role');
select pk_id
into @role_id
from tbl_reg_role
where name = 'test role';

insert into tbl_reg_role_permission (role_id, permission_id)
VALUES (@role_id, @per_put);
insert into tbl_reg_role_permission (role_id, permission_id)
VALUES (@role_id, @per_get);

delete
from tbl_reg_user;
insert into tbl_reg_user (domain, email, enabled, password, first_name, last_name, last_update, registration_date)
VALUES ('app', 'test@email', 1, '$2a$09$fWSSca91yxjr5ys6fPO1xO/v5Ae6HuivzwjTsZiSw6zYg7.t6rO9S', 'test', 'test',
        '2011-12-18 13:17:17', '2011-12-18 13:17:17');
select pk_id
into @user_id
from tbl_reg_user
where first_name = 'test';

insert into tbl_reg_user_role (user_id, role_id)
VALUES (@user_id, @role_id);
