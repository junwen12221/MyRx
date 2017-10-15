package io.mycat.mycat2.myrx.element;

import java.util.concurrent.SubmissionPublisher;

public class INQuery extends Element {
    public     String sql;
     public    String aliasTable;

        public INQuery(String sql, String aliasTable){
            this.sql = sql;
            this.aliasTable = aliasTable;
        }

        public Element which(String table, String alias) {
            return new Which(this,table,alias);
        }
    }