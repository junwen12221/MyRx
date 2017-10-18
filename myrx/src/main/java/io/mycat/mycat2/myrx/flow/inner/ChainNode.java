package io.mycat.mycat2.myrx.flow.inner;

public class ChainNode extends Node {
    Node nextNode;

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }
}
