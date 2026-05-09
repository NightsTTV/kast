class TreeNode<E extends Comparable<E>> {
    TreeNode<E> leftNode;
    E data;
    TreeNode<E> rightNode;

    public TreeNode(E nodeData) {
        data = nodeData;
        leftNode = rightNode = null;
    }

    public void insert(E insertValue) {
        if (insertValue.compareTo(data) < 0) {
            if (leftNode == null) {
                leftNode = new TreeNode<E>(insertValue);
            } else {
                leftNode.insert(insertValue);
            }
        } else if (insertValue.compareTo(data) > 0) {
            if (rightNode == null) {
                rightNode = new TreeNode<E>(insertValue);
            } else {
                rightNode.insert(insertValue);
            }
        }
    }
}