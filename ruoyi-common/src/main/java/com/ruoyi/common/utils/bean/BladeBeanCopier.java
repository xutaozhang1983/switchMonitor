package com.ruoyi.common.utils.bean;


import com.ruoyi.common.annotation.CopyProperty;
import com.ruoyi.common.utils.ReflectUtil;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.Label;
import org.springframework.asm.Type;
import org.springframework.cglib.core.*;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class BladeBeanCopier {
    private static final Type CONVERTER = TypeUtils.parseType("org.springframework.cglib.core.Converter");
    private static final Type BEAN_COPIER = TypeUtils.parseType(BladeBeanCopier.class.getName());
    private static final Type BEAN_MAP = TypeUtils.parseType(Map.class.getName());
    private static final Signature COPY;
    private static final Signature CONVERT;
    private static final Signature BEAN_MAP_GET;
    private static final Type CLASS_UTILS;
    private static final Signature IS_ASSIGNABLE_VALUE;
    private static final ConcurrentMap<BeanCopierKey, BladeBeanCopier> BEAN_COPIER_MAP;


    public BladeBeanCopier() {
    }

    public static BladeBeanCopier create(Class source, Class target, boolean useConverter) {
        return create(source, target, useConverter, false);
    }

    public static BladeBeanCopier create(Class source, Class target, boolean useConverter, boolean nonNull) {
        BeanCopierKey copierKey = new BeanCopierKey(source, target, useConverter, nonNull);
        return (BladeBeanCopier)BEAN_COPIER_MAP.computeIfAbsent(copierKey, (key) -> {
            Generator gen = new Generator();
            gen.setSource(key.getSource());
            gen.setTarget(key.getTarget());
            gen.setUseConverter(key.isUseConverter());
            gen.setNonNull(key.isNonNull());
            return gen.create(key);
        });
    }

    public abstract void copy(Object from, Object to, @Nullable Converter converter);

    static {
        COPY = new Signature("copy", Type.VOID_TYPE, new Type[]{Constants.TYPE_OBJECT, Constants.TYPE_OBJECT, CONVERTER});
        CONVERT = TypeUtils.parseSignature("Object convert(Object, Class, Object)");
        BEAN_MAP_GET = TypeUtils.parseSignature("Object get(Object)");
        CLASS_UTILS = TypeUtils.parseType(ClassUtils.class.getName());
        IS_ASSIGNABLE_VALUE = TypeUtils.parseSignature("boolean isAssignableValue(Class, Object)");
        BEAN_COPIER_MAP = new ConcurrentHashMap();
    }

    public static class Generator extends AbstractClassGenerator {
        private static final AbstractClassGenerator.Source SOURCE = new AbstractClassGenerator.Source(BladeBeanCopier.class.getName());
        private Class source;
        private Class target;
        private boolean useConverter;
        private boolean nonNull;

        Generator() {
            super(SOURCE);
        }

        public void setSource(Class source) {
            if (!Modifier.isPublic(source.getModifiers())) {
                this.setNamePrefix(source.getName());
            }

            this.source = source;
        }

        public void setTarget(Class target) {
            if (!Modifier.isPublic(target.getModifiers())) {
                this.setNamePrefix(target.getName());
            }

            this.target = target;
        }

        public void setUseConverter(boolean useConverter) {
            this.useConverter = useConverter;
        }

        public void setNonNull(boolean nonNull) {
            this.nonNull = nonNull;
        }

        protected ClassLoader getDefaultClassLoader() {
            return this.target.getClassLoader();
        }

        protected ProtectionDomain getProtectionDomain() {
            return ReflectUtils.getProtectionDomain(this.source);
        }

        public BladeBeanCopier create(Object key) {
            return (BladeBeanCopier)super.create(key);
        }

        public void generateClass(ClassVisitor v) {
            Type sourceType = Type.getType(this.source);
            Type targetType = Type.getType(this.target);
            ClassEmitter ce = new ClassEmitter(v);
            ce.begin_class(46, 1, this.getClassName(), BladeBeanCopier.BEAN_COPIER, (Type[])null, "<generated>");
            EmitUtils.null_constructor(ce);
            CodeEmitter e = ce.begin_method(1, BladeBeanCopier.COPY, (Type[])null);
            if (Map.class.isAssignableFrom(this.source)) {
                this.generateClassFormMap(ce, e, sourceType, targetType);
            } else {
                PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(this.source);
                PropertyDescriptor[] setters = ReflectUtils.getBeanSetters(this.target);
                Map<String, PropertyDescriptor> names = new HashMap(16);
                PropertyDescriptor[] var9 = getters;
                int var10 = getters.length;

                for(int var11 = 0; var11 < var10; ++var11) {
                    PropertyDescriptor getter = var9[var11];
                    names.put(getter.getName(), getter);
                }

                Local targetLocal = e.make_local();
                Local sourceLocal = e.make_local();
                e.load_arg(1);
                e.checkcast(targetType);
                e.store_local(targetLocal);
                e.load_arg(0);
                e.checkcast(sourceType);
                e.store_local(sourceLocal);
                PropertyDescriptor[] var31 = setters;
                int var32 = setters.length;

                for(int var13 = 0; var13 < var32; ++var13) {
                    PropertyDescriptor setter = var31[var13];
                    String propName = setter.getName();
                    CopyProperty targetIgnoreCopy = (CopyProperty) ReflectUtil.getAnnotation(this.target, propName, CopyProperty.class);
                    if (targetIgnoreCopy != null) {
                        if (targetIgnoreCopy.ignore()) {
                            continue;
                        }

                        String aliasTargetPropName = targetIgnoreCopy.value();
                        if (StringUtils.isNotBlank(aliasTargetPropName)) {
                            propName = aliasTargetPropName;
                        }
                    }

                    PropertyDescriptor getter = (PropertyDescriptor)names.get(propName);
                    if (getter != null) {
                        MethodInfo read = ReflectUtils.getMethodInfo(getter.getReadMethod());
                        Method writeMethod = setter.getWriteMethod();
                        MethodInfo write = ReflectUtils.getMethodInfo(writeMethod);
                        Type returnType = read.getSignature().getReturnType();
                        Type setterType = write.getSignature().getArgumentTypes()[0];
                        Class<?> getterPropertyType = getter.getPropertyType();
                        Class<?> setterPropertyType = setter.getPropertyType();
                        Label l0 = e.make_label();
                        if (ClassUtils.isAssignable(setterPropertyType, getterPropertyType)) {
                            e.load_local(targetLocal);
                            e.load_local(sourceLocal);
                            e.invoke(read);
                            boolean getterIsPrimitive = getterPropertyType.isPrimitive();
                            boolean setterIsPrimitive = setterPropertyType.isPrimitive();
                            if (this.nonNull) {
                                e.box(returnType);
                                Local var = e.make_local();
                                e.store_local(var);
                                e.load_local(var);
                                e.ifnull(l0);
                                e.load_local(targetLocal);
                                e.load_local(var);
                                e.unbox_or_zero(setterType);
                            } else {
                                if (getterIsPrimitive && !setterIsPrimitive) {
                                    e.box(returnType);
                                }

                                if (!getterIsPrimitive && setterIsPrimitive) {
                                    e.unbox_or_zero(setterType);
                                }
                            }

                            invokeWrite(e, write, writeMethod, this.nonNull, l0);
                        } else if (this.useConverter) {
                            e.load_local(targetLocal);
                            e.load_arg(2);
                            e.load_local(sourceLocal);
                            e.invoke(read);
                            e.box(returnType);
                            if (this.nonNull) {
                                Local var = e.make_local();
                                e.store_local(var);
                                e.load_local(var);
                                e.ifnull(l0);
                                e.load_local(targetLocal);
                                e.load_arg(2);
                                e.load_local(var);
                            }

                            EmitUtils.load_class(e, setterType);
                            e.push(propName);
                            e.invoke_interface(BladeBeanCopier.CONVERTER, BladeBeanCopier.CONVERT);
                            e.unbox_or_zero(setterType);
                            invokeWrite(e, write, writeMethod, this.nonNull, l0);
                        }
                    }
                }

                e.return_value();
                e.end_method();
                ce.end_class();
            }
        }

        private static void invokeWrite(CodeEmitter e, MethodInfo write, Method writeMethod, boolean nonNull, Label l0) {
            Class<?> returnType = writeMethod.getReturnType();
            e.invoke(write);
            if (!returnType.equals(Void.TYPE)) {
                e.pop();
            }

            if (nonNull) {
                e.visitLabel(l0);
            }

        }

        protected Object firstInstance(Class type) {
            return BeanUtils.newInstance(type);
        }

        protected Object nextInstance(Object instance) {
            return instance;
        }

        public void generateClassFormMap(ClassEmitter ce, CodeEmitter e, Type sourceType, Type targetType) {
            PropertyDescriptor[] setters = ReflectUtils.getBeanSetters(this.target);
            Local targetLocal = e.make_local();
            Local sourceLocal = e.make_local();
            e.load_arg(1);
            e.checkcast(targetType);
            e.store_local(targetLocal);
            e.load_arg(0);
            e.checkcast(sourceType);
            e.store_local(sourceLocal);
            Type mapBox = Type.getType(Object.class);
            PropertyDescriptor[] var9 = setters;
            int var10 = setters.length;

            for(int var11 = 0; var11 < var10; ++var11) {
                PropertyDescriptor setter = var9[var11];
                String propName = setter.getName();
                CopyProperty targetIgnoreCopy = (CopyProperty)ReflectUtil.getAnnotation(this.target, propName, CopyProperty.class);
                if (targetIgnoreCopy != null) {
                    if (targetIgnoreCopy.ignore()) {
                        continue;
                    }

                    String aliasTargetPropName = targetIgnoreCopy.value();
                    if (StringUtils.isNotBlank(aliasTargetPropName)) {
                        propName = aliasTargetPropName;
                    }
                }

                Method writeMethod = setter.getWriteMethod();
                MethodInfo write = ReflectUtils.getMethodInfo(writeMethod);
                Type setterType = write.getSignature().getArgumentTypes()[0];
                e.load_local(targetLocal);
                e.load_local(sourceLocal);
                e.push(propName);
                e.invoke_interface(BladeBeanCopier.BEAN_MAP, BladeBeanCopier.BEAN_MAP_GET);
                e.box(mapBox);
                Local var = e.make_local();
                e.store_local(var);
                e.load_local(var);
                Label l0 = e.make_label();
                e.ifnull(l0);
                EmitUtils.load_class(e, setterType);
                e.load_local(var);
                e.invoke_static(BladeBeanCopier.CLASS_UTILS, BladeBeanCopier.IS_ASSIGNABLE_VALUE);
                Label l1 = new Label();
                Class<?> returnType = writeMethod.getReturnType();
                if (this.useConverter) {
                    e.if_jump(153, l1);
                    e.load_local(targetLocal);
                    e.load_local(var);
                    e.unbox_or_zero(setterType);
                    e.invoke(write);
                    if (!returnType.equals(Void.TYPE)) {
                        e.pop();
                    }

                    e.goTo(l0);
                    e.visitLabel(l1);
                    e.load_local(targetLocal);
                    e.load_arg(2);
                    e.load_local(var);
                    EmitUtils.load_class(e, setterType);
                    e.push(propName);
                    e.invoke_interface(BladeBeanCopier.CONVERTER, BladeBeanCopier.CONVERT);
                    e.unbox_or_zero(setterType);
                    e.invoke(write);
                } else {
                    e.if_jump(153, l0);
                    e.load_local(targetLocal);
                    e.load_local(var);
                    e.unbox_or_zero(setterType);
                    e.invoke(write);
                }

                if (!returnType.equals(Void.TYPE)) {
                    e.pop();
                }

                e.visitLabel(l0);
            }

            e.return_value();
            e.end_method();
            ce.end_class();
        }
    }
}
