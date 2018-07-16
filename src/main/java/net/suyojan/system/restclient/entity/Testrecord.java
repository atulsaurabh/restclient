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
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "testrecord")
@NamedQueries({
    @NamedQuery(name = "Testrecord.findAll", query = "SELECT t FROM Testrecord t")})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Testrecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String category;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "test_station", nullable = false, length = 20)
    private String testStation;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String dut;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "order_code", nullable = false, length = 255)
    private String orderCode;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    private Short result;
    @Column(name = "result_code")
    private Integer resultCode;
    @Size(max = 255)
    @Column(name = "user_desc", length = 255)
    private String userDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "report_result", nullable = false)
    private short reportResult;
    @Basic(optional = false)
    @NotNull
    @Column(name = "error_category", nullable = false)
    private short errorCategory;
    @OneToMany(mappedBy = "testRecordId",fetch = FetchType.EAGER)
    private Collection<Testresult> testresultCollection;
    @OneToMany(mappedBy = "testrecordId")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<Dutcomponents> dutcomponentsCollection;
    @OneToMany(mappedBy = "testRecordId")
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.FALSE)
    private Collection<AppActions> appActionsCollection;
    
    private boolean sent=false;
    
    @Transient
   private String migratedFrom;
    
    public Testrecord() {
    }

    public Testrecord(Long id) {
        this.id = id;
    }

    public Testrecord(Long id, String category, String testStation, String dut, String orderCode, short reportResult, short errorCategory) {
        this.id = id;
        this.category = category;
        this.testStation = testStation;
        this.dut = dut;
        this.orderCode = orderCode;
        this.reportResult = reportResult;
        this.errorCategory = errorCategory;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTestStation() {
        return testStation;
    }

    public void setTestStation(String testStation) {
        this.testStation = testStation;
    }

    public String getDut() {
        return dut;
    }

    public void setDut(String dut) {
        this.dut = dut;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Short getResult() {
        return result;
    }

    public void setResult(Short result) {
        this.result = result;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public short getReportResult() {
        return reportResult;
    }

    public void setReportResult(short reportResult) {
        this.reportResult = reportResult;
    }

    public short getErrorCategory() {
        return errorCategory;
    }

    public void setErrorCategory(short errorCategory) {
        this.errorCategory = errorCategory;
    }

    public Collection<Testresult> getTestresultCollection() {
        return testresultCollection;
    }

    public void setTestresultCollection(Collection<Testresult> testresultCollection) {
        this.testresultCollection = testresultCollection;
    }

    public Collection<Dutcomponents> getDutcomponentsCollection() {
        return dutcomponentsCollection;
    }

    public void setDutcomponentsCollection(Collection<Dutcomponents> dutcomponentsCollection) {
        this.dutcomponentsCollection = dutcomponentsCollection;
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
        if (!(object instanceof Testrecord)) {
            return false;
        }
        Testrecord other = (Testrecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.suyojan.abbrest.Testrecord[ id=" + id + " ]";
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getMigratedFrom() {
        return migratedFrom;
    }

    public void setMigratedFrom(String migratedFrom) {
        this.migratedFrom = migratedFrom;
    }

        
}
