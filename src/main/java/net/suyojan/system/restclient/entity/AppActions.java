/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Suyojan
 */
@Entity
@Table(name = "app_actions")
@NamedQueries({
    @NamedQuery(name = "AppActions.findAll", query = "SELECT a FROM AppActions a")})
public class AppActions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "action_type", nullable = false)
    private short actionType;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 22)
    private Double usl;
    @Column(precision = 22)
    private Double lsl;
    @Column(precision = 22)
    private Double ucl;
    @Column(precision = 22)
    private Double lcl;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short status;
    @Size(max = 255)
    @Column(name = "failure_reason", length = 255)
    private String failureReason;
    @Size(max = 255)
    @Column(name = "remedy_comments", length = 255)
    private String remedyComments;
    @Column(name = "action_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionTime;
    @JoinColumn(name = "action_user", referencedColumnName = "id")
    @ManyToOne
    private AppUsers actionUser;
    @JoinColumn(name = "test_record_id", referencedColumnName = "id")
    @ManyToOne
    private Testrecord testRecordId;
    @JoinColumn(name = "remedy_type", referencedColumnName = "id")
    @ManyToOne
    private AppRemedyTypes remedyType;
    @JoinColumn(name = "test_result_id", referencedColumnName = "id")
    @ManyToOne
    private Testresult testResultId;

    public AppActions() {
    }

    public AppActions(Long id) {
        this.id = id;
    }

    public AppActions(Long id, short actionType, short status) {
        this.id = id;
        this.actionType = actionType;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public short getActionType() {
        return actionType;
    }

    public void setActionType(short actionType) {
        this.actionType = actionType;
    }

    public Double getUsl() {
        return usl;
    }

    public void setUsl(Double usl) {
        this.usl = usl;
    }

    public Double getLsl() {
        return lsl;
    }

    public void setLsl(Double lsl) {
        this.lsl = lsl;
    }

    public Double getUcl() {
        return ucl;
    }

    public void setUcl(Double ucl) {
        this.ucl = ucl;
    }

    public Double getLcl() {
        return lcl;
    }

    public void setLcl(Double lcl) {
        this.lcl = lcl;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getRemedyComments() {
        return remedyComments;
    }

    public void setRemedyComments(String remedyComments) {
        this.remedyComments = remedyComments;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public AppUsers getActionUser() {
        return actionUser;
    }

    public void setActionUser(AppUsers actionUser) {
        this.actionUser = actionUser;
    }

    public Testrecord getTestRecordId() {
        return testRecordId;
    }

    public void setTestRecordId(Testrecord testRecordId) {
        this.testRecordId = testRecordId;
    }

    public AppRemedyTypes getRemedyType() {
        return remedyType;
    }

    public void setRemedyType(AppRemedyTypes remedyType) {
        this.remedyType = remedyType;
    }

    public Testresult getTestResultId() {
        return testResultId;
    }

    public void setTestResultId(Testresult testResultId) {
        this.testResultId = testResultId;
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
        if (!(object instanceof AppActions)) {
            return false;
        }
        AppActions other = (AppActions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.suyojan.abbrest.AppActions[ id=" + id + " ]";
    }
    
}
