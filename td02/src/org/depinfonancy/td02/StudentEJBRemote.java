package org.depinfonancy.td02;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Local;

@Remote
public interface StudentEJBRemote {
	
	public List<Student> findStudents() throws Exception;
	
	public Student findStudentById(Student student) throws Exception;
	
	public void createStudent(Student student) throws Exception;
	
	public void deleteStudent(Student student) throws Exception;
	
	public void updateStudent(Student student) throws Exception;
	
}
