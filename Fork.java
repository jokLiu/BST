import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Fork<Key extends Comparable<Key>, Value> implements Bst<Key, Value> {

	/** The root key. */
	private final Key key;

	/** The root value. */
	private final Value value;

	/** The right and left subtrees. */
	private final Bst<Key, Value> left, right;

	/**
	 * Instantiates a new fork.
	 *
	 * @param key
	 *            the root key
	 * @param value
	 *            the root value
	 * @param left
	 *            the left subtree
	 * @param right
	 *            the right subtree
	 */
	public Fork(Key key, Value value, Bst<Key, Value> left, Bst<Key, Value> right) {
		assert (left != null); // Refuse to work with null pointers.
		assert (right != null);

		assert (left.smaller(key)); // Refuse to violate the bst property.
		assert (right.bigger(key)); // So all our objects will really be
									// bst's.

		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;

	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean smaller(Key k) {

		return key.compareTo(k) < 0 && right.smaller(k);
	}

	@Override
	public boolean bigger(Key k) {
		return key.compareTo(k) > 0 && left.bigger(k);
	}

	@Override
	public boolean has(Key k) {

		// checks the root and if it is not equal, checks right or left subtree
		if (k.compareTo(key) == 0)
			return true;
		else if (k.compareTo(key) < 0) // Only one sub-tree needs to be
										// searched.
			return left.has(k);
		else
			return right.has(k);
	}

	@Override
	public Optional<Value> find(Key k) {

		// checks the root and if it is not equal, checks right or left subtree
		if (k.compareTo(key) == 0)
			return Optional.of(value);
		else if (k.compareTo(key) < 0)
			return left.find(k);
		else
			return right.find(k);
	}

	@Override
	public Bst<Key, Value> put(Key k, Value v) {
		// if the root has the same value, return the new tree with the replaced
		// value
		if (k.compareTo(key) == 0)
			return new Fork<Key, Value>(k, v, left, right);

		// if it is smaller than the root, put it to the left subtree
		else if (k.compareTo(key) < 0)
			return new Fork<Key, Value>(key, value, left.put(k, v), right);

		// if it is bigger than the root, put it to the right subtree
		else
			return new Fork<Key, Value>(key, value, left, right.put(k, v));

	}

	@Override
	public Optional<Entry<Key, Value>> smallest() {
		// finding the most left value
		if (left.isEmpty()) {

			return Optional.of(new Entry<Key, Value>(key, value));
		}

		else
			return left.smallest();
	}

	@Override
	public Optional<Entry<Key, Value>> largest() {
		// finding the right most value
		if (right.isEmpty()) {

			return Optional.of(new Entry<Key, Value>(key, value));
		}

		else
			return right.largest();
	}

	@Override
	public Optional<Bst<Key, Value>> delete(Key k) {
		// if it is the root, then delete
		if (k.compareTo(key) == 0)

			// if left is empty, return right subtree
			if (left.isEmpty())
				return Optional.of(right);

			// if right is empty return right subtree
			else if (right.isEmpty())
				return Optional.of(left);

			// else return the new tree with the largest key and value from the
			// left subtree as it's root
			// and delete the them from the left subtree
			else // Both non-empty.
				return Optional.of(new Fork<Key, Value>(left.largest().get().getKey(), left.largest().get().getValue(),
						left.deleteLargest().get(), right));

		else // We have to delete from one of the subtrees.
			try {
				if (k.compareTo(key) < 0)
					return Optional.of(new Fork<Key, Value>(key, value, left.delete(k).get(), right));
				else
					return Optional.of(new Fork<Key, Value>(key, value, left, right.delete(k).get()));
			} catch (NoSuchElementException e) {
				return Optional.empty();
			}
	}

	@Override
	public Optional<Bst<Key, Value>> deleteSmallest() {

		// if the left is empty, then the root is the smallest
		// so return the right subtree
		if (left.isEmpty())
			return Optional.of(right);

		// else return new tree without the smallest node in the left subtree
		else {

			Bst<Key, Value> temp = new Fork<Key, Value>(key, value, left.deleteSmallest().get(), right);
			return Optional.of(temp);
		}

	}

	@Override
	public Optional<Bst<Key, Value>> deleteLargest() {
		if (right.isEmpty())
			return Optional.of(left);
		else {

			Bst<Key, Value> temp = new Fork<Key, Value>(key, value, left, right.deleteLargest().get());
			return Optional.of(temp);
		}
	}

	@Override
	public String fancyToString() {
		return "\n\n\n" + fancyToString(0) + "\n\n\n";
	}

	@Override
	public String fancyToString(int d) {

		int step = 4; // depth step
		String l = left.fancyToString(d + step);
		String r = right.fancyToString(d + step);
		return r + spaces(d) + key + " " + value + "\n" + l;
	}

	/**
	 * Helper method to put steps fro fancyToSrtring method
	 */
	private String spaces(int n) { // Helper method for the above:
		String s = "";
		for (int i = 0; i < n; i++)
			s = s + " ";
		return s;
	}

	@Override
	public int size() {

		// return the sum of the sizes of the left and right subtrees plus 1
		// (the root)
		return (1 + right.size() + left.size());
	}

	@Override
	public int height() {

		// if the right and left subtrees are empty then
		// return 0, because the tree with one node is at height level of 0
		if (left.isEmpty() && right.isEmpty()) {
			return 0;
		}

		// else if left is empty return height of the right subtree plus 1
		else if (left.isEmpty())
			return 1 + right.height();

		// else if right is empty return height of the left subtree plus 1
		else if (right.isEmpty())
			return 1 + left.height();

		// else return the height of the heighest subtree
		else {
			if (right.height() > left.height()) {
				return 1 + right.height();
			} else
				return 1 + left.height();
		}
	}

	@Override
	public void printInOrder() {

		Bst<Key, Value> t = new Fork<Key, Value>(key, value, left, right);
		@SuppressWarnings("unchecked")
		Bst<Key, Value>[] allTrees = (Bst<Key, Value>[]) Array.newInstance(t.getClass(), size());
		allTrees[0] = new Fork<Key, Value>(key, value, left, right);

		for (int i = 0; i < t.size(); i++) {

			System.out.println("Key: " + allTrees[i].smallest().get().getKey() + " // Value: "
					+ allTrees[i].smallest().get().getValue());
			if (i + 1 != t.size())
				allTrees[i + 1] = allTrees[i].deleteSmallest().get();
		}
	}

	/**
	 * Helper class for balanced() method
	 */
	public Bst<Key, Value> helper(Bst<Key, Value> tree, Entry<Key, Value>[] entries, int begin, int end) {
		// if the entry array begin and end positions are euqual
		// then it means that we reached the last value of the entries subarray
		// so return the tree with added value
		if (begin == end) {
			return tree.put(entries[begin].getKey(), entries[begin].getValue());
		}
		// else we find a midpoint of the subarray of entries
		// and put that point to the new tree
		int mid = (end + begin) / 2;
		Bst<Key, Value> tree2 = tree.put(entries[mid].getKey(), entries[mid].getValue());

		// we check if the midpoint is greater than begin value
		// if so we create a new tree by adding value from the one subtree
		if (mid - 1 >= begin) {
			Bst<Key, Value> tree3 = helper(tree2, entries, begin, mid - 1);

			// we check if the midpoint is less than end value
			// if so we create a new tree by adding value from the one subtree
			if (mid + 1 <= end) {
				Bst<Key, Value> tree4 = helper(tree3, entries, mid + 1, end);
				return tree4;
			} else {
				return tree3;
			}
		}
		// we check if the midpoint is less than end value
		// if so we create a new tree by adding value from the one subtree
		else if (mid + 1 <= end) {
			Bst<Key, Value> tree4 = helper(tree2, entries, mid + 1, end);
			return tree4;
		} else
			return tree2;

	}

	@Override
	public Bst<Key, Value> balanced() {
		
		Entry<Key, Value> e = new Entry<>(key, value);
		@SuppressWarnings("unchecked")
		Entry<Key, Value>[] entries = (Entry<Key, Value>[]) Array.newInstance(e.getClass(), size());
		saveInOrder(entries);
		
		Bst<Key, Value> tree = new Empty<Key, Value>();
		return helper(tree, entries, 0, size() - 1);

	}

	@Override
	public void saveInOrder(Entry<Key, Value>[] a) {
		saveInOrder(a, 0);

	}
	
	@Override
	public int saveInOrder(Entry<Key, Value>[] a, int k) {

		// create an array of trees
		Bst<Key, Value> t = new Fork<Key, Value>(key, value, left, right);
		@SuppressWarnings("unchecked")
		Bst<Key, Value>[] allTrees = (Bst<Key, Value>[]) Array.newInstance(t.getClass(), size());
		allTrees[0] = new Fork<Key, Value>(key, value, left, right);

		for (int i = 0; i < t.size(); i++) {
			// putting the smallest entry to the entries array
			if (i >= k)
				a[i] = allTrees[i].smallest().get();

			// and then we delete that entry from the tree
			if (i + 1 != t.size())
				allTrees[i + 1] = allTrees[i].deleteSmallest().get();
		}

		return t.size();
	}

	@Override
	public Optional<Key> getKey() {
		return Optional.of(key);
	}

	@Override
	public Optional<Value> getValue() {
		return Optional.of(value);
	}

	@Override
	public Optional<Bst<Key, Value>> getLeft() {
		return Optional.of(left);
	}

	@Override
	public Optional<Bst<Key, Value>> getRight() {
		return Optional.of(right);
	}

}
