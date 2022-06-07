package helloworldapp;

import io.temporal.api.workflowservice.v1.WorkflowServiceGrpc.WorkflowServiceStub;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.workflow.Workflow;

public class HelloWorldWorker {
    public static void main(String[] args) {
        // 1. Communication (here use gRPC to communicate with local)
        // Initialize a gRPC stubs wrapper talks to the local docker instance of the Temporal Service
        // by default newLocalServiceStubs Creates WorkflowService gRPC stubs pointed on to the locally running Temporal Server. The
        // Server should be available on 127.0.0.1:7233
        // local option
//        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        // remote kraken option
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance();
        WorkflowClientOptions clientOptions = WorkflowClientOptions.newBuilder()
                                                                   .setNamespace("Invest").build();
        WorkflowClient client = WorkflowClient.newInstance(service, clientOptions);
        // 2. Produce worker!
        // Instantiate a worker factory that can be used to create workers that poll specific Task Queues
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(Shared.HELLO_WORLD_TASK_QUEUE);
        // 3. Worker definition
        // This worker should hold both the Workflow and Activity Implementations
        worker.registerWorkflowImplementationTypes(HelloWorldWorkflowImpl.class);
        worker.registerActivitiesImplementations(new FormatImpl());
        // 4. Start polling the Task Queue
        // start() Starts all the workers created by this factory.
        factory.start();
    }
}
