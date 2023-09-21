package com.cst438.domain;
import java.sql.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Assignment {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;
    
    @OneToMany(mappedBy="assignment")
    private List<AssignmentGrade> assignmentGrades;
    
    private String name;
    private Date dueDate;
    
    
    private String description; // Ajout de la description
   
    private int instructorId; // Ajout de l'instructorId
    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public Course getCourse() {
        return course;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getInstructorId() {
        return instructorId;
    }
    
    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    
    public void updateFromDTO(AssignmentDTO assignmentDTO) {
        // Vérifiez si le nom de l'assignment est fourni dans le DTO
        if (assignmentDTO.assignmentName() != null) {
            // Mettez à jour le nom de l'assignment avec la valeur du DTO
            this.setName(assignmentDTO.assignmentName());
        }
        // Vérifiez si la date d'échéance est fournie dans le DTO
        if (assignmentDTO.dueDate() != null) {
            try {
                // Essayez de convertir la chaîne de date en objet Date
                this.setDueDate(Date.valueOf(assignmentDTO.dueDate()));
            } catch (IllegalArgumentException e) {
                // Gérez l'exception si la chaîne de date n'est pas au bon format.
                // Vous pouvez journaliser l'erreur ou effectuer d'autres actions appropriées ici.
                // Par exemple, vous pouvez définir une date par défaut ou renvoyer une erreur à l'utilisateur.
                // Dans cet exemple, nous affichons un message d'erreur.
                System.err.println("Erreur de format de date : " + e.getMessage());
            }
        }
      
    }
    
    
    
    @Override
    public String toString() {
        return "Assignment [id=" + id + ", course_id=" + course.getCourse_id() + ", name=" + name + ", dueDate=" + dueDate
                + "]";
    }
}