package ericsson.com.catalog.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Characteristic.
 */
@Document(collection = "characteristic")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "characteristic")
public class Characteristic implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("value")
    private String value;

    @DBRef
    @Field("basicPO")
    @JsonIgnoreProperties("characteristics(name)S")
    private BasicPO basicPO;

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

    public Characteristic name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public Characteristic value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BasicPO getBasicPO() {
        return basicPO;
    }

    public Characteristic basicPO(BasicPO basicPO) {
        this.basicPO = basicPO;
        return this;
    }

    public void setBasicPO(BasicPO basicPO) {
        this.basicPO = basicPO;
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
        Characteristic characteristic = (Characteristic) o;
        if (characteristic.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), characteristic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Characteristic{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
