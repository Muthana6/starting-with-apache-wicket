package app.web.pages.home;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.annotation.mount.MountPath;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import app.web.pages.BasePage;


@WicketHomePage
@MountPath(value = "home", alt = {"home2"})
@Slf4j
public class HomePage extends BasePage {

  public HomePage() {
    Label label = new Label("label", "Hello from Wicket and Spring Boot!");
    add(label);
  }



}
