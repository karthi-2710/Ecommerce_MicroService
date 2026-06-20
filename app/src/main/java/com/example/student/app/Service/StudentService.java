package com.example.student.app.Service;

import com.example.student.app.Model.Address;
import com.example.student.app.Model.Student;
import com.example.student.app.Repository.StudentRepository;
import com.example.student.app.dto.AddressDTO;
import com.example.student.app.dto.StudentRequest;
import com.example.student.app.dto.StudentResponse;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService {
    //Implementing to DB
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentResponse> getAllStudent() {
        return studentRepository.findAll().stream()
            .map(this::MapToUserResponse)
            .collect(Collectors.toList());
    }

    private StudentResponse MapToUserResponse(Student student) {
        StudentResponse response=new StudentResponse();
        response.setReg(student.getReg());
        response.setFirstname(student.getFname());
        response.setLastname(student.getLname());
        response.setEmail(student.getEmail());
        response.setPhone(student.getPhone());
        response.setRole(student.getRole());
        if(student.getAddress()!=null){
            AddressDTO addressDTO =new AddressDTO();
            response.setAddress(addressDTO);
            addressDTO.setStreet(student.getAddress().getStreet());
            addressDTO.setCity(student.getAddress().getCity());
            addressDTO.setState(student.getAddress().getState());
            addressDTO.setCountry(student.getAddress().getCountry());
            addressDTO.setZipcode(student.getAddress().getZipcode());
        }
        return response;
    }

    public String addStudent(Student s) {
        studentRepository.save(s);
        return "Student saved to DB";
    }

    public StudentResponse getStudent(long id) {
        return studentRepository.findById(id).map(this::MapToUserResponse).orElse(null);
    }

    public StudentResponse updStudent(Long id, StudentRequest s1) {
        Student student=studentRepository.findById(id).orElse(null);
        if(student==null){
            return null;
        }
        student.setFname(s1.getFname());
        student.setLname(s1.getLname());
        student.setEmail(s1.getEmail());
        student.setRole(s1.getUserRole());
        student.setPhone(s1.getPhone());
        if(s1.getAddress()!=null){
            Address address =student.getAddress();
            if(address==null){
                address=new Address();
            }
            address.setStreet(s1.getAddress().getStreet());
            address.setCity(s1.getAddress().getCity());
            address.setState(s1.getAddress().getState());
            address.setZipcode(s1.getAddress().getZipcode());
            address.setCountry(s1.getAddress().getCountry());
            student.setAddress(address);
        }
        studentRepository.save(student);
        return MapToUserResponse(student);


    }

    public String delStudent(long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return "Deleted from DB";
        }
        return "Not found";
    }
}


    //    public List<Student> getAllStudent() {
//        return students;
//    }


    //    public String addStudent(Student s){
//        students.add(s);
//        return " Stu Added success";
//    }


    //    public Student getStudent(long id) {
//        for (Student s : students) {
//            if (s.getReg() == id) {
//                return s;
//            }
//        }
//        return null;
//
//        //Using Stream
//        return students.stream().filter(s->s.getReg()==id).findFirst().orElse(null);
//    }


    //    public String upStudent(long id, Student s1) {
//        for (int i = 0; i < students.size(); i++) {
//            if (students.get(i).getReg() == id) {
//                students.set(i, s1);
//                return "Student Updated";
//            }
//        }
//        return "Student Not Found";
//
//        //Using Stream
//        Optional<Student> isPresent=students.stream().filter(st ->st.getReg()==id ).findFirst();
//        if(isPresent.isPresent()){
//            Student student=isPresent.get();
//            student.setFname(s1.getFname());
//            student.setLname(s1.getLname());
//            return "Updates done using Stream";
//
//        }
//        return "Not found";
//    }

//        if (studentRepository.existsById(id)) {
//            s1.setReg(id);
//            studentRepository.save(s1);
//            return "Updated in DB";
//        }
//        return "Not found";

    //        public String delStudent(long id) {
//        for (int i = 0; i < students.size(); i++) {
//            if (students.get(i).getReg() == id) {
//                students.remove(i);
//                return "Stu data deleted";
//            }
//        }
//        return "Student not found";
//    }
//    private final List<Student> students=new ArrayList<>();
//    public StudentService(){
//        students.add(new Student(1L, "A", "B"));
//        students.add(new Student(2L, "C", "D"));
//        students.add(new Student(3L, "E", "F"));
//    }



//    public String getfname(int id) {
//        for(int i=0;i<students.size();i++){
//            if(students.get(i).getReg()==id){
//                return students.get(i).getFname();
//            }
//        }
//        return "Not found";
//    }
//}
