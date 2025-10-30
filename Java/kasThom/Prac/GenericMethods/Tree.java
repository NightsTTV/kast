public class Tree<E extends Comparable<E>> {
    private TreeNode<E> root;

    public Tree() {
        root = null;
    }

    public void insertNode(E insertValue) {
        if (root == null) {
            root = new TreeNode<E>(insertValue);
        } else {
            root.insert(insertValue);
        }
    }

    public void preorderTraversal() {
        preorderHelper(root);
    }

    private void preorderHelper(TreeNode<E> node) {
        if (node == null) {
            return;
        }
        System.out.printf("%s ", node.data);
        preorderHelper(node.leftNode);
        preorderHelper(node.rightNode);
    }

    public void inorderTraversal() {
        inorderHelper(root);
    }

    private void inorderHelper(TreeNode<E> node) {
        if (node == null) {
            return;
        }
        inorderHelper(node.leftNode);
        System.out.printf("%s ", node.data);
        inorderHelper(node.rightNode);
    }

    public void postorderTraversal() {
        postorderHelper(root);
    }

    private void postorderHelper(TreeNode<E> node) {
        if (node == null) {
            return;
        }
        postorderHelper(node.leftNode);
        postorderHelper(node.rightNode);
        System.out.printf("%s ", node.data);
    }
}