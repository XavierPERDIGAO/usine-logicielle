USE Tasks;

CREATE TABLE IF NOT EXISTS Task (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    body MEDIUMTEXT NULL
);