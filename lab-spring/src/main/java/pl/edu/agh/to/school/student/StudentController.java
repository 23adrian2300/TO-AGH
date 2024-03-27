package pl.edu.agh.to.school.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.agh.to.school.grade.Grade;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "students")
public class StudentController {
    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDto> getStudents(@RequestParam(value = "indexNumber", required = false) String indexNumber) {
        if (indexNumber != null) {
            return studentService.getStudent(indexNumber)
                    .map(StudentDto::from)
                    .map(List::of)
                    .orElseGet(Collections::emptyList);
        }
        return studentService.getStudents().stream()
                .map(StudentDto::from)
                .toList();
    }

    @PostMapping("/{id}/grades")
    public GradeDto giveGrade(@PathVariable("id") int studentId, @RequestBody GradeDto gradeRequest) {
        return studentService.giveGrade(studentId, gradeRequest.courseId, gradeRequest.value)
                .map(grade -> new GradeDto(grade.getGradeValue(), grade.getCourse().getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/grades")
    public List<GradeDto> getGrades(@PathVariable("id") int studentId) {
        return studentService.getStudent(studentId)
                .map(student -> student.getGrades().stream()
                        .map(grade -> new GradeDto(grade.getGradeValue(), grade.getCourse().getId()))
                        .toList()
                ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/grades/average")
    public double getGradesAverage(@PathVariable("id") int studentId) {
        return studentService.calculateAverageGrade(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private record GradeDto(int value, int courseId) {
    }
}
