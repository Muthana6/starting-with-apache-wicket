package app.web.pages.home;
import app.services.MongoDBService;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import app.web.pages.BasePage;


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
  }




}
