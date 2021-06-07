package it.gov.pagopa.bpd.award_period.service;

import eu.sia.meda.service.BaseService;
import it.gov.pagopa.bpd.award_period.connector.jpa.AwardPeriodDAO;
import it.gov.pagopa.bpd.award_period.connector.jpa.model.AwardPeriod;
import it.gov.pagopa.bpd.award_period.exception.AwardPeriodNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @See AwardPeriodService
 */
@Service
class AwardPeriodServiceImpl extends BaseService implements AwardPeriodService {

    private final AwardPeriodDAO awardPeriodDAO;


    static <S extends AwardPeriod> Specification<S> isOffsetDateTimeSpecified(LocalDate offsetDate) {
        return (awardPeriod, cq, cb) ->
                cb.between(cb.literal(offsetDate), awardPeriod.get("startDate"), awardPeriod.get("endDate"));
    }

    @Autowired
    public AwardPeriodServiceImpl(AwardPeriodDAO awardPeriodDAO) {
        this.awardPeriodDAO = awardPeriodDAO;
    }

    @Override
    public AwardPeriod find(Long awardPeriodId) {
        AwardPeriod awardPeriod = awardPeriodDAO.findById(awardPeriodId)
                .orElseThrow(() -> new AwardPeriodNotFoundException(awardPeriodId));

        return awardPeriod;
    }


    @Override
    public List<AwardPeriod> findAll(LocalDate offsetDate) {

        List<AwardPeriod> awardPeriods = offsetDate == null ?
                awardPeriodDAO.findAll() : awardPeriodDAO.findAll(isOffsetDateTimeSpecified(offsetDate));

        return awardPeriods;
    }

    @Override
    public List<AwardPeriod> findActiveAwardPeriods() {

        List<AwardPeriod> awardPeriods = awardPeriodDAO.findActiveAwardPeriods();

        return awardPeriods;
    }
}
