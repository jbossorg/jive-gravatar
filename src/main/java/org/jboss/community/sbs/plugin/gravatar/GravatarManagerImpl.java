/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.gravatar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jboss.community.sbs.plugin.gravatar.dao.DbGravatarDAOImpl;
import org.jboss.community.sbs.plugin.gravatar.dao.GravatarDAO;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.jivesoftware.base.User;
import com.jivesoftware.base.UserManager;
import com.jivesoftware.base.UserNotFoundException;
import com.jivesoftware.base.event.v2.EventListener;
import com.jivesoftware.community.event.AvatarEvent;

/**
 * Implementation of Gravatar manager.
 *
 * @author Libor Krzyzanek (lkrzyzan)
 */
public class GravatarManagerImpl implements GravatarManager, EventListener<AvatarEvent> {

	protected static final Logger log = LogManager.getLogger(GravatarManagerImpl.class);

	private GravatarDAO gravatarDAO;

    private UserManager userManager;

	private Md5PasswordEncoder md5Encoder;

	/**
	 * Map of e-mail hashes. Key is email hash and value is user ID
	 *
	 * @see #getEmailHash(String)
	 */
	private Map<String, Long> emailHashMap = new HashMap<>();

	@Override
	public Map<String, Long> generateHashes() {
		HashMap<String, Long> map = new HashMap<>();
		log.info("Initialize hashes for Gravatar plugin");

		List<DbGravatarDAOImpl.UserIdEmailBean> userEmailsWithAvatar = gravatarDAO.getUserEmailsWithAvatar();

		for (DbGravatarDAOImpl.UserIdEmailBean entity : userEmailsWithAvatar) {
			map.put(getEmailHash(entity.email), entity.userID);
		}

		return map;
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
	public void handle(AvatarEvent e) {
		if (log.isTraceEnabled()) {
			log.trace("Avatar event captured: " + e);
		}
		// see DbAvatarManager.fireActiveAvatarModified(User, Avatar, Map<String, ?>)
        if (e.getType() == AvatarEvent.Type.MODIFIED) {
			Long userID = (Long) e.getParams().get("userID");

			try {
				User user = userManager.getUser(userID);

				if (log.isDebugEnabled()) {
					log.debug("Avatar modified. Going to add new hash for user id: " + user.getID());
				}
				synchronized (emailHashMap) {
					emailHashMap.putIfAbsent(getEmailHash(user.getEmail()), user.getID());
				}
			} catch (UserNotFoundException e1) {
				log.error("cannot find user", e1);
			}
		}
	}

	@Override
	public Long getUsername(String emailHash) {
		if (emailHashMap.isEmpty()) {
			synchronized (emailHashMap) {
				emailHashMap = generateHashes();
				if (log.isInfoEnabled()) {
					log.info("Hashes for Gravatar plugin generated. Count: " + emailHashMap.size());
				}
			}
		}
		return emailHashMap.get(emailHash);
	}

	@Override
	public void setEmailHashMap(Map<String, Long> emailHashMap) {
		this.emailHashMap = emailHashMap;
	}

	@Required
	public void setMd5Encoder(Md5PasswordEncoder md5Encoder) {
		this.md5Encoder = md5Encoder;
	}

	@Required
	public void setGravatarDAO(GravatarDAO gravatarDAO) {
		this.gravatarDAO = gravatarDAO;
	}

	@Required
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
