package io.mycat.mycat2.myrx;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by jamie on 2017/10/12.
 */
public interface Meng {

    default Meng limit(long count) {
        return null;
    }
    default Meng limitOffset(long offset,long count) {
        return null;
    }
    default Meng orderBy(BiFunction function) {
        return null;
    }
    default Meng collect(Consumer<Object> function){
function.accept(this);
        return null;
    }
    default Meng where(Engine.Condition condition){
        return new Engine.Where(condition,this);
    }
}
