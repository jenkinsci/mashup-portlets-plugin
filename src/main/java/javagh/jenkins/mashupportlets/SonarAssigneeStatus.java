package javagh.jenkins.mashupportlets;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * Sonar Assignee Status (used in config dropdowns).
 * 
 * @author G.Henzler
 *
 */
public enum SonarAssigneeStatus {

    ALL, UNASSIGNED, ASSIGNED;

    public static String getPriorityValueByNameJson() {
        List<String> items = new LinkedList<String>();
        for (SonarAssigneeStatus sonarPriority : values()) {
            items.add("'" + sonarPriority.name() + "':"
                    + sonarPriority.ordinal());
        }
        return "{ " + StringUtils.join(items, ", ") + " }";
    }
    
}