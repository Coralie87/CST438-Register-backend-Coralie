package com.cst438.controllers;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentDTO;
import com.cst438.domain.AssignmentRepository;
import com.cst438.domain.Course;
import com.cst438.domain.CourseRepository;
@RestController
@CrossOrigin
@RequestMapping("/api/assignments")
public class AssignmentController {
    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    CourseRepository courseRepository;
 // Get all assignments
    @GetMapping
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments() {
        List<Assignment> assignments = (List<Assignment>) assignmentRepository.findAll();
        if (assignments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // Convert assignments to AssignmentDTOs
            List<AssignmentDTO> assignmentDTOs = AssignmentDTO.convertToDTOs(assignments);
            return new ResponseEntity<>(assignmentDTOs, HttpStatus.OK);
        }
    }
    // Get a specific assignment by ID
    @GetMapping("/{assignmentId}")
    public ResponseEntity<AssignmentDTO> getAssignmentById(@PathVariable int assignmentId) {
        Optional<Assignment> optionalAssignment = assignmentRepository.findById(assignmentId);
        if (!optionalAssignment.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
        }
        Assignment assignment = optionalAssignment.get();
        AssignmentDTO assignmentDTO = AssignmentDTO.convertToDTO(assignment);
        return new ResponseEntity<>(assignmentDTO, HttpStatus.OK);
    }
    // Create a new assignment
    @PostMapping
    public ResponseEntity<AssignmentDTO> createAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        try {
            Assignment assignment = AssignmentDTO.convertToEntity(assignmentDTO);
            // Retrieve the course by its ID
            Optional<Course> course = courseRepository.findById(assignmentDTO.getCourseId());
            if (!course.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
            }
            assignment.setCourse(course.get());
            Assignment savedAssignment = assignmentRepository.save(assignment);
            AssignmentDTO responseDTO = AssignmentDTO.convertToDTO(savedAssignment);
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating assignment", e);
        }
    }
    // Update an assignment by ID
    @PutMapping("/{assignmentId}")
    public ResponseEntity<AssignmentDTO> updateAssignment(@PathVariable int assignmentId, @RequestBody AssignmentDTO assignmentDTO) {
        try {
            Optional<Assignment> optionalAssignment = assignmentRepository.findById(assignmentId);
            if (!optionalAssignment.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
            }
            Assignment assignment = optionalAssignment.get();
            assignment.updateFromDTO(assignmentDTO);
            // Retrieve the course by its ID
            Optional<Course> course = courseRepository.findById(assignmentDTO.getCourseId());
            if (!course.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
            }
            assignment.setCourse(course.get());
            Assignment updatedAssignment = assignmentRepository.save(assignment);
            AssignmentDTO responseDTO = AssignmentDTO.convertToDTO(updatedAssignment);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error updating assignment", e);
        }
    }
    // Delete an assignment by ID
    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(@PathVariable int assignmentId) {
        try {
            Optional<Assignment> optionalAssignment = assignmentRepository.findById(assignmentId);
            if (!optionalAssignment.isPresent()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
            }
            Assignment assignment = optionalAssignment.get();
            // You can implement logic to check if the assignment has associated grades here
            // if (assignmentHasGrades(assignment)) {
            //     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assignment has grades and cannot be deleted");
            // }
            assignmentRepository.delete(assignment);
            return new ResponseEntity<>("Assignment deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error deleting assignment", e);
        }
    }
}
