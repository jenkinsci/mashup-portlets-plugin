package javagh.jenkins.mashupportlets;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.bind.JavaScriptMethod;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.plugins.view.dashboard.DashboardPortlet;
import hudson.util.ListBoxModel;
import hudson.util.Secret;

/**
 * Shows SonarQube issues in a portlet.
 * 
 * @author G.Henzler
 *
 */
public class SonarIssuesPortlet extends AbstractMashupPortlet {

    private static final int DEFAULT_PRIO_NO = -1;

    private final String sonarBaseUrl;
    private final String sonarProjectsList;

    private final int sonarPriorityThreshold;
    private final int sonarNewIssuesPriorityThreshold;

    private final int sonarAssigneeStatus;
    private final boolean sonarShowAssigneeBar;
    private final String labelAssigneesRanking;
    private final int maxAssigneesInRanking;
    
    private final int maxEntries;
    private final int deltaDaysForNewIssues;

    private final String divId;

    private Secret secretSonarApiUserOrToken;
    private Secret secretSonarApiPw;
    
    @DataBoundConstructor
    public SonarIssuesPortlet(String name, String sonarBaseUrl,
            String sonarProjectsList, int sonarPriorityThreshold, int sonarAssigneeStatus, boolean sonarShowAssigneeBar,
            // advanced
            int maxEntries, int sonarNewIssuesPriorityThreshold,
            int deltaDaysForNewIssues, int violationDescriptionMaximumLength, 
            Secret secretSonarApiUserOrToken, Secret secretSonarApiPw,
            String labelAssigneesRanking, int maxAssigneesInRanking) {
        super(name);

        this.sonarBaseUrl = Utils.normalizeBaseUrl(sonarBaseUrl);   

        this.sonarProjectsList = sonarProjectsList;

        this.sonarPriorityThreshold = sonarPriorityThreshold;

        this.sonarAssigneeStatus = sonarAssigneeStatus;
        this.sonarShowAssigneeBar = sonarShowAssigneeBar;
        this.labelAssigneesRanking = labelAssigneesRanking;
        this.maxAssigneesInRanking = maxAssigneesInRanking;
        
        this.maxEntries = maxEntries;

        this.sonarNewIssuesPriorityThreshold = sonarNewIssuesPriorityThreshold;

        this.deltaDaysForNewIssues = deltaDaysForNewIssues;

        this.secretSonarApiUserOrToken = secretSonarApiUserOrToken;
        this.secretSonarApiPw = secretSonarApiPw;
        
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
    
    public int getMaxAssigneesInRanking() {
        return maxAssigneesInRanking > 0 ? maxAssigneesInRanking : 5;
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

    public int getSonarAssigneeStatus() {
		return sonarAssigneeStatus;
	}
	
	public boolean isSonarShowAssigneeBar() {
		return sonarShowAssigneeBar;
	}

	public String getLabelAssigneesRanking() {
		return labelAssigneesRanking;
	}

	public Secret getSecretSonarApiUserOrToken() {
		return secretSonarApiUserOrToken;
	}

	public Secret getSecretSonarApiPw() {
		return secretSonarApiPw;
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
    public String getAssigneeStatusValueByNameJson() {
        return SonarAssigneeStatus.getPriorityValueByNameJson();
    }
    
	@JavaScriptMethod
    public HttpResponse ajaxViaJenkins(String urlStr) {
		return new ServerSideHttpCall(urlStr, Secret.toString(secretSonarApiUserOrToken), Secret.toString(secretSonarApiPw));
    }

    @Extension
    public static class SonarIssuesPortletDescriptor extends Descriptor<DashboardPortlet> {

        @Override
        public String getDisplayName() {
            return "SonarQube Issues";
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
        
        public ListBoxModel doFillSonarAssigneeStatusItems() {
            ListBoxModel items = new ListBoxModel();
            for (SonarAssigneeStatus sonarAssigneeStatus : SonarAssigneeStatus.values()) {
                items.add(sonarAssigneeStatus.name(), String.valueOf(sonarAssigneeStatus.ordinal()));
            }
            return items;
        }

    }

}
