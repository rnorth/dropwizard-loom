# dropwizard-loom

A quick and dirty experiment running Dropwizard on an early-access build of [Project Loom](https://jdk.java.net/loom/).

This is incomplete and not a representative test at present.

Key points:

* Dropwizard, as a framework built atop Jetty, can be trivially made to use Fibers instead of Threads for servicing requests. See [FiberServerFactory](src/main/java/example/FiberServerFactory.java) and [FiberBackedThreadPool](src/main/java/example/FiberBackedThreadPool.java). The latter is almost entirely lifted from [this mailing list post](https://mail.openjdk.java.net/pipermail/loom-dev/2019-September/000696.html).
* Fibers seem to give significant performance improvements (especially tail latency).
* As expected, Loom is early access and some crashes have been observed.

## Building

Build inside a Docker image that includes the Loom variant of JDK14: 

```shell script
$ docker build -t dropwizard-loom .
```

## Running

```shell script
$ docker run -p 8080:8080 dropwizard-loom
```

## Testing performance

### Configuration

Whether or not we're using Fibers or Threads depends upon configuration. To switch, edit `config.yml`.

Set `server.type` to `default` to use Threads, or `fiber` to use Fibers.

After changing configuration, the image should be rebuilt.

### Testing performance

Use `slow-cooker` to generate load, e.g.:

```shell script
$ docker run --net host -it buoyantio/slow_cooker -qps 10 -concurrency 100 http://localhost:8080/
```

Slow cooker does not ramp up traffic slowly, so it is advised not to start with high (e.g. 1000+) concurrency and instead increase concurrency in steps.

## TODOs

* Switch away from using `gradle run` to run the service, as this is not a realistic way to run a service
* Set up repeatable test harness for running performance trials and measuring JVM stats
* Set up more representative tests than just current ping-pong endpoint
* Write up observed behaviour - including performance improvements and crashes