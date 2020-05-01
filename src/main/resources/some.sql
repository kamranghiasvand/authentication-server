delete
from tbl_reg_role_permission
where true;
delete
from tbl_reg_user_role
where true;
delete
from tbl_ad_user_permission
where true;
delete
from tbl_permission
where true;
delete
from tbl_ad_user
where true;
delete
from tbl_client
where true;
delete
from tbl_phone_verification
where true;
delete
from tbl_reg_role
where true;
delete
from tbl_reg_user
where true;



insert into tbl_permission (domain, method, name, url)
VALUES ('app', 'GET', 'get_regular_user_profile', '/api/profile');

select pk_id
into @per_id
from tbl_permission
where name = 'get_regular_user_profile';

insert into tbl_reg_role (domain, name)
VALUES ('app', 'regular_user');

select pk_id
into @role_id
from tbl_reg_role
where name = 'regular_user';


insert into tbl_reg_role_permission (role_id, permission_id)
VALUES (@role_id, @per_id);

insert into tbl_reg_user (domain, email, enabled, password, first_name, last_name, last_update, registration_date,phone)
VALUES ('app', 'test@email', 1, '1', 'firstName', 'lastName',
        '2011-12-18 13:17:17', '2011-12-18 13:17:17','+989333938680');

select pk_id
into @user_id
from tbl_reg_user
where first_name = 'firstName';

insert into tbl_reg_user_role (user_id, role_id)
VALUES (@user_id, @role_id);
