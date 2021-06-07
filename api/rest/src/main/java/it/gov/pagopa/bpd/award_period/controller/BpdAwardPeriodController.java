package it.gov.pagopa.bpd.award_period.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import it.gov.pagopa.bpd.award_period.model.AwardPeriodResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Controller to expose MicroService
 */
@Api(tags = "Bonus Pagamenti Digitali award-period Controller")
@RequestMapping("/bpd/award-periods")
public interface BpdAwardPeriodController {

    @GetMapping(value = "/{awardPeriodId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    AwardPeriodResource find(
            @PathVariable("awardPeriodId")
            @ApiParam(value = "${swagger.awardPeriod.awardPeriodId}", required = true)
            @NotNull
                    Long awardPeriodId
    );

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AwardPeriodResource> findAll(@RequestParam(value = "offsetDateTime", required = false)
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime offsetDateTime,
                                      @RequestParam(value="version", required=false, defaultValue = "1")
                                      Integer version
    );


    @GetMapping(value = "/actives", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<AwardPeriodResource> findActiveAwardPeriods();
}
