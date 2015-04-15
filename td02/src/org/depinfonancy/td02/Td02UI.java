package org.depinfonancy.td02;

import java.util.List;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("valo")
@CDIUI

public class Td02UI extends UI {

//	@WebServlet(value = "/*", asyncSupported = true)
//	@VaadinServletConfiguration(productionMode = false, ui = Td02UI.class)
//	public static class Servlet extends VaadinServlet {
//	}
	
	@EJB
	private StudentEJBRemote students;
	
	@Override
	protected void init(VaadinRequest request) {
//		try {
//			students = setup();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);
		
		
		//Sets up the top part of the page with the text boxes for entering data.
		final FormLayout top = new FormLayout();
		layout.addComponent(top);
		final TextField studentID = new TextField("Student ID");
		final TextField studentName = new TextField("Student Name");
		final TextField studentAddress = new TextField("Student Address");
		final TextField studentAge = new TextField("Student Age");
		top.addComponent(studentID);
		top.addComponent(studentName);
		top.addComponent(studentAddress);
		top.addComponent(studentAge);
		
		//Sets up the bottom part of the page with two lines of buttons and one for the return message.
		final VerticalLayout bottom = new VerticalLayout();
		layout.addComponent(bottom);	

		final HorizontalLayout bottom1 = new HorizontalLayout();
		bottom.addComponent(bottom1);
		final HorizontalLayout bottom2 = new HorizontalLayout();
		bottom.addComponent(bottom2);
		final VerticalLayout bottom3 = new VerticalLayout();
		bottom.addComponent(bottom3);
		
		//First Line.
		Button createStudent = new Button("Create Student");
		createStudent.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String returnMessage = null;
				try{
					int age = Integer.parseInt(studentAge.getValue());
					students.createStudent(new Student(studentName.getValue(), studentAddress.getValue(), age));
					returnMessage = "Student successfully added.";
				}catch(Exception e){
					System.out.println(e);
					returnMessage = "An error ocurred, check if you inserted a valid age value on the Student Age box.";
				}
				bottom3.removeAllComponents();
				bottom3.addComponent(new TextArea(returnMessage));
			}
		});
		bottom1.addComponent(createStudent);
		
		Button updateStudent = new Button("Update Student");
		updateStudent.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String returnMessage;
				try{
					int id = Integer.parseInt(studentID.getValue());
					int age = Integer.parseInt(studentAge.getValue());
					Student TempStudent = new Student(studentName.getValue(), studentAddress.getValue(), age);
					TempStudent.setStudentID(id);
					students.updateStudent(TempStudent);
					returnMessage = "Student successfully updated.";
				}catch(Exception e){
					System.out.println(e);
					returnMessage = "An error ocurred, check if you inserted valid age and id values.";
				}
				bottom3.removeAllComponents();
				bottom3.addComponent(new TextArea(returnMessage));
			}
		});
		bottom1.addComponent(updateStudent);
		
		Button deleteStudent = new Button("Delete Student");
		updateStudent.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String returnMessage;
				try{
					int id = Integer.parseInt(studentID.getValue());
					Student TempStudent = new Student("", "", 0);
					TempStudent.setStudentID(id);
					students.deleteStudent(students.findStudentById(TempStudent));
					returnMessage = "Student successfully deleted.";
				}catch(Exception e){
					System.out.println(e);
					returnMessage = "An error ocurred, check if you inserted a valid id value.";
				}
				bottom3.removeAllComponents();
				bottom3.addComponent(new TextArea(returnMessage));
			}
		});
		bottom1.addComponent(deleteStudent);
		
		Button findStudentByID = new Button("Find Student By ID");
		findStudentByID.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String returnMessage;
				try{
					int id = Integer.parseInt(studentID.getValue());
					Student TempStudent = new Student("", "", 0);
					TempStudent.setStudentID(id);
					returnMessage = studentToString(students.findStudentById(TempStudent));
					
				}catch(Exception e){
					System.out.println(e);
					returnMessage = "An error ocurred, check if you inserted a valid id value.";
				}
				bottom3.removeAllComponents();
				bottom3.addComponent(new TextArea(returnMessage));
			}
		});
		bottom2.addComponent(findStudentByID);
		
		Button findStudents = new Button("Find All Students");
		findStudents.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				List<Student> allStudents = null;
				try {
					allStudents = students.findStudents();
				} catch (Exception e) {
					e.printStackTrace();
				}
				bottom3.removeAllComponents();
				if(allStudents.size() == 0){
					bottom3.addComponent(new TextArea("There aren't students registered."));
				}else{
					for(Student s : allStudents){
						bottom3.addComponent(new TextArea(studentToString(s)));
					}
				}	
			}
		});
		bottom2.addComponent(findStudents);
		
		
		//
//		Button button = new Button("Click Me");
//		button.addClickListener(new Button.ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				layout.addComponent(new Label("Thank you for clicking"));
//			}
//		});
//		layout.addComponent(button);
	}
	
	public String studentToString(Student student){
		String returnMessage;
		if(student == null){
			returnMessage = "No student found.";
		}else{
			returnMessage = student.getName() + "\n"
					+ "ID: " + student.getStudentID() + "\n"
					+ "Address:" + student.getAddress() + "\n"
							+ "Age: " + student.getAge();
		}
		
		return returnMessage;
	}
	
}