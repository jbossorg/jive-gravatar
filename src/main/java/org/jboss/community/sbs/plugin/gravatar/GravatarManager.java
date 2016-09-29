/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.gravatar;

import java.util.Map;

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
     * Generate hashes
     *
     * @return new map with gravatar hashes
     */
    public Map<String, Long> generateHashes();

    /**
     * Set new email hash map
     *
     * @param emailHashMap
     */
    public void setEmailHashMap(Map<String, Long> emailHashMap);

}
