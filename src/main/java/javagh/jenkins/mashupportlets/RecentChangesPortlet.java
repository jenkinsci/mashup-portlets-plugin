package javagh.jenkins.mashupportlets;

import hudson.Extension; 
import hudson.model.Descriptor;
import hudson.plugins.view.dashboard.DashboardPortlet;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Shows the recent changes for a jenkins job in a portlet. 
 * 
 * @author G.Henzler
 *
 */
public class RecentChangesPortlet extends AbstractMashupPortlet {

	private String jenkinsJobName;
	private String jiraUrl;

	
	@DataBoundConstructor
	public RecentChangesPortlet(String name, String jenkinsJobName, String jiraUrl) {
		super(name);
		this.jenkinsJobName = jenkinsJobName;
		this.jiraUrl=jiraUrl;
	}

	
	public String getJenkinsJobName() {
		return jenkinsJobName;
	}

	public String getJenkinsJobNameForUrl() {
		
		return jenkinsJobName==null?"":jenkinsJobName.replace(" ", "%20");
		
	
	}
	
	

	
	public String getJiraUrl() {
		return jiraUrl;
	}




	@Extension
	public static class RecentChangesPortletDescriptor extends Descriptor<DashboardPortlet> {

		@Override
		public String getDisplayName() {
			return "Recent Changes Portlet";
		}
	}

}
