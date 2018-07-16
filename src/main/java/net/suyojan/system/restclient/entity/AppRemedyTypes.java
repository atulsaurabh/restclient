/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "app_remedy_types")
@NamedQueries({
    @NamedQuery(name = "AppRemedyTypes.findAll", query = "SELECT a FROM AppRemedyTypes a")})
public class AppRemedyTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "remedy_type", nullable = false, length = 45)
    private String remedyType;
    @OneToMany(mappedBy = "remedyType")
    private Collection<AppActions> appActionsCollection;

    public AppRemedyTypes() {
    }

    public AppRemedyTypes(Integer id) {
        this.id = id;
    }

    public AppRemedyTypes(Integer id, String remedyType) {
        this.id = id;
        this.remedyType = remedyType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemedyType() {
        return remedyType;
    }

    public void setRemedyType(String remedyType) {
        this.remedyType = remedyType;
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
        if (!(object instanceof AppRemedyTypes)) {
            return false;
        }
        AppRemedyTypes other = (AppRemedyTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.suyojan.abbrest.AppRemedyTypes[ id=" + id + " ]";
    }
    
}
