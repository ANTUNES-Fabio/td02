package org.depinfonancy.td02.test;

import junit.framework.TestCase;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import org.depinfonancy.td02.Student;
import org.depinfonancy.td02.StudentEJBRemote;

import java.util.List;
import java.util.Properties;

public class StudentEJBTest extends TestCase{

	 public void test() throws Exception {
	        final Properties p = new Properties();
	        p.put("studentDatabase", "new://Resource?type=DataSource");
	        p.put("studentDatabase.JdbcDriver", "com.mysql.jdbc.Driver");
	        p.put("studentDatabase.JdbcUrl", "jdbc:mysql://localhost:3306/students");

	        final Context context = EJBContainer.createEJBContainer(p).getContext();
	       
	        StudentEJBRemote students = (StudentEJBRemote) context.lookup("java:global/td02/StudentEJB");

	        int initialSize = students.findStudents().size();
	        
	        //Create and findStudents test.
	        Student TestStudent1 = new Student("Machado de Assis", "Rio de Janeiro", 175);
	        Student TestStudent2 = new Student("Manuel Bandeira", "Recife", 128);
	        Student TestStudent3 = new Student("Paulo Coelho", "Rio de Janeiro", 67);
	        students.createStudent(TestStudent1);
	        students.createStudent(TestStudent2);
	        students.createStudent(TestStudent3);

	        List<Student> list = students.findStudents();
	        assertEquals("Number of students incremented 3 times.", initialSize + 3, list.size());

	        //findStudentById test.
	        assertEquals("Find specific student by Id.", "Machado de Assis", students.findStudentById(TestStudent1).getName());
	        
	        //Update test.
	        TestStudent2.setAddress("Rio de Janeiro");
	        students.updateStudent(TestStudent2);
	        assertEquals("Address Update.", "Rio de Janeiro", students.findStudentById(TestStudent2).getAddress());
	        
	        //Delete test.
	        students.deleteStudent(TestStudent1);
	        students.deleteStudent(TestStudent2);
	        students.deleteStudent(TestStudent3);
	        
	        assertEquals("Number of students unchanged.", initialSize, students.findStudents().size());
	    }

}
