package com.example.crud.controller;

import com.example.crud.dto.CreateStudentRequestDto;
import com.example.crud.dto.CreateStudentResponseDto;
import com.example.crud.dto.GetStudentResponseDto;
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
    public ResponseEntity<CreateStudentResponseDto> findStudent(@PathVariable Long id){
        CreateStudentResponseDto studentRes = studentService.findStudent(id);
        return ResponseEntity.ok(studentRes);
    }

    // get all students
    @GetMapping
    public ResponseEntity<List<GetStudentResponseDto>> findAllStudent(){
        List<GetStudentResponseDto> studentListRes = studentService.findAllStudents();
        return ResponseEntity.ok(studentListRes);
    }

    // update a student.
    @PutMapping("{id}")
    public ResponseEntity<CreateStudentResponseDto> updateStudent(@PathVariable Long id, @RequestBody CreateStudentRequestDto studentReqDto){
        CreateStudentResponseDto studentResDto = studentService.updateStudent(id,studentReqDto);
        return ResponseEntity.ok(studentResDto);
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
