package app.repos;

import app.models.Todo;
import org.springframework.data.repository.CrudRepository;

// This interface extends CrudRepository to provide CRUD operations for Todo entities
public interface TodoRepository extends CrudRepository<Todo, String> {

    // Additional query methods can be defined here if needed
    // For example, to find todos by status or user, you can add methods like:
    // List<Todo> findByStatus(String status);
    // List<Todo> findByUserId(Long userId);
}
