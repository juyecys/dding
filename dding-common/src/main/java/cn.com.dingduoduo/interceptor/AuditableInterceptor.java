package cn.com.dingduoduo.interceptor;

import cn.com.dingduoduo.config.common.CommonContextHolder;
import cn.com.dingduoduo.utils.common.StringUtil;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Administrator on 2018/1/17.
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {
                MappedStatement.class, Object.class
        })
})
public class AuditableInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuditableInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Object result = null;

        if (target instanceof Executor) {

            final Object[] args = invocation.getArgs();

            //获取原始的ms
            MappedStatement ms = (MappedStatement) args[0];
            String commandName = ms.getSqlCommandType().name();

            Object parameterObject = args[1];
            BoundSql boundSql = ms.getBoundSql(parameterObject);
           /* List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();*/
            Object parameterObjects = boundSql.getParameterObject();
            logger.debug("parameterObjects: {}", parameterObjects);
            if (commandName.startsWith("INSERT")) {
                setField(parameterObjects, "createdDate");
                setField(parameterObjects, "createdBy");
                setField(parameterObjects, "updatedDate");
                setField(parameterObjects, "updatedBy");
            } else if (commandName.startsWith("UPDATE")) {
                setField(parameterObjects, "updatedDate");
                setField(parameterObjects, "updatedBy");
            } else if (commandName.startsWith("DELETE")) {

            } else if (commandName.startsWith("SELECT")) {

            }
            /**执行方法*/
            result = invocation.proceed();

        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }


    private void setField(Object parameterObjects, String fieldName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{

        if (parameterObjects != null) {
            for (Class clazz = parameterObjects.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    Field field = clazz.getDeclaredField(fieldName);
                    if (field != null) {
                        field.setAccessible(true);
                        if ("createdDate".equals(field.getName()) || "updatedDate".equals(field.getName())) {
                            setDateField(parameterObjects, field);
                        } else if ("createdBy".equals(field.getName()) || "updatedBy".equals(field.getName())) {
                            setUserField(parameterObjects, field);
                        }

                        /*Object param = field.get(parameterObjects);
                        if (param == null) {
                            continue;
                        }
                        logger.debug("field: {}, param:{}",field.getName(), param);*/
                    }
                } catch (NoSuchFieldException e) {
                }
            }
        }


    }

    private void setDateField(Object parameterObjects, Field field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = parameterObjects.getClass().getMethod("set" + StringUtil.toUpperCaseFirstOne(field.getName()), field.getType());
        Date date = new Date();
        m.invoke(parameterObjects, date);
    }

    private void setUserField(Object parameterObjects, Field field) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method m = parameterObjects.getClass().getMethod("set" + StringUtil.toUpperCaseFirstOne(field.getName()), field.getType());
        String userName = CommonContextHolder.getUserName();

        m.invoke(parameterObjects, userName);
    }

    public enum QueryMethodEnum {
        INSERT, UPDATE
    }
}
