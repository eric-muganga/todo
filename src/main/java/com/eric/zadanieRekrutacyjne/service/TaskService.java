package com.eric.zadanieRekrutacyjne.service;

import com.eric.zadanieRekrutacyjne.dto.TaskRequest;
import com.eric.zadanieRekrutacyjne.dto.TaskResponse;
import com.eric.zadanieRekrutacyjne.dto.mapper.TaskMapper;
import com.eric.zadanieRekrutacyjne.entity.Task;
import com.eric.zadanieRekrutacyjne.enums.TaskStatus;
import com.eric.zadanieRekrutacyjne.exception.TaskNotFoundException;
import com.eric.zadanieRekrutacyjne.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskResponse create(TaskRequest taskRequest) {
        Task task = taskMapper.toEntity(taskRequest);
        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public Page<TaskResponse> getAll(TaskStatus status, Pageable pageable) {
        Page<Task> page = (status != null)
                ? taskRepository.findAllByStatus(status, pageable)
                : taskRepository.findAll(pageable);

        return page.map(taskMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public TaskResponse getById(Long id) {
        return taskMapper.toResponse(findOrThrow(id));
    }

    @Transactional
    public TaskResponse update(Long id, TaskRequest taskRequest) {
        Task task = findOrThrow(id);
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        if (taskRequest.getStatus() != null) {
            task.setStatus(taskRequest.getStatus());
        }

        return taskMapper.toResponse(taskRepository.save(task));
    }

    @Transactional
    public void delete(Long id) {
        findOrThrow(id);
        taskRepository.deleteById(id);
    }

    private Task findOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
