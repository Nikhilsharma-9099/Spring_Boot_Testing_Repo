package com.luv2code.test;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
public class ApplicationExampleTest {

    private static int count = 0;

    @Value("${info.app.name}")
    private String appName;

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
        count = count + 1;
        System.out.println("Testing " + appName + " " + appDescription + " " + appVersion + " " + schoolName + " " + count);
        student.setFirstname("John");
        student.setLastname("Rambo");
        student.setEmailAddress("john@gmail.com");
        studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 98.0, 95.0, 99.0)));
        student.setStudentGrades(studentGrades);
    }

    @Test
    @DisplayName("Add Grade Results For Student Grades")
    public void addGradeResultsForStudentGrades() {
        assertEquals(392.0, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()
        ));

    }

    @Test
    @DisplayName("Add Grade Results For Student Grades Not Equal")
    public void addGradeResultsForStudentGradesAssertNotEqual() {
        assertNotEquals(0, studentGrades.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()
        ));

    }

    @Test
    @DisplayName("Is Grade Greater")
    public void isGradeGreaterStudentGrades() {
        assertTrue(studentGrades.isGradeGreater(100.0, 98.0), "Should be True!!");

    }

    @DisplayName("Is Grade Greater False")
    @Test
    public void isGradeGreaterStudentGradesAssertFalse() {
        assertFalse(studentGrades.isGradeGreater(98.0, 100.0), "Should be False!!");
    }

    @DisplayName("Check Null For Student Grades")
    @Test
    public void checkNullForStudentGrades() {
        assertNotNull(studentGrades.checkNull(student.getStudentGrades().getMathGradeResults()), "Should not be null");
    }

    @DisplayName("Create student without grade init")
    @Test
    public void createStudentWithoutGradeInit() {
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        studentTwo.setFirstname("Ron");
        studentTwo.setLastname("Smith");
        studentTwo.setEmailAddress("ron@gmail.com");
        assertNotNull(studentTwo.getFirstname());
        assertNotNull(studentTwo.getLastname());
        assertNotNull(studentTwo.getEmailAddress());
        assertNull( studentGrades.checkNull(studentTwo.getStudentGrades()));
    }

    @DisplayName("Verify Students are Prototypes")
    @Test
    public void verifyStudentsArePrototypes() {
        CollegeStudent studentTwo= context.getBean("collegeStudent", CollegeStudent.class);
        assertNotSame(student, studentTwo);
    }

    @DisplayName("Find Grade Point Average")
    @Test
    public void findGradePointAverage() {
        assertAll("Testing all assertEquals", () -> assertEquals(392.0, studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults())),
                () -> assertEquals(98.0, studentGrades.findGradePointAverage(student.getStudentGrades().getMathGradeResults())));
    }
}
