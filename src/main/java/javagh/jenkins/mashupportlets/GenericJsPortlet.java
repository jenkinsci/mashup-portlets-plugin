package javagh.jenkins.mashupportlets;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.plugins.view.dashboard.DashboardPortlet;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.bind.JavaScriptMethod;

public class GenericJsPortlet extends AbstractMashupPortlet {
	
	private String javascript;
	
	private String containerDivId;
	
	@DataBoundConstructor
	public GenericJsPortlet(String name, String javascript) {
		super(name);
		this.javascript = javascript;
		this.containerDivId = "div_genericJs_" + getId();
	}
	
	public String getJavascript() {
		return javascript;
	}

	public String getContainerDivId() {
		return containerDivId;
	}

	@JavaScriptMethod
    public HttpResponse ajaxViaJenkins(String urlStr, String user, String pw) {
		return new ServerSideHttpCall(urlStr, user, pw);
    }

	@Extension
	public static class GenericJsPortletDescriptor extends Descriptor<DashboardPortlet> {

		@Override
		public String getDisplayName() {
			return "Generic JS Portlet";
		}
	}

}
