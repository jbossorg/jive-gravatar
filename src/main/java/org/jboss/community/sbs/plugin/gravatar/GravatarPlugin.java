/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.community.sbs.plugin.gravatar;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jboss.community.sbs.plugin.gravatar.struts.mapping.GravatarURLMapping;

import com.jivesoftware.base.plugin.Plugin;
import com.jivesoftware.community.web.struts.JiveRestfulActionMapper;

/**
 * Gravatar Plugin inititializer
 * 
 * @author Libor Krzyzanek
 * 
 */
public class GravatarPlugin implements Plugin<GravatarPlugin> {

	protected static final Logger log = LogManager.getLogger(GravatarPlugin.class);

	private final String PLUGIN_NAME = "Gravatar Plugin";

	private JiveRestfulActionMapper actionMapper;

	private GravatarURLMapping gravatarURLMapping;

	@Override
	public void init() {
		log.info("Init " + PLUGIN_NAME);

		actionMapper.addURLMapping("/" + gravatarURLMapping.getNamespace(), gravatarURLMapping);
		log.info("GravatarURLMapping added, " + PLUGIN_NAME + " initialized");
	}

	@Override
	public void destroy() {
	}

	public JiveRestfulActionMapper getActionMapper() {
		return actionMapper;
	}

	public void setActionMapper(JiveRestfulActionMapper actionMapper) {
		this.actionMapper = actionMapper;
	}

	public GravatarURLMapping getGravatarURLMapping() {
		return gravatarURLMapping;
	}

	public void setGravatarURLMapping(GravatarURLMapping gravatarURLMapping) {
		this.gravatarURLMapping = gravatarURLMapping;
	}
}
