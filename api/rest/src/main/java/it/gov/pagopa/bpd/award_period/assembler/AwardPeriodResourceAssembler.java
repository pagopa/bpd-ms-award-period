package it.gov.pagopa.bpd.award_period.assembler;

import it.gov.pagopa.bpd.award_period.connector.jpa.model.AwardPeriod;
import it.gov.pagopa.bpd.award_period.model.AwardPeriodResource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Mapper between <AwardPeriodServiceModel> class and <AwardPeriodResource> Resource class
 */
@Service
public class AwardPeriodResourceAssembler {

    public AwardPeriodResource toResource(AwardPeriod awardPeriod, Integer version) {
        AwardPeriodResource resource = null;

        if (awardPeriod != null) {
            resource = new AwardPeriodResource();
            BeanUtils.copyProperties(awardPeriod, resource);

            this.defineAndSetStatus(resource,version);
        }
        return resource;
    }

    private void defineAndSetStatus(AwardPeriodResource awardPeriod, Integer version){
        LocalDate startDate = awardPeriod.getStartDate();
        LocalDate endDate = awardPeriod.getEndDate();
        LocalDate currentDate = LocalDate.now();
        if((currentDate.isEqual(startDate)||currentDate.isAfter(startDate)) &&
                (currentDate.isBefore(endDate)||currentDate.isEqual(endDate))){
            awardPeriod.setStatus("ACTIVE");
        } else if (currentDate.isBefore(startDate)) {
            awardPeriod.setStatus("INACTIVE");
        } else if((version!=null && version.intValue()>1) && (currentDate.isAfter(endDate) &&
                (currentDate.equals(endDate.plusDays(awardPeriod.getGracePeriod()))
                        || currentDate.isBefore(endDate.plusDays(awardPeriod.getGracePeriod()))))) {
            awardPeriod.setStatus("WAITING");
        } else {
            awardPeriod.setStatus("CLOSED");
        }
//        return awardPeriod;
    }
}
