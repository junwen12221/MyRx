package io.mycat.mycat2.myrx.element;

public  class Condition extends Element {
        Object left;
        Op op;
        Object right;

        public Condition(Object left, Op op, Object right) {
            this.left = left;
            this.op = op;
            this.right = right;
        }

    }