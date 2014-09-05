/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.gravatar;

/**
 * @author Libor Krzyzanek
 */
public interface GravatarManager {

	/**
	 * Get username based on e-mail hash
	 *
	 * @param emailHash
	 * @return
	 * @see http://en.gravatar.com/site/implement/hash/
	 */
	public Long getUsername(String emailHash);


	/**
	 * Initialize hashes
	 */
	public void initializeHashes();

}
