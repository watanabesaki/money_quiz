package com.example.nttr.money;

/**
 * Created by nttr on 2018/02/19.
 */

public class PaymentHistory {
    String name;
    int price;
    String imageName;

    String getImageUrl() {
        return "https://mb.api.cloud.nifty.com/2013-09-01/applications/SXgf2aDUL7UuNVkK/publicFiles/" + imageName;
    }
}
