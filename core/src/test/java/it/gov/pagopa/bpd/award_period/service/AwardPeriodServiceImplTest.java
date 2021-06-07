package it.gov.pagopa.bpd.award_period.service;

import it.gov.pagopa.bpd.award_period.connector.jpa.AwardPeriodDAO;
import it.gov.pagopa.bpd.award_period.connector.jpa.model.AwardPeriod;
import it.gov.pagopa.bpd.award_period.exception.AwardPeriodNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AwardPeriodServiceImpl.class)
public class AwardPeriodServiceImplTest {

    @MockBean
    private AwardPeriodDAO awardPeriodDAOMock;

    @Autowired
    private AwardPeriodServiceImpl awardPeriodService;

    private static final Long EXISTING_AWARD_PERIOD = 1L;
    private static final Long NOT_EXISTING_AWARD_PERIOD = 0L;


    @PostConstruct
    public void configureTests() {

        when(awardPeriodDAOMock.findById(anyLong()))
                .thenAnswer(invocation -> {
                    Long awardPeriodId = invocation.getArgument(0, Long.class);
                    Optional<AwardPeriod> result = Optional.empty();
                    if (EXISTING_AWARD_PERIOD.equals(awardPeriodId)) {
                        AwardPeriod awardPeriod = new AwardPeriod();
                        awardPeriod.setAwardPeriodId(awardPeriodId);
                        awardPeriod.setStartDate(LocalDate.now().minusDays(1));
                        awardPeriod.setEndDate(LocalDate.now().plusDays(1));
                        result = Optional.of(awardPeriod);
                    }
                    return result;
                });

        when(awardPeriodDAOMock.findAll())
                .thenAnswer(invocation -> {
                    AwardPeriod awp = new AwardPeriod();
                    awp.setStartDate(LocalDate.now().minusDays(1));
                    awp.setEndDate(LocalDate.now().plusDays(1));
                    final List<AwardPeriod> results = new ArrayList<>();
                    results.add(awp);
                    return results;
                });

        when(awardPeriodDAOMock.findAll(Mockito.any(Specification.class)))
                .thenAnswer(invocation -> {
                    AwardPeriod awp = new AwardPeriod();
                    awp.setStartDate(LocalDate.now().minusDays(1));
                    awp.setEndDate(LocalDate.now().plusDays(1));
                    final List<AwardPeriod> results = new ArrayList<AwardPeriod>();
                    results.add(awp);
                    return results;
                });

        when(awardPeriodDAOMock.findActiveAwardPeriods())
                .thenAnswer(invocation -> {
                    AwardPeriod awp = new AwardPeriod();
                    awp.setStartDate(LocalDate.now().minusDays(1));
                    awp.setEndDate(LocalDate.now().plusDays(1));
                    final List<AwardPeriod> results = new ArrayList<>();
                    results.add(awp);
                    return results;
                });


    }


    @Test
    public void find() {

        final AwardPeriod found = awardPeriodService.find(EXISTING_AWARD_PERIOD);

        verify(awardPeriodDAOMock, only()).findById(eq(EXISTING_AWARD_PERIOD));
        verify(awardPeriodDAOMock, times(1)).findById(eq(EXISTING_AWARD_PERIOD));
        assertEquals(EXISTING_AWARD_PERIOD, found.getAwardPeriodId());
        assertNotNull(found.getAwardPeriodId());
    }

    @Test(expected = AwardPeriodNotFoundException.class)
    public void find_KO() {

        final AwardPeriod found = awardPeriodService.find(NOT_EXISTING_AWARD_PERIOD);

        verify(awardPeriodDAOMock, only()).findById(eq(NOT_EXISTING_AWARD_PERIOD));
        verify(awardPeriodDAOMock, times(1)).findById(eq(NOT_EXISTING_AWARD_PERIOD));
    }

    @Test
    public void findAll_offsetDateNotSpecified() {

        final List<AwardPeriod> founds = awardPeriodService.findAll(null);

        verify(awardPeriodDAOMock, only()).findAll();
        verify(awardPeriodDAOMock, times(1)).findAll();
        assertNotNull(founds);
    }

    @Test
    public void findAll_offsetDateSpecified() {

        final List<AwardPeriod> founds = awardPeriodService.findAll(LocalDate.now());

        verify(awardPeriodDAOMock, only()).findAll(Mockito.any(Specification.class));
        verify(awardPeriodDAOMock, times(1)).findAll(Mockito.any(Specification.class));
        assertNotNull(founds);
    }

    @Test
    public void findActiveAwardPeriods() {
        final List<AwardPeriod> founds = awardPeriodService.findActiveAwardPeriods();

        verify(awardPeriodDAOMock, only()).findActiveAwardPeriods();
        verify(awardPeriodDAOMock, times(1)).findActiveAwardPeriods();
        assertNotNull(founds);
    }


}