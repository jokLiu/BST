// Write a class named BstTable that implements this interface using
// your code for binary search trees. Hence you get an implementation
// of *immutable* tables.

import java.util.*;

public interface Table <Key extends Comparable<Key>,Value> {

  boolean containsKey(Key v);     // Self-explanatory.
  Optional<Value> get(Key k);     // Returns the value of a key, if it exists.
  boolean isEmpty();              // Self-explanatory.
  Table<Key,Value> put(Key k, Value v);     // Table with added or replaced entry.
  Optional<Table<Key,Value>> remove(Key k); // Removes the entry with given key, if present.
  int size();                     // Number of entries in the table.
  Collection<Value> values();     // The collection of values in the table.
  Collection<Key> keys();         // The set of keys in the table.
}

// This interface is inspired by the Java Map interface
// https://docs.oracle.com/javase/7/docs/api/java/util/Map.html
//
// But it is (deliberately) different. In particular, we use Optional.
// https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html
//
// We retain the requirement that every key has at most one value.
// (Two entries with the same key are not allowed.)
