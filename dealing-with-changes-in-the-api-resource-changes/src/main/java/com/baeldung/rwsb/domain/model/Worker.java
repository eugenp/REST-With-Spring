package com.baeldung.rwsb.domain.model;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @OneToOne(mappedBy = "worker", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private ServiceUser user;

    public Worker(ServiceUser user, String firstName, String lastName) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Worker() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ServiceUser getUser() {
        return user;
    }

    public void setUser(ServiceUser user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.user.getEmail());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Worker other = (Worker) obj;
        if (this.user.getEmail() == null) {
            if (other.user.getEmail() != null)
                return false;
        } else if (!this.user.getEmail()
            .equals(other.user.getEmail()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Worker [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }

}
