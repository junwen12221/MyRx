package io.mycat.mycat2.myrx.element;

public class Where extends Element {
        Condition condition;
        Meng select;
        public Where where(Condition condition) {
            return null;
        }

        public Where(Condition condition, Meng select) {
            this.condition = condition;
            this.select = select;
        }
    }