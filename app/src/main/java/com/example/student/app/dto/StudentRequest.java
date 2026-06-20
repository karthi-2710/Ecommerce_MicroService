package com.example.student.app.dto;

import com.example.student.app.Model.Address;
import com.example.student.app.Model.UserRole;
import lombok.Data;

@Data
public class StudentRequest {
    private String fname;
    private String lname;
    private String phone;
    private String email;
    private UserRole userRole;
    private AddressDTO address;
}
