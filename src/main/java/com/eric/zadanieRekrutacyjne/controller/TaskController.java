package com.eric.zadanieRekrutacyjne.controller;

import com.eric.zadanieRekrutacyjne.dto.TaskRequest;
import com.eric.zadanieRekrutacyjne.dto.TaskResponse;
import com.eric.zadanieRekrutacyjne.enums.TaskStatus;
import com.eric.zadanieRekrutacyjne.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Task management API")
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Create a new task")
    public ResponseEntity<TaskResponse> create(
            @Valid @RequestBody TaskRequest taskRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.create(taskRequest));
    }

    @GetMapping
    @Operation(summary = "Get all tasks with optional status filter and pagination")
    public ResponseEntity<Page<TaskResponse>> getAll(
            @Parameter(description = "Filter by status: NEW, IN_PROGRESS, DONE")
            @RequestParam(required = false) TaskStatus status,
            @PageableDefault(size = 10, sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(taskService.getAll(status, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a task by ID")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing task")
    public ResponseEntity<TaskResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
