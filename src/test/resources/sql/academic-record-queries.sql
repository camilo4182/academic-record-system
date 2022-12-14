SELECT * FROM careers;
SELECT * FROM courses;
SELECT * FROM career_courses;
SELECT * FROM users;
SELECT * FROM professors;
SELECT * FROM students;
SELECT * FROM administrators;
SELECT * FROM roles;
SELECT * FROM classes;
SELECT * FROM enrollments;
SELECT * FROM enrollment_classes;

SELECT s.id AS student_id, u.first_name AS first_name, u.last_name AS last_name, u.username AS username,
u.email AS student_email, average_grade, c.id AS career_id, c.name AS career
FROM students s INNER JOIN users u ON s.id = u.id
INNER JOIN enrollments e ON e.student_id = s.id
INNER JOIN careers c ON e.career_id = c.id
WHERE u.id = 'c2e04860-bb02-4136-b338-17e1fdbd1491';

SELECT u.id AS prof_id, cl.id AS class_id, capacity, enrolled_students, available,
co.id AS course_id, co.name AS course, credits
FROM professors p INNER JOIN users u ON u.id = p.id
INNER JOIN classes cl ON cl.professor_id = p.id
INNER JOIN courses co ON cl.course_id = co.id
WHERE u.id = '2751b800-7971-4811-8b01-efb7a38d49b2';

SELECT u.id AS user_id, u.username AS name, u.password AS password, u.email AS email, r.name AS role
FROM users u INNER JOIN roles r ON u.role_id = r.id
WHERE u.username ILIKE 'student1.test1';

SELECT s.id AS student_id, u.first_name AS first_name, u.last_name AS last_name, u.username AS username, u.email AS email, average_grade, c.id AS career_id, c.name AS career
FROM students s INNER JOIN users u ON s.id = u.id
INNER JOIN enrollments e ON e.student_id = s.id
INNER JOIN careers c ON e.career_id = c.id
WHERE u.id = 'f830af09-9a80-4486-9797-70fd42704058';

/* Get users and their roles */
SELECT u.id AS id, u.first_name AS first_name, u.last_name AS last_name, u.username AS name, u.password AS password, u.email AS email, r.name AS role
FROM users u INNER JOIN roles r ON u.role_id = r.id;

/* Get all professors */
SELECT users.id AS user_id, professors.id AS prof_id, first_name , last_name, username, email, salary
FROM users INNER JOIN professors
ON users.id = professors.id;

/* Get students */
SELECT u.id, first_name, last_name, username, password, email, average_grade
FROM users u INNER JOIN students s ON u.id = s.id;

SELECT u.id, u.username AS student_name, u.email, average_grade, e.id
FROM users u INNER JOIN students s ON u.id = s.id
INNER JOIN enrollments e ON e.student_id = s.id;

/* Get courses and classes with professor names */
SELECT cl.id AS class_id, co.id AS course_id, co.name AS course_name, credits, available, p.id AS prof_id, 
u.first_name AS prof_first_name, u.last_name AS prof_last_name, u.email AS prof_email, salary
FROM classes cl INNER JOIN courses co ON co.id = cl.course_id
INNER JOIN professors p ON p.id = cl.professor_id
INNER JOIN users u ON p.id = u.id;

SELECT cl.id AS class_id, co.id AS course_id, co.name AS course_name, credits, available, p.id AS prof_id, 
u.first_name AS prof_first_name, u.last_name AS prof_last_name, u.email AS prof_email, salary
FROM classes cl INNER JOIN courses co ON co.id = cl.course_id
INNER JOIN professors p ON p.id = cl.professor_id
INNER JOIN users u ON p.id = u.id
WHERE co.name ILIKE 'Algorithms I' AND u.email ILIKE 'professor1@email.com';

/* Get careers with their courses */
SELECT ca.id AS career_id, ca.name AS career, co.id AS course_id, co.name AS course
FROM careers ca INNER JOIN career_courses cc ON ca.id = cc.career_id
INNER JOIN courses co ON co.id = cc.course_id;

SELECT ca.id AS career_id, ca.name AS career, co.id AS course_id, co.name AS course, credits
FROM careers ca INNER JOIN career_courses cc ON ca.id = cc.career_id
INNER JOIN courses co ON co.id = cc.course_id
WHERE ca.id = 'f6c6bc63-93b3-48db-b988-484f7f3f0e73'
GROUP BY ca.id, ca.name, co.id, co.name, credits
ORDER BY career;

/* Get careers with their courses and professors */


/* Get careers and their courses */
SELECT ca.name AS career_name, co.name AS course_name
FROM careers ca INNER JOIN career_classes cc ON ca.id = cc.career_id 
INNER JOIN classes cl ON cc.class_id = cl.id 
INNER JOIN courses co ON co.id = cl.course_id
GROUP BY career_name, course_name
ORDER BY career_name;

/* Get courses from a career */
SELECT ca.name AS career_name, co.name AS course_name, credits
FROM careers ca INNER JOIN career_classes cc ON ca.id = cc.career_id 
INNER JOIN classes cl ON cc.class_id = cl.id 
INNER JOIN courses co ON co.id = cl.course_id
WHERE ca.name ILIKE 'system engineering'
GROUP BY career_name, course_name, credits
ORDER BY career_name;

/* Get enrollment information with student, course and professor */
SELECT e.id AS enrollment_id, u.id AS student_id, u.first_name AS first_name, u.last_name AS last_name, u.username AS username, semester, cl.id AS class_id, capacity,
enrolled_students, available, co.id AS course_id, co.name AS course, credits, prof.professor_id, professor_first_name, professor_last_name,
e.career_id AS career_id, c.name AS career
FROM users u INNER JOIN students s ON u.id = s.id
INNER JOIN enrollments e ON e.student_id = s.id
INNER JOIN careers c ON c.id = e.career_id
INNER JOIN enrollment_classes ec ON e.id = ec.enrollment_id
INNER JOIN classes cl ON cl.id = ec.class_id
INNER JOIN courses co ON co.id = cl.course_id
INNER JOIN (
			SELECT p.id AS professor_id, u.first_name AS professor_first_name, u.last_name AS professor_last_name
			FROM professors p INNER JOIN users u ON u.id = p.id
		   ) AS prof ON cl.professor_id = prof.professor_id
WHERE s.id = 'c2e04860-bb02-4136-b338-17e1fdbd1491';

SELECT e.id AS enrollment_id, u.id AS student_id, u.name AS student, cl.id AS class_id, co.id AS course_id, co.name AS course, credits, semester
FROM users u INNER JOIN students s ON u.id = s.id
INNER JOIN enrollments e ON e.student_id = s.id
INNER JOIN enrollment_classes ec ON e.id = ec.enrollment_id
INNER JOIN classes cl ON cl.id = ec.class_id
INNER JOIN courses co ON co.id = cl.course_id
WHERE s.id = '4054438a-26b5-4606-9dd8-76ec2ff692a4';

SELECT e.id AS enrollment_id, u.id AS student_id, u.first_name AS first_name, u.last_name AS last_name, u.username AS username, e.career_id AS career_id, c.name AS career
FROM users u INNER JOIN students s ON u.id = s.id
INNER JOIN enrollments e ON e.student_id = s.id
INNER JOIN careers c ON c.id = e.career_id
WHERE s.id = 'f830af09-9a80-4486-9797-70fd42704058';
