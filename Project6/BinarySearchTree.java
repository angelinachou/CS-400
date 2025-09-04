/**
 * This class represents a binary search tree of that uses the SortedCollection interface. We take
 * generic generic type parameters and sort them based on the Comparable interface compareTo()
 * method.
 * 
 * The class provides methods to insert, search, check size, and clear the tree, as well as some
 * testers to demonstrate how the code works.
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollection<T> {
  protected BinaryTreeNode<T> root;

  /**
   * Initializes an empty Binary Search Tree with the root being null
   */
  public BinarySearchTree() {
    // initializing the root
    root = null;
  }

  /**
   * Inserts a new data value into the sorted collection.
   * 
   * @param data the new value being inserted
   * @throws NullPointerException if data argument is null, we do not allow null values to be stored
   *                              within a SortedCollection
   */
  @Override
  public void insert(T data) throws NullPointerException {
    if (data == null) {
      throw new NullPointerException(
          "The data argument is null and we do not allow null values to be stored within a sorted collection");
    }

    BinaryTreeNode<T> node = new BinaryTreeNode<>(data);

    // when there is nothing in the tree
    if (root == null) {
      root = node;
    } else {
      insertHelper(node, root);
    }
  }

  /**
   * Performs the naive binary search tree insert algorithm to recursively insert the provided
   * newNode (which has already been initialized with a data value) into the provided tree/subtree.
   * When the provided subtree is null, this method does nothing.
   */
  protected void insertHelper(BinaryTreeNode<T> newNode, BinaryTreeNode<T> subtree) {
    if (subtree == null) {
      return;
    }

    // = for the case that a duplicate value is being added
    if (newNode.getData().compareTo(subtree.getData()) <= 0) {
      if (subtree.childLeft() == null) {
        subtree.setChildLeft(newNode);
        newNode.setParent(subtree);
      } else {
        insertHelper(newNode, subtree.childLeft());
      }
    } else {
      if (subtree.childRight() == null) {
        subtree.setChildRight(newNode);
        newNode.setParent(subtree);
      } else {
        insertHelper(newNode, subtree.childRight());
      }
    }
  }

  /**
   * Check whether data is stored in the tree.
   * 
   * @param data the value to check for in the collection
   * @return true if the collection contains data one or more times, and false otherwise
   */
  @Override
  public boolean contains(Comparable<T> data) {
    return c(data, root);
  }

  /**
   * Performs the naive binary search tree contains algorithm to recursively search for a value data
   * within the provided tree/subtree. When the provided subtree is null, this method does nothing.
   */
  private boolean c(Comparable<T> data, BinaryTreeNode<T> tree) {
    if (tree == null) {
      return false;
    }

    int compare = data.compareTo(tree.getData());

    if (compare == 0) {
      return true;
    } else if (compare < 0) {
      return c(data, tree.childLeft());
    } else {
      return c(data, tree.childRight());
    }
  }

  /**
   * Counts the number of values in the collection, with each duplicate value being counted
   * separately within the value returned.
   * 
   * @return the number of values in the collection, including duplicates
   */
  @Override
  public int size() {
    return s(root);
  }

  /**
   * Performs the naive binary search tree insert algorithm to recursively add values to find the
   * size of tree. When the provided subtree is null, this method returns 0.
   */
  private int s(BinaryTreeNode<T> node) {
    if (node == null) {
      return 0;
    }

    return 1 + s(node.childLeft()) + s(node.childRight());
  }

  /**
   * Checks if the collection is empty.
   * 
   * @return true if the collection contains 0 values, false otherwise
   */
  @Override
  public boolean isEmpty() {
    return root == null;
  }

  /**
   * Removes all values and duplicates from the collection.
   */
  @Override
  public void clear() {
    root = null;
  }

  /**
   * First tester method: Using Integer values, tests for null pointer exception, insert method,
   * contains method for interior values , and size method.
   */
  public boolean test1() {
    BinarySearchTree<Integer> t1 = new BinarySearchTree<>();

    try {
      t1.insert(null);
      return false;
    } catch (NullPointerException e) {
    }

    t1.insert(8);
    t1.insert(4);
    t1.insert(17);
    t1.insert(9);
    t1.insert(1);
    return t1.contains(8) && t1.contains(9) && !t1.contains(5) && (t1.size() == 5);
  }

  /**
   * Second tester method: Using Integer values, tests for insert method, clear method, isEmpty
   * method, size method, duplicates, and contains method for leaves of tree.
   */
  public boolean test2() {
    BinarySearchTree<Integer> t2 = new BinarySearchTree<>();
    t2.insert(14);
    t2.insert(21);
    t2.insert(29);
    t2.insert(17);
    t2.insert(9);
    t2.insert(9);
    t2.insert(13);
    if (t2.size() != 7 && t2.contains(9) && t2.contains(29) && !t2.contains(10)) {
      return false;
    }

    t2.clear();
    return t2.size() == 0 && t2.isEmpty();
  }

  /**
   * Third tester method: Using String values, tests for insert method, contains method, size
   * method, and checks that the tree is in the correct order.
   */
  // Uses String
  // checks childLeft and childRight to see if it's in the correct order
  // does contain + size
  public boolean test3() {
    BinarySearchTree<String> t3 = new BinarySearchTree<>();
    t3.insert("dog");
    t3.insert("cat");
    t3.insert("aardvark");
    t3.insert("badger");
    t3.insert("goat");
    t3.insert("pig");

    if (!t3.root.getData().equals("dog")
        || !t3.root.childLeft().childLeft().childRight().getData().equals("badger")) {
      return false;
    }

    return t3.contains("pig") && t3.contains("badger") && t3.contains("aardvark") && t3.size() == 6;
  }

  public static void main(String[] args) {
    BinarySearchTree<Integer> t1 = new BinarySearchTree<>();
    System.out.println("Test 1: " + (t1.test1() ? "PASS" : "FAIL"));

    BinarySearchTree<Integer> t2 = new BinarySearchTree<>();
    System.out.println("Test 2: " + (t2.test2() ? "PASS" : "FAIL"));

    BinarySearchTree<String> t3 = new BinarySearchTree<>();
    System.out.println("Test 3: " + (t3.test3() ? "PASS" : "FAIL"));
  }
}
