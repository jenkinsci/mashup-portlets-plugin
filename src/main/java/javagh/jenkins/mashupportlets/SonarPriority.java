package javagh.jenkins.mashupportlets;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Sonar Priorities used for configuration dropdowns.
 * 
 * @author G.Henzler
 *
 */
public enum SonarPriority {

    INFO, MINOR, MAJOR, CRITICAL, BLOCKER;

    public static String getPriorityValueByNameJson() {
        List<String> items = new LinkedList<String>();
        for (SonarPriority sonarPriority : values()) {
            items.add("'" + sonarPriority.name() + "':"
                    + sonarPriority.ordinal());
        }
        return "{ " + StringUtils.join(items, ", ") + " }";
    }

}