package telran.pulse.monitoring;

import java.time.*;
import java.util.*;
import java.util.logging.*;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import static telran.pulse.monitoring.Constants.*;

public class App {
    static String topicARN;
    static String awsRegion;
    static SnsClient snsClient;
    static Logger logger = Logger.getLogger(LOGGER_ABNORMAL_VALUES_NOTIFIER_NAME);
    static {
        logger = Functions.loggerSetUp(logger);
        setUpEnvironment();
        setUpSnsClient();
    }

    public void handleRequest(DynamodbEvent event, Context context) {
        event.getRecords().forEach(r -> {
            Map<String, AttributeValue> image = r.getDynamodb().getNewImage();
            if (image == null) {
                logger.warning("No new image found");
            } else if (r.getEventName().equals(INSERT_EVENT_NAME)) {
                String message = getMessage(image);
                logger.finer("message is " + message);
                publishMessage(message);
            } else {
                logger.warning(r.getEventName() + " event name but should be " + INSERT_EVENT_NAME);
            }
        });
    }

    private void publishMessage(String message) {
        PublishRequest request = PublishRequest.builder()
                .message(message)
                .topicArn(topicARN)
                .build();
        snsClient.publish(request);
        logger.fine("Message published to SNS");
    }

    private String getMessage(Map<String, AttributeValue> image) {
        return String.format("patient %s\nabnormal pulse value %s\ndate-time %s",
                image.get(PATIENT_ID_ATTRIBUTE).getN(), 
                image.get(VALUE_ATTRIBUTE).getN(),
                getDateTime(image.get(TIMESTAMP_ATTRIBUTE).getN()));
    }

    private Object getDateTime(String timestampStr) {
        long timestamp = Long.parseLong(timestampStr);
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return res;
    }

    private static void setUpSnsClient() {
        snsClient = SnsClient.builder().region(Region.of(awsRegion)).build();
    }

    private static void setUpEnvironment() {
        Map<String, String> env = System.getenv();
        topicARN = env.get(TOPIC_ARN_ENV_VARIABLE);
        if (topicARN == null) {
            String errorStr = TOPIC_ARN_ENV_VARIABLE + " not found";
            logger.severe(errorStr);
            throw new NoSuchElementException(errorStr);
        }
        logger.config(TOPIC_ARN_ENV_VARIABLE + " is " + topicARN);
        awsRegion = env.getOrDefault(REGION_ENV_VARIABLE, DEFAULT_REGION_VALUE);
        logger.config(REGION_ENV_VARIABLE + " is " + awsRegion);
    }
}