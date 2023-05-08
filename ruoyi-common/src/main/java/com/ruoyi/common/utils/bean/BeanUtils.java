package com.ruoyi.common.utils.bean;

import com.fasterxml.jackson.databind.BeanProperty;
import com.ruoyi.common.utils.BladeBeanCopier;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.cglib.core.Converter;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Bean 工具类
 * 
 * @author ruoyi
 */
public class BeanUtils extends org.springframework.beans.BeanUtils
{
    /** Bean方法名中属性名开始的下标 */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /** * 匹配getter方法的正则表达式 */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /** * 匹配setter方法的正则表达式 */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");
    public BeanUtils() {
    }
    /**
     * Bean属性复制工具方法。
     * 
     * @param dest 目标对象
     * @param src 源对象
     */
    public static void copyBeanProp(Object dest, Object src)
    {
        try
        {
            copyProperties(src, dest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


        public static <T> T newInstance(Class<?> clazz) {
            return (T) instantiateClass(clazz);
        }

        public static <T> T newInstance(String clazzStr) {
            try {
                Class<?> clazz = ClassUtils.forName(clazzStr, (ClassLoader)null);
                return newInstance(clazz);
            } catch (ClassNotFoundException var2) {
                throw new RuntimeException(var2);
            }
        }

        @Nullable
        public static Object getProperty(@Nullable Object bean, String propertyName) {
            if (bean == null) {
                return null;
            } else {
                BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
                return beanWrapper.getPropertyValue(propertyName);
            }
        }

        public static void setProperty(Object bean, String propertyName, Object value) {
            Objects.requireNonNull(bean, "bean Could not null");
            BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean);
            beanWrapper.setPropertyValue(propertyName, value);
        }


        @Nullable
        public static <T> T copy(@Nullable Object source, Class<T> clazz) {
            return source == null ? null : copy(source, source.getClass(), clazz);
        }

        @Nullable
        public static <T> T copy(@Nullable Object source, Class sourceClazz, Class<T> targetClazz) {
            if (source == null) {
                return null;
            } else {
                BladeBeanCopier copier = BladeBeanCopier.create(sourceClazz, targetClazz, false);
                T to = newInstance(targetClazz);
                copier.copy(source, to, (Converter)null);
                return to;
            }
        }

        public static <T> List<T> copy(@Nullable Collection<?> sourceList, Class<T> targetClazz) {
            if (sourceList != null && !sourceList.isEmpty()) {
                List<T> outList = new ArrayList(sourceList.size());
                Class<?> sourceClazz = null;
                Iterator var4 = sourceList.iterator();

                while(var4.hasNext()) {
                    Object source = var4.next();
                    if (source != null) {
                        if (sourceClazz == null) {
                            sourceClazz = source.getClass();
                        }

                        T bean = copy(source, sourceClazz, targetClazz);
                        outList.add(bean);
                    }
                }

                return outList;
            } else {
                return Collections.emptyList();
            }
        }

        public static void copy(@Nullable Object source, @Nullable Object targetBean) {
            if (source != null && targetBean != null) {
                BladeBeanCopier copier = BladeBeanCopier.create(source.getClass(), targetBean.getClass(), false);
                copier.copy(source, targetBean, (Converter)null);
            }
        }

        public static void copyNonNull(@Nullable Object source, @Nullable Object targetBean) {
            if (source != null && targetBean != null) {
                BladeBeanCopier copier = BladeBeanCopier.create(source.getClass(), targetBean.getClass(), false, true);
                copier.copy(source, targetBean, (Converter)null);
            }
        }

        @Nullable
        public static <T> T copyWithConvert(@Nullable Object source, Class<T> targetClazz, Class<T> clazz) {
            return source == null ? null : copyWithConvert(source, (Class<T>) source.getClass(), targetClazz);
        }


        @Nullable
        public static <T> T copyProperties(@Nullable Object source, Class<T> targetClazz) throws BeansException {
            if (source == null) {
                return null;
            } else {
                T to = newInstance(targetClazz);
                copyProperties((Object)source, (Object)to);
                return to;
            }
        }

        public static <T> List<T> copyProperties(@Nullable Collection<?> sourceList, Class<T> targetClazz) throws BeansException {
            if (sourceList != null && !sourceList.isEmpty()) {
                List<T> outList = new ArrayList(sourceList.size());
                Iterator var3 = sourceList.iterator();

                while(var3.hasNext()) {
                    Object source = var3.next();
                    if (source != null) {
                        T bean = copyProperties(source, targetClazz);
                        outList.add(bean);
                    }
                }

                return outList;
            } else {
                return Collections.emptyList();
            }
        }


        public static <T> T toBean(Map<String, Object> beanMap, Class<T> valueType) {
            Objects.requireNonNull(beanMap, "beanMap Could not null");
            T to = newInstance(valueType);
            if (beanMap.isEmpty()) {
                return to;
            } else {
                copy((Object)beanMap, (Object)to);
                return to;
            }
        }

        @Nullable
        public static Object generator(@Nullable Object superBean, BeanProperty... props) {
            if (superBean == null) {
                return null;
            } else {
                Class<?> superclass = superBean.getClass();
                Object genBean = generator(superclass, props);
                copy(superBean, genBean);
                return genBean;
            }
        }




}
