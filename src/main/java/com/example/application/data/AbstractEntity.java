package com.example.application.data;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import static com.example.application.data.constants.DataEntityConstants.IDGENERATOR;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IDGENERATOR)
    // The initial value is to account for data.sql demo data ids
    @SequenceGenerator(name = IDGENERATOR, initialValue = 1000)
    private Long id;

    @Version
    private int version;

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity that)) {
            return false; // null or not an AbstractEntity class
        }
        if (getId() != null) {
            return getId().equals(that.getId());
        }
        return super.equals(that);
    }
}
