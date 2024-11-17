package telran.pulse.monitoring;

public interface Constants {
    String PATIENT_ID_ATTRIBUTE = "patientId";
    String TIMESTAMP_ATTRIBUTE = "timestamp";
    String VALUE_ATTRIBUTE = "value";
    String PREVIOUS_VALUE_ATTRIBUTE = "previousValue";
    String CURRENT_VALUE_ATTRIBUTE = "currentValue";

    String PULSE_JUMPS_TABLE_NAME = "pulse_jumps_values";
    String LAST_PULSE_VALUES_TABLE_NAME = "last_pulse_values";
    String ABNORMAL_VALUES_TABLE_NAME = "pulse_abnormal_values";

    String LOGGER_LEVEL_ENV_VARIABLE = "LOGGER_LEVEL";
    String DEFAULT_LOGGER_LEVEL = "INFO";
    String FACTOR_ENV_VARIABLE = "FACTOR";
    float DEFAULT_FACTOR_VALUE = 0.2f;
    String BASE_URL_ENV_NAME="BASE_URL";
    String TOPIC_ARN_ENV_VARIABLE = "TOPIC_ARN";
    String REGION_ENV_VARIABLE = "REGION";
    String DEFAULT_REGION_VALUE = "us-east-1";

    String MIN_FIELD_NAME = "min";
    String MAX_FIELD_NAME = "max";
    String ERROR_NAME = "error";

    String LOGGER_PULSE_JUMP_ANALYZER_NAME = "pulse-jump-analyzer";
    String LOGGER_PULSE_VALUE_ANALYZER_NAME = "pulse-value-analyzer";
    String LOGGER_ABNORMAL_VALUES_NOTIFIER_NAME = "abnormal-values-notifier";
    String INSERT_EVENT_NAME = "INSERT";
}
