package tn.uma.isamm.services;

import java.util.List;

import tn.uma.isamm.entities.Student;

public interface StudentService{
	
	Student createStudent(Student student);

    Student getStudentById(Long id);

    List<Student> getAllStudents();

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);
}
