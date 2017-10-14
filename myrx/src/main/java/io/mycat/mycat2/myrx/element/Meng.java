package io.mycat.mycat2.myrx.element;

import javax.lang.model.element.ElementVisitor;
import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Created by jamie on 2017/10/12.
 */
public interface Meng {

    default Meng limit(long count) {
        return null;
    }
    default Meng limitOffset(long offset,long count) {
        return null;
    }
    default Meng orderBy(BiFunction function) {
        return null;
    }

    default void collect(Consumer<Object> function) {
        function.accept(this);
    }

    default Meng where(Condition condition) {
        return new Where(condition, this);
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

    default void accept(Object visitor) throws Exception {
        Method method = findMethod(visitor, getClass());
        if (method != null) {
            method.invoke(visitor, this);
        }
    }

    default void visit() {
        if (this instanceof Condition) {
            new ConditionVisitor().visit((Condition) this);
        } else if (this instanceof INQuery) {
            new INQueryVisitor().visit((INQuery) this);
        } else if (this instanceof Join) {
            new JoinVisitor().visit((Join) this);
        } else if (this instanceof Where) {
            new WhereVisitor().visit((Where) this);
        } else if (this instanceof Which) {
            new WhichVisitor().visit((Which) this);
        } else {
            System.out.println("异常");
        }
    }
}
