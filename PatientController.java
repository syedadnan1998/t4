package com.example.project.controller;


import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.Patient;
import com.example.project.service.PatientService;

@RestController
public class PatientController {
  @Autowired
  PatientService patService;

  @PostMapping(value = "/patients/register")
  public JSONObject registerPatient(@RequestBody Patient patient){
    return patService.registerPatient(patient);
  }
  @GetMapping(value = "/patients/list")
  public List<Patient> getPatients(){
    return patService.getPatients();
  }

  @GetMapping(value = "patients/view/{id}")
  public Patient viewPatient(@PathVariable("id") String id) {
    return patService.viewPatient(id);
  }
  @DeleteMapping(value = "patients/view/{id}")
  public Patient deletePatient(@PathVariable("id") String id) {
    return patService.deletePatient(id);
  }
  
}
