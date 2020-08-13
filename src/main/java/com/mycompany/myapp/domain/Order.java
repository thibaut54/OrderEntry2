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

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "field_1")
    private String field1;

    @Column(name = "field_2")
    private String field2;

    @Column(name = "field_3")
    private String field3;

    @Column(name = "field_4")
    private String field4;

    @Column(name = "field_5")
    private String field5;

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

    public Long getOrderId() {
        return orderId;
    }

    public Order orderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getField1() {
        return field1;
    }

    public Order field1(String field1) {
        this.field1 = field1;
        return this;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public Order field2(String field2) {
        this.field2 = field2;
        return this;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public Order field3(String field3) {
        this.field3 = field3;
        return this;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public Order field4(String field4) {
        this.field4 = field4;
        return this;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public String getField5() {
        return field5;
    }

    public Order field5(String field5) {
        this.field5 = field5;
        return this;
    }

    public void setField5(String field5) {
        this.field5 = field5;
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
            ", orderId=" + getOrderId() +
            ", field1='" + getField1() + "'" +
            ", field2='" + getField2() + "'" +
            ", field3='" + getField3() + "'" +
            ", field4='" + getField4() + "'" +
            ", field5='" + getField5() + "'" +
            "}";
    }
}
