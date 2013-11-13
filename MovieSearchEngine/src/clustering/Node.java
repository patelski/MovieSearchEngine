/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moviesearchengine.clustering;

/**
 *
 * @author flavius
 */
public class Node {
    private Node left;
    private Node right;
    private String id;
    private double similarity;
    private int leavesCount;
    private boolean visited = false;

    public Node() {
    }

    public Node(String id) {
        this.id = id;
        this.leavesCount = 1;
    }

    public Node(Node left, Node right, double similarity) {
        this.left = left;
        this.right = right;
        this.similarity = similarity;
        this.leavesCount = left.getLeavesCount() + right.getLeavesCount();
    }

    public int getLeavesCount() {
        return leavesCount;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getId() {
        return id;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isVisited() {
        return visited;
    }
}
