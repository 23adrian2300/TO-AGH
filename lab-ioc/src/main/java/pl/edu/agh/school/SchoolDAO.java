package pl.edu.agh.school;

import java.util.Collections;
import java.util.List;

import com.google.inject.Inject;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.persistence.IPersistenceManager;

public class SchoolDAO {

    @Inject
    private Logger log;

    private final List<Teacher> teachers;

    private final List<SchoolClass> classes;

    private final IPersistenceManager persistenceManager;

    @Inject
    public SchoolDAO(IPersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
        teachers = this.persistenceManager.loadTeachers();
        classes = this.persistenceManager.loadClasses();
    }

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
            persistenceManager.saveTeachers(teachers);
            log.log("Added " + teacher.toString());
        }
    }

    public void addClass(SchoolClass newClass) {
        if (!classes.contains(newClass)) {
            classes.add(newClass);
            persistenceManager.saveClasses(classes);
            log.log("Added " + newClass.toString());
        }
    }

    public List<SchoolClass> getClasses() {
        return Collections.unmodifiableList(classes);
    }

    public List<Teacher> getTeachers() {
        return Collections.unmodifiableList(teachers);
    }
}
