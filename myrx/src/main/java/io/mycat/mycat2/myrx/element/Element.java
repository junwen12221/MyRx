package io.mycat.mycat2.myrx.element;

import java.lang.reflect.Method;

public abstract class Element implements Meng {
    Element upNode;

    public Element getUpNode() {
        return upNode;
    }

    public void setUpNode(Element upNode) {
        this.upNode = upNode;
    }
}