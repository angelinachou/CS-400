import org.junit.jupiter.api.Test;

public class RedBlackTree<T extends Comparable<T>> extends BSTRotation<T> {

  /**
   * No arguments constructor
   */
  public RedBlackTree() {
    super();
  }

  /**
   * Checks if a new red node in the RedBlackTree causes a red property violation by having a red
   * parent. If this is not the case, the method terminates without making any changes to the tree.
   * If a red property violation is detected, then the method repairs this violation and any
   * additional red property violations that are generated as a result of the applied repair
   * operation.
   * 
   * @param newNode a newly inserted red node, or a node turned red by previous repair
   */
  protected void ensureRedProperty(RBTNode<T> newNode) {
    if (newNode == null || newNode.parent() == null || newNode.parent().parent() == null) {
      System.out.println("Either the child, parent, or grandparent node is null");
      return;
    }

    RBTNode<T> parent = newNode.parent();
    RBTNode<T> grandparent = newNode.parent().parent();
    RBTNode<T> sibling;

    if (grandparent.childLeft() == parent) {
      sibling = grandparent.childRight();
    } else {
      sibling = grandparent.childLeft();
    }

    // has black parent
    if (!parent.isRed) {
      return;
    }

    if (sibling != null && sibling.isRed) {
      case1(newNode);
      ensureRedProperty(grandparent);
    } else if (!sibling.isRed) {
      if ((grandparent.childLeft() == parent && parent.childLeft() == newNode)
          || grandparent.childRight() == parent && parent.childRight() == newNode) {
        case2(newNode);
      } else {
        case3(newNode);
      }
    }
  }

  private void case1(RBTNode<T> child) {
    child.parent().flipColor();
    child.parent().parent().flipColor();

    // parent is left child
    if (child.parent().parent().childRight() != null) {
      child.parent().parent().childRight().flipColor();
    }

    // sibling is left child
    if (child.parent().parent().childLeft() != null) {
      child.parent().parent().childLeft().flipColor();
    }
  }

  private void case2(RBTNode<T> child) {
    child.parent().flipColor();
    child.parent().parent().flipColor();
    rotate(child.parent(), child.parent().parent());
  }

  private void case3(RBTNode<T> child) {
    rotate(child, child.parent());
    // TODO - unclear if this is supposed to call case2 through the parent or child node
    case2(child.parent());
  }

  @Override
  public void insert(T data) throws NullPointerException {
    if (data == null) {
      throw new NullPointerException("Data is null.");
    }

    RBTNode<T> node = new RBTNode<>(data);
    node.isRed = true;
    super.insertHelper(node, root);

    if (node != root) {
      ensureRedProperty(node);
    }

    ((RBTNode<T>) this.root).isRed = false;
  }


  /**
   * This test is testing Case 1 in which a red node is added where the sibling is also red. I took
   * this red black tree from quiz question 3.
   */
  @Test
  public void Test1() {
    RedBlackTree<Character> tree = new RedBlackTree<>();
    tree.root = new RBTNode<Character>('K');
    tree.insert('K');
    tree.insert('F');
    tree.insert('R');
    tree.insert('D');
    tree.insert('H');
    tree.insert('O');
    tree.insert('T');
    tree.insert('I');
    tree.insert('G');

    // this is the new node to insert to test case 1 - everything else should work fine
    tree.insert('J');

    System.out.println(tree.root.data == 'A');
  }

  @Test
  public void Test2() {

  }

  public void Test3() {

  }

}
