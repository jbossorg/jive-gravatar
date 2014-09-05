/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.gravatar.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Libor Krzyzanek
 */
public class DbGravatarDAOImpl extends JiveJdbcDaoSupport implements GravatarDAO {

	@Override
	public List<UserIdEmailBean> getUserEmailsWithAvatar() {
		String sql = "SELECT ju.userID, ju.email FROM jiveAvatarUser ja join jive.jiveuser ju on ju.userID = ja.userID;";
		return getSimpleJdbcTemplate().query(sql, new RowMapper<UserIdEmailBean>() {
			@Override
			public UserIdEmailBean mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new UserIdEmailBean(rs.getLong(1), rs.getString(2));
			}
		});
	}

	/**
	 * Helper bean
	 */
	public class UserIdEmailBean {
		public Long userID;

		public String email;

		public UserIdEmailBean(Long userID, String email) {
			this.userID = userID;
			this.email = email;
		}
	}

}
