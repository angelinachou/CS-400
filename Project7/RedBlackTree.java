// import static org.junit.jupiter.api.Assertions.*;
// import org.junit.jupiter.api.Test;

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
    RBTNode<T> sibling = (grandparent.childLeft() == parent) ? grandparent.childRight() : grandparent.childLeft();
    
    // has black parent
    if (!parent.isRed) {
      return;
    
    }
    
    // assigns the node to a certain case violation
    if (sibling != null && sibling.isRed) {
        case1(newNode);
        ensureRedProperty(grandparent);
    } else if (sibling == null || !sibling.isRed) {
        if ((grandparent.childLeft() == parent && parent.childLeft() == newNode) || (grandparent.childRight() == parent && parent.childRight() == newNode)) {
            case2(newNode);
            } else {
        case3(newNode);
        }
    }
  }

  private void case1(RBTNode<T> child) {
    RBTNode<T> parent = child.parent();
    RBTNode<T> grandparent = parent.parent();
    RBTNode<T> sibling = (grandparent.childLeft() == parent) ? grandparent.childRight() : grandparent.childLeft();

    parent.flipColor();
    if (sibling != null) {
        sibling.flipColor();
    } 
    grandparent.flipColor();
    // could result in the grandparent causing a double red violation so have to check for that
    ensureRedProperty(grandparent);
  }

  private void case2(RBTNode<T> child) {
    if (child.parent() == null || child.parent().parent() == null) {
        return;
    }

    child.parent().flipColor();
    child.parent().parent().flipColor();
    rotate(child.parent(), child.parent().parent());
  }

  private void case3(RBTNode<T> child) {
    if (child.parent() == null) {
        return;
    }

    rotate(child, child.parent());
    // child becomes parent of the parent node, so have to figure out where the parent is and call case2 through there
    if (child.parent() != null) {
        if (child.childRight() != null) {
            case2(child.childRight());
        } else {            
            case2(child.childLeft());
        }
    }
  }

  @Override
  public void insert(T data) throws NullPointerException {
    if (data == null) {
      throw new NullPointerException("Data is null.");
    }

    RBTNode<T> node = new RBTNode<>(data);
    node.isRed = true;
    if (root == null) {
        root = node;
    } else {
        super.insertHelper(node, root);
        ensureRedProperty(node);
    }

    // ensure that the root node is always black
    ((RBTNode<T>) this.root).isRed = false;
  }

  /**
   * Test1 tests case1 with the most basic possible red black tree. 
   */
  /**
  @Test
  public void Test1() {
    RedBlackTree<Integer> rbt = new RedBlackTree<>();
    rbt.insert(10);
    assertTrue(!((RBTNode<Integer>) rbt.root).isRed);
    rbt.insert(15); 
    rbt.insert(5);

    // 1 should create a double red, which would then need to be addressed by making the sibling and parent nodes black.
    rbt.insert(1);
    assertTrue(!((RBTNode<Integer>) rbt.root).isRed);
    assertTrue(!((RBTNode<Integer>) rbt.root.childRight()).isRed);
    assertTrue(!((RBTNode<Integer>) rbt.root.childLeft()).isRed);
    assertTrue(((RBTNode<Integer>) rbt.root.childLeft().childLeft()).isRed);
  }
  */

  /**
   * Test2 tests case 2 with the most basic possible red black tree. Since this one requires rotation, I also made sure that all the characters were shifted to the correct place.
   */
  /**
  @Test
  public void Test2() {
        RedBlackTree<Character> rbt = new RedBlackTree<>();
        rbt.insert('D');
        assertTrue(!((RBTNode<Character>) rbt.root).isRed);
        rbt.insert('C');
        rbt.insert('E');
    // manually flipping the color of the sibling to black so we put this into case 2
    ((RBTNode<Character>)rbt.root.childRight()).flipColor();

    // this is where the double red occurs.
        rbt.insert('A');
        
    // testing to make sure each node is the correct color and ends up in the right spot
    assertTrue(!((RBTNode<Character>) rbt.root).isRed && ((RBTNode<Character>) rbt.root).data == 'C');
        assertTrue(((RBTNode<Character>) rbt.root.childRight()).isRed && ((RBTNode<Character>) rbt.root.childRight()).data == 'D');
    assertTrue(((RBTNode<Character>) rbt.root.childLeft()).isRed && ((RBTNode<Character>) rbt.root.childLeft()).data == 'A');
    assertTrue(!((RBTNode<Character>) rbt.root.childRight().childRight()).isRed && ((RBTNode<Character>) rbt.root.childRight().childRight()).data == 'E');
  }
  */

  /** 
   * Test3 tests case 3 with the most basic possible red black tree. Since this one requires rotation, I also made sure that all the characters were shifted to the correct place.
   */
  @Test
  /**
  public void Test3() {
        RedBlackTree<Character> rbt = new RedBlackTree<>();
        rbt.insert('D');
        assertTrue(!((RBTNode<Character>) rbt.root).isRed);
        rbt.insert('B');
        rbt.insert('E');
    // manually flipping the color of the sibling to black so we put this into case 2
        ((RBTNode<Character>)rbt.root.childRight()).flipColor();

    // inserting C so that it becomes the right child of B, putting this into case 3
    rbt.insert('C');

    // testing to make sure each node is the correct color and in the right spot
        assertTrue(!((RBTNode<Character>) rbt.root).isRed && ((RBTNode<Character>) rbt.root).data == 'C');
        assertTrue(((RBTNode<Character>) rbt.root.childLeft()).isRed && ((RBTNode<Character>) rbt.root.childLeft()).data == 'B');
        assertTrue(((RBTNode<Character>) rbt.root.childRight()).isRed && ((RBTNode<Character>) rbt.root.childRight()).data == 'D');
        assertTrue(!((RBTNode<Character>) rbt.root.childRight().childRight()).isRed && ((RBTNode<Character>) rbt.root.childRight().childRight()).data == 'E');
  }
  */

  /**
   * This test is testing Case 1 in which a red node is added where the sibling is also red. I took
   * this red black tree from quiz question 3.
   */
  /**
  @Test
  public void Test4() {
    RedBlackTree<Character> rbt = new RedBlackTree<>();
    rbt.insert('K');
    rbt.insert('F');
    rbt.insert('R');
    rbt.insert('D');
    rbt.insert('H');
    rbt.insert('O');
    rbt.insert('T');
    rbt.insert('I');
    rbt.insert('G');

    // checking that all the nodes and colors are correct before insertion
    assertTrue(!((RBTNode<Character>) rbt.root).isRed && ((RBTNode<Character>) rbt.root).data == 'K');
    assertTrue(((RBTNode<Character>) rbt.root.childLeft()).isRed && ((RBTNode<Character>) rbt.root.childLeft()).data == 'F');
    assertTrue(!((RBTNode<Character>) rbt.root.childRight()).isRed && ((RBTNode<Character>) rbt.root.childRight()).data == 'R');
    assertTrue(!((RBTNode<Character>) rbt.root.childLeft().childLeft()).isRed && ((RBTNode<Character>) rbt.root.childLeft().childLeft()).data == 'D');
    assertTrue(!((RBTNode<Character>) rbt.root.childLeft().childRight()).isRed && ((RBTNode<Character>) rbt.root.childLeft().childRight()).data == 'H');
    assertTrue(((RBTNode<Character>) rbt.root.childRight().childLeft()).isRed && ((RBTNode<Character>) rbt.root.childRight().childLeft()).data == 'O');
    assertTrue(((RBTNode<Character>) rbt.root.childRight().childRight()).isRed && ((RBTNode<Character>) rbt.root.childRight().childRight()).data == 'T');
    assertTrue(((RBTNode<Character>) rbt.root.childLeft().childRight().childLeft()).isRed && ((RBTNode<Character>) rbt.root.childLeft().childRight().childLeft()).data == 'G');
    assertTrue(((RBTNode<Character>) rbt.root.childLeft().childRight().childRight()).isRed && ((RBTNode<Character>) rbt.root.childLeft().childRight().childRight()).data == 'I');

    // this is where we insert the new letter J
    rbt.insert('J');

    // checking that all the nodes and colors are correct after the insertion and rotations and color flipping
    assertTrue(!((RBTNode<Character>) rbt.root).isRed && ((RBTNode<Character>) rbt.root).data == 'H');
    assertTrue(((RBTNode<Character>) rbt.root.childLeft()).isRed && ((RBTNode<Character>) rbt.root.childLeft()).data == 'F');
    assertTrue(((RBTNode<Character>) rbt.root.childRight()).isRed && ((RBTNode<Character>) rbt.root.childRight()).data == 'K');
    assertTrue(!((RBTNode<Character>) rbt.root.childLeft().childLeft()).isRed && ((RBTNode<Character>) rbt.root.childLeft().childLeft()).data == 'D');
    assertTrue(!((RBTNode<Character>) rbt.root.childLeft().childRight()).isRed && ((RBTNode<Character>) rbt.root.childLeft().childRight()).data == 'G');
    assertTrue(!((RBTNode<Character>) rbt.root.childRight().childLeft()).isRed && ((RBTNode<Character>) rbt.root.childRight().childLeft()).data == 'I');
    assertTrue(!((RBTNode<Character>) rbt.root.childRight().childRight()).isRed && ((RBTNode<Character>) rbt.root.childRight().childRight()).data == 'R');
    assertTrue(((RBTNode<Character>) rbt.root.childRight().childLeft().childRight()).isRed && ((RBTNode<Character>) rbt.root.childRight().childLeft().childRight()).data == 'J');
    assertTrue(((RBTNode<Character>) rbt.root.childRight().childRight().childLeft()).isRed && ((RBTNode<Character>) rbt.root.childRight().childRight().childLeft()).data == 'O');
    assertTrue(((RBTNode<Character>) rbt.root.childRight().childRight().childRight()).isRed && ((RBTNode<Character>) rbt.root.childRight().childRight().childRight()).data == 'T');
  }
  */
}