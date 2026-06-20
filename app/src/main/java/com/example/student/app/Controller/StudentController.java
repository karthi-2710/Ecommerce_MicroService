package com.example.student.app.Controller;


import java.util.List;
import com.example.student.app.Model.Student;
import com.example.student.app.Service.StudentService;
import com.example.student.app.dto.StudentRequest;
import com.example.student.app.dto.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")

public class StudentController {

    private final StudentService studentService;

    @GetMapping("/get")
    //With response entity
    public ResponseEntity<List<StudentResponse>> getStudents() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @PostMapping("/post")
    public ResponseEntity<String> addStudent(@RequestBody Student s) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(s));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable long id) {
        StudentResponse response = studentService.getStudent(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/put/{id}")
    public ResponseEntity<StudentResponse> updStudent(@PathVariable Long id, @RequestBody StudentRequest s1) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(studentService.updStudent(id, s1));
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> delStudent(@PathVariable int id) {
        return ResponseEntity.ok(studentService.delStudent(id));
    }
}


//        @GetMapping("/getfname/{id}")
//        public String getfname(@PathVariable int id){
//            return studentService.getfname(id);
//        }

//    public StudentController() {
//        stu.add(new Student(1, "Sharuk", "Alcaraz"));
//        stu.add(new Student(2, "Virat", "Kohli"));
//    }


// basic API
// @GetMapping("/hello")
// public String abc(){
// return "Hello hello";
// }

// @GetMapping("/spring")
// public String str(){
// return "spring spring";
// }
//    List<Student> stu = new ArrayList<>();

//    @GetMapping("/studentsget")
//    public List<Student>getStu() {
//        return stu;
//    }

//    @PostMapping("/studentspost")
//    public String addStu(@RequestBody Student s) {
//        stu.add(s);
//        return "Student data added";
//
//    }
//
//    @PutMapping("/studentsput/{id}")
//    public String updateStudent(@PathVariable int id, @RequestBody Student s) {
//        stu.set(id, s);
//        return "Student data updateed ";
//    }

