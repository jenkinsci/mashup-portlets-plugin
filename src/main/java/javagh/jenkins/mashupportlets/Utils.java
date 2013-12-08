package javagh.jenkins.mashupportlets;

import org.apache.commons.lang.StringUtils;

public class Utils {

    public static String configListToJsonList(String configList) {
        if (configList == null) {
            return "[]";
        }
        String[] items = configList.split("[,; ]+");
        String json = "['" + StringUtils.join(items, "','") + "']";
        return json;
    }

}
