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
package org.jboss.community.sbs.plugin.gravatar.struts;

import org.jboss.community.sbs.plugin.gravatar.GravatarManager;

import com.jivesoftware.community.action.UserAvatarAction;
import com.jivesoftware.community.license.annotations.LicensePageViewHint;
import com.jivesoftware.community.license.metering.pageview.impl.JivePageViewHint;
import com.jivesoftware.community.web.struts.SetReferer;

/**
 * Action provides Gravatar bridge to SBS avatar
 * 
 * @see http://en.gravatar.com/site/implement/images/
 * @author <a href="mailto:lkrzyzan@redhat.com">Libor Krzyzanek</a>
 */
@LicensePageViewHint(hint = { JivePageViewHint.never })
// never score as a pageview
@SetReferer(false)
public class GravatarAction extends UserAvatarAction {

  private static final long serialVersionUID = 3823341890793708625L;

  private String avatarHash;

  /**
   * Avatar size
   */
  private Integer s;

  private GravatarManager gravatarManager;

  @Override
  public String execute() {
    Long userID = gravatarManager.getUsername(avatarHash);
    if (userID != null) {
      setUserID(userID);
    }
    if (s != null) {
      setSize(s);
    }

    return super.execute();
  }

  public void setAvatarHash(String avatarHash) {
    this.avatarHash = avatarHash;
  }

  public void setS(Integer s) {
    this.s = s;
  }

  public Integer getS() {
    return s;
  }

  public void setGravatarManager(GravatarManager gravatarManager) {
    this.gravatarManager = gravatarManager;
  }
}
