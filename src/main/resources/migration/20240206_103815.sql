CREATE TABLE IF NOT EXISTS User
(
    UserID        INT PRIMARY KEY AUTO_INCREMENT,
    Name          VARCHAR(50)  NOT NULL,
    BirthDate     DATE,
    Gender        CHAR(1),
    ContactNumber VARCHAR(20),
    Password      VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS HealthData
(
    HealthDataID           INT PRIMARY KEY AUTO_INCREMENT,
    UserID                 INT,
    MeasurementDateTime    DATETIME,
    Height                 FLOAT,
    Weight                 FLOAT,
    BodyFatPercentage      FLOAT,
    BloodPressureSystolic  INT,
    BloodPressureDiastolic INT,
    BloodSugar             FLOAT,
    HeartRate              INT,
    StressLevel            INT,
    FOREIGN KEY (UserID) REFERENCES User (UserID)
);

CREATE TABLE IF NOT EXISTS Exercise
(
    ExerciseID       INT PRIMARY KEY AUTO_INCREMENT,
    UserID           INT,
    ExerciseDateTime DATETIME,
    ExerciseType     VARCHAR(50),
    Duration         INT,
    CaloriesBurned   INT,
    FOREIGN KEY (UserID) REFERENCES User (UserID)
);

CREATE TABLE IF NOT EXISTS Diet
(
    DietID       INT PRIMARY KEY AUTO_INCREMENT,
    UserID       INT,
    MealDateTime DATETIME,
    FoodName     VARCHAR(100),
    Quantity     FLOAT,
    Calories     INT,
    FOREIGN KEY (UserID) REFERENCES User (UserID)
);

CREATE TABLE IF NOT EXISTS Hospital
(
    HospitalID    INT PRIMARY KEY AUTO_INCREMENT,
    HospitalName  VARCHAR(100) NOT NULL,
    Address       VARCHAR(255),
    ContactNumber VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS MedicalRecord
(
    MedicalRecordID INT PRIMARY KEY AUTO_INCREMENT,
    UserID          INT,
    HospitalID      INT,
    VisitDateTime   DATETIME,
    Diagnosis       TEXT,
    Prescription    TEXT,
    FOREIGN KEY (UserID) REFERENCES User (UserID),
    FOREIGN KEY (HospitalID) REFERENCES Hospital (HospitalID)
);
