package javagh.jenkins.mashupportlets;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.plugins.view.dashboard.DashboardPortlet;
import hudson.util.ListBoxModel;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.bind.JavaScriptMethod;

/**
 * Shows Sonar (version<=3.5) violations in a portlet.
 * 
 * @author G.Henzler
 *
 */
public class SonarViolationsPortlet extends AbstractMashupPortlet {

    private static final int DEFAULT_PRIO_NO = -1;

    private final String sonarBaseUrl;
    private final String sonarProjectsList;

    private final int sonarPriorityThreshold;
    private final int sonarNewIssuesPriorityThreshold;

    private final int maxEntries;
    private final int deltaDaysForNewIssues;

    private final String divId;
    
    private final String sonarApiUser;
    private final String sonarApiPw;

    @DataBoundConstructor
    public SonarViolationsPortlet(String name, String sonarBaseUrl,
            String sonarProjectsList, int sonarPriorityThreshold,
            int maxEntries, int sonarNewIssuesPriorityThreshold,
            int deltaDaysForNewIssues, int violationDescriptionMaximumLength, String sonarApiUser, String sonarApiPw) {
        super(name);
        
        this.sonarBaseUrl = Utils.normalizeBaseUrl(sonarBaseUrl);   
        this.sonarProjectsList = sonarProjectsList;

        this.sonarPriorityThreshold = sonarPriorityThreshold;

        this.maxEntries = maxEntries;

        this.sonarNewIssuesPriorityThreshold = sonarNewIssuesPriorityThreshold;

        this.deltaDaysForNewIssues = deltaDaysForNewIssues;

        this.sonarApiUser = sonarApiUser;
        this.sonarApiPw = sonarApiPw;
        
        this.divId = "sonarDiv_" + getId();
    }



    public String getDivId() {
        return divId;
    }

    public String getSonarBaseUrl() {
        return sonarBaseUrl;
    }

    public String getSonarProjectsList() {
        return sonarProjectsList;
    }

    public int getMaxEntries() {
        return maxEntries > 0 ? maxEntries : 50;
    }

    public int getSonarPriorityThreshold() {
        return sonarPriorityThreshold;
    }

    public int getSonarNewIssuesPriorityThreshold() {
        return sonarNewIssuesPriorityThreshold;
    }

    public int getDeltaDaysForNewIssues() {
        return deltaDaysForNewIssues > 0 ? deltaDaysForNewIssues : 5;
    }

    public String getSonarProjectsJson() {
        String projectsJson = Utils.configListToJsonList(sonarProjectsList);
        return projectsJson;
    }

    public String getSonarPrioritiesJson() {
        return getPrioritiesListForThreshold(sonarPriorityThreshold);
    }

    public String getSonarNewIssuesPrioritiesJson() {
        int applicableThreshold = sonarNewIssuesPriorityThreshold != DEFAULT_PRIO_NO ? sonarNewIssuesPriorityThreshold
                : sonarPriorityThreshold;
        return getPrioritiesListForThreshold(applicableThreshold);
    }

    private String getPrioritiesListForThreshold(int threshold) {
        SonarPriority[] values = SonarPriority.values();
        Object[] applicableValues = ArrayUtils.subarray(values, threshold, values.length);
        ArrayUtils.reverse(applicableValues);
        String json = "['" + StringUtils.join(applicableValues, "', '") + "']";
        return json;
    }

    public String getPriorityValueByNameJson() {
        return SonarPriority.getPriorityValueByNameJson();
    }

	@JavaScriptMethod
    public HttpResponse ajaxViaJenkins(String urlStr) {
		return new ServerSideHttpCall(urlStr, sonarApiUser, sonarApiPw);
    }

    @Extension
    public static class SonarViolationsPortletDescriptor extends Descriptor<DashboardPortlet> {

        @Override
        public String getDisplayName() {
            return "Sonar Violations (Sonar <= 3.5)";
        }

        public ListBoxModel doFillSonarPriorityThresholdItems() {
            ListBoxModel items = new ListBoxModel();
            for (SonarPriority sonarPriority : SonarPriority.values()) {
                items.add(sonarPriority.name(), String.valueOf(sonarPriority.ordinal()));
            }
            return items;
        }

        public ListBoxModel doFillSonarNewIssuesPriorityThresholdItems() {
            ListBoxModel items = new ListBoxModel();
            items.add("Same as for default list", String.valueOf(DEFAULT_PRIO_NO));
            items.addAll(doFillSonarPriorityThresholdItems());
            return items;
        }

    }

}
