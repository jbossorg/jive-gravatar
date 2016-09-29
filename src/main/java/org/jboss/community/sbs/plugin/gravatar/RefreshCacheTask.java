/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */

package org.jboss.community.sbs.plugin.gravatar;

import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Task which clears email hash cache
 *
 * @author Libor Krzyzanek
 */
public class RefreshCacheTask implements Runnable {

	protected static final Logger log = LogManager.getLogger(RefreshCacheTask.class);

	private GravatarManager gravatarManager;

	@Override
	public void run() {
		log.debug("Going to refresh gravatar hashes");
		try {
			Map<String, Long> map = gravatarManager.generateHashes();
			if (log.isInfoEnabled()) {
				log.info("Hashes for Gravatar plugin generated. Count: " + map.size());
			}
			gravatarManager.setEmailHashMap(map);
		} catch (RuntimeException e) {
			log.error("Error occurred during gravatar hashes refresh", e);
		}
	}

	public void setGravatarManager(GravatarManager gravatarManager) {
		this.gravatarManager = gravatarManager;
	}
}
