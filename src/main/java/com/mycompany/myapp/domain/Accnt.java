package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Accnt.
 */
@Entity
@Table(name = "accnt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Accnt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

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

    @ManyToOne
    @JsonIgnoreProperties(value = "accnts", allowSetters = true)
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Accnt accountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getField1() {
        return field1;
    }

    public Accnt field1(String field1) {
        this.field1 = field1;
        return this;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public Accnt field2(String field2) {
        this.field2 = field2;
        return this;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public Accnt field3(String field3) {
        this.field3 = field3;
        return this;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public Accnt field4(String field4) {
        this.field4 = field4;
        return this;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public String getField5() {
        return field5;
    }

    public Accnt field5(String field5) {
        this.field5 = field5;
        return this;
    }

    public void setField5(String field5) {
        this.field5 = field5;
    }

    public Order getOrder() {
        return order;
    }

    public Accnt order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Accnt)) {
            return false;
        }
        return id != null && id.equals(((Accnt) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Accnt{" +
            "id=" + getId() +
            ", accountId=" + getAccountId() +
            ", field1='" + getField1() + "'" +
            ", field2='" + getField2() + "'" +
            ", field3='" + getField3() + "'" +
            ", field4='" + getField4() + "'" +
            ", field5='" + getField5() + "'" +
            "}";
    }
}
