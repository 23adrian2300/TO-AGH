package pl.edu.agh.to.school.course;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.agh.to.school.student.Student;
import pl.edu.agh.to.school.student.StudentDto;

import java.util.List;

@RestController
@RequestMapping(path = "courses")
public class CourseController {

    private final CourseService courseService;


    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping(value = "/{id}/students")
    public List<StudentDto> getStudents(@PathVariable("id") int courseId) {
        return courseService.getCourse(courseId)
                .map(Course::getStudents)
                .map(students -> students.stream()
                        .map(StudentDto::from)
                        .toList())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
