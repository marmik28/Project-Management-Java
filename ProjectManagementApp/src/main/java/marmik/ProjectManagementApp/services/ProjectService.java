package marmik.ProjectManagementApp.services;

import marmik.ProjectManagementApp.Project;
import marmik.ProjectManagementApp.User;
import marmik.ProjectManagementApp.repositories.ProjectRepository;
import marmik.ProjectManagementApp.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new project
    public Project createProject(Project project) throws Exception {
        // Fetch owner
        Optional<User> owner = userRepository.findById(project.getOwner());
        if (owner.isEmpty()) {
            throw new Exception("Owner not found.");
        }

        // Fetch and validate assigned users
        List<ObjectId> assignedUserIds = project.getAssignedUsers();
        List<User> assignedUsers = userRepository.findAllById(assignedUserIds);
        if (assignedUsers.size() != assignedUserIds.size()) {
            throw new Exception("Some assigned users not found.");
        }

        // Save project with valid users
        return projectRepository.save(project);
    }

    // Get all projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Get project by ID
    public Optional<Project> getProjectById(ObjectId id) {
        return projectRepository.findById(id);
    }

    // Update a project
    public Project updateProject(ObjectId id, Project updatedProject) {
        if (projectRepository.existsById(id)) {
            updatedProject.setId(id);
            return projectRepository.save(updatedProject);
        }
        return null;
    }

    // Delete a project
    public void deleteProject(ObjectId id) {
        projectRepository.deleteById(id);
    }

    // Add a user to a project
    public Project addUserToProject(ObjectId projectId, ObjectId userId) throws Exception {
        Optional<Project> project = projectRepository.findById(projectId);
        Optional<User> user = userRepository.findById(userId);

        if (project.isPresent() && user.isPresent()) {
            Project updatedProject = project.get();
            List<ObjectId> assignedUsers = updatedProject.getAssignedUsers();

            if (!assignedUsers.contains(userId)){
                assignedUsers.add(userId);
            }

            return projectRepository.save(updatedProject);
        } else {
            throw new Exception("Project or User not found");
        }
    }

    // Remove a user from a project
    public Project removeUserFromProject(ObjectId projectId, ObjectId userId) throws Exception {
        Optional<Project> project = projectRepository.findById(projectId);
        Optional<User> user = userRepository.findById(userId);

        if (project.isPresent() && user.isPresent()) {
            Project updatedProject = project.get();
            List<ObjectId> assignedUsers = updatedProject.getAssignedUsers();

            assignedUsers.remove(userId);

            return projectRepository.save(updatedProject);
        } else {
            throw new Exception("Project or User not found");
        }
    }

}

