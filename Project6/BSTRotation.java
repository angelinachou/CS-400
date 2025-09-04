public class BSTRotation<T extends Comparable<T>> extends BinarySearchTree<T> {

  /**
   * No arguments constructor
   */
  public BSTRotation() {
  }

  /**
   * Performs the rotation operation on the provided nodes within this tree. When the provided child
   * is a left child of the provided parent, this method will perform a right rotation. When the
   * provided child is a right child of the provided parent, this method will perform a left
   * rotation. When the provided nodes are not related in one of these ways, this method will either
   * throw a NullPointerException: when either reference is null, or otherwise will throw an
   * IllegalArgumentException.
   *
   * @param child  is the node being rotated from child to parent position
   * @param parent is the node being rotated from parent to child position
   * @throws NullPointerException     when either passed argument is null
   * @throws IllegalArgumentException when the provided child and parent nodes are not initially
   *                                  (pre-rotation) related that way
   */
  protected void rotate(BinaryTreeNode<T> child, BinaryTreeNode<T> parent)
      throws NullPointerException, IllegalArgumentException {

    // null pointer exception thrown when either reference is null - prevents having to check later
    // on when performing actual rotation
    if (child == null || parent == null) {
      throw new NullPointerException("References cannot be null");
    }

    // checking if rotation should be a right or left rotation - if child is on the right, then it
    // is a left rotation, and if the child is on the left, it is a right rotation
    if (parent.childRight() == child) {
      leftRotation(child, parent);
    } else if (parent.childLeft() == child) {
      rightRotation(child, parent);
    }
    // in the case that the child is not on the right or left of the parent, that means it is not
    // directly descended from the parent, making the rotation impossible
    else {
      throw new IllegalArgumentException("The provided child and parent nodes are not related");
    }
  }

  /**
   * Performs the left rotation operation on the provided nodes within this tree. Checks whether the
   * child should become the root, maintains structure of left subtree of the parent node and the
   * right subtree of the child node and moves all nodes between the child and parent from left
   * subtree of child to right subtree of parent
   *
   * @param child  is the node being rotated from child to parent position
   * @param parent is the node being rotated from parent to child position
   */
  private void leftRotation(BinaryTreeNode<T> child, BinaryTreeNode<T> parent) {
    // In case there is an in between node/tree that would need to be shifted to the left subtree
    BinaryTreeNode<T> middle = child.childLeft();
    BinaryTreeNode<T> grandparent = parent.parent();

    // Shifting the values between the parent and child node to be the right child of the parent
    parent.setChildRight(middle);
    if (middle != null) {
      middle.setParent(parent);
    }

    // Switching it so that the parent is the child and the child is the parent
    child.setChildLeft(parent);
    parent.setParent(child);

    // Making sure that everything above child is valid
    // In the case that parent was the root, making child the new root
    if (grandparent == null) {
      root = child;
    }
    // In the case that this parent tree was in the left subtree, setting child as the left subtree
    else if (grandparent.childLeft() == parent) {
      grandparent.setChildLeft(child);
    }
    // In the case that this parent tree was in the right subtree, setting child as the right
    // subtree
    else {
      grandparent.setChildRight(child);
    }
    child.setParent(grandparent);
  }

  /**
   * Performs the right rotation operation on the provided nodes within this tree. Checks whether
   * the child should become the root, maintains structure of right subtree of the parent node and
   * the left subtree of the child node and moves all nodes between the child and parent from right
   * subtree of child to left subtree of parent
   *
   * @param child  is the node being rotated from child to parent position
   * @param parent is the node being rotated from parent to child position
   */
  private void rightRotation(BinaryTreeNode<T> child, BinaryTreeNode<T> parent) {
    // In case there is an in between node/tree that would need to be shifted to the right subtree
    BinaryTreeNode<T> middle = child.childRight();
    BinaryTreeNode<T> grandparent = parent.parent();

    // Shifting the values between the parent and child node to be the left child of the parent
    parent.setChildLeft(middle);
    if (middle != null) {
      middle.setParent(parent);
    }

    // Switching it so that the parent is the child and the child is the parent
    child.setChildRight(parent);
    parent.setParent(child);

    // Making sure that everything above child is valid
    // In the case that parent was the root, making child the new root
    if (grandparent == null) {
      root = child;
    }
    // In the case that this parent tree was in the left subtree, setting child as the left subtree
    else if (grandparent.childLeft() == parent) {
      grandparent.setChildLeft(child);
    }
    // In the case that this parent tree was in the right subtree, setting child as the right
    // subtree
    else {
      grandparent.setChildRight(child);
    }
    child.setParent(grandparent);
  }

  /**
   * First tester method: Tests left rotation where parent is root, one with a shared child and one
   * with 0 shared children
   */
  public boolean test1() {
    BSTRotation<Integer> tree = new BSTRotation<>();
    BinaryTreeNode<Integer> root = new BinaryTreeNode<>(5);
    BinaryTreeNode<Integer> rt = new BinaryTreeNode<>(8);
    BinaryTreeNode<Integer> lf = new BinaryTreeNode<>(3);

    BSTRotation<Integer> tree2 = new BSTRotation<>();
    BinaryTreeNode<Integer> root2 = new BinaryTreeNode<>(4);
    BinaryTreeNode<Integer> rt2 = new BinaryTreeNode<>(10);

    tree.root = root;
    root.setChildRight(rt);
    rt.setParent(root);
    root.setChildLeft(lf);
    lf.setParent(root);

    tree2.root = root2;
    root2.setChildRight(rt2);
    rt2.setParent(root2);

    tree.rotate(rt, root);
    tree2.rotate(rt2, root2);

    return tree.root == rt && tree.root.childLeft() == root
        && tree.root.childLeft().childLeft() == lf && tree2.root == rt2
        && tree2.root.childLeft() == root2;
  }

  /**
   * Second tester method: Tests right rotation where parent is not root, with two shared children
   */
  public boolean test2() {
    BSTRotation<Integer> tree = new BSTRotation<>();
    BinaryTreeNode<Integer> grandparent = new BinaryTreeNode<>(4);
    BinaryTreeNode<Integer> parent = new BinaryTreeNode<>(8);
    BinaryTreeNode<Integer> child = new BinaryTreeNode<>(6);
    BinaryTreeNode<Integer> middle = new BinaryTreeNode<>(7);
    BinaryTreeNode<Integer> right = new BinaryTreeNode<>(12);

    tree.root = grandparent;
    grandparent.setChildRight(parent);
    parent.setParent(grandparent);
    parent.setChildLeft(child);
    child.setParent(parent);
    child.setChildRight(middle);
    middle.setParent(child);
    parent.setChildRight(right);
    right.setParent(parent);

    tree.rotate(child, parent);

    return tree.root == grandparent && tree.root.childRight() == child
        && tree.root.childRight().childRight() == parent
        && tree.root.childRight().childRight().childLeft() == middle
        && tree.root.childRight().childRight().childRight() == right;
  }

  /**
   * Third tester method: Tests left rotation where parent is not root, with three shared children
   */
  public boolean test3() {
    BSTRotation<Integer> tree = new BSTRotation<>();
    BinaryTreeNode<Integer> grandparent = new BinaryTreeNode<>(14);
    BinaryTreeNode<Integer> parent = new BinaryTreeNode<>(6);
    BinaryTreeNode<Integer> child = new BinaryTreeNode<>(9);
    BinaryTreeNode<Integer> middle = new BinaryTreeNode<>(8);
    BinaryTreeNode<Integer> right = new BinaryTreeNode<>(13);
    BinaryTreeNode<Integer> left = new BinaryTreeNode<>(3);

    tree.root = grandparent;
    grandparent.setChildLeft(parent);
    parent.setParent(grandparent);
    parent.setChildRight(child);
    child.setParent(parent);
    child.setChildRight(right);
    right.setParent(child);
    parent.setChildLeft(left);
    left.setParent(parent);
    child.setChildRight(right);
    right.setParent(child);
    child.setChildLeft(middle);
    middle.setParent(child);

    tree.rotate(child, parent);

    return tree.root == grandparent && tree.root.childLeft() == child
        && tree.root.childLeft().childRight() == right
        && tree.root.childLeft().childLeft() == parent
        && tree.root.childLeft().childLeft().childRight() == middle
        && tree.root.childLeft().childLeft().childLeft() == left;
  }

  /**
   * Main method: Runs the tester method to confirm that the rotate method and its private helper
   * methods work
   */
  public static void main(String[] args) {
    BSTRotation<Integer> tree = new BSTRotation<>();
    System.out.println("Test 1: " + (tree.test1() ? "PASS" : "FAIL"));
    System.out.println("Test 2: " + (tree.test2() ? "PASS" : "FAIL"));
    System.out.println("Test 3: " + (tree.test3() ? "PASS" : "FAIL"));
  }
}
