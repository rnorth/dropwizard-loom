/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package example;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new App().run("server", "config.yml");
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(Configuration configuration,
                    Environment environment) {
        environment.jersey().register(ApiResource.class);
    }
}
