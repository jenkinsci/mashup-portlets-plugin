package javagh.jenkins.mashupportlets;

import hudson.plugins.view.dashboard.DashboardPortlet;

/** 
 * No code on java level, just on jelly include level.
 *
 * @author G.Henzler
 *
 */
public abstract class AbstractMashupPortlet extends DashboardPortlet {

	public AbstractMashupPortlet(String name) {
		super(name);
	}

}
