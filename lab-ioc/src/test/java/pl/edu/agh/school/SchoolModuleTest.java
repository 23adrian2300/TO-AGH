package pl.edu.agh.school;

import com.google.inject.Guice;
import org.junit.jupiter.api.Test;
import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.demo.SchoolModule;

import static org.junit.jupiter.api.Assertions.assertSame;

public class SchoolModuleTest {

    @Test
    public void loggerIsSingleton() {
        var injector = Guice.createInjector(new SchoolModule());
        final Logger logger1 = injector.getInstance(Logger.class);
        final Logger logger2 = injector.getInstance(Logger.class);

        assertSame(logger1, logger2);
    }
}
