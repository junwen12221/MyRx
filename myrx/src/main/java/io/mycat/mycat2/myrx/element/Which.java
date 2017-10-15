package io.mycat.mycat2.myrx.element;

public class Which extends Element {
   public String table;
   public String alias;
   public INQuery in;

    public Which(INQuery inQuery, String table, String alias){
        this.table = table;
        this.alias = alias;
        this.in = inQuery;
    }
}