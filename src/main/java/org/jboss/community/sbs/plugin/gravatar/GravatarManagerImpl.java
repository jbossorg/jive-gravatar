/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.gravatar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jivesoftware.base.UserManager;
import com.jivesoftware.base.event.UserEvent;
import com.jivesoftware.base.event.v2.EventListener;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jboss.community.sbs.plugin.gravatar.dao.DbGravatarDAOImpl;
import org.jboss.community.sbs.plugin.gravatar.dao.GravatarDAO;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * Implementation of Gravatar manager.
 *
 * @author Libor Krzyzanek (lkrzyzan)
 */
public class GravatarManagerImpl implements GravatarManager, EventListener<UserEvent> {

	protected static final Logger log = LogManager.getLogger(GravatarManagerImpl.class);

	private UserManager userManager;

	private GravatarDAO gravatarDAO;

	private Md5PasswordEncoder md5Encoder;

	/**
	 * Map of e-mail hashes. Key is email hash and value is user ID
	 *
	 * @see #getEmailHash(String)
	 */
	private Map<String, Long> emailHashMap = null;

	private void initializeHashes() {
		emailHashMap = new HashMap<String, Long>();
		log.info("Initialize hashes for Gravatar plugin");

		List<DbGravatarDAOImpl.UserIdEmailBean> userEmailsWithAvatar = gravatarDAO.getUserEmailsWithAvatar();

		for (DbGravatarDAOImpl.UserIdEmailBean entity : userEmailsWithAvatar) {
			emailHashMap.put(getEmailHash(entity.email), entity.userID);
		}

		if (log.isInfoEnabled()) {
			log.info("Hashes count: " + emailHashMap.size());
		}
		log.info("Hashes for Gravatar plugin generated");
	}

	public void clearEmailHashCache() {
		emailHashMap = null;
	}

	/**
	 * Based on http://en.gravatar.com/site/implement/hash/
	 *
	 * @param email
	 * @return
	 */
	protected String getEmailHash(String email) {
		String normalizedEmail = email.trim().toLowerCase();
		return md5Encoder.encodePassword(normalizedEmail, null);
	}

	@SuppressWarnings("incomplete-switch")
	public void handle(UserEvent e) {
		switch (e.getType()) {
			case CREATED:
				clearEmailHashCache();
		}
	}

	@Override
	public Long getUsername(String emailHash) {
		if (emailHashMap == null) {
			initializeHashes();
		}
		return emailHashMap.get(emailHash);
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setMd5Encoder(Md5PasswordEncoder md5Encoder) {
		this.md5Encoder = md5Encoder;
	}

	public void setGravatarDAO(GravatarDAO gravatarDAO) {
		this.gravatarDAO = gravatarDAO;
	}

}
