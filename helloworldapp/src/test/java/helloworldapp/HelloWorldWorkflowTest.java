package helloworldapp;

import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowRule;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Use JUnite to test the workflow
 */
public class HelloWorldWorkflowTest {

    @Rule
    public TestWorkflowRule testWorkflowRule =
        TestWorkflowRule.newBuilder()
            .setWorkflowTypes(HelloWorldWorkflowImpl.class)
            .setDoNotStart(true)
            .build();

    @Test
    public void testGetGreeting() {
        // Use rule just as a true workflow
        testWorkflowRule.getWorker().registerActivitiesImplementations(new FormatImpl());
        testWorkflowRule.getTestEnvironment().start();

        HelloWorldWorkflow workflow =
            testWorkflowRule.getWorkflowClient()
                .newWorkflowStub(
                    HelloWorldWorkflow.class,
                    WorkflowOptions.newBuilder().setTaskQueue(testWorkflowRule.getTaskQueue()).build()
                );
        // Test if the workflow could execute the getGreeting() activity correctly
        String greeting = workflow.getGreeting("World");
        assertEquals("Hello World made by zwu!", greeting);
        testWorkflowRule.getTestEnvironment().shutdown();
    }

    @Test
    public void testMockedGetGreeting() {
        Format formatActivities = mock(Format.class, withSettings().withoutAnnotations());
        when(formatActivities.composeGreeting(anyString())).thenReturn("Hello Mock World made by zwu!");

        testWorkflowRule.getWorker().registerActivitiesImplementations(formatActivities);
        testWorkflowRule.getTestEnvironment().start();

        HelloWorldWorkflow workflow =
            testWorkflowRule.getWorkflowClient()
                            .newWorkflowStub(
                                HelloWorldWorkflow.class,
                                WorkflowOptions.newBuilder().setTaskQueue(testWorkflowRule.getTaskQueue()).build()
                            );
        String greeting = workflow.getGreeting("Mock World");
        assertEquals("Hello Mock World made by zwu!", greeting);
        testWorkflowRule.getTestEnvironment().shutdown();




    }


}
