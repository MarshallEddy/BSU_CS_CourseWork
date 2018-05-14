SET GLOBAL max_allowed_packet = 1024*1024*14;

DROP DATABASE TodoListTaskManager;
CREATE DATABASE IF NOT EXISTS TodoListTaskManager;
USE TodoListTaskManager;


CREATE TABLE IF NOT EXISTS tasks(
    task_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    task_label VARCHAR(500) NOT NULL,
    task_create_date DATE NOT NULL,
    task_due_date DATE,
    task_status ENUM('active', 'completed', 'canceled') NOT NULL
);

CREATE TABLE IF NOT EXISTS tags(
    tag_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    tag_text VARCHAR(500) NOT NULL
);

CREATE TABLE IF NOT EXISTS taggedTasks(
    taggedTask_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    task_id INTEGER NOT NULL REFERENCES task,
    FOREIGN KEY (task_id) REFERENCES tasks (task_id),
    INDEX (task_id),

    tag_id INTEGER NOT NULL REFERENCES tag,
    FOREIGN KEY (tag_id) REFERENCES tags (tag_id),
    INDEX (tag_id)
);

