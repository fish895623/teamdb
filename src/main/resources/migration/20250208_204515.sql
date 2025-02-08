ALTER TABLE User
    ADD COLUMN HospitalID INT,
    ADD CONSTRAINT fk_user_hospital FOREIGN KEY (HospitalID) REFERENCES Hospital (HospitalID);

UPDATE User u
    JOIN (SELECT UserID, HospitalID
          FROM MedicalRecord
          WHERE VisitDateTime = (SELECT MAX(VisitDateTime)
                                 FROM MedicalRecord m
                                 WHERE m.UserID = MedicalRecord.UserID)) latest_records ON u.UserID = latest_records.UserID
SET u.HospitalID = latest_records.HospitalID
WHERE u.HospitalID IS NULL;
