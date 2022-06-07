package helloworldapp;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

/**
 * Interface for the Format Activity Object
 */
@ActivityInterface
public interface Format {
    @ActivityMethod
    String composeGreeting(String name);
}
