package tn.uma.isamm.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.uma.isamm.entities.Student;
import tn.uma.isamm.repositories.StudentRepository;
import tn.uma.isamm.services.StudentService;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new IllegalArgumentException("L'email existe déjà : " + student.getEmail());
        }

        return studentRepository.save(student);
    }
    
    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant avec ID " + id + " n'existe pas"));
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant avec ID " + id + " n'existe pas"));

        existingStudent.setUsername(student.getUsername());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setPassword(student.getPassword());
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setPhone(student.getPhone());
        existingStudent.setUniversity(student.getUniversity());
        existingStudent.setMajor(student.getMajor());
        existingStudent.setYear(student.getYear());
        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant avec ID " + id + " n'existe pas"));
        studentRepository.delete(student);
    }
}
