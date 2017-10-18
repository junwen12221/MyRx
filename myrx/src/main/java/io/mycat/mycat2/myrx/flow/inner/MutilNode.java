package io.mycat.mycat2.myrx.flow.inner;

import java.util.ArrayList;
import java.util.List;

public class MutilNode extends Node{
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
}
