DROP TABLE IF EXISTS enrollment_classes CASCADE;
DROP TABLE IF EXISTS career_courses CASCADE;
DROP TABLE IF EXISTS classes CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS enrollments CASCADE;
DROP TABLE IF EXISTS careers CASCADE;
DROP TABLE IF EXISTS professors CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS administrators CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS roles CASCADE;

CREATE TABLE careers (
	id UUID PRIMARY KEY,
	name VARCHAR(60) NOT NULL UNIQUE
);

CREATE TABLE courses (
	id UUID PRIMARY KEY,
	name VARCHAR(60) NOT NULL UNIQUE,
	credits INTEGER NOT NULL
);

CREATE TABLE career_courses (
	career_id UUID REFERENCES careers(id) ON DELETE CASCADE,
	course_id UUID REFERENCES courses(id) ON DELETE CASCADE,
	PRIMARY KEY (career_id, course_id)
);

CREATE TABLE roles (
	id UUID PRIMARY KEY,
	role VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE users (
	id UUID PRIMARY KEY,
	first_name VARCHAR(20) NOT NULL UNIQUE,
	last_name VARCHAR(20) NOT NULL,
	username VARCHAR(45) NOT NULL UNIQUE,
	password VARCHAR(200) NOT NULL,
	email VARCHAR(60) NOT NULL UNIQUE,
	role_id UUID REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE professors (
	id UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
	salary DECIMAL NOT NULL
);

CREATE TABLE students (
	id UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
	average_grade DECIMAL NOT NULL
);

CREATE TABLE administrators (
	id UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE classes (
	id UUID PRIMARY KEY,
	capacity INTEGER NOT NULL,
	enrolled_students INTEGER NOT NULL,
	available BOOLEAN NOT NULL,
	course_id UUID REFERENCES courses(id) ON DELETE CASCADE,
	professor_id UUID REFERENCES professors(id) ON DELETE CASCADE
);

CREATE TABLE enrollments (
	id UUID PRIMARY KEY,
	student_id UUID UNIQUE REFERENCES students(id) ON DELETE CASCADE,
	career_id UUID REFERENCES careers(id) ON DELETE CASCADE
);

CREATE TABLE enrollment_classes (
	enrollment_id UUID REFERENCES enrollments(id) ON DELETE CASCADE,
	class_id UUID REFERENCES classes(id) ON DELETE CASCADE,
	semester SMALLINT NOT NULL,
	PRIMARY KEY (enrollment_id, class_id)
);

/* ================================================== TEST DATA ================================================== */
INSERT INTO careers (id, name) VALUES (gen_random_uuid(), 'Software Engineering');
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
	(SELECT id FROM careers WHERE name ILIKE 'Software Engineering'),
	(SELECT id FROM courses WHERE name ILIKE 'Algorithms I')
);

INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'Software Engineering'),
	(SELECT id FROM courses WHERE name ILIKE 'Algebra')
);

INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'Software Engineering'),
	(SELECT id FROM courses WHERE name ILIKE 'Calculus I')
);

INSERT INTO career_courses (career_id, course_id) VALUES (
	(SELECT id FROM careers WHERE name ILIKE 'Software Engineering'),
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



/* ---------------------------------- Administrators creation ---------------------------------- */

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



/** ---------------------------------- Students creation ---------------------------------- */

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
	(SELECT id FROM careers WHERE name ILIKE 'Software Engineering')
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
	(SELECT id FROM careers WHERE name ILIKE 'Software Engineering')
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



/* ---------------------------------- Professors creation ---------------------------------- */

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



/* ---------------------------------- Classes creation ---------------------------------- */
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

INSERT INTO classes (id, capacity, enrolled_students, available, course_id, professor_id) VALUES (
	gen_random_uuid(),
	0,
	0,
	FALSE,
	(SELECT id FROM courses WHERE name ILIKE 'Data Bases'),
	(SELECT id FROM users WHERE username ILIKE 'professor2.test2')
);



/* ---------------------------------- Enrollment to classes ---------------------------------- */
INSERT INTO enrollment_classes (enrollment_id, class_id, semester) VALUES (
	(SELECT e.id
		FROM users u INNER JOIN students s ON u.id = s.id
		INNER JOIN enrollments e ON e.student_id = s.id
		WHERE u.username ILIKE 'student1.test1'),
	(SELECT cl.id
		FROM classes cl INNER JOIN courses co ON co.id = cl.course_id
		INNER JOIN professors p ON p.id = cl.professor_id
		INNER JOIN users u ON p.id = u.id
		WHERE co.name ILIKE 'Algorithms I' AND u.email ILIKE 'professor1@email.com'),
	1
);