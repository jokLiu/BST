
//$ javac -Xlint BstTests.java
//$ Java -ea BstTests 
import java.util.*;

public class BstTests {

	public static void main(String[] args) {
		Bst<Integer, String> t1 = new Empty<>();
		assert (t1.size() == 0);
		assert (t1.height() == -1);
		assert (t1.isEmpty());

		Bst<Integer, String> t2 = t1.put(30, "John");
		assert (t1.isEmpty());
		assert (t2.size() == 1);
		assert (t2.height() == 0);

		Bst<Integer, String> t3 = t2.put(40, "Mary").put(25, "Peter").put(37, "Monica").put(34, "Nicolas").put(31,
				"Martin");
		assert (t3.size() == 6);
		assert (t2.size() == 1);
		assert (t2.height() == 0);

		Bst<Integer, String> t4 = t3.put(31, "Kathy");
		assert (t3.size() == 6);
		assert (t4.size() == 6);

		Bst<Integer, String> t5 = t3.balanced();
		assert (t5.size() == t3.size());
		assert (t5.height() <= t3.height());
		assert (t5.height() <= log2floor(t5.size()));
		
		
		//My tests
		Bst<Integer, String> empty = new Empty<Integer, String>();
		Bst<Integer, String> tree = new Fork<Integer, String>(20, "John", empty, empty);
		Bst<Integer, String> tree2 = tree.put(10, "Akvi");
		Bst<Integer, String> tree3 = tree2.put(30, "Jaco");
		Bst<Integer, String> tree4 = tree3.put(50, "Tim");
		Bst<Integer, String> tree5 = tree4.put(60, "Mary");
		Bst<Integer, String> tree6 = tree5.put(15, "Jill");
		Bst<Integer, String> tree7 = tree6.put(35, "Anna");
		Bst<Integer, String> tree8 = tree7.put(18, "Greg");
		
		assert(tree8.has(30));
		assert(!tree2.has(30));
		assert(tree8.largest().get().getKey() == 60);
		assert((tree8.smallest().get().getKey())== 10);
		assert(!tree3.smaller(30));
		assert(!tree5.smaller(51));
		assert(!tree2.bigger(20));
		assert(tree7.bigger(9));
		assert(tree6.find(15).get().equals("Jill"));
		
		Bst<Integer, String> tree9 = tree8.balanced();
		assert(tree9.height() <= log2floor(t5.size()));
		assert(tree9.largest().get().getValue().equals("Mary"));
		Bst<Integer, String> tree10 = tree9.deleteLargest().get();
		assert(tree10.largest().get().getKey()==50);
		
		Bst<Integer, String> tree11 = tree10.delete(50).get();
		assert(tree11.largest().get().getKey()==35);
		assert(tree10.largest().get().getKey()==50);
		assert(tree11.smallest().get().getKey() == 10);
		
		Bst<Integer, String> tree12 = tree11.delete(10).get();
		assert(tree12.smallest().get().getKey()==15);
		
		Bst<Integer, String> tree13 = tree12.deleteSmallest().get();
		assert(tree12.smallest().get().getKey()==15);
		assert(tree13.smallest().get().getKey()==18);
		
		assert(tree8.getKey().get() == 20);
		assert(tree8.getValue().get().equals("John"));
		assert(!tree8.getLeft().get().find(20).equals("John"));
		assert(tree8.getLeft().get().size() + tree8.getRight().get().size() == tree8.size()-1);
		assert(tree8.getLeft().get().getKey().get() == 10);
		assert(tree8.getRight().get().getKey().get() == 30);
		assert(tree8.getRight().get().balanced().height() <= log2floor(tree8.getRight().get().size()));
		
		
		//Table test
		Table<Integer, String> table = new BstTable<Integer, String>();
		Table<Integer, String> table2 = table.put(10, "Akvi");
		Table<Integer, String> table3 = table2.put(30, "Jaco");
		Table<Integer, String> table4 = table3.put(50, "Tim");
		Table<Integer, String> table5 = table4.put(35, "Anna");
				
		assert(table.isEmpty());
		assert(!table2.isEmpty());
		assert(table5.containsKey(35));		
		assert(!table4.containsKey(35));
		assert(table5.get(50).get().equals("Tim"));
		assert(table.isEmpty());
		assert(!table5.isEmpty());
		
		Table<Integer, String> table6 = table5.remove(50).get();
		assert(!table6.containsKey(50));		
		assert(table5.containsKey(50));		
		assert(table5.size()==4);
		assert(table6.size()==3);
		assert(table6.keys().contains(10));
		assert(table6.keys().contains(30));
		assert(table6.keys().contains(35));
		assert(!table6.keys().contains(50));
		assert(table5.keys().contains(50));
		
		assert(table6.values().contains("Akvi"));
		assert(table6.values().contains("Jaco"));
		assert(table6.values().contains("Anna"));
		assert(!table6.values().contains("Tim"));
		assert(table4.values().contains("Tim"));
		assert(!table3.values().contains("Tim"));
		System.out.println("Tests passed");
		// You should add more tests designed by yourself.
	}

	private static int log2floor(int x) {
		assert (x > 0);
		int y = 0;
		do {
			y = y + 1;
			x = x / 2;
		} while (x > 0);
		return y;
	}
}
