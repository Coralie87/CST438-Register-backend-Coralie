package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cst438.domain.Assignment;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class AssignmentController {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testGetAllAssignments() throws Exception {
        MockHttpServletResponse response;

        response = mvc.perform(
                MockMvcRequestBuilders
                        .get("/assignments")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());

        // Assurez-vous que la réponse contient des attributions
        // Vous devrez ajuster cela en fonction de votre logique réelle.
        // Assignment[] assignments = fromJsonString(response.getContentAsString(), Assignment[].class);
        // assertNotNull(assignments);
    }

    @Test
    public void testAddAssignment() throws Exception {
        MockHttpServletResponse response;

        Assignment assignment = new Assignment();
        assignment.setName("Database Assignment");
        assignment.setDescription("Create a database schema");
        assignment.setInstructorId(1);

        response = mvc.perform(
                MockMvcRequestBuilders
                        .post("/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(assignment))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());

        // Assurez-vous que la réponse contient l'attribution créée
        // Vous devrez ajuster cela en fonction de votre logique réelle.
        // Assignment result = fromJsonString(response.getContentAsString(), Assignment.class);
        // assertNotNull(result);
        // assertNotNull(result.getId());
    }

    @Test
    public void testUpdateAssignment() throws Exception {
        MockHttpServletResponse response;

        int assignmentId = 1;
        Assignment updatedAssignment = new Assignment();
        updatedAssignment.setName("Updated Assignment Name");

        response = mvc.perform(
                MockMvcRequestBuilders
                        .put("/assignments/" + assignmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedAssignment))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(200, response.getStatus());

        // Assurez-vous que la réponse contient l'attribution mise à jour
        // Vous devrez ajuster cela en fonction de votre logique réelle.
        // Assignment result = fromJsonString(response.getContentAsString(), Assignment.class);
        // assertNotNull(result);
    }

    @Test
    public void testDeleteAssignment() throws Exception {
        MockHttpServletResponse response;

        int assignmentId = 1;

        response = mvc.perform(
                MockMvcRequestBuilders
                        .delete("/assignments/" + assignmentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(204, response.getStatus());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
    }

