INSERT INTO user (email, password) VALUES
                                       ('user@gmail.com', '$2a$10$aV1Q1oOdT.zbONLhsl2Gm.tFPB8FiJ1Cte.XmEcbRMtnBULpArJoO'),
                                       ('test@gmail.com', '$2a$10$aV1Q1oOdT.zbONLhsl2Gm.tFPB8FiJ1Cte.XmEcbRMtnBULpArJoO'),
                                       ('green@gmail.com', '$2a$10$aV1Q1oOdT.zbONLhsl2Gm.tFPB8FiJ1Cte.XmEcbRMtnBULpArJoO');

INSERT INTO category (user_id, name) VALUES
                                         (1, '공부'),
                                         (1, '운동'),
                                         (2, '업무'),
                                         (3, '취미'),
                                         (3, '여행');

INSERT INTO task
(user_id, category_id, title, content, status, permission, shared, shared_link, start_date, end_date, expiration_date)
VALUES

(1, 1, '알고리즘 문제 풀기', '백준 DP 문제 도전', 'PROGRESS', 'EDIT', 1, '5Dorotxdu46F1P5MK113_A', '2025-08-20', '2025-08-25', '2025-08-30'),
(1, 1, 'SQL 복습', '조인과 서브쿼리 정리', 'NONE', 'VIEW', 0, NULL, '2025-08-22', '2025-08-23', NULL),

(1, 2, '런닝 5km', '아침 조깅', 'DONE', 'VIEW', 0, NULL, '2025-08-15', '2025-08-15', NULL),

(2, 3, '보고서 작성', '주간 업무 보고 정리', 'PROGRESS', 'EDIT', 1, 'link-bob-task1', '2025-08-19', '2025-08-21', '2025-08-31'),

(3, 4, '기타 연습', '코드 진행 연습하기', 'NONE', 'VIEW', 0, NULL, '2025-08-22', '2025-08-28', NULL),
(3, 5, '제주도 여행 계획', '숙소 예약 및 일정 정리', 'PROGRESS', 'EDIT', 1, 'link-charlie-task1', '2025-08-24', '2025-08-28', '2025-09-05');
