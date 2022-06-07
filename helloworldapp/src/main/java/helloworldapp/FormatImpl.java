package helloworldapp;

/**
 * implement the Format Activity Interface
 */
public class FormatImpl implements Format{
    @Override
    public String composeGreeting(String name) {
        return "Hello " + name + " made by zwu!";
    }
}
