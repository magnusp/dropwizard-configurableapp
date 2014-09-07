package com.example.configurableapp;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.example.configurableapp.resources.MyResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by magnus on 2014-09-05.
 */
public class TheApplication extends Application<AppConfiguration> {
    public static void main(String[] args) throws Exception {
        new TheApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zk = configuration.getZookeeperFactory().build(environment);
        /*byte[] stuff = zk.getData("/zk-demo", false, null);
        String foo = new String(stuff);*/
        StringFilterService sfs = new StringFilterService();
        sfs.setZookeeper(zk);
        final MyResource resource = new MyResource(sfs);
        environment.jersey().register(resource);
    }

}
