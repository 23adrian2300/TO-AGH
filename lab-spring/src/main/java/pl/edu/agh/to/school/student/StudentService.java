package pl.edu.agh.to.school.student;

import org.springframework.stereotype.Service;
import pl.edu.agh.to.school.course.Course;
import pl.edu.agh.to.school.course.CourseRepository;
import pl.edu.agh.to.school.grade.Grade;
import pl.edu.agh.to.school.grade.GradeRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    private final GradeRepository gradeRepository;

    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, GradeRepository gradeRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.courseRepository = courseRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudent(String indexNumber) {
        return studentRepository.findByIndexNumber(indexNumber);
    }

    @Transactional
    public Optional<Grade> giveGrade(int studentId, int courseId, int gradeValue) {

        record Entities(Student student, Course course) {
        }

        return studentRepository.findById(studentId)
                .flatMap(student -> courseRepository.findById(courseId)
                        .map(course -> new Entities(student, course)))
                .map(entities -> {
                    Grade grade = new Grade(gradeValue, entities.course);
                    gradeRepository.save(grade);

                    entities.student.giveGrade(grade);
                    studentRepository.save(entities.student);

                    return grade;
                });
    }

    public Optional<Student> getStudent(int studentId) {
        return studentRepository.findById(studentId);
    }

    public Optional<Double> calculateAverageGrade(int studentId) {
        return studentRepository.calculateGradesAverage(studentId);
//        return getStudent(studentId)
//                .map(this::calculateAverage);
    }

    private double calculateAverage(Student student) {
        return student.getGrades().stream()
                .mapToInt(Grade::getGradeValue)
                .average()
                .orElse(0);
    }
}
