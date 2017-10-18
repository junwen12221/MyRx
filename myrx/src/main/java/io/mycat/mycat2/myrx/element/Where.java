package io.mycat.mycat2.myrx.element;

import java.util.function.Predicate;

public class Where extends Element {
    public Condition condition;
    public Predicate predicate;
    public Element select;
    public Where(Condition condition, Element select) {
            this.condition = condition;
            this.select = select;
        }

    public Where(Predicate predicate, Element select) {
        this.condition = condition;
        this.select = select;
    }

    public Where where(Condition condition) {
        return null;
    }
    }