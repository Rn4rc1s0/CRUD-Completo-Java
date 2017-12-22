use mobi;


insert into departamento values(1,'financeiro');
insert into departamento values(2,'desenvolvimento');
insert into departamento values(3,'suporte');

insert into funcionario values(null,null,'jose1',null,1);
insert into funcionario values(null,null,'jose2',null,2);



select nomeDepartamento,idDepartamento from departamento where idDepartamento not in(select idDepartamento from departamento natural join funcionario);

