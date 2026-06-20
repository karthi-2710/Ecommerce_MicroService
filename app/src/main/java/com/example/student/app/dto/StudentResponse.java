package com.example.student.app.dto;

import com.example.student.app.Model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Long reg;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private UserRole role;
    private AddressDTO address;
}
