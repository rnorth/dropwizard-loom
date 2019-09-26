package example;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.dropwizard.server.DefaultServerFactory;
import org.eclipse.jetty.util.thread.ThreadPool;

@JsonTypeName("fiber")
public class FiberServerFactory extends DefaultServerFactory {

    @Override
    protected ThreadPool createThreadPool(MetricRegistry metricRegistry) {
        return new FiberBackedThreadPool();
    }
}
