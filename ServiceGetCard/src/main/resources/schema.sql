DROP TABLE IF EXISTS Ages;
CREATE TABLE Ages (
    ageId INT AUTO_INCREMENT PRIMARY KEY,
    age INT NOT NULL
);

DROP TABLE IF EXISTS Cards;
CREATE TABLE Cards (
    cardId INT AUTO_INCREMENT PRIMARY KEY,
    cardName NVARCHAR(25) NOT NULL
);

DROP TABLE IF EXISTS Passions;
CREATE TABLE Passions (
    passionId INT AUTO_INCREMENT PRIMARY KEY,
    passionName NVARCHAR(25) NOT NULL
);

DROP TABLE IF EXISTS Salarys;
CREATE TABLE Salarys (
    salaryId INT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL
);

DROP TABLE IF EXISTS Profiles;
CREATE TABLE Profiles (
    passionId INT NOT NULL ,
    minSalaryId INT NOT NULL,
    maxSalaryId INT NOT NULL,
    minAgeId INT NOT NULL,
    maxAgeId INT NOT NULL,
    cardId INT NOT NULL,
    FOREIGN KEY (passionId) REFERENCES Passions (passionId),
    FOREIGN KEY (minSalaryId) REFERENCES Salarys (salaryId),
    FOREIGN KEY (maxSalaryId) REFERENCES Salarys (salaryId),
    FOREIGN KEY (minAgeId) REFERENCES Ages (ageId),
    FOREIGN KEY (maxAgeId) REFERENCES Ages (ageId),
    FOREIGN KEY (cardId) REFERENCES Cards (cardId)
);
