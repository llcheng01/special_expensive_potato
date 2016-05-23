package net.hereisjohnny.webservice.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by gomez on 5/23/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    @JsonProperty("is_orderable")
    private boolean isOrderable;
    @JsonProperty("is_sellable")
    private boolean isSellable;
    @JsonProperty("general_description")
    private String generalDescription;
    @JsonProperty("online_description")
    private OnlineDescription onlineDescription;

    public Item() {
    }

    public boolean isOrderable() {
        return isOrderable;
    }

    public void setOrderable(boolean orderable) {
        isOrderable = orderable;
    }

    public boolean isSellable() {
        return isSellable;
    }

    public void setSellable(boolean sellable) {
        isSellable = sellable;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    public OnlineDescription getOnlineDescription() {
        return onlineDescription;
    }

    public void setOnlineDescription(OnlineDescription onlineDescription) {
        this.onlineDescription = onlineDescription;
    }

    @Override
    public String toString() {
        return "Item{" +
                "isOrderable=" + isOrderable +
                ", isSellable=" + isSellable +
                ", generalDescription='" + generalDescription + '\'' +
                ", onlineDescription=" + onlineDescription.getValue() +
                '}';
    }
}
