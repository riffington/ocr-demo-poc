-- Clear Data
SET SQL_SAFE_UPDATES = 0;
DELETE FROM task;
DELETE FROM document_word;
DELETE FROM document_line;
DELETE FROM document_paragraph;
DELETE FROM document_area;
DELETE FROM document_page;
DELETE FROM document;
DELETE FROM baseline_tuple;
DELETE FROM bounding_box;
DELETE FROM user_profile;
SET SQL_SAFE_UPDATES = 1;

-- Remove Tables
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS document_word;
DROP TABLE IF EXISTS document_line;
DROP TABLE IF EXISTS document_paragraph;
DROP TABLE IF EXISTS document_area;
DROP TABLE IF EXISTS document_page;
DROP TABLE IF EXISTS document;
DROP TABLE IF EXISTS baseline_tuple;
DROP TABLE IF EXISTS bounding_box;
DROP TABLE IF EXISTS user_profile;

-- Create Tables
CREATE TABLE IF NOT EXISTS bounding_box (
	id INT NOT NULL AUTO_INCREMENT,
    top_left_x INT NOT NULL,
    top_left_y INT NOT NULL,
    bottom_right_x INT NOT NULL,
    bottom_right_y INT NOT NULL,
	created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS baseline_tuple (
	id INT NOT NULL AUTO_INCREMENT,
    value_a FLOAT(6),
    value_b FLOAT(6),
	created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS task (
	id INT NOT NULL AUTO_INCREMENT,
    document_id INT NOT NULL,
	task_type VARCHAR(100) NOT NULL,
    related_id INT,
    image_ref VARCHAR(500),
    is_complete TINYINT NOT NULL DEFAULT 0,
	created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY ( id ),
	CONSTRAINT task_doc_fk FOREIGN KEY (document_id) REFERENCES document (id)
);

CREATE TABLE IF NOT EXISTS document (
	id INT NOT NULL AUTO_INCREMENT,
	guid VARCHAR(50) NOT NULL,
    content_type VARCHAR(100),
	created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS document_page (
	id INT NOT NULL AUTO_INCREMENT,
    id_name VARCHAR(100),
	title VARCHAR(100) NOT NULL,
	page_number INT NOT NULL DEFAULT 0,
    document_id INT NOT NULL,
    bounding_box_id INT,
	created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY ( id ),
	CONSTRAINT doc_fk FOREIGN KEY (document_id) REFERENCES document (id),
	CONSTRAINT doc_page_box_fk FOREIGN KEY (bounding_box_id) REFERENCES bounding_box (id)
);

CREATE TABLE IF NOT EXISTS document_area (
	id INT NOT NULL AUTO_INCREMENT,
    id_name VARCHAR(100),
    page_id INT NOT NULL,
    bounding_box_id INT,
	created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY ( id ),
	CONSTRAINT doc_page_fk FOREIGN KEY (page_id) REFERENCES document_page (id),
	CONSTRAINT doc_area_box_fk FOREIGN KEY (bounding_box_id) REFERENCES bounding_box (id)
);

CREATE TABLE IF NOT EXISTS document_paragraph (
	id INT NOT NULL AUTO_INCREMENT,
    id_name VARCHAR(100),
    area_id INT NOT NULL,
    lang VARCHAR(100),
    bounding_box_id INT,
	created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY ( id ),
	CONSTRAINT doc_area_fk FOREIGN KEY (area_id) REFERENCES document_area (id),
	CONSTRAINT doc_parag_box_fk FOREIGN KEY (bounding_box_id) REFERENCES bounding_box (id)
);

CREATE TABLE IF NOT EXISTS document_line (
	id INT NOT NULL AUTO_INCREMENT,
    id_name VARCHAR(100),
    paragraph_id INT NOT NULL,
    x_size FLOAT(6),
    x_descenders FLOAT(6),
    x_ascenders FLOAT(6),
    baseline_tuple_id INT,
    bounding_box_id INT,
	created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY ( id ),
	CONSTRAINT doc_para_fk FOREIGN KEY (paragraph_id) REFERENCES document_paragraph (id),
	CONSTRAINT doc_line_tuple_fk FOREIGN KEY (baseline_tuple_id) REFERENCES baseline_tuple (id),
	CONSTRAINT doc_line_box_fk FOREIGN KEY (bounding_box_id) REFERENCES bounding_box (id)
);

CREATE TABLE IF NOT EXISTS document_word (
	id INT NOT NULL AUTO_INCREMENT,
    id_name VARCHAR(100),
    line_id INT NOT NULL,
    text_value VARCHAR(1000),
    corrected_text_value VARCHAR(1000),
    confidence INT,
    bounding_box_id INT,
	created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY ( id ),
	CONSTRAINT doc_line_fk FOREIGN KEY (line_id) REFERENCES document_line (id),
	CONSTRAINT doc_word_box_fk FOREIGN KEY (bounding_box_id) REFERENCES bounding_box (id)
);

CREATE TABLE IF NOT EXISTS user_profile (
	id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    image_name VARCHAR(100),
    image_type VARCHAR(5),
    creds_username VARCHAR(100) NOT NULL,
    creds_password VARCHAR(100) NOT NULL,
    user_role VARCHAR(50) NOT NULL,
	created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    is_deleted TINYINT NOT NULL DEFAULT 0,
	PRIMARY KEY ( id )
);

-- SAMPLE USERS
-- insert into user_profile (first_name, last_name, image_name, image_type, creds_username, creds_password, user_role) values ('Darwin', 'Peterson', 'avatar2', 'png', 'user1', '$2a$10$XCqJoMC4DSvidChtjlnpA.PbJkk7Ucu6RE.l0vx0Q4Pesp8wOz1/y', 'USER');
-- insert into user_profile (first_name, last_name, image_name, image_type, creds_username, creds_password, user_role) values ('Angela', 'Han', 'avatar7', 'png', 'user2', '$2a$10$z1OfWbfziwc23MWnUoYryu.fh2jHJkpgl6ETZ92l9Q2x0zzlhqYFC', 'USER');
-- insert into user_profile (first_name, last_name, image_name, image_type, creds_username, creds_password, user_role) values ('Praneeth', 'Patnaik', 'avatar5', 'png', 'admin', '$2a$10$r4YyFG/kCil14vDkxIIUo.l0u6M1iaVhzsXkER3TUT0ILzcIF.u16', 'ADMIN');

-- SAMPLE DOCUMENTS
-- insert into document (guid, content_type) values ('3e9857b3-60ec-41b1-affb-5ebeb50ee936', 'Briefs');
-- insert into document (guid, content_type) values ('98d83a54-8221-4e1b-82a0-93bc89fd5f13', 'Trials');
-- insert into document (guid, content_type) values ('c54bd83b-f05b-4e4e-aa63-8475cd862d3e', 'Statutes');
