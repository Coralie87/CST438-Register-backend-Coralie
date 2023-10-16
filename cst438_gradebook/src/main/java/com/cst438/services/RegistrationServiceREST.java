package com.cst438.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cst438.domain.FinalGradeDTO;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Enrollment;

@Service
@ConditionalOnProperty(prefix = "registration", name = "service", havingValue = "rest")
@RestController
public class RegistrationServiceREST implements RegistrationService {

	
	RestTemplate restTemplate = new RestTemplate();
	
	@Value("${registration.url}") 
	String registration_url;
	
	public RegistrationServiceREST() {
		System.out.println("REST registration service ");
	}
	
	@Override
	public void sendFinalGrades(int course_id, FinalGradeDTO[] grades) {
	    System.out.println("Start sendFinalGrades " + course_id);

	    // Créer l'URL pour l'endpoint du service d'inscription
	    String registrationEndpoint = registration_url + "/course/" + course_id;

	    // Envoyer les notes finales en utilisant RestTemplate
	    restTemplate.postForObject(registrationEndpoint, grades, FinalGradeDTO[].class);
	}
	
	@Autowired
	CourseRepository courseRepository;

	@Autowired
	EnrollmentRepository enrollmentRepository;

	
	/*
	 * endpoint used by registration service to add an enrollment to an existing
	 * course.
	 */
	@PostMapping("/enrollment")
    @Transactional
    public EnrollmentDTO addEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {
        try {
            // Save the enrollment information to the database
            Enrollment enrollment = new Enrollment();  //create entity
            enrollment.setStudentEmail(enrollmentDTO.studentEmail());
            enrollment.setStudentName(enrollmentDTO.studentName());
            Course c = courseRepository.findById(enrollmentDTO.courseId()).orElse(null);
            enrollment.setCourse(c);
            
            enrollmentRepository.save(enrollment);

            System.out.println("Enrollment information added successfully: " + enrollmentDTO);

            // Return the enrolled enrollment DTO (you can customize this based on your needs)
            return enrollmentDTO;
        } catch (Exception e) {
            // Handle exceptions, e.g., database errors
            System.err.println("Error adding enrollment information: " + e.getMessage());
            return null; // Return an appropriate response in case of an error
        }
    }
	
}
