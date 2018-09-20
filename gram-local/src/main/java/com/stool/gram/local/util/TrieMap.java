package com.stool.gram.local.util;


import java.util.*;

public class TrieMap<K extends Iterable<T>, T,  V> implements Map<K, V> {

    private Node<T, V> root;
    private int size;

    public TrieMap() {
        root = new Node<>();
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsKey(Object key) {
        Node<T, V> cur = root;
        Iterable<T> it = (Iterable<T>) key;
        for (T t : it) {
            if (cur.next.get(t) == null) {
                return false;
            }
            cur = cur.next.get(t);
        }
        return cur.value != null;
    }

    @Override
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        Node<T, V> cur = root;
        Iterable<T> it = (Iterable<T>) key;
        for (T t : it) {
            if (cur.next.get(t) == null) {
                return null;
            }
            cur = cur.next.get(t);
        }
        return cur.value;
    }

    @Override
    public V put(K key, V value) {
        Node<T, V> cur = root;
        for (T t : key) {
            if (cur.next.get(t) == null) {
                cur.next.put(t, new Node<>());
            }
            cur = cur.next.get(t);
        }
        if (cur.value == null) {
            size++;
        }
        cur.value = value;
        return value;
    }

    /**
     * 这里无论有没有删除，都返回null
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public V remove(Object key) {
        Iterable<T> it = (Iterable<T>) key;
        remove(it.iterator(), root);
        return null;
    }

    // 在Trie的node下扫描key的it迭代器，如果node需要删除，则返回value，否则返回null
    private boolean remove(Iterator<T> it, Node<T, V> node) {
        // 当扫描完最后一个元素时，直接判断是否需要删除node
        if (!it.hasNext()) {
            if (node.value == null) {               // 此时node节点没有value，说明Trie中没有这个key，返回false，什么都不做
                return false;
            } else {
                size--;     // 此时node有value，所以删除这个value，size--
                if (node.next.size() > 0) {      // 此时node有value，但node下面还有子节点，则将node的value删除，但不需要删除node
                    node.value = null;
                    return false;
                } else {                                // 此时node有value，且node下面没有子节点，故可以删除该node。
                    return true;
                }
            }
        }

        T t = it.next();
        Node<T, V> nextNode = node.next.get(t);
        if (nextNode == null) {     // 扫描不到t，说明Trie中没有这个key，返回false，什么都不做
            return false;
        }
        if (remove(it, nextNode)) {      // 尝试删除下一个节点，若下一个节点需要删除，则在map中将其删除
            node.next.remove(t);
        }

        if (node.value != null || node.next.size() > 0) {   // node不是最后一个节点，所以若node有value 或者 node下还有子节点，则不需要删除
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K key : m.keySet()) {
            put(key, m.get(key));
        }
    }

    @Override
    public void clear() {
        root = new Node<>();
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    private static class Node<T, V> {

        V value;
        Map<T, Node<T, V>> next;

        Node(V value) {
            this.value = value;
            next = new HashMap<>();
        }

        Node() {
            this(null);
        }
    }
}
