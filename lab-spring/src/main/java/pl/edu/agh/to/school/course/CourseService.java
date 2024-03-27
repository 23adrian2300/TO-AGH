package pl.edu.agh.to.school.course;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository studentRepository) {
        this.courseRepository = studentRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourse(int courseId) {
        return courseRepository.findById(courseId);
    }
}
