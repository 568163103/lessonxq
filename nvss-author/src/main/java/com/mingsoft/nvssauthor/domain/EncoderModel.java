package com.mingsoft.nvssauthor.domain;

public class EncoderModel {
    /**
    * model, letter and number are permitted
    */
    private String model;

    /**
    * provider name
    */
    private String provider;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}