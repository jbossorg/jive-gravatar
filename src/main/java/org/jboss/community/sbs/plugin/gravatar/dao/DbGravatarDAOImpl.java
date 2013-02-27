/*
 * JBoss Community http://jboss.org/
 *
 * Copyright (c) 2010 Red Hat Middleware, LLC. All rights reserved.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT A WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License, v.2.1 along with this distribution; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 *
 * Red Hat Author(s): Libor Krzyzanek
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
