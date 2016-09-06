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

package org.jboss.community.sbs.plugin.gravatar.struts.mapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.jboss.community.sbs.plugin.gravatar.struts.GravatarAction;

import com.jivesoftware.community.web.struts.mapping.NamespacedURLMapping;

/**
 * URL Mapper for Gravatar URL<br/>
 * Maps /gravatar/{hash}/{size}.png to {@link GravatarAction}
 * 
 * @author Libor Krzyzanek
 * 
 */
public class GravatarURLMapping implements NamespacedURLMapping {

	public static final String GRAVATAR_NAMESPACE = "gravatar";

	protected static final Logger log = LogManager.getLogger(GravatarURLMapping.class);

	@SuppressWarnings("unchecked")
	@Override
	public void process(String uri, ActionMapping mapping) {
		if (log.isDebugEnabled()) {
			log.debug("Process uri: " + uri + ", mapping params: " + mapping.getParams());
		}
		String[] uriElements = uri.split("/");
		Map<String, Object> params = mapping.getParams();
		if (null == params) {
			params = new HashMap<>();
		}

		if (log.isDebugEnabled()) {
			log.debug("uriElements: " + Arrays.toString(uriElements));
		}

		// /context/gravatar/hash
		if (uriElements.length > 2) {
			params.put("avatarHash", uriElements[2]);
		}
		// /context/gravatar/hash/size.png
		if (uriElements.length > 3) {
			if (uriElements[3].endsWith(".png")) {
				params.put("s", uriElements[3].replace(".png", ""));
			} else {
				params.put("s", uriElements[3]);
			}
		}

		mapping.setName("gravatar");
		mapping.setNamespace("/gravatar-actions");
		mapping.setParams(params);
		mapping.setMethod("execute");
	}

	@Override
	public String getNamespace() {
		return GRAVATAR_NAMESPACE;
	}

}
