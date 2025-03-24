package ed.lab;

public class E01InvertBT {
    public TreeNode<Integer> invertTree(TreeNode<Integer> root) {
        if (root == null) return null;

        TreeNode<Integer> temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);

        return root;
    }
}