package javagh.jenkins.mashupportlets;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.plugins.view.dashboard.DashboardPortlet;

import org.kohsuke.stapler.DataBoundConstructor;

public class TestResultsPortlet extends AbstractMashupPortlet {

	private String jenkinsJobName;
	
	@DataBoundConstructor
	public TestResultsPortlet(String name, String jenkinsJobName) {
		super(name);
		this.jenkinsJobName = jenkinsJobName;
	}
	
	public String getJenkinsJobName() {
		return jenkinsJobName;
	}

	public String getJenkinsJobNameForUrl() {
		return jenkinsJobName==null?"":jenkinsJobName.replace(" ", "%20");
	}


	@Extension
	public static class RecentChangesPortletDescriptor extends Descriptor<DashboardPortlet> {

		@Override
		public String getDisplayName() {
			return "Test Results Portlet";
		}
	}

}
