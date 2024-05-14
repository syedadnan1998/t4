package com.example.project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.Model.Appointment;
import com.example.project.Model.Patient;
import com.example.project.repository.AppointmentRepository;
import com.example.project.repository.PatientRepository;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appRepo;

    public JSONObject registerAppointment(Appointment appointment) {
        JSONObject obj = new JSONObject();
        try {
            appRepo.save(appointment);
            obj.put("message", "Booking successful");
        } catch(Exception ex) {
            obj.put("message", "Booking failure");
        }
        return obj;
    }
    public void deleteAppointment(String appintId) {
        appRepo.deleteById(appintId);
    }

    public List<Appointment> getAllAppointments() {
        return appRepo.findAll();
    }


    public Appointment viewAppointment(String id) {
        Optional<Appointment> app = appRepo.findById(id);
        if (app.isPresent()) {
            return app.get();
        }
        return null;
    }
}
