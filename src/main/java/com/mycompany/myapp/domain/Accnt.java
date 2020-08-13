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

    @Column(name = "acct_cd")
    private String acctCd;

    @Column(name = "acct_name")
    private String acctName;

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

    public String getAcctCd() {
        return acctCd;
    }

    public Accnt acctCd(String acctCd) {
        this.acctCd = acctCd;
        return this;
    }

    public void setAcctCd(String acctCd) {
        this.acctCd = acctCd;
    }

    public String getAcctName() {
        return acctName;
    }

    public Accnt acctName(String acctName) {
        this.acctName = acctName;
        return this;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
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
            ", acctCd='" + getAcctCd() + "'" +
            ", acctName='" + getAcctName() + "'" +
            "}";
    }
}
