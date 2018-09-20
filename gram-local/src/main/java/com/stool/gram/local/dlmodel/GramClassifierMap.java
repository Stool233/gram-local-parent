package com.stool.gram.local.dlmodel;

import com.stool.gram.local.util.TrieMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 使用Trie来保存 时间点序列 与 模型 之间的映射（时间点序列与模型一一对应）
 * 使用HashMap保存 模型名称 与 模型 之间的映射
 *
 * 使用读写锁保证线程安全
 *
 */
public class GramClassifierMap {
    private Map<Collection<Integer>, GramClassifier> timeModelMap;
    private Map<String, GramClassifier> nameModelMap;

    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock readLock = rwl.readLock();
    private Lock writeLock = rwl.writeLock();


    public GramClassifierMap() {
        this.timeModelMap = new TrieMap<>();
        this.nameModelMap = new HashMap<>();
    }

    private void put(String name, GramClassifier gramModel) {
        nameModelMap.put(name, gramModel);
    }

    public GramClassifier get(String name) {
        readLock.lock();
        try {
            return nameModelMap.get(name);
        } finally {
            readLock.unlock();
        }
    }

    public void put(GramClassifier gramClassifier) {
        writeLock.lock();
        try {
            put(gramClassifier.getGramModel().getTimes(), gramClassifier);
            put(gramClassifier.getGramModel().getModelName(), gramClassifier);
        } finally{
            writeLock.unlock();
        }
    }


    private void put(Collection<Integer> times, GramClassifier gramClassifier) {
        timeModelMap.put(times, gramClassifier);
    }

    public GramClassifier get(Collection<Integer> times) {
        readLock.lock();
        try {
            return timeModelMap.get(times);
        } finally {
            readLock.unlock();
        }
    }

    public void clear() {
        writeLock.lock();
        try {

            Collection<GramClassifier> gramModels = nameModelMap.values();
            for (GramClassifier gramClassifier : gramModels) {
                gramClassifier.close();
            }

            timeModelMap.clear();
            nameModelMap.clear();
        } finally {
            writeLock.unlock();
        }
    }


}
