package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ApplicationExampleTest {

	private static int count = 0;

	@Value("${info.app.name}")
	private String appInfo;

	@Value("${info.app.description}")
	private String appDescription;

	@Value("${info.app.version}")
	private String appVersion;

	@Value("${info.school.name}")
	private String schoolName;

	@Autowired
	CollegeStudent student;

	@Autowired
	StudentGrades studentGrades;

	@Autowired
	ApplicationContext context;

	@BeforeEach
	public void beforeEach() {
		count += 1;
		System.out.println("Testing: " + appInfo + " which is " + appDescription + " Version: " + appVersion
				+ ". Execution of test method " + count);
		student.setFirstname("Eric");
		student.setLastname("Roby");
		student.setEmailAddress("eric.roby@mail.com");
		studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
		student.setStudentGrades(studentGrades);
	}

	@Test
	@DisplayName("Add grade results for student grades")
	void addGradeResultsForStudentGrades() {
		assertEquals(353.25,
				studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
	}

	@Test
	@DisplayName("Add grade result for student not equal")
	void addGradeResultsForStudentNotEqual() {
		assertNotEquals(1,
				studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));
	}

	@Test
	@DisplayName("Is grade greater")
	void isGradeGreaterStudentGrades() {
		assertTrue(studentGrades.isGradeGreater(90, 75), "failure - should be true");
	}

	@Test
	@DisplayName("Is grade not greater")
	void isGradeNotGreaterStudentGrades() {
		assertFalse(studentGrades.isGradeGreater(2, 35), "failure - should be false");
	}

	@Test
	@DisplayName("Check null for student grades")
	void checkNullForStudentGrades() {
		assertNotNull(studentGrades.checkNull(student.getStudentGrades().getMathGradeResults()),
				"object should not be null");
	}

	@Test
	@DisplayName("Create student without grade init")
	void createStudentWithoutGradesInit() {
		CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
		studentTwo.setFirstname("Chad");
		studentTwo.setLastname("Darby");
		studentTwo.setEmailAddress("chad.darby@mail.com");
		assertNotNull(studentTwo.getFirstname());
		assertNotNull(studentTwo.getLastname());
		assertNotNull(studentTwo.getEmailAddress());
		assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));
	}
}
