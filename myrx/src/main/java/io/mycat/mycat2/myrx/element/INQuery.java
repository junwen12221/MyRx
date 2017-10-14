package io.mycat.mycat2.myrx.element;

public class INQuery implements Meng {
        String sql;
        String aliasTable;

        public INQuery(String sql, String aliasTable) {
            this.sql = sql;
            this.aliasTable = aliasTable;
        }

        public Meng which(String table, String alias) {
            return new Which(this,table,alias);
        }
    }