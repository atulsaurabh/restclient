/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Suyojan
 */
@Entity
@Table(name = "dutcomponents")
@NamedQueries({
    @NamedQuery(name = "Dutcomponents.findAll", query = "SELECT d FROM Dutcomponents d")})
public class Dutcomponents implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "order_code", nullable = false, length = 45)
    private String orderCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "serial_number", nullable = false, length = 45)
    private String serialNumber;
    @JoinColumn(name = "testrecord_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Testrecord testrecordId;

    public Dutcomponents() {
    }

    public Dutcomponents(Long id) {
        this.id = id;
    }

    public Dutcomponents(Long id, String orderCode, String serialNumber) {
        this.id = id;
        this.orderCode = orderCode;
        this.serialNumber = serialNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Testrecord getTestrecordId() {
        return testrecordId;
    }

    public void setTestrecordId(Testrecord testrecordId) {
        this.testrecordId = testrecordId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dutcomponents)) {
            return false;
        }
        Dutcomponents other = (Dutcomponents) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.suyojan.abbrest.Dutcomponents[ id=" + id + " ]";
    }
    
}
