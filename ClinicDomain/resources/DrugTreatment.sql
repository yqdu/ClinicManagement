--<ScriptOptions statementTerminator=";"/>

CREATE TABLE drugtreatment (
		id INT8 NOT NULL,
		dosage FLOAT8,
		drug VARCHAR(255)
	);

CREATE UNIQUE INDEX drugtreatment_pkey ON drugtreatment (id ASC);

ALTER TABLE drugtreatment ADD CONSTRAINT drugtreatment_pkey PRIMARY KEY (id);

ALTER TABLE drugtreatment ADD CONSTRAINT fk_drugtreatment_id FOREIGN KEY (id)
	REFERENCES treatment (id);

