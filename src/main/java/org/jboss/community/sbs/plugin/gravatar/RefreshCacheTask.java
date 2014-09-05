/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */

package org.jboss.community.sbs.plugin.gravatar;

/**
 * Task which clears email hash cache
 *
 * @author Libor Krzyzanek
 */
public class RefreshCacheTask implements Runnable {

	private GravatarManager gravatarManager;

	@Override
	public void run() {
		gravatarManager.initializeHashes();
	}

	public void setGravatarManager(GravatarManager gravatarManager) {
		this.gravatarManager = gravatarManager;
	}
}
