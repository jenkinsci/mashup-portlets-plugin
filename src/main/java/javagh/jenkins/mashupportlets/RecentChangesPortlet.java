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
	private int numChanges;
	private String jiraUrl;


	@DataBoundConstructor
	public RecentChangesPortlet(String name, String jenkinsJobName, int numChanges, String jiraUrl) {
		super(name);
		this.jenkinsJobName = jenkinsJobName;
		this.numChanges = numChanges;
		this.jiraUrl=jiraUrl;
	}

	public String getJenkinsJobName() {
		return jenkinsJobName;
	}

	public String getJenkinsJobNameForUrl() {
		return jenkinsJobName==null?"":jenkinsJobName.replace(" ", "%20");
	}

	public int getNumChanges() {
		return numChanges;
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
