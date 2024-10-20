package com.marmik.backend.repositories;

import com.marmik.backend.Task;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, ObjectId> {
    List<Task> findByProjectId(ObjectId projectId);
}
