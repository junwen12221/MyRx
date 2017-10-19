package io.mycat.mycat2.myrx;

import io.mycat.mycat2.myrx.element.Out;
import io.mycat.mycat2.myrx.node.Node;
import io.mycat.mycat2.myrx.node.PrintNode;
import io.mycat.mycat2.myrx.visitor.CompileVisitor;
import io.mycat.mycat2.myrx.visitor.SetUpNodeVisitor;

import java.util.List;
import java.util.stream.IntStream;

import static io.mycat.mycat2.myrx.Engine.asynTest;
import static io.mycat.mycat2.myrx.Engine.fromSQL;

/**
 * Created by jamie on 2017/10/18.
 */
public class MyEngine {
    public static void main(String[] args) throws Exception {
        Out sql = fromSQL("select a.id,a.price", "a")
                .which("tableA", "a")
                .join(
                        fromSQL("select b.id,b.username from b", "b")
                                .which("tableB", "b")
                                .where((s) -> s.toString().contains("6"))
                )
                .join(fromSQL("select a.id,a.price", "a"))
                .where((s) -> Integer.valueOf(s.toString()) % 2 == 0)
                .out("tableA.id", "tableA.price", "tableB.username");
        List<Node> nodes = compile(sql);
        for (int i = 0; i < nodes.size(); i++) {
            Node publish = nodes.get(i);
            asynTest(IntStream.range(i * 10, (i + 1) * 10), (c) -> {
                publish.onNext(c);
            }, 30 + i * 30, () -> publish.onComplete());
        }
        Thread.sleep(2000);
    }

    static List<Node> compile(Out out) {
        out.accept(new SetUpNodeVisitor());
        CompileVisitor compileVisitor = new CompileVisitor();
        Node node = out.accept(compileVisitor);
        PrintNode printNode = new PrintNode();
        node.setTopNode(printNode);
        System.out.println("=======!===============!=========");
        return compileVisitor.getSource();
    }
}
