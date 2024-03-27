package pl.edu.agh.to.school.grade;

import pl.edu.agh.to.school.course.Course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Grade {

    @Id
    @GeneratedValue
    private int id;

    private int gradeValue;

    @OneToOne
    private Course course;

    public Grade(int value, Course course) {
        this.gradeValue = value;
        this.course = course;
    }

    public Grade() {}

    public int getId() {
        return id;
    }

    public int getGradeValue() {
        return gradeValue;
    }

    public Course getCourse() {
        return course;
    }
}
