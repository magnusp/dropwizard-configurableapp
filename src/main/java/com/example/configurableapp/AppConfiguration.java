package com.example.configurableapp;

import com.example.configurableapp.core.zookeeper.ZooKeeperFactory;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by magnus on 2014-09-05.
 */
public class AppConfiguration extends Configuration {
    private ZooKeeperFactory zookeeper = new ZooKeeperFactory();

    @JsonProperty("zookeeper")
    public ZooKeeperFactory getZookeeperFactory() {
        return zookeeper;
    }

    @JsonProperty("zookeeper")
    public void setZookeeperFactory(ZooKeeperFactory factory) {
        this.zookeeper = factory;
    }

}
