select * from orderStatus
where (statusID = 2 or statusID = 3 or statusID = 5) and (statusID = 2)

update orderStatus
set statusName = N'Delivering', description='Order is packaged and in delivering to customer'
where statusID = 3

insert into orderStatus(statusName,[description]) values ('Completed','Product are deliverd to customer and the Order is completed')