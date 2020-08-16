package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "order_initiator")
    private String orderInitiator;

    @Column(name = "side_combo")
    private String sideCombo;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "good_till")
    private String goodTill;

    @Column(name = "trading_instruction")
    private String tradingInstruction;

    @OneToMany(mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Accnt> accnts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Instrument instrument;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderInitiator() {
        return orderInitiator;
    }

    public Order orderInitiator(String orderInitiator) {
        this.orderInitiator = orderInitiator;
        return this;
    }

    public void setOrderInitiator(String orderInitiator) {
        this.orderInitiator = orderInitiator;
    }

    public String getSideCombo() {
        return sideCombo;
    }

    public Order sideCombo(String sideCombo) {
        this.sideCombo = sideCombo;
        return this;
    }

    public void setSideCombo(String sideCombo) {
        this.sideCombo = sideCombo;
    }

    public String getQuantity() {
        return quantity;
    }

    public Order quantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderType() {
        return orderType;
    }

    public Order orderType(String orderType) {
        this.orderType = orderType;
        return this;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getGoodTill() {
        return goodTill;
    }

    public Order goodTill(String goodTill) {
        this.goodTill = goodTill;
        return this;
    }

    public void setGoodTill(String goodTill) {
        this.goodTill = goodTill;
    }

    public String getTradingInstruction() {
        return tradingInstruction;
    }

    public Order tradingInstruction(String tradingInstruction) {
        this.tradingInstruction = tradingInstruction;
        return this;
    }

    public void setTradingInstruction(String tradingInstruction) {
        this.tradingInstruction = tradingInstruction;
    }

    public Set<Accnt> getAccnts() {
        return accnts;
    }

    public Order accnts(Set<Accnt> accnts) {
        this.accnts = accnts;
        return this;
    }

    public Order addAccnt(Accnt accnt) {
        this.accnts.add(accnt);
        accnt.setOrder(this);
        return this;
    }

    public Order removeAccnt(Accnt accnt) {
        this.accnts.remove(accnt);
        accnt.setOrder(null);
        return this;
    }

    public void setAccnts(Set<Accnt> accnts) {
        this.accnts = accnts;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Order instrument(Instrument instrument) {
        this.instrument = instrument;
        return this;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", orderInitiator='" + getOrderInitiator() + "'" +
            ", sideCombo='" + getSideCombo() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", orderType='" + getOrderType() + "'" +
            ", goodTill='" + getGoodTill() + "'" +
            ", tradingInstruction='" + getTradingInstruction() + "'" +
            "}";
    }
}
