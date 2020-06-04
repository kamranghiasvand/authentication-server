package com.bluebox.security.authenticationserver.persistence.service.sms.meliPayment.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author by kamran ghiasvand
 */
@Setter
@Getter
public class MeliPaymentReq {
    private MeliPaymentReq() {
    }

    private String username;
    private String password;
    private String to;
    private String from;
    private String text;
    private Boolean isFlash = false;

    public static MeliPaymentReqBuilder newBuilder() {
        return new MeliPaymentReqBuilder();
    }

    public static class MeliPaymentReqBuilder {
        private MeliPaymentReq instance;

        public MeliPaymentReqBuilder() {
            instance = new MeliPaymentReq();
        }

        public MeliPaymentReqBuilder username(String username) {
            instance.setUsername(username);
            return this;
        }

        public MeliPaymentReqBuilder password(String password) {
            instance.setPassword(password);
            return this;
        }

        public MeliPaymentReqBuilder to(String to) {
            instance.setTo(to);
            return this;
        }

        public MeliPaymentReqBuilder form(String from) {
            instance.setFrom(from);
            return this;
        }

        public MeliPaymentReqBuilder text(String text) {
            instance.setText(text);
            return this;
        }
        public MeliPaymentReqBuilder isFlash(Boolean isFlash){
            instance.setIsFlash(isFlash);
            return this;
        }
        public MeliPaymentReq build(){
            return instance;
        }
    }
}
