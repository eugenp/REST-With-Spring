package com.baeldung.um.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.baeldung.common.interfaces.INameableDto;
import com.baeldung.common.persistence.model.INameableEntity;

@Entity
@XmlRootElement
public class Patient implements INameableEntity, INameableDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String diagnosis;

    private Date entryDate;

    private Date exitDate;

    //

    public Patient() {
        super();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    //

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((diagnosis == null) ? 0 : diagnosis.hashCode());
        result = (prime * result) + ((entryDate == null) ? 0 : entryDate.hashCode());
        result = (prime * result) + ((exitDate == null) ? 0 : exitDate.hashCode());
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Patient other = (Patient) obj;
        if (diagnosis == null) {
            if (other.diagnosis != null) {
                return false;
            }
        } else if (!diagnosis.equals(other.diagnosis)) {
            return false;
        }
        if (entryDate == null) {
            if (other.entryDate != null) {
                return false;
            }
        } else if (!entryDate.equals(other.entryDate)) {
            return false;
        }
        if (exitDate == null) {
            if (other.exitDate != null) {
                return false;
            }
        } else if (!exitDate.equals(other.exitDate)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    //

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Patient [id=")
            .append(id)
            .append(", name=")
            .append(name)
            .append(", diagnosis=")
            .append(diagnosis)
            .append(", entryDate=")
            .append(entryDate)
            .append(", exitDate=")
            .append(exitDate)
            .append("]");
        return builder.toString();
    }

}
