package ed.lab;

import java.util.Comparator;

public class E03AVLTree<T> {
    private final Comparator<T> comparator;
    private Node<T> root;
    private int size;

    private static class Node<T> {
        T value;
        Node<T> left, right;
        int height;

        Node(T value) {
            this.value = value;
            this.height = 1;
        }
    }

    public E03AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
        this.root = null;
        this.size = 0;
    }

    public void insert(T value) {
        if (search(value) == null) {
            root = insert(root, value);
            size++;
        }
    }

    private Node<T> insert(Node<T> node, T value) {
        if (node == null) {
            return new Node<>(value);
        }

        if (comparator.compare(value, node.value) < 0) {
            node.left = insert(node.left, value);
        } else if (comparator.compare(value, node.value) > 0) {
            node.right = insert(node.right, value);
        }

        return balance(node);
    }

    public void delete(T value) {
        if (search(value) != null) {
            root = delete(root, value);
            size--;
        }
    }

    private Node<T> delete(Node<T> node, T value) {
        if (node == null) return null;

        if (comparator.compare(value, node.value) < 0) {
            node.left = delete(node.left, value);
        } else if (comparator.compare(value, node.value) > 0) {
            node.right = delete(node.right, value);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node<T> minLargerNode = findMin(node.right);
                node.value = minLargerNode.value;
                node.right = delete(node.right, minLargerNode.value);
            }
        }
        return (node != null) ? balance(node) : null;
    }

    public T search(T value) {
        Node<T> node = search(root, value);
        return (node != null) ? node.value : null;
    }

    private Node<T> search(Node<T> node, T value) {
        if (node == null) return null;
        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) return search(node.left, value);
        if (cmp > 0) return search(node.right, value);
        return node;
    }

    public int height() {
        return getHeight(root);
    }

    public int size() {
        return size;
    }

    private int getHeight(Node<T> node) {
        return (node == null) ? 0 : node.height;
    }

    private int getBalanceFactor(Node<T> node) {
        return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
    }

    private Node<T> balance(Node<T> node) {
        if (node == null) return null;
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (getBalanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (balanceFactor < -1) {
            if (getBalanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    private Node<T> rotateRight(Node<T> y) {
        Node<T> x = y.left;
        y.left = x.right;
        x.right = y;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    private Node<T> rotateLeft(Node<T> x) {
        Node<T> y = x.right;
        x.right = y.left;
        y.left = x;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        return y;
    }

    private Node<T> findMin(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}