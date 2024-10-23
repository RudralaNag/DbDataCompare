CREATE TABLE IF NOT EXISTS employee (
    empid BIGINT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

INSERT INTO employee (firstname, lastname, address) VALUES ('John', 'Doe', '123 Main St');
INSERT INTO employee (firstname, lastname, address) VALUES ('Jane', 'Smith', '456 Elm St');
INSERT INTO employee (firstname, lastname, address) VALUES ('Ram', 'raj', '58 main road');