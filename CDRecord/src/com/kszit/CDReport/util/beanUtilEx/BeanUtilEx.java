package com.kszit.CDReport.util.beanUtilEx;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.*;
public final class BeanUtilEx
extends BeanUtils {

private static Map cache = new HashMap();
private static Log logger = LogFactory.getFactory().getInstance(BeanUtilEx.class);

private BeanUtilEx() {
}

static {
//注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
ConvertUtils.register(new UtilDateConvert(), java.util.Date.class);
//ConvertUtils.register(new SqlTimestampConverter(), java.sql.Timestamp.class);
//注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
ConvertUtils.register(new SqlDateConvert(), java.sql.Date.class);
}

public static void copyProperties(Object target, Object source) throws
  InvocationTargetException, IllegalAccessException {
//update bu zhuzf at 2004-9-29
//支持对日期copy

org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);

}
}
