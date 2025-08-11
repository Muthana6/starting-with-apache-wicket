package app.services;

import app.models.Todo;
import app.repos.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j // Using Lombok's @Slf4j to enable logging
public class MongoDBService {

    @Autowired
    private TodoRepository repo;

    /**
     * This method is called during the application startup to populate the MongoDB database with initial data.
     * It creates and saves 8 Todo instances with titles and bodies.
     */
    @PostConstruct // This annotation indicates that this method should be called after the bean's properties have been set
    void setup() {
        log.info("MongoDBService setup called and populating the database with initial data.");
        repo.deleteAll(); // Clear the existing data in the repository

        // Populating the database with initial data
        for(int i = 0; i < 8; i++) {
            Todo todo = new Todo(); // Create a new Todo instance
            todo.setTitle("Todo " + i);
            todo.setBody("This is the body of Todo " + i);
            save(todo); // Save the Todo instance to the repository
        }
    }

    /**
     * Saves a Todo instance to the MongoDB repository.
     *
     * @param todo The Todo instance to be saved.
     */
    public void save(Todo todo) {
        log.info("Saving Todo: {}", todo);
        repo.save(todo); // Save the Todo instance to the MongoDB repository
    }
}
