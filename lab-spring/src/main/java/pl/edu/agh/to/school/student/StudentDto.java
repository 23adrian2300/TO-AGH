package pl.edu.agh.to.school.student;

import java.time.LocalDate;

public record StudentDto(int id, String firstName, String lastName, LocalDate birthDate, String indexNumber) {

    public static StudentDto from(Student student) {
        return new StudentDto(student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getBirthDate(),
                student.getIndexNumber());
    }
}
