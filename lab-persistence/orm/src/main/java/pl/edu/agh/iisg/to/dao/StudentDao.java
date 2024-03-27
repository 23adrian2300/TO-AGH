package pl.edu.agh.iisg.to.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import pl.edu.agh.iisg.to.model.Course;
import pl.edu.agh.iisg.to.model.Grade;
import pl.edu.agh.iisg.to.model.Student;

import javax.persistence.PersistenceException;

public class StudentDao extends GenericDao<Student> {

    public Optional<Student> create(final String firstName, final String lastName, final int indexNumber) {
        //TODO - implement
        try {
        save(new Student(firstName, lastName, indexNumber));
        return findByIndexNumber(indexNumber);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Student> findByIndexNumber(final int indexNumber) {
        //TODO - implement
        try{
            Student student = currentSession().createQuery("Select s From Student s where s.indexNumber = :indexNumber", Student.class)
                    .setParameter("indexNumber", indexNumber).uniqueResult();
            return Optional.of(student);
        }
        catch (PersistenceException e) {
            e.printStackTrace();}


        return Optional.empty();
    }

    public Map<Course, Float> createReport(final Student student) {
        Map<Course, Float> gradesMap = new HashMap<>();
        Map<Course, Integer> gradeCountsMap = new HashMap<>();
        for (Grade grade : student.gradeSet()) {
            gradesMap.put(grade.course(),
                    (gradesMap.containsKey(grade.course()) ?
                            gradesMap.get(grade.course()) :
                            0) + grade.grade()
            );
            gradeCountsMap.put(
                    grade.course(),
                    gradeCountsMap.containsKey(grade.course()) ?
                            gradeCountsMap.get(grade.course()) + 1 :
                            1
            );
        }
        gradesMap.replaceAll((c, v) -> gradesMap.get(c) /
                gradeCountsMap.get(c));

        return gradesMap;
    }
    }

