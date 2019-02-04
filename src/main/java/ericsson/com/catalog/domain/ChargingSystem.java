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
 * A ChargingSystem.
 */
@Document(collection = "charging_system")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "chargingsystem")
public class ChargingSystem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("service_name")
    private String serviceName;

    @Field("offer_template")
    private String offerTemplate;

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

    public String getServiceName() {
        return serviceName;
    }

    public ChargingSystem serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getOfferTemplate() {
        return offerTemplate;
    }

    public ChargingSystem offerTemplate(String offerTemplate) {
        this.offerTemplate = offerTemplate;
        return this;
    }

    public void setOfferTemplate(String offerTemplate) {
        this.offerTemplate = offerTemplate;
    }

    public Set<BasicPO> getBasicPOS() {
        return basicPOS;
    }

    public ChargingSystem basicPOS(Set<BasicPO> basicPOS) {
        this.basicPOS = basicPOS;
        return this;
    }

    public ChargingSystem addBasicPO(BasicPO basicPO) {
        this.basicPOS.add(basicPO);
        basicPO.getChargingSystems().add(this);
        return this;
    }

    public ChargingSystem removeBasicPO(BasicPO basicPO) {
        this.basicPOS.remove(basicPO);
        basicPO.getChargingSystems().remove(this);
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
        ChargingSystem chargingSystem = (ChargingSystem) o;
        if (chargingSystem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), chargingSystem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChargingSystem{" +
            "id=" + getId() +
            ", serviceName='" + getServiceName() + "'" +
            ", offerTemplate='" + getOfferTemplate() + "'" +
            "}";
    }
}
