package com.cst438.domain;
import java.sql.Date;
import java.util.List;
public record AssignmentDTO(int id, String assignmentName, String dueDate, String courseTitle, int courseId) {
    public static AssignmentDTO convertToDTO(Assignment assignment) {
        String dueDateStr = assignment.getDueDate() != null ? assignment.getDueDate().toString() : null;
        return new AssignmentDTO(
            assignment.getId(),
            assignment.getName(),
            dueDateStr,
            assignment.getCourse().getTitle(),
            assignment.getCourse().getCourse_id()
        );
    }
    public static Assignment convertToEntity(AssignmentDTO assignmentDTO) {
        Assignment assignment = new Assignment();
        assignment.setName(assignmentDTO.assignmentName());
        // Convertir la chaîne de date en Date si elle n'est pas nulle
        if (assignmentDTO.dueDate() != null) {
            assignment.setDueDate(Date.valueOf(assignmentDTO.dueDate()));
        }
        // Vous pouvez ajouter d'autres champs à mapper ici selon vos besoins.
        return assignment;
    }
    public Integer getCourseId() {
        return courseId;
    }
    public static List<AssignmentDTO> convertToDTOs(List<Assignment> assignments) {
        return assignments.stream()
            .map(AssignmentDTO::convertToDTO)
            .toList();
    }
}
