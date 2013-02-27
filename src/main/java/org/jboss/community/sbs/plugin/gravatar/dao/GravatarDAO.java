/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.gravatar.dao;

import java.util.List;

import com.jivesoftware.community.impl.dao.AvatarDAO;

/**
 * DAO for Gravatar plugin. Includes mainly helper methods for {@link AvatarDAO}
 * 
 * @author Libor Krzyzanek (lkrzyzan)
 * 
 */
public interface GravatarDAO {

	/**
	 * Get list of users which have custom avatar
	 * 
	 * @return
	 */
	public List<Long> getUsersWithAvatar();

}
