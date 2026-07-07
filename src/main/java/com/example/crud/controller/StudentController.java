package com.example.crud.controller;

import com.example.crud.dto.CreateStudentRequestDto;
import com.example.crud.dto.CreateStudentResponseDto;
import com.example.crud.entity.Student;
import com.example.crud.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    // create student
    @PostMapping
    public ResponseEntity<CreateStudentResponseDto> createStudent(@RequestBody CreateStudentRequestDto studentReqDto){
        CreateStudentResponseDto studentResDto = studentService.createStudent(studentReqDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(studentResDto);
    }

    // get single student
    @GetMapping("{id}")
    public ResponseEntity<Student> findStudent(@PathVariable Long id){
        Student student = studentService.findStudent(id);

        return ResponseEntity.ok(student);
    }
    // get all students
    @GetMapping
    public ResponseEntity<List<Student>> findAllStudent(){
        List<Student> studentListRes = studentService.findAllStudents();
        return ResponseEntity.ok(studentListRes);
    }

    // update a student.
    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student){
        Student studentRes = studentService.updateStudent(id,student);
        return ResponseEntity.ok(studentRes);
    }

    // delete a student permanently
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        String res = studentService.deleteStudent(id);

        return ResponseEntity.ok(res);
    }
    @PatchMapping("{id}")
    public ResponseEntity<String> deleteStudentSoftly(@PathVariable Long id){
        String res = studentService.deleteStudentSoftly(id);

        return ResponseEntity.ok(res);
    }

}
