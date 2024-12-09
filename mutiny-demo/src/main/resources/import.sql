Insert into md_user(id, u_date_of_birth, u_address, u_email, u_firstname, u_lastname)
select nextval('md_user_seq'),
       '2001-01-01',
       'address' || currval('md_user_seq'),
       'email' || currval('md_user_seq') || '@example.com',
       'firstname' || currval('md_user_seq'),
       'lastname' || currval('md_user_seq')
from generate_series(1, 1000);