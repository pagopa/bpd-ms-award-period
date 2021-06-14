package it.gov.pagopa.bpd.award_period.controller;

import it.gov.pagopa.bpd.award_period.Prova;
import org.junit.Assert;
import org.junit.Test;

public class ProvaTest {

    @Test
    public void test(){
        Prova p = new Prova();
        p.test();
        Assert.assertTrue(Boolean.TRUE);
    }
}
