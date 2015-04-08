package org.depinfonancy.td02;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
//import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class StudentEJB implements StudentEJBRemote{

    @PersistenceContext(unitName = "student-unit")
    private EntityManager em;

    public List<Student> findStudents() throws Exception {
        Query query = em.createQuery("SELECT s from Student as s");
        return query.getResultList();
    }
    
    public Student findStudentById(Student student) throws Exception {
//    	Query query = em.createQuery("SELECT s from Student as s WHERE studentID = " + student.getStudentID());
//        return (Student)query.getResultList().get(0);
    	return em.find(Student.class, student.getStudentID());
    }

    public void createStudent(Student student) throws Exception {
        em.persist(student);
    }

    public void deleteStudent(Student student) throws Exception {
        em.remove(em.merge(student));
    }
    
    // Finds and updates given student with a single query in order to avoid concurrency.
    public void updateStudent(Student student) throws Exception {
    	Query query = em.createQuery("UPDATE Student SET name = \'" + student.getName()
    			+ "\', address = \'" + student.getAddress()
    			+ "\', age = " + student.getAge()
    			+ " WHERE studentId = " + student.getStudentID());
    	query.executeUpdate();
    }

}