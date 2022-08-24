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

INSERT INTO roles (id, name) VALUES (gen_random_uuid(), 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (gen_random_uuid(), 'ROLE_STUDENT');
INSERT INTO roles (id, name) VALUES (gen_random_uuid(), 'ROLE_PROFESSOR');


INSERT INTO users (id, name, password, email, role_id) VALUES (
	gen_random_uuid(),
	'admin1',
	'1234',
	'test_admin1@email.com',
	(SELECT id FROM roles WHERE name ILIKE 'ROLE_ADMIN') /* ROLE */
);
INSERT INTO administrators (id) VALUES (
	(SELECT id FROM users WHERE name ILIKE 'admin1')
);



INSERT INTO users (id, name, password, email, role_id) VALUES (
	gen_random_uuid(),
	'admin2',
	'$2a$12$v5BCniWLT724a4TUXwRreumxKz9duqIdnjRFfS8jSeS3BsClgCQte',
	'test_admin2@email.com',
	(SELECT id FROM roles WHERE name ILIKE 'ROLE_ADMIN') /* ROLE */
);
INSERT INTO administrators (id) VALUES (
	(SELECT id FROM users WHERE name ILIKE 'admin2')
);


INSERT INTO users (id, name, password, email, role_id) VALUES (
	gen_random_uuid(),
	'student1',
	'$2a$12$kkMzXQx6f6ZA5xXYwe.kHOHBNyusS8ZKBlkafINFp.0LmEArlM/WG',
	'student1@email.com',
	(SELECT id FROM roles WHERE name ILIKE 'ROLE_STUDENT') /* ROLE */
);
INSERT INTO students (id, average_grade) VALUES (
	(SELECT id FROM users WHERE name ILIKE 'student1'),
	0
);


INSERT INTO enrollments (id, student_id, career_id) VALUES (
	gen_random_uuid(),
	(SELECT u.id FROM students s INNER JOIN users u ON s.id = u.id WHERE u.name ILIKE 'student1'),
	(SELECT id FROM careers WHERE name ILIKE 'System Engineeering')
);


INSERT INTO users (id, name, password, email, role_id) VALUES (
	gen_random_uuid(),
	'professor1',
	'$2a$12$1zmW2FVKfyx1fNGP4HlJpOs9Ki4.x2FG4YhKZsjLiwNNBiAERF9JO',
	'professor1@email.com',
	(SELECT id FROM roles WHERE name ILIKE 'ROLE_PROFESSOR') /* ROLE */
);
INSERT INTO professors (id, salary) VALUES (
	(SELECT id FROM users WHERE name ILIKE 'professor1'),
	40000
);