package it.gov.pagopa.bpd.award_period.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.sia.meda.DummyConfiguration;
import eu.sia.meda.error.config.LocalErrorConfig;
import eu.sia.meda.error.handler.MedaExceptionHandler;
import eu.sia.meda.error.service.impl.LocalErrorManagerServiceImpl;
import it.gov.pagopa.bpd.award_period.assembler.AwardPeriodResourceAssembler;
import it.gov.pagopa.bpd.award_period.connector.jpa.model.AwardPeriod;
import it.gov.pagopa.bpd.award_period.model.AwardPeriodResource;
import it.gov.pagopa.bpd.award_period.service.AwardPeriodService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@WebMvcTest(value = BpdAwardPeriodControllerImpl.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {
        BpdAwardPeriodControllerImpl.class,
        DummyConfiguration.class,
        MedaExceptionHandler.class,
        LocalErrorManagerServiceImpl.class
})
@Import(LocalErrorConfig.class)
@TestPropertySource(properties = {
        "error-manager.enabled=true",
        "spring.application.name=bpd-ms-award-period-api-rest"
})
public class BpdAwardPeriodControllerImplTest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private AwardPeriodService awardPeriodServiceMock;
    @SpyBean
    private AwardPeriodResourceAssembler awardPeriodResourceAssemblerMock;

    @PostConstruct
    public void configureTest() {
        AwardPeriod foundAwardPeriod = new AwardPeriod();
        foundAwardPeriod.setAwardPeriodId(0L);
        foundAwardPeriod.setStartDate(LocalDate.now().minusDays(1));
        foundAwardPeriod.setEndDate(LocalDate.now().plusDays(1));
        foundAwardPeriod.setGracePeriod(new Long(5));

        AwardPeriod awardPeriod = new AwardPeriod();
        awardPeriod.setAwardPeriodId(0L);
        awardPeriod.setStartDate(LocalDate.now().minusDays(1));
        awardPeriod.setEndDate(LocalDate.now().plusDays(1));
        awardPeriod.setGracePeriod(new Long(5));
        List<AwardPeriod> awardPeriodList = new ArrayList<>();
        awardPeriodList.add(awardPeriod);

        doReturn(foundAwardPeriod).when(awardPeriodServiceMock).find(eq(0L));
        doReturn(awardPeriodList).when(awardPeriodServiceMock).findAll(Mockito.any());
        doReturn(awardPeriodList).when(awardPeriodServiceMock).findActiveAwardPeriods();

    }

    @Test
    public void find() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/bpd/award-periods/0")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
        AwardPeriodResource awardPeriodResource = objectMapper.readValue(result.getResponse().getContentAsString(),
                AwardPeriodResource.class);
        assertNotNull(awardPeriodResource);
        verify(awardPeriodServiceMock).find(eq(0L));
        verify(awardPeriodResourceAssemblerMock).toResource(any(AwardPeriod.class), any());
    }

    @Test
    public void find_nullCase() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/bpd/award-periods/null")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();

        Mockito.verifyZeroInteractions(awardPeriodServiceMock, awardPeriodResourceAssemblerMock);
    }

    @Test
    public void findAll() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/bpd/award-periods")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        List<AwardPeriodResource> awardPeriodResourceList = objectMapper.
                readValue(result.getResponse().
                        getContentAsString(), new TypeReference<List<AwardPeriodResource>>() {
                });

        assertNotNull(awardPeriodResourceList);
        awardPeriodResourceList.forEach(awPeriod -> verify(awardPeriodResourceAssemblerMock)
                .toResource(any(AwardPeriod.class), any()));

    }

    @Test
    public void findActiveAwardPeriods() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .get("/bpd/award-periods/actives")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        List<AwardPeriodResource> awardPeriodResourceList = objectMapper.
                readValue(result.getResponse().
                        getContentAsString(), new TypeReference<List<AwardPeriodResource>>() {
                });

        assertNotNull(awardPeriodResourceList);
        awardPeriodResourceList.forEach(awPeriod -> verify(awardPeriodResourceAssemblerMock)
                .toResource(any(AwardPeriod.class), any()));

    }

}