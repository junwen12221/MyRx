package io.mycat.mycat2.myrx.element;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Created by jamie on 2017/10/12.
 */
public interface Meng {

    default Element limit(long count) {
        return null;
    }
    default Element limitOffset(long offset,long count) {
        return null;
    }
    default Element orderBy(BiFunction function) {
        return null;
    }

    default void collect(Consumer<Object> function) {
        function.accept(this);
    }

    default Element where(Condition condition) {
        return new Where(condition, (Element)this);
    }
    default Element where(Object left, Op op, Object right) {
        return new Where(new Condition(left, op, right), (Element)this);
    }
    default Out out(String... resultSetMeta) {
        return new Out(resultSetMeta,(Element)this);
    }
    default Element join(Element...elements) {
        Element[] newElement=new Element[elements.length+1];
        newElement[0]=(Element) this;
        System.arraycopy(elements,0,newElement,1,elements.length);
        return new Join(newElement);
    }
    static Method findMethod(Object visitor, Class<?> type) {
        if (type == Object.class) {
            return null;
        } else {
            try {
                return visitor.getClass().getMethod("visit", type);
            } catch (NoSuchMethodException e) {
                return findMethod(visitor, type.getSuperclass());
            }
        }
    }

    default<T> T accept(Object visitor) {
        Method method = findMethod(visitor, getClass());
        if (method != null) {
            try {
                return(T) method.invoke(visitor, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
