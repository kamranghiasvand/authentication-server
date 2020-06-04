package com.bluebox.security.authenticationserver.persistence.service.sms.meliPayment.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author by kamran ghiasvand
 */
@Getter
@Setter
public class MeliPaymentResp {
    private String value;
    private int retStatus;
    private String strRetStatus;
}
