--<ScriptOptions statementTerminator=";"/>

CREATE TABLE radiology (
		id INT8 NOT NULL
	);

CREATE UNIQUE INDEX radiology_pkey ON radiology (id ASC);

ALTER TABLE radiology ADD CONSTRAINT radiology_pkey PRIMARY KEY (id);

ALTER TABLE radiology ADD CONSTRAINT fk_radiology_id FOREIGN KEY (id)
	REFERENCES treatment (id);

