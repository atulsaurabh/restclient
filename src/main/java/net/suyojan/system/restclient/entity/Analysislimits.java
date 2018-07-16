/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Suyojan
 */
@Entity
@Table(name = "analysislimits")
@NamedQueries({
    @NamedQuery(name = "Analysislimits.findAll", query = "SELECT a FROM Analysislimits a")})
public class Analysislimits implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String category;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "param_name", nullable = false, length = 30)
    private String paramName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, precision = 12, scale = 5)
    private BigDecimal center;
    @Basic(optional = false)
    @NotNull
    @Column(name = "warning_pct", nullable = false, precision = 12, scale = 5)
    private BigDecimal warningPct;
    @Basic(optional = false)
    @NotNull
    @Column(name = "error_pct", nullable = false, precision = 12, scale = 5)
    private BigDecimal errorPct;
    @Size(max = 255)
    @Column(length = 255)
    private String label;
    @Basic(optional = false)
    @NotNull
    @Column(name = "variable_limits", nullable = false)
    private short variableLimits;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double lcl;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private double ucl;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "param")
    private Collection<AppGroupParams> appGroupParamsCollection;

    public Analysislimits() {
    }

    public Analysislimits(Integer id) {
        this.id = id;
    }

    public Analysislimits(Integer id, String category, String paramName, BigDecimal center, BigDecimal warningPct, BigDecimal errorPct, short variableLimits, double lcl, double ucl) {
        this.id = id;
        this.category = category;
        this.paramName = paramName;
        this.center = center;
        this.warningPct = warningPct;
        this.errorPct = errorPct;
        this.variableLimits = variableLimits;
        this.lcl = lcl;
        this.ucl = ucl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public BigDecimal getCenter() {
        return center;
    }

    public void setCenter(BigDecimal center) {
        this.center = center;
    }

    public BigDecimal getWarningPct() {
        return warningPct;
    }

    public void setWarningPct(BigDecimal warningPct) {
        this.warningPct = warningPct;
    }

    public BigDecimal getErrorPct() {
        return errorPct;
    }

    public void setErrorPct(BigDecimal errorPct) {
        this.errorPct = errorPct;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public short getVariableLimits() {
        return variableLimits;
    }

    public void setVariableLimits(short variableLimits) {
        this.variableLimits = variableLimits;
    }

    public double getLcl() {
        return lcl;
    }

    public void setLcl(double lcl) {
        this.lcl = lcl;
    }

    public double getUcl() {
        return ucl;
    }

    public void setUcl(double ucl) {
        this.ucl = ucl;
    }

    public Collection<AppGroupParams> getAppGroupParamsCollection() {
        return appGroupParamsCollection;
    }

    public void setAppGroupParamsCollection(Collection<AppGroupParams> appGroupParamsCollection) {
        this.appGroupParamsCollection = appGroupParamsCollection;
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
        if (!(object instanceof Analysislimits)) {
            return false;
        }
        Analysislimits other = (Analysislimits) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.suyojan.abbrest.Analysislimits[ id=" + id + " ]";
    }
    
}
