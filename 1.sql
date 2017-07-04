create table student_range(
    sno number(4) constraint s_pk primary key,
    sname varchar2(10) constraint s_uk unique,
    sage number,
    sex char(2),
    cno number(2)
    )
    partition by range 
