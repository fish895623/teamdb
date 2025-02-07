package org.example.model;


import java.time.LocalDateTime;
import java.util.Date;

public class MedicalRecord {
  public int MedicalRecordID;
  public int UserID;
  public int HospitalID;
  public Date VisitDateTime;
  public String Diagnosis;
  public String Prescription;
  public LocalDateTime Now;
}
