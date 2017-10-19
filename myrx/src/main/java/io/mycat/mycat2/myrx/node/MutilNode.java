package io.mycat.mycat2.myrx.node;

import java.util.ArrayList;
import java.util.List;

public class MutilNode<T> extends Node<T> {
    List<Node> nodes=new ArrayList<>();

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
    public void addNode(Node node){
        this.nodes.add(node);
    }

    @Override
    public void onNext(T item) {
        // System.out.println("数据聚合:" + item);
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
