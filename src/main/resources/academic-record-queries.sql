SELECT * FROM careers;
SELECT * FROM courses;
SELECT * FROM career_courses;
SELECT * FROM users;
SELECT * FROM professors;
SELECT * FROM students;
SELECT * FROM classes;
SELECT * FROM enrollments;
SELECT * FROM enrollment_classes;

DELETE FROM users;

/* Get all professors */
SELECT users.id AS user_id, professors.id AS prof_id, name, email, salary
FROM users INNER JOIN professors
ON users.id = professors.id;

/* Get students */
SELECT u.id, u.name AS student_name, u.email, average_grade
FROM users u INNER JOIN students s ON u.id = s.id;

SELECT u.id, u.name AS student_name, u.email, average_grade, e.id
FROM users u INNER JOIN students s ON u.id = s.id
INNER JOIN enrollments e ON e.student_id = s.id;

/* Get courses and classes with professor names */
SELECT cl.id AS class_id, co.id AS course_id, co.name AS course_name, credits, available, p.id AS prof_id, u.name AS prof_name, u.email AS prof_email, salary
FROM classes cl INNER JOIN courses co ON co.id = cl.course_id
INNER JOIN professors p ON p.id = cl.professor_id
INNER JOIN users u ON p.id = u.id;

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
SELECT ca.id AS career_id, ca.name AS career_name, co.id AS course_id, co.name AS course_name, p.id AS prof_id, u.name AS prof_name
FROM careers ca INNER JOIN career_classes cc ON ca.id = cc.career_id 
INNER JOIN classes cl ON cc.class_id = cl.id 
INNER JOIN professors p ON p.id = cl.professor_id 
INNER JOIN users u ON u.id = p.id 
INNER JOIN courses co ON co.id = cl.course_id;

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
SELECT e.id AS enrollment_id, u.id AS student_id, u.name AS student, semester, cl.id AS class_id, capacity, enrolled_students, available, co.id AS course_id, co.name AS course, credits, prof.professor_id, professor_name
FROM users u INNER JOIN students s ON u.id = s.id
INNER JOIN enrollments e ON e.student_id = s.id
INNER JOIN enrollment_classes ec ON e.id = ec.enrollment_id
INNER JOIN classes cl ON cl.id = ec.class_id
INNER JOIN courses co ON co.id = cl.course_id
INNER JOIN (
			SELECT p.id AS professor_id, u.name AS professor_name
		   	FROM professors p INNER JOIN users u ON u.id = p.id
		   ) AS prof ON cl.professor_id = prof.professor_id
WHERE s.id = '488d91ea-4392-4cb7-adc7-e4f304497add' AND semester = 1;

SELECT e.id AS enrollment_id, u.id AS student_id, u.name AS student, cl.id AS class_id, co.id AS course_id, co.name AS course, credits, semester
FROM users u INNER JOIN students s ON u.id = s.id
INNER JOIN enrollments e ON e.student_id = s.id
INNER JOIN enrollment_classes ec ON e.id = ec.enrollment_id
INNER JOIN classes cl ON cl.id = ec.class_id
INNER JOIN courses co ON co.id = cl.course_id
WHERE s.id = '4054438a-26b5-4606-9dd8-76ec2ff692a4';

SELECT e.id AS enrollment_id, u.id AS student_id, u.name AS student, e.career_id AS career_id, c.name AS career
FROM users u INNER JOIN students s ON u.id = s.id
INNER JOIN enrollments e ON e.student_id = s.id
INNER JOIN careers c ON c.id = e.career_id
WHERE s.id = '4054438a-26b5-4606-9dd8-76ec2ff692a4';
