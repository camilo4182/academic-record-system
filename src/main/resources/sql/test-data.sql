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


INSERT INTO roles (id, name) VALUES (gen_random_uuid(), 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (gen_random_uuid(), 'ROLE_STUDENT');
INSERT INTO roles (id, name) VALUES (gen_random_uuid(), 'ROLE_PROFESSOR');


INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (
	gen_random_uuid(),
	'Admin1',
	'Test1',
	'admin1.test1',
	'1234',
	'test_admin1@email.com',
	(SELECT id FROM roles WHERE name ILIKE 'ROLE_ADMIN') /* ROLE */
);
INSERT INTO administrators (id) VALUES (
	(SELECT id FROM users WHERE username ILIKE 'admin1.test1')
);



INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (
	gen_random_uuid(),
	'Admin2',
	'Test2',
	'admin2.test2',
	'$2a$12$v5BCniWLT724a4TUXwRreumxKz9duqIdnjRFfS8jSeS3BsClgCQte',
	'test_admin2@email.com',
	(SELECT id FROM roles WHERE name ILIKE 'ROLE_ADMIN') /* ROLE */
);
INSERT INTO administrators (id) VALUES (
	(SELECT id FROM users WHERE username ILIKE 'admin2.test2')
);


INSERT INTO users (id, first_name, last_name, username, password, email, role_id) VALUES (
	gen_random_uuid(),
	'Student1',
	'Test1',
	'student1.test1',
	'$2a$12$kkMzXQx6f6ZA5xXYwe.kHOHBNyusS8ZKBlkafINFp.0LmEArlM/WG',
	'student1@email.com',
	(SELECT id FROM roles WHERE name ILIKE 'ROLE_STUDENT') /* ROLE */
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
	'Professor1',
	'Test1',
	'professor1.test1',
	'$2a$12$1zmW2FVKfyx1fNGP4HlJpOs9Ki4.x2FG4YhKZsjLiwNNBiAERF9JO',
	'professor1@email.com',
	(SELECT id FROM roles WHERE name ILIKE 'ROLE_PROFESSOR') /* ROLE */
);
INSERT INTO professors (id, salary) VALUES (
	(SELECT id FROM users WHERE username ILIKE 'professor1.test1'),
	40000
);