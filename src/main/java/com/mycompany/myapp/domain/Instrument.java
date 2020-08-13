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

    @Column(name = "instrument_id")
    private Long instrumentId;

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

    public Long getInstrumentId() {
        return instrumentId;
    }

    public Instrument instrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
        return this;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getField1() {
        return field1;
    }

    public Instrument field1(String field1) {
        this.field1 = field1;
        return this;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public Instrument field2(String field2) {
        this.field2 = field2;
        return this;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public Instrument field3(String field3) {
        this.field3 = field3;
        return this;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public Instrument field4(String field4) {
        this.field4 = field4;
        return this;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public String getField5() {
        return field5;
    }

    public Instrument field5(String field5) {
        this.field5 = field5;
        return this;
    }

    public void setField5(String field5) {
        this.field5 = field5;
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
            ", instrumentId=" + getInstrumentId() +
            ", field1='" + getField1() + "'" +
            ", field2='" + getField2() + "'" +
            ", field3='" + getField3() + "'" +
            ", field4='" + getField4() + "'" +
            ", field5='" + getField5() + "'" +
            "}";
    }
}
