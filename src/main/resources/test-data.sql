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

INSERT INTO roles (id, name) VALUES (gen_random_uuid(), 'ADMIN');
INSERT INTO roles (id, name) VALUES (gen_random_uuid(), 'STUDENT');
INSERT INTO roles (id, name) VALUES (gen_random_uuid(), 'PROFESSOR');


INSERT INTO users (id, name, password, email, role_id) VALUES (
	gen_random_uuid(),
	'admin1',
	'1234',
	'test_admin1@email.com',
	(SELECT id FROM roles WHERE name ILIKE 'admin')
);
INSERT INTO administrators (id) VALUES (
	(SELECT id FROM users WHERE name ILIKE 'admin1')
);



INSERT INTO users (id, name, password, email, role_id) VALUES (
	gen_random_uuid(),
	'admin2',
	'$2a$12$v5BCniWLT724a4TUXwRreumxKz9duqIdnjRFfS8jSeS3BsClgCQte',
	'test_admin2@email.com',
	(SELECT id FROM roles WHERE name ILIKE 'admin')
);
INSERT INTO administrators (id) VALUES (
	(SELECT id FROM users WHERE name ILIKE 'admin2')
);