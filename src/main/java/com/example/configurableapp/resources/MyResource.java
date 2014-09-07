package com.example.configurableapp.resources;

/**
 * Created by magnus on 2014-09-05.
 */

import com.codahale.metrics.annotation.Timed;
import com.example.configurableapp.StringFilterService;
import com.example.configurableapp.TheApplication;
import com.example.configurableapp.core.Saying;
import com.google.common.base.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class MyResource {
    private final AtomicLong counter;
    private final StringFilterService sfs;

    public MyResource(StringFilterService sfs) {
        this.counter = new AtomicLong();
        this.sfs = sfs;
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        return new Saying(counter.incrementAndGet(), sfs.filter(name.or("<No name>")));
    }
}