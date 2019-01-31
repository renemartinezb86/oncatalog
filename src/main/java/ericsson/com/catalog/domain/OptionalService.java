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

/**
 * A OptionalService.
 */
@Document(collection = "optional_service")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "optionalservice")
public class OptionalService implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("service_id")
    private String serviceId;

    @Field("cardinality")
    private String cardinality;

    @Field("group")
    private String group;

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

    public String getServiceId() {
        return serviceId;
    }

    public OptionalService serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getCardinality() {
        return cardinality;
    }

    public OptionalService cardinality(String cardinality) {
        this.cardinality = cardinality;
        return this;
    }

    public void setCardinality(String cardinality) {
        this.cardinality = cardinality;
    }

    public String getGroup() {
        return group;
    }

    public OptionalService group(String group) {
        this.group = group;
        return this;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Set<BasicPO> getBasicPOS() {
        return basicPOS;
    }

    public OptionalService basicPOS(Set<BasicPO> basicPOS) {
        this.basicPOS = basicPOS;
        return this;
    }

    public OptionalService addBasicPO(BasicPO basicPO) {
        this.basicPOS.add(basicPO);
        basicPO.getOptionalServices().add(this);
        return this;
    }

    public OptionalService removeBasicPO(BasicPO basicPO) {
        this.basicPOS.remove(basicPO);
        basicPO.getOptionalServices().remove(this);
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
        OptionalService optionalService = (OptionalService) o;
        if (optionalService.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), optionalService.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OptionalService{" +
            "id=" + getId() +
            ", serviceId='" + getServiceId() + "'" +
            ", cardinality='" + getCardinality() + "'" +
            ", group='" + getGroup() + "'" +
            "}";
    }
}
