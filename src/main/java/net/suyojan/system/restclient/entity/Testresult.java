/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Suyojan
 */
@Entity
@Table(name = "testresult")
@NamedQueries({
    @NamedQuery(name = "Testresult.findAll", query = "SELECT t FROM Testresult t")})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Testresult implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
 //  @JsonIgnore
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "param_name", nullable = false, length = 30)
    private String paramName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "param_value", nullable = false, precision = 12, scale = 5)
    private BigDecimal paramValue;
    @JoinColumn(name = "test_record_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Testrecord testRecordId;
    @OneToMany(mappedBy = "testResultId")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Collection<AppActions> appActionsCollection;
    
    @Transient
    private Long oldid;
    
    public Testresult() {
    }

    public Testresult(Long id) {
        this.id = id;
    }

    public Long getOldid() {
        return oldid;
    }

    public void setOldid(Long oldid) {
        this.oldid = oldid;
    }
    
    

    public Testresult(Long id, String paramName, BigDecimal paramValue) {
        this.id = id;
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public BigDecimal getParamValue() {
        return paramValue;
    }

    public void setParamValue(BigDecimal paramValue) {
        this.paramValue = paramValue;
    }

    public Testrecord getTestRecordId() {
        return testRecordId;
    }

    public void setTestRecordId(Testrecord testRecordId) {
        this.testRecordId = testRecordId;
    }

    public Collection<AppActions> getAppActionsCollection() {
        return appActionsCollection;
    }

    public void setAppActionsCollection(Collection<AppActions> appActionsCollection) {
        this.appActionsCollection = appActionsCollection;
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
        if (!(object instanceof Testresult)) {
            return false;
        }
        Testresult other = (Testresult) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.suyojan.abbrest.Testresult[ id=" + id + " ]";
    }
    
}
