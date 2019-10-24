INSERT IGNORE INTO roles(name) VALUES('ROLE_USER');
INSERT IGNORE INTO roles(name) VALUES('ROLE_ADMIN');
INSERT IGNORE INTO roles(name) VALUES('ROLE_GFL');

INSERT IGNORE INTO users (username, email, password)
SELECT 'admin', 'admin@web.de', '$2a$10$trkenZXZ71ttKNHdocd19.AlPDCsXrhpCFg4e/ZmkMjNJQ4sNrtaS'
WHERE NOT EXISTS (SELECT * FROM users WHERE username='admin');

INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT '1', '1';

INSERT IGNORE INTO user_roles (user_id, role_id)
SELECT '1', '2';
