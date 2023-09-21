package com.cst438;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private List<Assignment> assignments = new ArrayList<>(); // Une liste de devoirs simulée

    // Endpoint pour ajouter un devoir
    @PostMapping("/")
    public ResponseEntity<String> addAssignment(@RequestBody Assignment assignment) {
        // Simule l'ajout du devoir à la liste
        assignments.add(assignment);
        return ResponseEntity.ok("Devoir ajouté avec succès");
    }

    // Endpoint pour mettre à jour un devoir
    @PutMapping("/{assignmentId}")
    public ResponseEntity<String> updateAssignment(@PathVariable Long assignmentId, @RequestBody Assignment assignment) {
        // Recherchez le devoir par ID (simplifié pour cet exemple)
        Optional<Assignment> existingAssignment = assignments.stream()
                .filter(a -> a.getId().equals(assignmentId))
                .findFirst();

        if (existingAssignment.isPresent()) {
            // Mettez à jour le devoir (simplifié pour cet exemple)
            Assignment updatedAssignment = existingAssignment.get();
            updatedAssignment.setName(assignment.getName());
            return ResponseEntity.ok("Devoir mis à jour avec succès");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint pour supprimer un devoir
    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<String> deleteAssignment(@PathVariable Long assignmentId, @RequestParam(required = false) boolean force) {
        // Recherchez le devoir par ID (simplifié pour cet exemple)
        Optional<Assignment> existingAssignment = assignments.stream()
                .filter(a -> a.getId().equals(assignmentId))
                .findFirst();

        if (existingAssignment.isPresent()) {
            Assignment assignmentToDelete = existingAssignment.get();

            if (!force && assignmentToDelete.hasGrades()) {
                return ResponseEntity.badRequest().body("Le devoir a des notes. Utilisez le paramètre FORCE pour forcer la suppression.");
            }

            // Supprimez le devoir (simplifié pour cet exemple)
            assignments.remove(assignmentToDelete);
            return ResponseEntity.ok("Devoir supprimé avec succès");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint pour répertorier tous les devoirs
    @GetMapping("/")
    public ResponseEntity<List<Assignment>> listAssignments() {
        return ResponseEntity.ok(assignments);
    }
}
