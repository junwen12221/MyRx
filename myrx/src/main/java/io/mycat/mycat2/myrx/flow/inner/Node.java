package io.mycat.mycat2.myrx.flow.inner;

import java.lang.reflect.Method;

public abstract class Node<T> implements MySubsriberInterface<T> {
    Node topNode;
    String name;
    Node nextNode;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getTopNode() {
        return topNode;
    }

    public void setTopNode(Node topNode) {
        this.topNode = topNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public <R> R accept(Object visitor) {
        Method method = findMethod(visitor, getClass());
        if (method != null) {
            try {
                return (R) method.invoke(visitor, this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
