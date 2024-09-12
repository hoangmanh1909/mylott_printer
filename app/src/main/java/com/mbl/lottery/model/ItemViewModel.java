package com.mbl.lottery.model;

public class ItemViewModel {
    String value;
    String text;
    boolean isCheck;
    public ItemViewModel(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public ItemViewModel(String value, String text,boolean isCheck) {
        this.value = value;
        this.text = text;
        this.isCheck = isCheck;
    }




    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
