package ericsson.com.catalog.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import ericsson.com.catalog.domain.enumeration.PoServiceType;

/**
 * A PoService.
 */
@Document(collection = "po_service")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "poservice")
public class PoService implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("type")
    private PoServiceType type;

    @DBRef
    @Field("basicPOS")
    @JsonIgnore
    private Set<BasicPO> basicPOS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PoService name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PoServiceType getType() {
        return type;
    }

    public PoService type(PoServiceType type) {
        this.type = type;
        return this;
    }

    public void setType(PoServiceType type) {
        this.type = type;
    }

    public Set<BasicPO> getBasicPOS() {
        return basicPOS;
    }

    public PoService basicPOS(Set<BasicPO> basicPOS) {
        this.basicPOS = basicPOS;
        return this;
    }

    public PoService addBasicPO(BasicPO basicPO) {
        this.basicPOS.add(basicPO);
        basicPO.getPoServices().add(this);
        return this;
    }

    public PoService removeBasicPO(BasicPO basicPO) {
        this.basicPOS.remove(basicPO);
        basicPO.getPoServices().remove(this);
        return this;
    }

    public void setBasicPOS(Set<BasicPO> basicPOS) {
        this.basicPOS = basicPOS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PoService poService = (PoService) o;
        if (poService.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), poService.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PoService{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
