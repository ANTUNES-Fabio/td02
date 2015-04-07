package org.depinfonancy.td02;

import junit.framework.TestCase;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import java.util.List;
import java.util.Properties;

public class StudentEJBTest extends TestCase{

	 public void test() throws Exception {
	        final Properties p = new Properties();
	        p.put("studentDatabase", "new://Resource?type=DataSource");
	        p.put("studentDatabase.JdbcDriver", "com.mysql.jdbc.Driver");
	        p.put("studentDatabase.JdbcUrl", "jdbc:mysql://localhost:3306/students");

	        final Context context = EJBContainer.createEJBContainer(p).getContext();
	       
	        StudentEJB students = (StudentEJB) context.lookup("java:global/td02/StudentEJB");

	        students.createStudent(new Student("Quentin Tarantino", "N'importe pas", 1992));
	        students.createStudent(new Student("Joel Coen", "Fargo", 1996));
	        students.createStudent(new Student("Joel Coen", "The Big Lebowski", 1998));

	        List<Student> list = students.findStudents();
	        assertEquals("List.size()", 3, list.size());

	        for (Student student : list) {
	            students.deleteStudent(student);
	        }

	        assertEquals("StudentEJB.findStudents()", 0, students.findStudents().size());
	    }

}
