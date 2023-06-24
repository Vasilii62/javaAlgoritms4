import java.util.*;

public class RedBlackTree<T extends Comparable<T>> {

    private Node root;

    private enum Color { RED, BLACK }

    private class Node {
        T value;
        Node left;
        Node right;
        Node parent;
        Color color;
    }

    public void add(T value) {
        if (root == null) {
            root = new Node();
            root.value = value;
            root.color = Color.BLACK;
        } else {
            Node newNode = new Node();
            newNode.value = value;
            newNode.color = Color.RED;

            Node current = root;
            Node parent = null;

            while (current != null) {
                parent = current;
                if (value.compareTo(current.value) < 0) {
                    current = current.left;
                } else if (value.compareTo(current.value) > 0) {
                    current = current.right;
                } else {
                    return; // Дублирующие значения не допускаются
                }
            }

            newNode.parent = parent;

            if (value.compareTo(parent.value) < 0) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }

            balanceAfterInsertion(newNode);
        }
    }

    private void balanceAfterInsertion(Node node) {
        node.color = Color.RED;

        while (node != null && node != root && node.parent.color == Color.RED) {
            if (parentOf(node) == leftOf(parentOf(parentOf(node)))) {
                Node y = rightOf(parentOf(parentOf(node)));
                if (colorOf(y) == Color.RED) {
                    setColor(parentOf(node), Color.BLACK);
                    setColor(y, Color.BLACK);
                    setColor(parentOf(parentOf(node)), Color.RED);
                    node = parentOf(parentOf(node));
                } else {
                    if (node == rightOf(parentOf(node))) {
                        node = parentOf(node);
                        rotateLeft(node);
                    }
                    setColor(parentOf(node), Color.BLACK);
                    setColor(parentOf(parentOf(node)), Color.RED);
                    rotateRight(parentOf(parentOf(node)));
                }
            } else {
                Node y = leftOf(parentOf(parentOf(node)));
                if (colorOf(y) == Color.RED) {
                    setColor(parentOf(node), Color.BLACK);
                    setColor(y, Color.BLACK);
                    setColor(parentOf(parentOf(node)), Color.RED);
                    node = parentOf(parentOf(node));
                } else {
                    if (node == leftOf(parentOf(node))) {
                        node = parentOf(node);
                        rotateRight(node);
                    }
                    setColor(parentOf(node), Color.BLACK);
                    setColor(parentOf(parentOf(node)), Color.RED);
                    rotateLeft(parentOf(parentOf(node)));
                }
            }
        }

        root.color = Color.BLACK;
    }

    private Color colorOf(Node node) {
        return (node == null ? Color.BLACK : node.color);
    }

    private void setColor(Node node, Color color) {
        if (node != null)
            node.color = color;
    }

    private Node parentOf(Node node) {
        return (node == null ? null : node.parent);
    }

    private Node leftOf(Node node) {
        return (node == null ? null : node.left);
    }

    private Node rightOf(Node node) {
        return (node == null ? null : node.right);
    }

    private void rotateLeft(Node node) {
        if (node != null) {
            Node rightChild = node.right;
            node.right = rightChild.left;
            if (rightChild.left != null)
                rightChild.left.parent = node;
            rightChild.parent = node.parent;
            if (node.parent == null)
                root = rightChild;
            else if (node.parent.left == node)
                node.parent.left = rightChild;
            else
                node.parent.right = rightChild;
            rightChild.left = node;
            node.parent = rightChild;
        }
    }

    private void rotateRight(Node node) {
        if (node != null) {
            Node leftChild = node.left;
            node.left = leftChild.right;
            if (leftChild.right != null)
                leftChild.right.parent = node;
            leftChild.parent = node.parent;
            if (node.parent == null)
                root = leftChild;
            else if (node.parent.right == node)
                node.parent.right = leftChild;
            else
                node.parent.left = leftChild;
            leftChild.right = node;
            node.parent = leftChild;
        }
    }

    private void printTree(Node node, String indent, boolean last) {
        if (node != null) {
            System.out.print(indent);
            if (last) {
                System.out.print("└─");
                indent += "  ";
            } else {
                System.out.print("├─");
                indent += "| ";
            }
            System.out.println(node.value + " " + (node.color == Color.RED ? "(Red)" : "(Black)"));
            printTree(node.left, indent, false);
            printTree(node.right, indent, true);
        }
    }

    public void printTree() {
        printTree(root, "", true);
    }

    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(3);
        tree.add(7);
        tree.add(12);
        tree.add(17);
        tree.add(11);
        tree.add(6);
        tree.add(16);
        tree.add(4);
        tree.add(8);
        tree.add(13);
        tree.add(18);

        tree.printTree();
    }
}


