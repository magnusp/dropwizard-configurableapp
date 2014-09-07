package com.example.configurableapp;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by magnus on 2014-09-07.
 */
public class StringFilterService implements Watcher {
    public enum FILTER {NONE, UPPER, LOWER}

    private ZooKeeper zk;
    private FILTER currentFilter;

    @Override
    public void process(WatchedEvent watchedEvent) {
        switch (watchedEvent.getType()) {
            case NodeDataChanged:
                fetchFilterConfiguration();
                break;
        }
    }

    public boolean fetchFilterConfiguration() {
        if(this.zk == null) {
            currentFilter = FILTER.NONE;
            return true;
        }
        try {
            String value = new String(this.zk.getData("/zk-demo", this, null));
            if (value.equals(FILTER.NONE.toString())) {
                currentFilter = FILTER.NONE;
            } else if (value.equals(FILTER.UPPER.toString())) {
                currentFilter = FILTER.UPPER;
            } else if (value.equals(FILTER.LOWER.toString())) {
                currentFilter = FILTER.LOWER;
            } else {
                currentFilter = FILTER.NONE;
            }
            return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setZookeeper(ZooKeeper zk) throws KeeperException, InterruptedException {
        this.zk = zk;
        this.zk.exists("/zk-demo", this);
    }

    public String filter(String input) {
        if (currentFilter == null) {
            fetchFilterConfiguration();
        }
        if (currentFilter == FILTER.NONE) {
            return input;
        } else if (currentFilter == FILTER.LOWER) {
            return input.toLowerCase();
        } else if (currentFilter == FILTER.UPPER) {
            return input.toUpperCase();
        } else {
            return input;
        }
    }
}
