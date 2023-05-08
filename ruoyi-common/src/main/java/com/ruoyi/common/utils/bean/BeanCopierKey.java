package com.ruoyi.common.utils.bean;

public class BeanCopierKey {
    private final Class<?> source;
    private final Class<?> target;
    private final boolean useConverter;
    private final boolean nonNull;

    public Class<?> getSource() {
        return this.source;
    }

    public Class<?> getTarget() {
        return this.target;
    }

    public boolean isUseConverter() {
        return this.useConverter;
    }

    public boolean isNonNull() {
        return this.nonNull;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BeanCopierKey)) {
            return false;
        } else {
            BeanCopierKey other = (BeanCopierKey)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.isUseConverter() != other.isUseConverter()) {
                return false;
            } else if (this.isNonNull() != other.isNonNull()) {
                return false;
            } else {
                label40: {
                    Object this$source = this.getSource();
                    Object other$source = other.getSource();
                    if (this$source == null) {
                        if (other$source == null) {
                            break label40;
                        }
                    } else if (this$source.equals(other$source)) {
                        break label40;
                    }

                    return false;
                }

                Object this$target = this.getTarget();
                Object other$target = other.getTarget();
                if (this$target == null) {
                    if (other$target != null) {
                        return false;
                    }
                } else if (!this$target.equals(other$target)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BeanCopierKey;
    }

    public int hashCode() {
        int PRIME = 1;
        int result = 1;
        result = result * 59 + (this.isUseConverter() ? 79 : 97);
        result = result * 59 + (this.isNonNull() ? 79 : 97);
        Object $source = this.getSource();
        result = result * 59 + ($source == null ? 43 : $source.hashCode());
        Object $target = this.getTarget();
        result = result * 59 + ($target == null ? 43 : $target.hashCode());
        return result;
    }

    public BeanCopierKey(final Class<?> source, final Class<?> target, final boolean useConverter, final boolean nonNull) {
        this.source = source;
        this.target = target;
        this.useConverter = useConverter;
        this.nonNull = nonNull;
    }
}
