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

/**
 *
 * @author Suyojan
 */
@Entity
@Table(name = "app_group_params")
@NamedQueries({
    @NamedQuery(name = "AppGroupParams.findAll", query = "SELECT a FROM AppGroupParams a")})
public class AppGroupParams implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @JoinColumn(name = "app_group", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private AppGroups appGroup;
    @JoinColumn(name = "param", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private Analysislimits param;

    public AppGroupParams() {
    }

    public AppGroupParams(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppGroups getAppGroup() {
        return appGroup;
    }

    public void setAppGroup(AppGroups appGroup) {
        this.appGroup = appGroup;
    }

    public Analysislimits getParam() {
        return param;
    }

    public void setParam(Analysislimits param) {
        this.param = param;
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
        if (!(object instanceof AppGroupParams)) {
            return false;
        }
        AppGroupParams other = (AppGroupParams) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.suyojan.abbrest.AppGroupParams[ id=" + id + " ]";
    }
    
}
