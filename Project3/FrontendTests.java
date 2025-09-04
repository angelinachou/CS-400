import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;


public class FrontendTests {
  // this method tests the load method. Because it doesn't actually print out any output, I checked
  // that the specific song in the Backend_Placeholder was added to the fiveMost method.
  @Test
  public void frontendTest1() {
    TextUITester tester = new TextUITester("load apt.mp3\nquit\n");
    IterableSortedCollection<Song> tree = new Tree_Placeholder();
    Backend_Placeholder b = new Backend_Placeholder(tree);
    Frontend f = new Frontend(new Scanner(System.in), b);

    f.runCommandLoop();
    String output = tester.checkOutput();

    List<String> songs = b.fiveMost();
    assertTrue(songs.contains("DJ Got Us Fallin' In Love (feat. Pitbull)"));
  }

  // this method tests the year me2thod.
  @Test
  public void frontendTest2() {
    TextUITester tester = new TextUITester("year 2000\nquit\n");
    IterableSortedCollection<Song> tree = new Tree_Placeholder();
    Backend_Placeholder b = new Backend_Placeholder(tree);
    Frontend f = new Frontend(new Scanner(System.in), b);

    f.runCommandLoop();
    String output = tester.checkOutput();

    List<String> songs = b.fiveMost();
    System.out.println(songs.size());
    System.out.println(songs.get(0));
    System.out.println(songs.get(1));
    assertTrue(songs.isEmpty());
  }


  // TODO
  // public void frontendTest3() {
  // }

  public FrontendTests() {
  }
}
