package ericsson.com.catalog.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BasicPO.
 */
@Document(collection = "basic_po")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "basicpo")
public class BasicPO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("po_id")
    private String poId;

    @Field("name")
    private String name;

    @DBRef
    @Field("characteristics")
    private Set<Characteristic> characteristics = new HashSet<>();
    @DBRef
    @Field("optionalServices")
    private Set<OptionalService> optionalServices = new HashSet<>();
    @DBRef
    @Field("catalog")
    @JsonIgnoreProperties("basicPOs")
    private Catalog catalog;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoId() {
        return poId;
    }

    public BasicPO poId(String poId) {
        this.poId = poId;
        return this;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getName() {
        return name;
    }

    public BasicPO name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public BasicPO characteristics(Set<Characteristic> characteristics) {
        this.characteristics = characteristics;
        return this;
    }

    public BasicPO addCharacteristics(Characteristic characteristic) {
        this.characteristics.add(characteristic);
        characteristic.setBasicPO(this);
        return this;
    }

    public BasicPO removeCharacteristics(Characteristic characteristic) {
        this.characteristics.remove(characteristic);
        characteristic.setBasicPO(null);
        return this;
    }

    public void setCharacteristics(Set<Characteristic> characteristics) {
        this.characteristics = characteristics;
    }

    public Set<OptionalService> getOptionalServices() {
        return optionalServices;
    }

    public BasicPO optionalServices(Set<OptionalService> optionalServices) {
        this.optionalServices = optionalServices;
        return this;
    }

    public BasicPO addOptionalServices(OptionalService optionalService) {
        this.optionalServices.add(optionalService);
        optionalService.setBasicPO(this);
        return this;
    }

    public BasicPO removeOptionalServices(OptionalService optionalService) {
        this.optionalServices.remove(optionalService);
        optionalService.setBasicPO(null);
        return this;
    }

    public void setOptionalServices(Set<OptionalService> optionalServices) {
        this.optionalServices = optionalServices;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public BasicPO catalog(Catalog catalog) {
        this.catalog = catalog;
        return this;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
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
        BasicPO basicPO = (BasicPO) o;
        if (basicPO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), basicPO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BasicPO{" +
            "id=" + getId() +
            ", poId='" + getPoId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
