/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.entity;

import java.io.Serializable;
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
@Table(name = "app_users")
@NamedQueries({
    @NamedQuery(name = "AppUsers.findAll", query = "SELECT a FROM AppUsers a")})
public class AppUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "create_time", nullable = false, length = 25)
    private String createTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "imei_number", nullable = false, length = 45)
    private String imeiNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_name", nullable = false, length = 255)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "acess_level", nullable = false)
    private short acessLevel;
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_active", nullable = false)
    private short userActive;
    @Size(max = 15)
    @Column(name = "mo_no", length = 15)
    private String moNo;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 199)
    @Column(length = 199)
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<AppUserGroup> appUserGroupCollection;
    @OneToMany(mappedBy = "actionUser")
    private Collection<AppActions> appActionsCollection;

    public AppUsers() {
    }

    public AppUsers(Integer id) {
        this.id = id;
    }

    public AppUsers(Integer id, String createTime, String imeiNumber, String userName, short acessLevel, short userActive) {
        this.id = id;
        this.createTime = createTime;
        this.imeiNumber = imeiNumber;
        this.userName = userName;
        this.acessLevel = acessLevel;
        this.userActive = userActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImeiNumber() {
        return imeiNumber;
    }

    public void setImeiNumber(String imeiNumber) {
        this.imeiNumber = imeiNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public short getAcessLevel() {
        return acessLevel;
    }

    public void setAcessLevel(short acessLevel) {
        this.acessLevel = acessLevel;
    }

    public short getUserActive() {
        return userActive;
    }

    public void setUserActive(short userActive) {
        this.userActive = userActive;
    }

    public String getMoNo() {
        return moNo;
    }

    public void setMoNo(String moNo) {
        this.moNo = moNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<AppUserGroup> getAppUserGroupCollection() {
        return appUserGroupCollection;
    }

    public void setAppUserGroupCollection(Collection<AppUserGroup> appUserGroupCollection) {
        this.appUserGroupCollection = appUserGroupCollection;
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
        if (!(object instanceof AppUsers)) {
            return false;
        }
        AppUsers other = (AppUsers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.suyojan.abbrest.AppUsers[ id=" + id + " ]";
    }
    
}
