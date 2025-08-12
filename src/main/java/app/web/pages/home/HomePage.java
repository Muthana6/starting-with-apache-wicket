package app.web.pages.home;
import app.models.Todo;
import app.services.MongoDBService;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import app.web.pages.BasePage;

import java.util.List;


@WicketHomePage
@MountPath(value = "home", alt = {"home2"})
@Slf4j
public class HomePage extends BasePage {
  @SpringBean
  private MongoDBService mongoDBService;

  public HomePage() {
    Label label = new Label("label", "Hello from Wicket and Spring Boot!" +
        " MongoDBService is injected: " + mongoDBService.getRepo().count());
    add(label);

    List<Todo> todos = mongoDBService.getAllItems(); // Fetch all items from the MongoDBService

    // Create a ListView to display the list of items
    ListView<Todo> todosList = new ListView<>("todosList", todos) {

      // This method is called for each item in the list to populate the ListItem
      @Override
      protected void populateItem(ListItem<Todo> item) {
        // Add a Label component to display the 'title' property of the current item
        // PropertyModel binds the label to the 'title' property of the object in this list item
        item.add(new Label(
                "title",
                new PropertyModel<String>(item.getModel(), "title")
        ));

        // Add another Label component to display the 'body' property of the current item
        // Using a lambda expression to bind the label to the 'body' property of the object in this list item
        item.add(new Label(
                "body",
                () -> item.getModelObject().getBody()
        ));
      }
    };
    add(todosList);
  }

}
