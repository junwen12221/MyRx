package io.mycat.mycat2.myrx;

import io.mycat.mycat2.myrx.element.*;
import io.mycat.mycat2.myrx.flow.inner.Node;
import io.mycat.mycat2.myrx.flow.inner.PrintNode;
import io.mycat.mycat2.myrx.visitor.CompileVisitor;
import io.mycat.mycat2.myrx.visitor.FromDown2UpVisitor;
import io.mycat.mycat2.myrx.visitor.GeneratorVisitor;
import io.mycat.mycat2.myrx.visitor.SetUpNodeVisitor;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import static io.mycat.mycat2.myrx.element.Op.AND;
import static io.mycat.mycat2.myrx.element.Op.IN;

/**
 * Created by jamie on 2017/10/12.
 */
public class Engine {

    public static INQuery from(String sql, String aliasTable) {
        return new INQuery(sql, aliasTable);
    }

    public static INQuery fromSQL(String sql, String aliasTable) {
        return new INQuery(sql, aliasTable);
    }


    public static Meng join(Element... elements) {
        return new Join(elements);
    }

    public static Condition condition(Object left, Op op, Object right) {
        return new Condition(left, op, right);
    }


    public static void main(String[] args) throws Exception {
        //select a.id,a.price,b.username where a.id in (111,222,333,444) and b.id=a.id
        Condition id_in_list = Engine.condition("a.id", IN, "(111,222,333,444)");
        Out sql3 = fromSQL("select a.id,a.price", "a")
                .which("tableA", "a")
                .where(id_in_list)
                .join(
                        fromSQL("select b.id,b.username from b", "b")
                                .which("tableB", "b")
                )
                .where(
                        id_in_list,
                        AND,
                        condition("b.id", AND, "a.id"))
                .join(fromSQL("select a.id,a.price", "a"))
                .where("1", Op.IN, "(1,2)")
                .out("tableA.id", "tableA.price", "tableB.username");


        sql3.accept(new SetUpNodeVisitor());
        FromDown2UpVisitor visitor = new FromDown2UpVisitor();
        sql3.accept(visitor);
        System.out.println("=>>>>>>>>>>>>>>>>>> join <<<<<<<<<<<<<<<<<<<<<<<=");
        GeneratorVisitor generatorVisitor = visitor.generatorVisitor;
//        Map<Join,List<MyProcessorInterface>> joinMap= generatorVisitor.getJoinMap();
//      for (Map.Entry<Join,List<MyProcessorInterface>> entry: joinMap.entrySet()){
//       MyJoinProcessor joinProcessor=   new MyJoinProcessor();
//         List<MyProcessorInterface> list= entry.getValue();
//          for (int i = 0; i <list.size() ; i++) {
//              joinProcessor.put(String.valueOf(i),list.get(i));
//          }
//          joinProcessor.init();
//
        visitor.generatorVisitor.getJoin2JoinProcessorMap().entrySet().forEach((i) -> System.out.println(i));
        System.out.println("-----------------------------------------------");
        visitor.generatorVisitor.getJoin2ProcessorListMap().entrySet().forEach((i) -> System.out.println(i));
        System.out.println("-----------------------------------------------");
        visitor.generatorVisitor.getJoin2PublisherListMap().entrySet().forEach((i) -> System.out.println(i));
        System.out.println("-----------------------------------------------");
        visitor.generatorVisitor.getJoin2SubsriberListMap().entrySet().forEach((i) -> System.out.println(i));
        System.out.println("-----------------------------------------------");
        System.out.println(visitor.generatorVisitor.getOut());

        CompileVisitor compileVisitor = new CompileVisitor();
        Node node = sql3.accept(compileVisitor);
        PrintNode printNode = new PrintNode();
        node.setTopNode(printNode);
        System.out.println("=======!===============!=========");
        List<Node> nodes = compileVisitor.getSource();
        for (int i = 0; i < nodes.size(); i++) {
            Node publish = nodes.get(i);
            asynTest(IntStream.range(1, i * 10), (c) -> publish.onNext(c), 30 + i);
        }
        Thread.sleep(2000);
    }

    public static void asynTest(IntStream stream, Consumer<String> consumer, long waittime, Runnable runnable) {
        new Thread(() -> {
            stream.boxed()
                    .map((i) -> Integer.valueOf(i).toString()).map((i) -> {
                try {
                    Thread.sleep(waittime);
                    //  System.out.println("waiting......");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return i;
            }).forEach(consumer::accept);
            runnable.run();
        }).start();
    }

    public static void asynTest(IntStream stream, Consumer<String> consumer, long waittime) {
        new Thread(() -> {
            stream.boxed()
                    .map((i) -> Integer.valueOf(i).toString()).map((i) -> {
                try {
                    Thread.sleep(waittime);
                    System.out.println("waiting......");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return i;
            }).forEach(consumer::accept);
        }).start();
    }
}
