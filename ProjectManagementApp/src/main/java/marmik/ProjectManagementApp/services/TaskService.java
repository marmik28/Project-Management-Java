package marmik.ProjectManagementApp.services;

import marmik.ProjectManagementApp.Task;
import marmik.ProjectManagementApp.User;
import marmik.ProjectManagementApp.repositories.ProjectRepository;
import marmik.ProjectManagementApp.repositories.TaskRepository;
import marmik.ProjectManagementApp.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    // Create a new task
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    // Get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get task by ID
    public Optional<Task> getTaskById(ObjectId id) {
        return taskRepository.findById(id);
    }

    // Update a task
    public Task updateTask(ObjectId id, Task updatedTask) {
        if (taskRepository.existsById(id)) {
            updatedTask.setId(id);
            return taskRepository.save(updatedTask);
        }
        return null;
    }

    // Delete a task
    public void deleteTask(ObjectId id) {
        taskRepository.deleteById(id);
    }

    // Get tasks by project ID
    public List<Task> getTasksByProjectId(ObjectId projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    // Assign a user to a task
    public Task assignUserToTask(ObjectId taskId, ObjectId userId) throws Exception {
        Optional<Task> task = taskRepository.findById(taskId);
        Optional<User> user = userRepository.findById(userId);

        if (task.isPresent() && user.isPresent()) {
            Task updatedTask = task.get();
            updatedTask.setAssignedTo(user.get());
            return taskRepository.save(updatedTask);
        } else {
            throw new Exception("Task or User not found");
        }
    }

    // Remove user from a task
    public Task removeUserFromTask(ObjectId taskId) throws Exception {
        Optional<Task> task = taskRepository.findById(taskId);

        if (task.isPresent()) {
            Task updatedTask = task.get();
            updatedTask.setAssignedTo(null);
            return taskRepository.save(updatedTask);
        } else {
            throw new Exception("Task not found");
        }
    }

}

