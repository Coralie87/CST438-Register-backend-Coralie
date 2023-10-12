package com.cst438.domain;

public record EnrollmentDTO(int id, String studentEmail, String studentName, int courseId) {

//    public Enrollment toEnrollment() {
//        // Create a new Enrollment object and set its properties based on the DTO
//        return new Enrollment(this.studentEmail, this.courseId);
//    }

    // Getter method for courseId
    public int getCourseId() {
        return courseId;
    }

    // Getter method for studentEmail
    public String getStudentEmail() {
        return studentEmail;
    }
}