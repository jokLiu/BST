// * Write an implementation of immutable binary search trees, using
//   the composite pattern with Empty and Fork classes implementing
//   the interface Bst.
//
//   This is similar to, but different from the code Bst.zip discussed
//   in the lectures and given on Canvas, which you may use as a
//   starting point.
//
// * Your program should compile with the option -Xlint
//   ("enable all recommended warnings").
//
// * Do not modify this class.
//
// * The class Empty implementing Bst should have a constructor
//
//       public Empty()

// * The class Fork implementing Bst should have a constructor
//
//       public Fork(Key key, Value value, Bst<Key,Value> left, Bst<Key,Value> right)
//
// * More information and requirements below.

import java.util.*;

public interface Bst<Key extends Comparable<Key>,Value> {
  public boolean                    isEmpty();
  public boolean                    smaller(Key k); 
  public boolean                    bigger(Key k);  
  public boolean                    has(Key k);     
  public Optional<Value>            find(Key k);  
  public Bst<Key,Value>             put(Key k,Value v);  
  public Optional<Bst<Key,Value>>   delete(Key k);  
  public Optional<Entry<Key,Value>> smallest();   
  public Optional<Bst<Key,Value>>   deleteSmallest();
  public Optional<Entry<Key,Value>> largest(); 
  public Optional<Bst<Key,Value>>   deleteLargest();
  public String                     fancyToString();
  public String                     fancyToString(int d);
  public int                        size();       
  public int                        height();     
  public void                       printInOrder();     
  public void                       saveInOrder(Entry<Key,Value> a[]); 
  public int                        saveInOrder(Entry<Key,Value> a[], int i); 
  public Bst<Key,Value>             balanced(); 

  // Added 2016/02/25 for automatic marking purposes.
  public Optional<Key> getKey();
  public Optional<Value> getValue();
  public Optional<Bst<Key,Value>> getLeft();
  public Optional<Bst<Key,Value>> getRight(); 
}

// * fancyToString() is not assessed, but mandatory. You will use it
//   for debugging.
//
// * printInOrder() is not assessed. It is practice for saveInOrder().
//
// * The constructors of the classes Empty and Fork should *not* allow
//   building trees which do not have the binary search property. This
//   should be checked with assert (Cf. lecture code Bst.zip).

/*
   -------------------------------------------------------------------
   boolean isEmpty();      

      Is this tree empty?

   -------------------------------------------------------------------
   boolean smaller(Key k); 

      Does every node have its key smaller than k?

   -------------------------------------------------------------------
   boolean bigger(Key k);  

      Does every node have its key bigger than k?

   -------------------------------------------------------------------
   boolean has(Key k);     

      Does the key occur in this tree?

   -------------------------------------------------------------------
   Optional<Value> find(Key k); 

      Finds the value of the node with a given key k, if it exists.

   -------------------------------------------------------------------
   <Bst<Key,Value> put(Key k,Value v);  

      Returns a copy of this tree with k,v inserted, if the key isn't
      already there, or with the value replaced, it is already there.

   -------------------------------------------------------------------
   Optional<Bst<Key,Value>>  delete(Key k);  

      Returns a copy of "this" with the node with key k deleted, it if exists.

   -------------------------------------------------------------------
   Optional<Entry<Key,Value>> smallest();   

      Returns the entry with smallest key (=left-most node), if it exists.

   -------------------------------------------------------------------
   Optional<Bst<Key,Value>> deleteSmallest();

      Return new tree with smallest element deleted, if it exists.

   -------------------------------------------------------------------
   Optional<Entry<Key,Value>>  largest();    

      Returns entry with largest key (=right-most node), if it exists.

   -------------------------------------------------------------------
   Optional<Bst<Key,Value>> deleteLargest();

      Return new tree with largest element deleted, if it exists.

   -------------------------------------------------------------------
   String  fancyToString();

      2-dimensional, rotated tree printing. Not marked. Use for debugging.

   -------------------------------------------------------------------
   String fancyToString(int d);

      Starting at a given position d.

   -------------------------------------------------------------------
   int size();       

      Counts how many values are stored.

   -------------------------------------------------------------------
   int height();     

      Gives the height of this tree. That of the empty tree is -1 by
      convention.

   -------------------------------------------------------------------
   void printInOrder();

      Prints the values in key order (left, then root, then right).

   -------------------------------------------------------------------
   void saveInOrder(Entry<Key,Value> a[]); 

      Save entries in key order in the array a starting at 0, as in an
      in-order traversal (left, then root, then right).

   -------------------------------------------------------------------
   int saveInOrder(Entry<Key,Value> a[], int i);

      Same but starting at position i, and inform the caller what the
      next available position of the array is.

   -------------------------------------------------------------------
   Bst<Key,Value> balanced(); 

      Returns a balanced copy of this tree (let's take this to mean,
      ambiguously, a tree with same key-value pairs but with minimal
      height).

 */
