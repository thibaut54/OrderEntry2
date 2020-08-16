package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Instrument.
 */
@Entity
@Table(name = "instrument")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Instrument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "bloomberg_ticker")
    private String bloombergTicker;

    @Column(name = "currency_quotation")
    private String currencyQuotation;

    @Column(name = "isin")
    private String isin;

    @Column(name = "market")
    private String market;

    @OneToMany(mappedBy = "instrument")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Order> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Instrument name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloombergTicker() {
        return bloombergTicker;
    }

    public Instrument bloombergTicker(String bloombergTicker) {
        this.bloombergTicker = bloombergTicker;
        return this;
    }

    public void setBloombergTicker(String bloombergTicker) {
        this.bloombergTicker = bloombergTicker;
    }

    public String getCurrencyQuotation() {
        return currencyQuotation;
    }

    public Instrument currencyQuotation(String currencyQuotation) {
        this.currencyQuotation = currencyQuotation;
        return this;
    }

    public void setCurrencyQuotation(String currencyQuotation) {
        this.currencyQuotation = currencyQuotation;
    }

    public String getIsin() {
        return isin;
    }

    public Instrument isin(String isin) {
        this.isin = isin;
        return this;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getMarket() {
        return market;
    }

    public Instrument market(String market) {
        this.market = market;
        return this;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Instrument orders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Instrument addOrder(Order order) {
        this.orders.add(order);
        order.setInstrument(this);
        return this;
    }

    public Instrument removeOrder(Order order) {
        this.orders.remove(order);
        order.setInstrument(null);
        return this;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Instrument)) {
            return false;
        }
        return id != null && id.equals(((Instrument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Instrument{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", bloombergTicker='" + getBloombergTicker() + "'" +
            ", currencyQuotation='" + getCurrencyQuotation() + "'" +
            ", isin='" + getIsin() + "'" +
            ", market='" + getMarket() + "'" +
            "}";
    }
}
