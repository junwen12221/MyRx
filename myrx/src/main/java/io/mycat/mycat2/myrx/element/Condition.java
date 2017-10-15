package io.mycat.mycat2.myrx.element;

public  class Condition extends Element {
   public Object left;
        Op op;
    public Object right;

    public Condition(Object left, Op op, Object right) {
            this.left = left;
            this.op = op;
            this.right = right;
        }
    }