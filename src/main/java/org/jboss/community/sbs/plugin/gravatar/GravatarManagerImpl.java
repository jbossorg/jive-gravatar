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
package org.jboss.community.sbs.plugin.gravatar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jboss.community.sbs.plugin.gravatar.dao.GravatarDAO;

import com.jivesoftware.base.User;
import com.jivesoftware.base.UserManager;
import com.jivesoftware.base.UserNotFoundException;
import com.jivesoftware.base.event.UserEvent;
import com.jivesoftware.base.event.v2.EventListener;

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

    List<Long> users = gravatarDAO.getUsersWithAvatar();

    for (Long userID : users) {
      User user;
      try {
        user = userManager.getUser(userID);
        emailHashMap.put(getEmailHash(user.getEmail()), userID);
      } catch (UserNotFoundException e) {
        log.warn("Cannot load user for Gravatar plugin", e);
      }
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

  public void handle(UserEvent e) {
    switch (e.getType()) {
    case CREATED:
      clearEmailHashCache();
    }
  };

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
