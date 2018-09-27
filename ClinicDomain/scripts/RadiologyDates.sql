--<ScriptOptions statementTerminator=";"/>

CREATE TABLE radiology_dates (
		radiology_id INT8,
		dates DATE
	);

ALTER TABLE radiology_dates ADD CONSTRAINT fk_radiology_dates_radiology_id FOREIGN KEY (radiology_id)
	REFERENCES treatment (id);

