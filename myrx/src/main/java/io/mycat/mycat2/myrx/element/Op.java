package io.mycat.mycat2.myrx.element;

import java.util.function.BiFunction;

public  enum Op implements BiFunction<Object, Object, Object> {
        IN {
            @Override
            public Object apply(Object o, Object o2) {
                return null;
            }
        }, OR {
            @Override
            public Object apply(Object o, Object o2) {
                return null;
            }
        }, AND {
            @Override
            public Object apply(Object o, Object o2) {
                return null;
            }
        }

    }