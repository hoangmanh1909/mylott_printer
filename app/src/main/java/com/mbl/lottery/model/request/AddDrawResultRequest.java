package com.mbl.lottery.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddDrawResultRequest {
    @SerializedName("ProductID")
    @Expose
    private int productID;
    @SerializedName("Result")
    @Expose
    private Object result;
    @SerializedName("JackpotMin01")
    @Expose
    private int jackpotMin01;
    @SerializedName("Jackpot01")
    @Expose
    private int jackpot01;
    @SerializedName("JackpotMin02")
    @Expose
    private int jackpotMin02;
    @SerializedName("Jackpot02")
    @Expose
    private int jackpot02;
    @SerializedName("Bonus")
    @Expose
    private Object bonus;

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getJackpotMin01() {
        return jackpotMin01;
    }

    public void setJackpotMin01(int jackpotMin01) {
        this.jackpotMin01 = jackpotMin01;
    }

    public int getJackpot01() {
        return jackpot01;
    }

    public void setJackpot01(int jackpot01) {
        this.jackpot01 = jackpot01;
    }

    public int getJackpotMin02() {
        return jackpotMin02;
    }

    public void setJackpotMin02(int jackpotMin02) {
        this.jackpotMin02 = jackpotMin02;
    }

    public int getJackpot02() {
        return jackpot02;
    }

    public void setJackpot02(int jackpot02) {
        this.jackpot02 = jackpot02;
    }

    public Object getBonus() {
        return bonus;
    }

    public void setBonus(Object bonus) {
        this.bonus = bonus;
    }
}
