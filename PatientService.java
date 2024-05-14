package com.example.project.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.Model.Patient;
import com.example.project.repository.PatientRepository;

@Service
public class PatientService {
  @Autowired
  PatientRepository patientRepo;

  public JSONObject registerPatient(Patient patient) {
    try {
      JSONObject obj = new JSONObject();
      obj.put("message", "Registration successful");
      patientRepo.save(patient);
      return obj;
    } catch (Exception ex) {
      JSONObject obj = new JSONObject();
      obj.put("message", "Registration failute");
      return obj;
    }

  }

  public List<Patient> getPatients() {
    return patientRepo.findAll();
  }

  public Patient viewPatient(String id) {
    Optional<Patient> user = patientRepo.findById(id);
    if (user.isPresent()) {
      return user.get();
    }
    return null;

  }
  public Patient deletePatient(String id) {
    Optional<Patient> user = patientRepo.findById(id);
    if (user.isPresent()) {
      patientRepo.delete(user.get());
      return user.get();
    }
    return null;

  }

}
