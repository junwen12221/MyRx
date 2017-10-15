package io.mycat.mycat2.myrx.element;

public class Where extends Element {
    public Condition condition;
    public Element select;
        public Where where(Condition condition) {
            return null;
        }

    public Where(Condition condition, Element select) {
            this.condition = condition;
            this.select = select;
        }
    }