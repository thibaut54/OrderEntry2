package com.mycompany.myapp.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Accnt} entity.
 */
public class AccntDTO implements Serializable {
    
    private Long id;

    private Long accountId;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;


    private Long orderId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5;
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
            ", accountId=" + getAccountId() +
            ", field1='" + getField1() + "'" +
            ", field2='" + getField2() + "'" +
            ", field3='" + getField3() + "'" +
            ", field4='" + getField4() + "'" +
            ", field5='" + getField5() + "'" +
            ", orderId=" + getOrderId() +
            "}";
    }
}
