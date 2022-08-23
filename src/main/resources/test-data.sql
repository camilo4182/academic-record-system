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

INSERT INTO roles (id, name) VALUES ('43ae5336-b80e-481e-91b9-63de834c842d', 'ADMIN');
INSERT INTO roles (id, name) VALUES (gen_random_uuid(), 'STUDENT');
INSERT INTO roles (id, name) VALUES (gen_random_uuid(), 'PROFESSOR');

INSERT INTO users (id, name, password, email, role_id) VALUES (
	'b4bf6713-473c-431c-9558-4d41eab67983',
	'TestAdmin',
	'password',
	'test_admin@email.com',
	'43ae5336-b80e-481e-91b9-63de834c842d'
);

INSERT INTO administrators (id) VALUES ('b4bf6713-473c-431c-9558-4d41eab67983');