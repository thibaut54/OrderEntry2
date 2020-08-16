package com.mycompany.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Accnt} entity.
 */
public class AccntDTO implements Serializable {
    
    private Long id;

    private String acctCd;

    private String acctName;


    private Long orderId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcctCd() {
        return acctCd;
    }

    public void setAcctCd(String acctCd) {
        this.acctCd = acctCd;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccntDTO)) {
            return false;
        }

        return id != null && id.equals(((AccntDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccntDTO{" +
            "id=" + getId() +
            ", acctCd='" + getAcctCd() + "'" +
            ", acctName='" + getAcctName() + "'" +
            ", orderId=" + getOrderId() +
            "}";
    }
}
