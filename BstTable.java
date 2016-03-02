import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Optional;
import java.util.TreeSet;

/**
 * The Class BstTable.
 *
 * @param <Key>
 *            the generic type of the key
 * @param <Value>
 *            the generic type of the value
 */
public class BstTable<Key extends Comparable<Key>, Value> implements Table<Key, Value> {

	/** The BST. */
	private final Bst<Key, Value> bst;

	/**
	 * Instantiates a new empty bst table. Main constructor
	 */
	public BstTable() {
		this.bst = new Empty<Key, Value>();
	}

	/**
	 * Instantiates a new bst table Helper constructor
	 * 
	 * @param bstree
	 *            the binary search tree
	 */
	public BstTable(Bst<Key, Value> bstree) {
		this.bst = bstree;
	}

	/*
	 * Checks if the table contains a key
	 * 
	 * @param v the key
	 * 
	 * @return if the table contains a key
	 */
	@Override
	public boolean containsKey(Key v) {

		return bst.has(v);
	}

	/*
	 * Returns the value of a key, if it exists.
	 * 
	 * @param k the key
	 * 
	 * @return the value of the key
	 */
	@Override
	public Optional<Value> get(Key k) {

		return bst.find(k);
	}

	/*
	 * Checks if the table is empty
	 * 
	 * @return if the table is empty
	 */
	@Override
	public boolean isEmpty() {

		return bst.isEmpty();
	}

	/*
	 * table with added or replaced entry
	 * 
	 * @param k the key
	 * 
	 * @param v the value
	 * 
	 * @return the new table with the added or replaced entry
	 */
	@Override
	public Table<Key, Value> put(Key k, Value v) {

		return new BstTable<Key, Value>(bst.put(k, v));
	}

	/*
	 * Removes the entry with given key, if present.
	 * 
	 * @param k the key
	 * 
	 * @return the new table with removed entry
	 */
	@Override
	public Optional<Table<Key, Value>> remove(Key k) {

		if (bst.has(k)) {
			return Optional.of(new BstTable<Key, Value>(bst.delete(k).get()));
		} else
			return Optional.empty();
	}

	/*
	 * @return the size of the table
	 */
	@Override
	public int size() {

		return bst.size();
	}

	/*
	 * The collection of values in the table.
	 * 
	 * @return the values
	 */
	@Override
	public Collection<Value> values() {

		// new collection of values
		Collection<Value> values = new TreeSet<Value>();

		// array of entries
		Entry<Key, Value>[] entries = information();
		bst.saveInOrder(entries);

		for (int i = 0; i < size(); i++) {
			values.add(entries[i].getValue());
		}

		return values;
	}

	/*
	 * The collection of keys in the table.
	 * 
	 * @return the keys
	 */
	@Override
	public Collection<Key> keys() {

		// new collection of keys
		Collection<Key> keys = new TreeSet<Key>();

		// array of entries
		Entry<Key, Value>[] entries = information();
		bst.saveInOrder(entries);

		for (int i = 0; i < size(); i++) {
			keys.add(entries[i].getKey());
		}
		return keys;

	}

	/**
	 * creates empty array of entries
	 *
	 * @return the array of entries (empty)
	 */
	private Entry<Key, Value>[] information() {

		// new array of entries
		Entry<Key, Value> e = new Entry<>(bst.smallest().get().getKey(), bst.smallest().get().getValue());
		@SuppressWarnings("unchecked")
		Entry<Key, Value>[] a = (Entry<Key, Value>[]) Array.newInstance(e.getClass(), size());

		return a;

	}

}
