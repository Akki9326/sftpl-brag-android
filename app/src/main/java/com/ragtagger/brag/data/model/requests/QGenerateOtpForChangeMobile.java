package com.ragtagger.brag.data.model.requests;

/**
 * Created by alpesh.rathod on 3/8/2018.
 */

public class QGenerateOtpForChangeMobile {

    private String mobileNumber;
    private String password;

    public QGenerateOtpForChangeMobile(String mobileNumber, String password) {
        this.mobileNumber = mobileNumber;
        this.password = password;
    }
}
