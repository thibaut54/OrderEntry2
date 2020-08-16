package com.mycompany.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Instrument} entity.
 */
public class InstrumentDTO implements Serializable {
    
    private Long id;

    private String name;

    private String bloombergTicker;

    private String currencyQuotation;

    private String isin;

    private String market;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloombergTicker() {
        return bloombergTicker;
    }

    public void setBloombergTicker(String bloombergTicker) {
        this.bloombergTicker = bloombergTicker;
    }

    public String getCurrencyQuotation() {
        return currencyQuotation;
    }

    public void setCurrencyQuotation(String currencyQuotation) {
        this.currencyQuotation = currencyQuotation;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstrumentDTO)) {
            return false;
        }

        return id != null && id.equals(((InstrumentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstrumentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bloombergTicker='" + getBloombergTicker() + "'" +
            ", currencyQuotation='" + getCurrencyQuotation() + "'" +
            ", isin='" + getIsin() + "'" +
            ", market='" + getMarket() + "'" +
            "}";
    }
}
