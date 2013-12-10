package javagh.jenkins.mashupportlets;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.plugins.view.dashboard.DashboardPortlet;
import hudson.util.ListBoxModel;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.bind.JavaScriptMethod;

/**
 * Shows Sonar (version<=3.5) reviews in a portlet.
 * 
 * @author G.Henzler
 *
 */
public class SonarReviewsPortlet extends AbstractMashupPortlet {

    private static final int DEFAULT_MAX_ENTRIES = 30;

    private final String sonarBaseUrl;
    private final int maxEntries;

    private final String limitToProjects;
    private final String limitToStatus;

    private final String displayMode;

    private final String divId;
    
    private final String sonarApiUser;
    private final String sonarApiPw;

    @DataBoundConstructor
    public SonarReviewsPortlet(String name, String sonarBaseUrl,
            int maxEntries, String limitToProjects, String limitToStatus, String displayMode, String sonarApiUser, String sonarApiPw) {
        super(name);
        this.divId = "sonarDiv_" + getId();

        this.sonarBaseUrl = Utils.normalizeBaseUrl(sonarBaseUrl);   

        this.limitToProjects = limitToProjects;

        this.limitToStatus = limitToStatus;
        this.maxEntries = maxEntries;
        this.displayMode = displayMode;
        
        this.sonarApiUser = sonarApiUser;
        this.sonarApiPw = sonarApiPw;

    }

    public String getDivId() {
        return divId;
    }

    public String getSonarBaseUrl() {
        return sonarBaseUrl;
    }

    public int getMaxEntries() {
        return maxEntries > 0 ? maxEntries : DEFAULT_MAX_ENTRIES;
    }

    public String getLimitToProjects() {
        return limitToProjects;
    }

    public String getLimitToStatus() {
        return StringUtils.isNotBlank(limitToStatus) ? limitToStatus : "OPEN,REOPENED";
    }

    public String getDisplayMode() {
        return displayMode;
    }

    public String getLimitToProjectsJson() {
        String projectsJson = Utils.configListToJsonList(limitToProjects);
        return projectsJson;
    }

    public String getLimitToStatusJson() {
        String limitToStatusJson = Utils.configListToJsonList(getLimitToStatus());
        return limitToStatusJson;
    }

    public String getPriorityValueByNameJson() {
        return SonarPriority.getPriorityValueByNameJson();
    }

	@JavaScriptMethod
    public HttpResponse ajaxViaJenkins(String urlStr) {
		return new ServerSideHttpCall(urlStr, sonarApiUser, sonarApiPw);
    }

    @Extension
    public static class SonarReviewsPortletDescriptor extends Descriptor<DashboardPortlet> {

        public static final String MODE_COUNTS = "countsByUser";
        public static final String MODE_LIST = "completeList";

        @Override
        public String getDisplayName() {
            return "Sonar Reviews (Sonar <= 3.5)";
        }

        public ListBoxModel doFillDisplayModeItems() {
            ListBoxModel items = new ListBoxModel();
            items.add("Small - Review counts per user", MODE_COUNTS);
            items.add("Large - Complete list of reviews", MODE_LIST);
            return items;
        }

    }

}
