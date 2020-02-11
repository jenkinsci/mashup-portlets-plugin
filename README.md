Jenkins Mashup Portlets
=======================

[![Jenkins Plugin](https://img.shields.io/jenkins/plugin/v/mashup-portlets-plugin.svg)](https://plugins.jenkins.io/mashup-portlets-plugin)
[![GitHub release](https://img.shields.io/github/release/jenkinsci/mashup-portlets-plugin.svg?label=changelog)](https://github.com/jenkinsci/mashup-portlets-plugin/releases/latest)
[![Jenkins Plugin Installs](https://img.shields.io/jenkins/plugin/i/mashup-portlets-plugin.svg?color=blue)](https://plugins.jenkins.io/mashup-portlets-plugin)

Older versions of this plugin may not be safe to use. Please review the
following warnings before using an older version:

-   [Credentials stored in plain
    text](https://jenkins.io/security/advisory/2019-07-11/#SECURITY-775)

## Overview 

Additional portlets for jenkins Dashboards (see https://plugins.jenkins.io/dashboard-view/)

- Generic JS Portlet: Allows to run arbitrary JS to create content in the portlet 
  content area (if the user has edit rights for the given Dashboard View). The function ajaxViaJenkins() 
  allows running ajax requests avoiding cross domain issues by routing them through jenkins
- Recent Changes Portlet: Will show the recent SCM changes of a configured project
- SonarQube Issues Portlet: Shows issues for the given projects (projects and severity can be configured)

See help sections in portlet configuration for more information.

## List of Portlets  

The following portlets can be used in dashboards (requires
plugin [Dashboard
View](https://wiki.jenkins.io/display/JENKINS/Dashboard+View)):

-   **Generic JS Portlet**: Allows to run arbitrary JS to create content
    in the portlet content area by loading html snippets or json data
    from arbitrary sources on the internet/intranet.  

    **Important:**
    Using this portlet will allow anyone that can edit a dashboard view
    to add arbitrary JS that is also visible to/executed by other
    users - only install this plugin if you trust everybody that you
    give permissions to edit views!

-   **Recent Changes Portlet**: Shows the recent SCM changes for a
    configured job
-   **Test Results Portlet**: Shows the test results for a configured
    job
-   **SonarQube Portlet**: Shows issues from SonarQube directly in
    Jenkins (using the REST API)

## Generic JS Portlet

Allows to run arbitrary JS to create content in the portlet content area
by loading html snippets or json data from arbitrary sources on the
internet/intranet. The function ajaxViaJenkins() allows running ajax
requests avoiding cross domain issues by routing them through jenkins.
Any content type that can be handled by javascript (html, json etc.) can
be used.

The following example is not really useful in a real world scenario, but
shows how easy it is to pull in content via javascript. 

Configuration:

![](https://wiki.jenkins.io/download/attachments/71434257/generic-js-portlet-google-example-config.png?version=1&modificationDate=1387792305000&api=v2)  
Portlet View:

![](https://wiki.jenkins.io/download/attachments/71434257/generic-js-portlet-google-example.png?version=1&modificationDate=1387792372000&api=v2)

## Recent Changes Portlet

Shows the recent SCM changes for a configured job. If the optional JIRA
Base URL is configured, JIRA Issue IDs are automatically made
clickable. 

Configuration:

![](https://wiki.jenkins.io/download/attachments/71434257/recent-changes-portlet-config.png?version=1&modificationDate=1387792551000&api=v2)  
Portlet View:

![](https://wiki.jenkins.io/download/attachments/71434257/recent-changes-portlet.png?version=1&modificationDate=1387792708000&api=v2)

## **Test Results Portlet**

Shows the test results for a configured job.

## Changelog

#### Version 1.1.2 (Feb 11 2020)

-   Fix for Generic JS Version
    ([JENKINS-61039](https://issues.jenkins-ci.org/browse/JENKINS-61039))
    to work with latest Dashboard Plugin
    
#### Version 1.1.0 (May 9 2019)

-   Fix for Generic JS Version
    ([JENKINS-57309](https://issues.jenkins-ci.org/browse/JENKINS-57309))
    to work with latest security model of Jenkins
-   Fixed SonarQube Portlet to work correctly with latest SonarQube LTS
    version 6.7.x
    ([JENKINS-57354](https://issues.jenkins-ci.org/browse/JENKINS-57354), [JENKINS-57308](https://issues.jenkins-ci.org/browse/JENKINS-57308)),
    also fixed a security
    issue ![(warning)](https://wiki.jenkins.io/s/en_GB/8100/5084f018d64a97dc638ca9a178856f851ea353ff/_/images/icons/emoticons/warning.svg) Upgrade
    to this version is recommended! (token has to be re-entered)

#### Version 1.0.9 (April 5 2018)

-   Using SonarQube Token instead of user/pw now.

#### Version 1.0.8 (April 20 2017)

-   Add timeouts to HttpURLConnection usage in ServerSideHttpCall as
    used by
    GenericJsPortlet ([JENKINS-43711](https://issues.jenkins-ci.org/browse/JENKINS-43711))

#### Version 1.0.7 (Jan 9 2017)

-   Compatibility to SonarQube 5.6.3 LTS
    ([JENKINS-40357](https://issues.jenkins-ci.org/browse/JENKINS-40357))

#### Version 1.0.6 (Nov 05 2015)

-   Recent Changes Portlet now allows to configure the maximal number of
    changes to be shown (thanks
    to [tabeyti](https://github.com/tabeyti) for this pull request).

#### Version 1.0.5 (Jan 24 2015)

-   SonarQube Portlet allows to show alerts for given project(s) now
-   Fixed Generic JS Portlet's js method ajaxViaJenkins to run without
    credentials for current Jenkins versions (tested with 1.580.1). The
    JS method ajaxViaJenkins() now works again with either two (url and
    callback) or four (url, user, pw, callback)
    parameters. See [JENKINS-26556](https://issues.jenkins-ci.org/browse/JENKINS-26556). 

#### Version 1.0.3 (Aug 25 2014)

-   Minor layout fix in SonarQube portlet

#### Version 1.0.2 (Aug 12 2014)

-   SonarQube portlet: Added config option for maximum users shown in
    asignees ranking and fixed error handling (if ajaxViaJenkins fails)

#### Version 1.0.1 (Jul 31 2014)

-   Fixed search URL (when clicking on user in busiest assignees) for
    SonarQube 4.1.2

#### Version 1.0.0 (Jul 24 2014)

-   Added code mirror editing for javascript in view configuration
    (Generic JS Portlet)
-   Made SonarQube portlet compatible with latest SonarQube version
    (tested with 4.1.2 and 4.3.2). Removed old Sonar Portlets for Sonar
    \< 3.6

#### Version 0.9.2 (Jan 19 2014)

-   Fixed Link in Recent Changes Portlet
    ([JENKINS-21429](https://issues.jenkins-ci.org/browse/JENKINS-21429))
-   Added functionality from deprecated Sonar Reviews Portlet to
    SonarQube Issues Portlet
    ([JENKINS-21430](https://issues.jenkins-ci.org/browse/JENKINS-21430))

#### Version 0.9.1 (Dec 23 2013)

-   Fixed image URLs

#### Version 0.9 (Dec 19 2013)

-   Initial Version at jenkins-ci.org
    (<https://github.com/jenkinsci/mashup-portlets-plugin>)
