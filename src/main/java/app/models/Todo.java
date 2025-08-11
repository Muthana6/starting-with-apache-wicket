package app.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "todos")
@Data // Using @Data from Lombok to generate getters, setters, toString, equals, and hashCode methods
public class Todo {
    @Id
    private String id;

    private String title;
    private String body;
    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean closed = false;
}
