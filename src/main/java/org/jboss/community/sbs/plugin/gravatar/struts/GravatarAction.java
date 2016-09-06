/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.gravatar.struts;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jboss.community.sbs.plugin.gravatar.GravatarManager;

import com.jivesoftware.community.action.UserAvatarAction;
import com.jivesoftware.community.action.util.AlwaysAllowAnonymous;
import com.jivesoftware.community.web.struts.SetReferer;

/**
 * Action provides Gravatar bridge to SBS avatar
 * 
 * @see http://en.gravatar.com/site/implement/images/
 * @author <a href="mailto:lkrzyzan@redhat.com">Libor Krzyzanek</a>
 */
@SetReferer(false)
@AlwaysAllowAnonymous
public class GravatarAction extends UserAvatarAction {

	private static final long serialVersionUID = 3823341890793708625L;

	protected static final Logger log = LogManager.getLogger(GravatarAction.class);

	private String avatarHash;

	/**
	 * Avatar size
	 */
	private Integer[] s;

	private GravatarManager gravatarManager;

	@Override
	public String input() {
		return this.execute();
	}

	@Override
	public String execute() {
		if (log.isDebugEnabled()) {
			log.debug("Get Gravatar for hash: " + avatarHash + ", size: " + s);
		}
		Long userID = gravatarManager.getUsername(avatarHash);
		if (userID != null) {
			setUserID(userID);
		}
		if (s != null && s.length > 0) {
			setSize(s[0]);
		}

		return super.execute();
	}

	public void setAvatarHash(String avatarHash) {
		this.avatarHash = avatarHash;
	}

	public void setS(Integer[] s) {
		this.s = s;
	}

	public Integer[] getS() {
		return s;
	}

	public void setGravatarManager(GravatarManager gravatarManager) {
		this.gravatarManager = gravatarManager;
	}
}
