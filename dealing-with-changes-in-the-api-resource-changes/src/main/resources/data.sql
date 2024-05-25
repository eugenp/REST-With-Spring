INSERT INTO Campaign(id, code, name, description) VALUES ('ebcbeadc-c7de-45ec-8c45-7d23a2554cc6', 'C1', 'Campaign 1', 'Description of Campaign 1');
INSERT INTO Campaign(id, code, name, description) VALUES ('20da99d1-2991-4777-b7b2-dcb14b08c469', 'C2', 'Campaign 2', 'About Campaign 2');
INSERT INTO Campaign(id, code, name, description) VALUES (uuid(), 'C3', 'Campaign 3', 'About Campaign 3');
INSERT INTO Campaign(id, code, name, description) VALUES ('20da99d1-2991-4777-b7b2-dcb14b08c468', 'C4', 'Campaign 4', 'About Campaign 4');

INSERT INTO Worker(id, first_name, last_name) VALUES (default, 'John', 'Doe');
INSERT INTO Worker(id, first_name, last_name) VALUES (default, 'Mike', 'Other');

INSERT INTO Service_User(worker_id, email) VALUES (1, 'john@test.com');
INSERT INTO Service_User(worker_id, email) VALUES (2, 'mike@other.com');

INSERT INTO Task(id, uuid, name, due_date, description, campaign_id, status) VALUES (default, uuid(), 'Task 1', '2030-01-12', 'Task 1 Description', 'ebcbeadc-c7de-45ec-8c45-7d23a2554cc6', 0);
INSERT INTO Task(id, uuid, name, due_date, description, campaign_id, status) VALUES (default, uuid(), 'Task 2', '2030-02-10', 'Task 2 Description', 'ebcbeadc-c7de-45ec-8c45-7d23a2554cc6', 0);
INSERT INTO Task(id, uuid, name, due_date, description, campaign_id, status) VALUES (default, uuid(), 'Task 3', '2030-03-16', 'Task 3 Description', 'ebcbeadc-c7de-45ec-8c45-7d23a2554cc6', 0);
INSERT INTO Task(id, uuid, name, due_date, description, campaign_id, status, assignee_id) VALUES (default, uuid(), 'Task 4', '2030-06-25', 'Task 4 Description', '20da99d1-2991-4777-b7b2-dcb14b08c469', 0, 1);