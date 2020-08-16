package com.mycompany.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Order} entity.
 */
public class OrderDTO implements Serializable {
    
    private Long id;

    private String orderInitiator;

    private String sideCombo;

    private String quantity;

    private String orderType;

    private String goodTill;

    private String tradingInstruction;


    private Long instrumentId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderInitiator() {
        return orderInitiator;
    }

    public void setOrderInitiator(String orderInitiator) {
        this.orderInitiator = orderInitiator;
    }

    public String getSideCombo() {
        return sideCombo;
    }

    public void setSideCombo(String sideCombo) {
        this.sideCombo = sideCombo;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getGoodTill() {
        return goodTill;
    }

    public void setGoodTill(String goodTill) {
        this.goodTill = goodTill;
    }

    public String getTradingInstruction() {
        return tradingInstruction;
    }

    public void setTradingInstruction(String tradingInstruction) {
        this.tradingInstruction = tradingInstruction;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        return id != null && id.equals(((OrderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", orderInitiator='" + getOrderInitiator() + "'" +
            ", sideCombo='" + getSideCombo() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", orderType='" + getOrderType() + "'" +
            ", goodTill='" + getGoodTill() + "'" +
            ", tradingInstruction='" + getTradingInstruction() + "'" +
            ", instrumentId=" + getInstrumentId() +
            "}";
    }
}
