package org.depinfonancy.td02;

import java.util.Properties;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("td02")
public class Td02UI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Td02UI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		
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
		
		//First Line.
		final HorizontalLayout bottom1 = new HorizontalLayout();
		bottom.addComponent(bottom1);
		
		Button createStudent = new Button("Create Student");
		createStudent.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String returnMessage;
				try{
					int age = Integer.parseInt(studentAge.getValue());
					createStudent(studentName.getValue(), studentAddress.getValue(), age);
					returnMessage = "Student added successfully";
				}catch(Exception e){
					System.out.println(e);
					returnMessage = "An error ocurred, check if you inserted a valid age value on the Student Age box";
				}
				System.out.println(returnMessage);
				
				
			}
		});
		bottom1.addComponent(createStudent);
		
		//
//		Button button = new Button("Click Me");
//		button.addClickListener(new Button.ClickListener() {
//			public void buttonClick(ClickEvent event) {
//				layout.addComponent(new Label("Thank you for clicking"));
//			}
//		});
//		layout.addComponent(button);
	}

	public StudentEJBRemote setup() throws Exception{
		
		final Properties p = new Properties();
        p.put("studentDatabase", "new://Resource?type=DataSource");
        p.put("studentDatabase.JdbcDriver", "com.mysql.jdbc.Driver");
        p.put("studentDatabase.JdbcUrl", "jdbc:mysql://localhost:3306/students");
        
        final Context context = EJBContainer.createEJBContainer(p).getContext();
  
        return (StudentEJBRemote) context.lookup("java:global/td02/StudentEJB");
	}
	
	private void createStudent(String name, String address, int age) throws Exception{
		//Sets up the persistence EJB.
		StudentEJBRemote students = setup();
		//Creates the new Student.
		students.createStudent(new Student(name, address, age));
	}
}