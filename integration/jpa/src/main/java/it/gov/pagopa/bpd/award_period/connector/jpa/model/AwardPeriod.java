package it.gov.pagopa.bpd.award_period.connector.jpa.model;

import it.gov.pagopa.bpd.common.connector.jpa.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"awardPeriodId"}, callSuper = false)
@Table(name = "bpd_award_period")
@Where(clause = "ENABLED_B = 'TRUE'")
@Cacheable
public class AwardPeriod extends BaseEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "id", sequenceName = "bpd_award_period_award_period_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    @Column(name = "award_period_id_n")
    private Long awardPeriodId;

    @Column(name = "aw_period_start_d")
    private LocalDate startDate;

    @Column(name = "aw_period_end_d")
    private LocalDate endDate;

    @Column(name = "aw_grace_period_n")
    private Long gracePeriod;

    @Column(name = "trx_volume_min_n")
    private Long minTransactionNumber;

    @Column(name = "amount_max_n")
    private Long maxAmount;

    @Column(name = "ranking_min_n")
    private Long minPosition;

    @Column(name = "trx_cashback_max_n")
    private Long maxTransactionCashback;

    @Column(name = "period_cashback_max_n")
    private Long maxPeriodCashback;

    @Column(name = "cashback_perc_n")
    private Long cashbackPercentage;

    @Column(name = "trx_eval_max_n")
    private Long maxTransactionEvaluated;

}
