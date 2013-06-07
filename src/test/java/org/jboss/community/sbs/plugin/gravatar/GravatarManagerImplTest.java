/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 */
package org.jboss.community.sbs.plugin.gravatar;

import static org.junit.Assert.assertEquals;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.junit.Test;

public class GravatarManagerImplTest {

  private GravatarManagerImpl gravatarManager;

  @Test
  public void testGetEmailHash() {
    gravatarManager = new GravatarManagerImpl();
    gravatarManager.setMd5Encoder(new Md5PasswordEncoder());

    assertEquals("0bc83cb571cd1c50ba6f3e8a78ef1346", gravatarManager.getEmailHash("MyEmailAddress@example.com"));
    
    assertEquals("9dc0a566713c9309e25d14e2faca4337", gravatarManager.getEmailHash("velias@redhat.com"));
  }

}
