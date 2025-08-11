package app.web.pages;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * BasePage is an abstract parent class for all Wicket pages in the application.
 *
 * It:
 * - Provides common initialization for pages.
 * - Injects dependencies using Wicket's Injector.
 * - Adds Bootstrap and jQuery resources to every page automatically.
 */
public abstract class BasePage extends WebPage {

	/**
	 * Constructor with page parameters.
	 *
	 * @param params PageParameters for this page (e.g., query params in URL).
	 */
	public BasePage(PageParameters params) {
		super(params);
		initPage();
	}

	/**
	 * Default constructor.
	 * Automatically injects dependencies into this page.
	 */
	public BasePage() {
		// Wicket's dependency injection (e.g., @Inject or @SpringBean)
		Injector.get().inject(this);
		initPage();
	}

	/**
	 * Common page initialization logic for all pages.
	 * Currently empty but can be extended for:
	 * - Adding common components (e.g., navigation bar)
	 * - Setting page-specific metadata (e.g., title)
	 */
	private void initPage() {
		// Placeholder for shared initialization code
	}

	/**
	 * Adds necessary JavaScript and CSS libraries to the HTML <head>.
	 *
	 * @param response The header response object used to include resources.
	 */
	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);

		// Include jQuery from Wicket's configured JavaScript settings
		response.render(JavaScriptHeaderItem.forReference(
				getApplication().getJavaScriptLibrarySettings().getJQueryReference()
		));

		// Include Wicket's AJAX JavaScript for Ajax-enabled components
		response.render(JavaScriptHeaderItem.forReference(
				getApplication().getJavaScriptLibrarySettings().getWicketAjaxReference()
		));

		// Include Bootstrap 5 JavaScript (from CDN)
		response.render(JavaScriptHeaderItem.forUrl(
				"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
		));

		// Include Bootstrap 5 CSS (from CDN)
		response.render(CssHeaderItem.forUrl(
				"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
		));
	}
}
