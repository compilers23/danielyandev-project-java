package contracts;

import nodes.Node;

import java.util.Queue;

public interface IParser {
    Queue<Node> parse();
}
