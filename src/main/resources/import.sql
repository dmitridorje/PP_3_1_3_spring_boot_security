INSERT INTO roles (id, name) VALUES (1, 'USER'),(2,'ADMIN');

INSERT INTO users (email, username, name, surname, age, password) VALUES ('phil@phil.com', 'phil', 'philipp', 'philippovich', 37, '$2a$10$4q50uqOKuv7392r1nyaHBODqh6yLVLjeI8ZmoTaUmVjX10omTWdBW'),('nick@nick.com', 'nick', 'nick', 'nickovich', 28, '$2a$10$wjn/ECg4tTLueLKPlfu/s.XIpQdYa8KwG4nsYqzm82JPxWsYNjnj2'), ('kate@kate.com', 'kate', 'kate', 'katovich', 16, '$2a$10$JVTBp2QCVYaTGIvpjP0aQ./iHxqT7iX1ROJ/nh5ANBKsSMi.1d1C2');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 2),(2, 1),(3, 1),(3, 2);