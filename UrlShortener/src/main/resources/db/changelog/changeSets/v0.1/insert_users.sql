
-- changeset Natacool:insert_test_users
INSERT INTO Users (Email, Role, Status, RegisteredAt) VALUES
('admin@example.com', 'ADMIN', 'ACTIVE', '2024-05-21'),
('john@example.com', 'CLIENT', 'ACTIVE', '2024-06-21'),
('jane@example.com', 'CLIENT', 'ACTIVE', '2025-01-21'),
('alice@example.com', 'CLIENT', 'BLOCKED', '2024-08-21'),
('bob@example.com', 'CLIENT', 'DELETED', '2024-09-21'),
('ben@example.com', 'CLIENT', 'BLOCKED', '2024-10-21'),
('mike@example.com', 'CLIENT', 'ACTIVE', '2024-11-21'),
('jack@example.com', 'CLIENT', 'ACTIVE', '2024-12-21'),
('emma@example.com', 'CLIENT', 'ACTIVE', '2025-01-21');
