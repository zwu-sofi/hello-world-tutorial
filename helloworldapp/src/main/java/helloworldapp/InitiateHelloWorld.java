package helloworldapp;

import io.temporal.api.workflowservice.v1.WorkflowServiceGrpc.WorkflowServiceStub;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

import java.sql.SQLOutput;

public class InitiateHelloWorld {
    public static void main(String[] args) throws Exception {
//        // local docker option
//        // gRPC stub, and a client that could use the stub to communicate
//        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
//        WorkflowClient client = WorkflowClient.newInstance(service);

        // remote kraken option
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        WorkflowClientOptions clientOptions = WorkflowClientOptions.newBuilder()
                                                                   .setNamespace("Invest").build();
        WorkflowClient client = WorkflowClient.newInstance(service, clientOptions);

        // build the Task Queue for the workflow
        WorkflowOptions options = WorkflowOptions.newBuilder()
            .setTaskQueue(Shared.HELLO_WORLD_TASK_QUEUE)
            .build();
        HelloWorldWorkflow workflow = client.newWorkflowStub(HelloWorldWorkflow.class, options);
        String greeting = workflow.getGreeting("World");
        System.out.println(greeting);
        System.exit(0);
    }
}
