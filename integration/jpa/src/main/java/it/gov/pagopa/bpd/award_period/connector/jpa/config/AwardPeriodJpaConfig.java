package it.gov.pagopa.bpd.award_period.connector.jpa.config;

import it.gov.pagopa.bpd.common.connector.jpa.config.BaseJpaConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/AwardPeriodJpaConnectionConfig.properties")
public class AwardPeriodJpaConfig extends BaseJpaConfig {
}
