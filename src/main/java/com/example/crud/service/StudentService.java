package com.example.crud.service;

import com.example.crud.entity.Student;
import com.example.crud.repository.StudentRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student studentReq){
        // perform the business
        System.out.println("Inside services");
        if(studentReq.getName().length() < 3 || studentReq.getName().length() > 50){
            throw new RuntimeException("Name should be of minimum 3 characters or maximum of 50 characters");
        }
        if(studentReq.getEmail().isBlank()){
            throw new RuntimeException("Email is required");
        }
        if(studentReq.getAge() < 18){
            throw new RuntimeException("Age must be greater then 18");
        }

        studentReq.setDeleted(false);
        Student studentRes = studentRepository.save(studentReq);
        return studentRes;
    }

    public Student findStudent(Long id){
        // find the student by id
        Optional<Student> studentRes = studentRepository.findByIdAndDeletedIsFalse(id);

        if(studentRes.isPresent()){
            return studentRes.get();
        }
        return null;
    }

    public List<Student> findAllStudents(){
        List<Student> studentListRes = studentRepository.findAllByDeletedIsFalse();

        return studentListRes;
    }

    public Student updateStudent(Long id, Student student ){
        // get the the student by id;
        Optional<Student> existingStudent = studentRepository.findById(id);

        if(existingStudent.isEmpty()){
            return null;
        }

        Student newStudent = existingStudent.get();
        newStudent.setName(student.getName());
        newStudent.setEmail(student.getEmail());
        newStudent.setAge(student.getAge());
        newStudent.setSubject(student.getSubject());
        newStudent.setRollNo(student.getRollNo());

        Student studentRes = studentRepository.save(newStudent);

        return studentRes;

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
}
