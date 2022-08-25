INSERT INTO careers (id, name) VALUES (gen_random_uuid(), 'System Engineeering');
INSERT INTO careers (id, name) VALUES (gen_random_uuid(), 'Medicine');
INSERT INTO careers (id, name) VALUES (gen_random_uuid(), 'Economics');
INSERT INTO careers (id, name) VALUES (gen_random_uuid(), 'Chemistry');

INSERT INTO courses (id, name, credits) VALUES (gen_random_uuid(), 'Algorithms I', 4);
INSERT INTO courses (id, name, credits) VALUES (gen_random_uuid(), 'Cellular Biology', 3);
INSERT INTO courses (id, name, credits) VALUES (gen_random_uuid(), 'Algebra', 3);
INSERT INTO courses (id, name, credits) VALUES (gen_random_uuid(), 'Calculus I', 4);
INSERT INTO courses (id, name, credits) VALUES (gen_random_uuid(), 'Metabolism', 5);
INSERT INTO courses (id, name, credits) VALUES (gen_random_uuid(), 'Data Bases', 3);
INSERT INTO courses (id, name, credits) VALUES (gen_random_uuid(), 'Microeconomics', 4);


INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'System Engineeering'),
	(SELECT id FROM courses WHERE name ILIKE 'Algorithms I')
);

INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'System Engineeering'),
	(SELECT id FROM courses WHERE name ILIKE 'Algebra')
);

INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'System Engineeering'),
	(SELECT id FROM courses WHERE name ILIKE 'Calculus I')
);

INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'System Engineeering'),
	(SELECT id FROM courses WHERE name ILIKE 'Data Bases')
);

INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'Medicine'),
	(SELECT id FROM courses WHERE name ILIKE 'Metabolism')
);

INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'Medicine'),
	(SELECT id FROM courses WHERE name ILIKE 'Cellular Biology')
);

INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'Economics'),
	(SELECT id FROM courses WHERE name ILIKE 'Algebra')
);

INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'Economics'),
	(SELECT id FROM courses WHERE name ILIKE 'Calculus I')
);

INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'Economics'),
	(SELECT id FROM courses WHERE name ILIKE 'Microeconomics')
);

INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'Chemistry'),
	(SELECT id FROM courses WHERE name ILIKE 'Cellular Biology')
);


INSERT INTO roles (id, role) VALUES (gen_random_uuid(), 'ROLE_ADMIN');
INSERT INTO roles (id, role) VALUES (gen_random_uuid(), 'ROLE_STUDENT');
INSERT INTO roles (id, role) VALUES (gen_random_uuid(), 'ROLE_PROFESSOR');



/* ----------------------------------Administrators creation---------------------------------- */

INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (
	gen_random_uuid(),
	'Admin1',
	'Test1',
	'admin1.test1',
	'$2a$12$U2hGz5E74FQJ98uj.Fj1leeM/hnZTwa9YFd439J/VdFYHiE1hTHxS',
	'test_admin1@email.com',
	(SELECT id FROM roles WHERE role ILIKE 'ROLE_ADMIN') /* ROLE */
);
INSERT INTO administrators (id) VALUES (
	(SELECT id FROM users WHERE username ILIKE 'admin1.test1')
);


INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (
	gen_random_uuid(),
	'Admin2',
	'Test2',
	'admin2.test2',
	'$2a$12$sI24YXpBPKJnTAcZdsknHuflJJVueZ.ktWtVq0QI/88zzvTHhebvO',
	'test_admin2@email.com',
	(SELECT id FROM roles WHERE role ILIKE 'ROLE_ADMIN') /* ROLE */
);
INSERT INTO administrators (id) VALUES (
	(SELECT id FROM users WHERE username ILIKE 'admin2.test2')
);



/** ----------------------------------Students creation---------------------------------- */

INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (
	gen_random_uuid(),
	'Student1',
	'Test1',
	'student1.test1',
	'$2a$12$yxjoQChKhxmYXUwXeS46e.l.7K9Dty60U776TCrT98JM1tVmjvdSi',
	'student1@email.com',
	(SELECT id FROM roles WHERE role ILIKE 'ROLE_STUDENT') /* ROLE */
);
INSERT INTO students (id, average_grade) VALUES (
	(SELECT id FROM users WHERE username ILIKE 'student1.test1'),
	0
);
INSERT INTO enrollments (id, student_id, career_id) VALUES (
	gen_random_uuid(),
	(SELECT u.id FROM students s INNER JOIN users u ON s.id = u.id WHERE u.username ILIKE 'student1.test1'),
	(SELECT id FROM careers WHERE name ILIKE 'System Engineeering')
);


INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (
	gen_random_uuid(),
	'Student2',
	'Test2',
	'student2.test2',
	'$2a$12$agsoFw93pNWKw8gNuh1HEu/GlVSXCg0ALu6zygc7ybKD1Hx53lRzG',
	'student2@email.com',
	(SELECT id FROM roles WHERE role ILIKE 'ROLE_STUDENT') /* ROLE */
);
INSERT INTO students (id, average_grade) VALUES (
	(SELECT id FROM users WHERE username ILIKE 'student2.test2'),
	0
);
INSERT INTO enrollments (id, student_id, career_id) VALUES (
	gen_random_uuid(),
	(SELECT u.id FROM students s INNER JOIN users u ON s.id = u.id WHERE u.username ILIKE 'student2.test2'),
	(SELECT id FROM careers WHERE name ILIKE 'System Engineeering')
);


INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (
	gen_random_uuid(),
	'Student3',
	'Test3',
	'student3.test3',
	'$2a$12$QOXLegMgAnSRVroG25lEfu8xSQfuqsJbdHDjcEcab2Su/FNPHwxEC',
	'student3@email.com',
	(SELECT id FROM roles WHERE role ILIKE 'ROLE_STUDENT') /* ROLE */
);
INSERT INTO students (id, average_grade) VALUES (
	(SELECT id FROM users WHERE username ILIKE 'student3.test3'),
	0
);
INSERT INTO enrollments (id, student_id, career_id) VALUES (
	gen_random_uuid(),
	(SELECT u.id FROM students s INNER JOIN users u ON s.id = u.id WHERE u.username ILIKE 'student3.test3'),
	(SELECT id FROM careers WHERE name ILIKE 'Medicine')
);



/* ----------------------------------Professors creation---------------------------------- */

INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (
	gen_random_uuid(),
	'Professor1',
	'Test1',
	'professor1.test1',
	'$2a$12$trL46RMiKiVgYdAesYLzxeuEHi1otJzJViiAvZMAbbzQSOzMVnp6K',
	'professor1@email.com',
	(SELECT id FROM roles WHERE role ILIKE 'ROLE_PROFESSOR') /* ROLE */
);
INSERT INTO professors (id, salary) VALUES (
	(SELECT id FROM users WHERE username ILIKE 'professor1.test1'),
	4000
);

INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (
	gen_random_uuid(),
	'Professor2',
	'Test2',
	'professor2.test2',
	'$2a$12$JYJ4rp9qUCQ8HXTHtFYIQOZdkyT0rzcVgkYPAmUTpdSsVXuuc8eBK',
	'professor2@email.com',
	(SELECT id FROM roles WHERE role ILIKE 'ROLE_PROFESSOR') /* ROLE */
);
INSERT INTO professors (id, salary) VALUES (
	(SELECT id FROM users WHERE username ILIKE 'professor2.test2'),
	78000
);

INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (
	gen_random_uuid(),
	'Professor3',
	'Test3',
	'professor3.test3',
	'$2a$12$jFwdIm21E9CyrEcg8MI2UeYCGJnQH.2I.1UPHe7mqfM3GNvrS7PAa',
	'professor3@email.com',
	(SELECT id FROM roles WHERE role ILIKE 'ROLE_PROFESSOR') /* ROLE */
);
INSERT INTO professors (id, salary) VALUES (
	(SELECT id FROM users WHERE username ILIKE 'professor3.test3'),
	25800
);



/* ----------------------------------Classes creation---------------------------------- */
INSERT INTO classes (id, capacity, enrolled_students, available, course_id, professor_id) VALUES (
	gen_random_uuid(),
	2,
	0,
	TRUE,
	(SELECT id FROM courses WHERE name ILIKE 'Algorithms I'),
	(SELECT id FROM users WHERE username ILIKE 'professor1.test1')
);

INSERT INTO classes (id, capacity, enrolled_students, available, course_id, professor_id) VALUES (
	gen_random_uuid(),
	3,
	0,
	TRUE,
	(SELECT id FROM courses WHERE name ILIKE 'Algorithms I'),
	(SELECT id FROM users WHERE username ILIKE 'professor2.test2')
);

INSERT INTO classes (id, capacity, enrolled_students, available, course_id, professor_id) VALUES (
	gen_random_uuid(),
	2,
	0,
	TRUE,
	(SELECT id FROM courses WHERE name ILIKE 'Cellular Biology'),
	(SELECT id FROM users WHERE username ILIKE 'professor3.test3')
);

INSERT INTO classes (id, capacity, enrolled_students, available, course_id, professor_id) VALUES (
	gen_random_uuid(),
	3,
	0,
	TRUE,
	(SELECT id FROM courses WHERE name ILIKE 'Data Bases'),
	(SELECT id FROM users WHERE username ILIKE 'professor1.test1')
);