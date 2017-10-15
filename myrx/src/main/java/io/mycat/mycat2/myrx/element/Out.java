package io.mycat.mycat2.myrx.element;

/**
 * Created by jamie on 2017/10/15.
 */
public class Out extends Element {
   public Element element;
   public String[] resultSetMeta;

    public Out(String []resultSetMeta,Element element) {
        this.element = element;
        this.resultSetMeta=resultSetMeta;
    }
}