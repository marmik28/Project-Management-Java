package marmik.ProjectManagementApp.repositories;

import marmik.ProjectManagementApp.Project;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends MongoRepository<Project, ObjectId> {
}
