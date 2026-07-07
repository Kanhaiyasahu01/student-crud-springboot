package com.example.crud.service;

import com.example.crud.dto.CreateStudentRequestDto;
import com.example.crud.dto.CreateStudentResponseDto;
import com.example.crud.dto.GetStudentResponseDto;
import com.example.crud.entity.Student;
import com.example.crud.repository.StudentRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentReqDto){
        // get the student from student req dto

        Student student = maptoEntity(studentReqDto);
        student.setDeleted(false);
        Student savedStudent = studentRepository.save(student);
        CreateStudentResponseDto studentRes = mapToDto(savedStudent);
        studentRes.setMessage("Student Created Successfully");
        return studentRes;
    }

    public CreateStudentResponseDto findStudent(Long id){
        // find the student by id
        Optional<Student> studentRes = studentRepository.findByIdAndDeletedIsFalse(id);

        if(studentRes.isPresent()){
            // map to response dto
            CreateStudentResponseDto studentResDto =  mapToDto(studentRes.get());
            studentResDto.setMessage("Student fetched successfully");
            return studentResDto;
        }
        return null;
    }

    public List<GetStudentResponseDto> findAllStudents(){
        List<Student> studentListRes = studentRepository.findAllByDeletedIsFalse();

        List<GetStudentResponseDto> studentResDtoList = maptoList(studentListRes);

        return studentResDtoList;
    }

    public CreateStudentResponseDto updateStudent(Long id, CreateStudentRequestDto studentReqDto ){
        // get the the student by id;
        Optional<Student> existingStudent = studentRepository.findById(id);

        if(existingStudent.isEmpty()){
            return null;
        }

        Student newStudent = existingStudent.get();
        newStudent.setName(studentReqDto.getName());
        newStudent.setEmail(studentReqDto.getEmail());
        newStudent.setAge(studentReqDto.getAge());
        newStudent.setSubject(studentReqDto.getSubject());
        newStudent.setRollNo(studentReqDto.getRollNo());

        Student studentRes = studentRepository.save(newStudent);

        CreateStudentResponseDto studentResDto = mapToDto(studentRes);
        studentResDto.setMessage("Student updated successfully");
        return studentResDto;

    }

    public String deleteStudent(Long id){
        // find student
        studentRepository.deleteById(id);

        return "Student Deleted";
    }

    public String deleteStudentSoftly(Long id){
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);
        if(existingStudent.isEmpty()){
            return "Student Not Found";
        }

        Student deletedStudent = existingStudent.get();

        deletedStudent.setDeleted(true);
        Student s1 = studentRepository.save(deletedStudent);
        return "Student Saved Deleted";
    }

    private Student maptoEntity(CreateStudentRequestDto studentRequestDto){
        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setEmail(studentRequestDto.getEmail());
        student.setSubject(studentRequestDto.getSubject());
        student.setAge(studentRequestDto.getAge());
        student.setRollNo(studentRequestDto.getRollNo());

        return student;
    }

    private CreateStudentResponseDto mapToDto(Student student){
        CreateStudentResponseDto studentResponseDto = new CreateStudentResponseDto();
        studentResponseDto.setName(student.getName());
        studentResponseDto.setEmail(student.getEmail());
        studentResponseDto.setAge(student.getAge());
        studentResponseDto.setSubject(student.getSubject());
        studentResponseDto.setRollNo(student.getRollNo());

        return studentResponseDto;
    }

    private List<GetStudentResponseDto> maptoList(List<Student> studentListRes){

        List<GetStudentResponseDto> studentResDtoList = new ArrayList<>();
        for(Student student: studentListRes){
            GetStudentResponseDto studentResDto = new GetStudentResponseDto();
            studentResDto.setName(student.getName());
            studentResDto.setEmail(student.getEmail());
            studentResDto.setAge(student.getAge());
            studentResDto.setRollNo(student.getRollNo());
            studentResDto.setSubject(student.getSubject());
            studentResDtoList.add(studentResDto);
        }

        return studentResDtoList;
    }
}
