package com.example.project.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.project.Model.Appointment;
import com.example.project.service.AppointmentService;

import java.util.List;

@RestController
public class AppointmentController {
    @Autowired
    AppointmentService appService;

    @PostMapping(value = "/appointment/register")
    public JSONObject registerAppointment(@RequestBody Appointment appointment) {
      return appService.registerAppointment(appointment);
    }

    @GetMapping(value = "/appointment/list")
    public List<Appointment> getAppointments() {
      return appService.getAllAppointments();
    }

    @GetMapping(value = "/appointment/view/{id}")
    @PreAuthorize("hasRole('USER')")
    public Appointment viewAppointments(@PathVariable("id") String id) {
    	System.out.println(id);
      return appService.viewAppointment(id);
    }

    

}
