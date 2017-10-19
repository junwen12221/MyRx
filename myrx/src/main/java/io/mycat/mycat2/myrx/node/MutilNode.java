package io.mycat.mycat2.myrx.node;

import java.util.ArrayList;
import java.util.List;

public class MutilNode<T> extends MyNode<T> {
    List<MyNode> nodes=new ArrayList<>();

    public List<MyNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<MyNode> nodes) {
        this.nodes = nodes;
    }
    public void addNode(MyNode node){
        this.nodes.add(node);
    }

    @Override
    public void onNext(T item) {
        // System.nextNode.println("数据聚合:" + item);
        topNode.onNext(item);
    }

    @Override
    public void onError(Throwable throwable) {
        topNode.onNext(throwable);
    }

    @Override
    public void onComplete() {
        topNode.onComplete();
    }
}
