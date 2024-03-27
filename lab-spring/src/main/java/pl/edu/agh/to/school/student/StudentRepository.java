package pl.edu.agh.to.school.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {


    Optional<Student> findByIndexNumber(String indexNumber);


    @Query(value = "select avg(g.gradeValue) from Student s join s.grades g where s.id = :studentId")
//    @Query(value = "select avg(g.grade_value) from Grade as g " +
//            "inner join student_grades as sg on g.id = sg.grades_id " +
//            "where sg.student_id = :studentId", nativeQuery = true)
    Optional<Double> calculateGradesAverage(@Param("studentId") int studentId);


}
