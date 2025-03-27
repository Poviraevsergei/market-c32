package com.tms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Transient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Scope("prototype")
@Component
@Entity(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_seq_gen")
    private Long id;
    private String firstname;
    @Column(name = "second_name")
    private String secondName;
    private Integer age;
    private String email;
    private String sex;
    
    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "created", updatable = false)
    private Timestamp created;

    @Column(name = "updated", insertable = false)
    private Timestamp updated;

    @PrePersist
    protected void onCreate() {
        created = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Timestamp(System.currentTimeMillis());
    }

    @Column(name = "is_deleted")
    private Boolean isDeleted;
    
    @Transient
    private Security securityInfo;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", secondName='" + secondName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", isDeleted=" + isDeleted +
                ", securityInfo=" + securityInfo +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Security getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(Security securityInfo) {
        this.securityInfo = securityInfo;
    }
}
