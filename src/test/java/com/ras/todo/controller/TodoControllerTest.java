package com.ras.todo.controller;

import com.ras.todo.model.Todo;
import com.ras.todo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TodoControllerTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTodos() {
        // Arrange
        Todo todo1 = new Todo("Task 1", "Description 1");
        Todo todo2 = new Todo("Task 2", "Description 2");
        when(todoRepository.findAll()).thenReturn(Arrays.asList(todo1, todo2));

        // Act
        List<Todo> result = todoController.getAllTodos();

        // Assert
        assertEquals(2, result.size());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void testGetTodoByIdFound() {
        // Arrange
        Todo todo = new Todo("Task 1", "Description 1");
        todo.setId(1L);
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        // Act
        ResponseEntity<Todo> response = todoController.getTodoById(1L);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testGetTodoByIdNotFound() {
        // Arrange
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Todo> response = todoController.getTodoById(1L);

        // Assert
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void testCreateTodo() {
        // Arrange
        Todo todo = new Todo("New Task", "New Description");
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        // Act
        Todo result = todoController.createTodo(todo);

        // Assert
        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void testHealthCheck() {
        // Act
        String result = todoController.health();

        // Assert
        assertEquals("Todo Application is healthy!", result);
    }
}