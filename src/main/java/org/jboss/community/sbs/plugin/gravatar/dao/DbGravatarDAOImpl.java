/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.gravatar.dao;

import java.util.List;

import com.jivesoftware.base.database.LongRowMapper;
import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;

/**
 * @author Libor Krzyzanek (lkrzyzan)
 * 
 */
public class DbGravatarDAOImpl extends JiveJdbcDaoSupport implements GravatarDAO {

	@Override
	public List<Long> getUsersWithAvatar() {
		String sql = "SELECT userID FROM jiveAvatarUser";
		return getSimpleJdbcTemplate().query(sql, LongRowMapper.getLongRowMapper());
	}

}
