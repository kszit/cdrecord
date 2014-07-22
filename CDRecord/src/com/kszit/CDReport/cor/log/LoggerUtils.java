/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.log;

/**
 *
 * @author Administrator
 */
public class LoggerUtils
{
  public static String format(String msg, String[] args)
  {
    if ((msg != null) && (msg.length() > 0) && (msg.indexOf('#') > -1)) {
      StringBuilder sb = new StringBuilder();
      boolean isArg = false;
      for (int x = 0; x < msg.length(); ++x) {
        char c = msg.charAt(x);
        if (isArg) {
          isArg = false;
          if (Character.isDigit(c)) {
            int val = Character.getNumericValue(c);
            if ((val >= 1) && (val <= args.length)) {
              sb.append(args[(val - 1)]);
              continue;
            }
          }
          sb.append('#');
        }
        if (c == '#') {
          isArg = true;
        }
        else
          sb.append(c);
      }
      if (isArg) {
        sb.append('#');
      }
      return sb.toString();
    }
    return msg;
  }
}
