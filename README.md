Jenkins Mashup Portlets
=======================

Additional portlets for jenkins Dashboards (see https://wiki.jenkins-ci.org/display/JENKINS/Dashboard+View)

- Generic JS Portlet: Let's you run arbitrary JS to create content in the portlet 
  frame. The function ajaxViaJenkins() allows running any ajax request without cross domain 
  issues (using https://wiki.jenkins-ci.org/display/JENKINS/AJAX+with+JavaScript+proxy)
- Recent Changes Portlet: Will show the recent SCM changes of a configured project
- SonarQube Issues Portlet: Shows issues for the given projects (projects and severity can be configured)
- Sonar Violations Portlet (Sonar <=3.5): Shows violations for the given projects (projects and severity can be configured)
- Sonar Reviews Portlet (Sonar <=3.5): Shows open sonar reviews

See help sections in portlet configuration for more information.