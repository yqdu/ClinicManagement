--<ScriptOptions statementTerminator=";"/>

CREATE TABLE patient (
		id INT8 NOT NULL,
		birthdate DATE,
		name VARCHAR(255),
		patientid INT8
	);

CREATE UNIQUE INDEX patient_pkey ON patient (id ASC);

ALTER TABLE patient ADD CONSTRAINT patient_pkey PRIMARY KEY (id);

