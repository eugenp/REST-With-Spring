INSERT INTO Campaign(id, code, name, description) VALUES (default, 'C1', 'Campaign 1', 'Description of Campaign 1');
INSERT INTO Campaign(id, code, name, description) VALUES (default, 'C2', 'Campaign 2', 'About Campaign 2');
INSERT INTO Campaign(id, code, name, description) VALUES (default, 'C3', 'Campaign 3', 'About Campaign 3');

INSERT INTO Worker(id, email, first_name, last_name) VALUES (default, 'john@test.com', 'John', 'Doe');

INSERT INTO Task(id, uuid, name, due_date, description, campaign_id, status, estimated_hours) VALUES (default, uuid(), 'Task 1', '2030-01-12', 'Task 1 Description', 1, 0, 8);
INSERT INTO Task(id, uuid, name, due_date, description, campaign_id, status) VALUES (default, uuid(), 'Task 2', '2030-02-10', 'Task 2 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, campaign_id, status) VALUES (default, uuid(), 'Task 3', '2030-03-16', 'Task 3 Description', 1, 0);
INSERT INTO Task(id, uuid, name, due_date, description, campaign_id, status, assignee_id) VALUES (default, uuid(), 'Task 4', '2030-06-25', 'Task 4 Description', 2, 0, 1);