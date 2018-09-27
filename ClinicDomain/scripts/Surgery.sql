--<ScriptOptions statementTerminator=";"/>

CREATE TABLE surgery (
		id INT8 NOT NULL,
		date DATE
	);

CREATE UNIQUE INDEX surgery_pkey ON surgery (id ASC);

ALTER TABLE surgery ADD CONSTRAINT surgery_pkey PRIMARY KEY (id);

ALTER TABLE surgery ADD CONSTRAINT fk_surgery_id FOREIGN KEY (id)
	REFERENCES treatment (id);

