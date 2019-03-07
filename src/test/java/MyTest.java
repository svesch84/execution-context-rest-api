import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.cloud.dataflow.rest.client.support.ExecutionContextJacksonMixIn;
import org.springframework.cloud.dataflow.rest.client.support.ExitStatusJacksonMixIn;
import org.springframework.cloud.dataflow.rest.client.support.StepExecutionJacksonMixIn;
import org.springframework.cloud.dataflow.rest.resource.StepExecutionProgressInfoResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MyTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void name() throws IOException {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.addMixIn(StepExecution.class, StepExecutionJacksonMixIn.class);
        objectMapper.addMixIn(ExitStatus.class, ExitStatusJacksonMixIn.class);
        objectMapper.addMixIn(ExecutionContext.class, ExecutionContextJacksonMixIn.class);

        InputStream resourceAsStream = MyTest.class.getResourceAsStream("/response.json");
        StepExecutionProgressInfoResource resource = objectMapper.readValue(resourceAsStream, StepExecutionProgressInfoResource.class);
        Assert.assertEquals(3, resource.getStepExecution().getExecutionContext().size());
    }
}
