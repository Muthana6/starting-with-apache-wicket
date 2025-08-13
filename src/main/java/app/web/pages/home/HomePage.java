package app.web.pages.home;
import app.models.Todo;
import app.services.MongoDBService;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
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

  FeedbackPanel fb; // FeedbackPanel to display messages to the user

  public HomePage() {
    Label label = new Label("label", "Hello from Wicket and Spring Boot!" +
        " MongoDBService is injected: " + mongoDBService.getRepo().count());
    add(label);

    fb = new FeedbackPanel("feedbackPanel");
    fb.setOutputMarkupPlaceholderTag(true); // Enable this panel to be updated via Ajax
    add(fb); // Add the FeedbackPanel to the page to display messages

    WebMarkupContainer sectionForm = new WebMarkupContainer("sectionForm");
    sectionForm.setOutputMarkupId(true) ; // Enable this container to be updated via Ajax
    add(sectionForm); // Add a WebMarkupContainer to the page to hold the form

    Form<Void> form = new Form("form");
    sectionForm.add(form);

    WebMarkupContainer formNew = new WebMarkupContainer("formNew");

    AjaxLink<Void> btnAdd = new AjaxLink<>("addItemLink") {
      @Override
      public void onClick(AjaxRequestTarget target) {
        formNew.setVisible(!formNew.isVisible()); // Toggle visibility of the formNew container
        target.add(formNew);
      }
    };
    form.add(btnAdd); // Add an AjaxLink to the form for adding new items

    formNew.setOutputMarkupPlaceholderTag(true); // Enable this container to be updated via Ajax
    formNew.setVisible(true);// Set the visibility of the formNew container to true so it is displayed initially
    form.add(formNew); // Add the container to the form

    Todo todoItem = new Todo(); // Create a new  item to bind to the form fields
    form.setDefaultModel(new CompoundPropertyModel<Object>(todoItem)); // Set the form's model to a CompoundPropertyModel that binds to the todoItem

    TextField<String> title = new TextField<>("title");
    TextField<String> body = new TextField<>("body");
    AjaxLink<Void> btnSave = new AjaxLink<>("save") {
      @Override
      public void onClick(AjaxRequestTarget target) {

        // Create a new  instance and set its properties from the form fields
        Todo todo = new Todo();
        todo.setTitle(title.getValue()); // Get the title from the form field
        todo.setBody(body.getValue()); // Get the body from the form field
        mongoDBService.save(todo); // Save the new item using the MongoDBService

        // Clear the form fields after saving
        todoItem.setTitle("");
        todoItem.setBody("");

        formNew.setVisible(false); // Hide the formNew container after saving

        showInfo(target, "Item saved successfully!"); // Show a success message in the FeedbackPanel
        target.add(sectionForm); // Update the formNew container in the Ajax request
      }
    };

    btnSave.add(new AjaxFormSubmitBehavior(form,"click") {}); // Add an AjaxFormSubmitBehavior to the save button to handle form submission

    formNew.add(title, body, btnSave); // Add text fields for title and body to the formNew container


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
    form.add(todosList);
  }

  /**
   * This method is used to display an informational message in the FeedbackPanel.
   * It updates the FeedbackPanel with the provided message and adds it to the Ajax request target.
   *
   * @param target The AjaxRequestTarget used to update the FeedbackPanel
   * @param msg The message to be displayed in the FeedbackPanel
   */
  private void showInfo(AjaxRequestTarget target, String msg) {
    info(msg); // Display an informational message in the FeedbackPanel
    target.add(fb); // Add the FeedbackPanel to the Ajax request target to update it
  }

}
