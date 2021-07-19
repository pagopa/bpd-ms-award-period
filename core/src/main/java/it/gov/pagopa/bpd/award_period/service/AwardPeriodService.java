package it.gov.pagopa.bpd.award_period.service;

import it.gov.pagopa.bpd.award_period.connector.jpa.model.AwardPeriod;

import java.time.LocalDate;
import java.util.List;

/**
 * A service to manage the Business Logic related to AwardPeriod
 */
public interface AwardPeriodService {

    AwardPeriod find(Long awardPeriodId);

    List<AwardPeriod> findAll(LocalDate offsetDate);

    List<AwardPeriod> findActiveAwardPeriods();

}
