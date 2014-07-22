/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Administrator
 */
public abstract class LogService
{
  public static Log getLogger(Class<?> cls)
  {
    return LogFactory.getFactory().getInstance(cls);
  }

  public static Log getLogger(String name) {
    return LogFactory.getFactory().getInstance(name);
  }

  public static Log getLogger() {
    return getLogger("");
  }
}
