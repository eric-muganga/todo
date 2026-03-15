package com.eric.zadanieRekrutacyjne.dto.mapper;

import com.eric.zadanieRekrutacyjne.dto.TaskRequest;
import com.eric.zadanieRekrutacyjne.dto.TaskResponse;
import com.eric.zadanieRekrutacyjne.entity.Task;
import com.eric.zadanieRekrutacyjne.enums.TaskStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskResponse toResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public Task toEntity(TaskRequest request) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.NEW)
                .build();
    }
}
