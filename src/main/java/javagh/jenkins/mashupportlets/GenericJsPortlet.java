package javagh.jenkins.mashupportlets;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.plugins.view.dashboard.DashboardPortlet;

import java.util.Random;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.bind.JavaScriptMethod;

/**
 * Generic JS Portlet for creating mashups. 
 * 
 * @author G.Henzler
 *
 */
public class GenericJsPortlet extends AbstractMashupPortlet {
	
	private static Random generator = new Random(); 
	  
	private String javascript;

	private String containerDivId;
	
	@DataBoundConstructor
	public GenericJsPortlet(String name, String javascript) {
		super(name);
		this.javascript = javascript;
		// needed since hudson.plugins.view.dashboard.DashboardPortlet changed its id handling with version 2.12
		// at configuration change time (on view save and when this code is executed) getId() does not work
		// properly, it only returns "portlet-" without a number
		// in genericjsportlet.jelly getId() cannot be used anymore as a frontend ID because it contains a dash now (using containerDivId in all places instead) 
		int uniqueId = generator.nextInt(32000);
		this.containerDivId = "genericJs" + uniqueId;
	}
	
	public String getJavascript() {
		return javascript;
	}
	
	public String getContainerDivId() {
		return containerDivId;
	}
	
	@JavaScriptMethod
    public HttpResponse ajaxViaJenkinsWithoutAuth(String urlStr) {
		return new ServerSideHttpCall(urlStr, null, null);
    }
	
	@JavaScriptMethod
    public HttpResponse ajaxViaJenkinsWithAuth(String urlStr, String user, String pw) {
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
