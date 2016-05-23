package net.hereisjohnny.webservice.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by gomez on 5/23/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnlineDescription {
    private String value;
    private String type;

    public OnlineDescription() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OnlineDescription{" +
                "value='" + value + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
