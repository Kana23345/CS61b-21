package hashmap;

import java.util.*;

/**
 * A hash table-backed Map implementation. Provides amortized constant time
 * access to elements via get(), remove(), and put() in the best case.
 * <p>
 * Assumes null keys will never be inserted, and does not resize down upon remove().
 *
 * @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            buckets[i].clear();
        }
        size=0;
        keySet.clear();
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        if (containsKey(key))
        {
            Collection<Node> b=buckets[getIndex(key)];
            for (Node n:b){
                if (n.key.equals(key))
                    return n.value;
            }
        }
        return null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
      Associates the specified value with the specified key in this map.
      If the map previously contained a mapping for the key,
      the old value is replaced.

      @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        if (key==null)throw new IllegalArgumentException("Null keys are not allowed");
        Collection<Node> bucket=buckets[getIndex(key)];
        for (Node n:bucket){
            if (n.key.equals(key)){
                n.value=value;
                return;
            }
        }
            bucket.add(new Node(key,value));
            keySet.add(key);
            size+=1;
            if (size>loadFactor*capacity)resize();
    }

    private void resize() {
        int newCapacity=capacity*2;
        Collection<Node>[] newBuckets=createTable(newCapacity);
        for (Collection<Node> b:buckets){
            for (Node n:b){
                int newIndex=(n.key.hashCode()%newCapacity+newCapacity)%newCapacity;
                newBuckets[newIndex].add(n);
            }
        }
        buckets=newBuckets;
        capacity=newCapacity;
    }

    //获取索引
    private int getIndex(K key){
        return  (key.hashCode()% capacity+capacity)%capacity;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     */
    @Override
    public Set<K> keySet() {
        return keySet;
    }

    /**
     Removes the mapping for the specified key from this map if present.
     Not required for Lab 8. If you don't implement this, throw an
     UnsupportedOperationException.
     * @param key
     */
    @Override
    public V remove(K key) {
        if (keySet.contains(key))
        {
            int index=getIndex(key);
            Collection<Node> b=buckets[index];
            for (Node n:b){
                if (n.key.equals(key)){
                    b.remove(n);
                    size--;
                    keySet.remove(key);
                    return n.value;
                }
            }
        }
        return null;
    }

    /**
      Removes the entry for the specified key only if it is currently mapped to
      the specified value. Not required for Lab 8. If you don't implement this,
      throw an UnsupportedOperationException.

     * @param key
     * @param value
     */
    @Override
    public V remove(K key, V value) {
        if (keySet.contains(key))
        {
            int index=getIndex(key);
            Collection<Node> b=buckets[index];
            for (Node n:b){
                if (n.key.equals(key)){
                    b.remove(n);
                    size--;
                    keySet.remove(key);
                    return n.value;
                }
            }
        }
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }

    //默认常量
    private static final int DEFAULT_CAPACITY = 16;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private int size = 0;
    private int capacity;
    private double loadFactor;
    private Set<K> keySet;
    // You should probably define some more!

    /**
     * Constructors
     */
    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad     maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.loadFactor = maxLoad;
        this.capacity = initialSize;
        buckets=createTable(initialSize);
        this.size = 0;
        this.keySet = new HashSet<>();

    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     Returns a data structure to be a hash table bucket
     <p>
     The only requirements of a hash table bucket are that we can:
     1. Insert items (`add` method)
     2. Remove items (`remove` method)
     3. Iterate through items (`iterator` method)
     <p>
     Each of these methods is supported by java.util.Collection,
     Most data structures in Java inherit from Collection, so we
     can use almost any data structure as our buckets.
     <p>
     Override this method to use different data structures as
     the underlying bucket type
     <p>
     BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
      Returns a table to back our hash table. As per the comment
      above, this table can be an array of Collection objects
      <p>
      BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
      THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION

      @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table=new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i]=createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

}
