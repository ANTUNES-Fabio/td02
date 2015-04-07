package org.depinfonancy.td02;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

@Stateful
public class StudentEJB {

    @PersistenceContext(unitName = "student-unit", type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    public List<Student> findStudents() throws Exception {
        Query query = em.createQuery("SELECT s from Student as s");
        return query.getResultList();
    }
    
    public List<Student> findStudentById(Student student) throws Exception {
    	Query query = em.createQuery("SELECT s from Student as s WHERE studentID=" + student.getStudentID());
        return query.getResultList();
    }

    public void createStudent(Student student) throws Exception {
        em.persist(student);
    }

    public void deleteStudent(Student student) throws Exception {
        em.remove(student);
    }


}