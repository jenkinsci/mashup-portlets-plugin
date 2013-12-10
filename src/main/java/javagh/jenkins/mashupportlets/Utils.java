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

    public static  String normalizeBaseUrl(String sonarBaseUrl) {
		if(sonarBaseUrl.endsWith("/")) {
        	sonarBaseUrl = sonarBaseUrl.substring(0, sonarBaseUrl.length()-1);
        }
		return sonarBaseUrl;
	}
	
    
}
