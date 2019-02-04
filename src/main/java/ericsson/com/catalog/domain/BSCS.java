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
 * A BSCS.
 */
@Document(collection = "bscs")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "bscs")
public class BSCS implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("service_name")
    private String serviceName;

    @Field("bscs_service")
    private String bscsService;

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

    public BSCS serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBscsService() {
        return bscsService;
    }

    public BSCS bscsService(String bscsService) {
        this.bscsService = bscsService;
        return this;
    }

    public void setBscsService(String bscsService) {
        this.bscsService = bscsService;
    }

    public Set<BasicPO> getBasicPOS() {
        return basicPOS;
    }

    public BSCS basicPOS(Set<BasicPO> basicPOS) {
        this.basicPOS = basicPOS;
        return this;
    }

    public BSCS addBasicPO(BasicPO basicPO) {
        this.basicPOS.add(basicPO);
        basicPO.getBSCSses().add(this);
        return this;
    }

    public BSCS removeBasicPO(BasicPO basicPO) {
        this.basicPOS.remove(basicPO);
        basicPO.getBSCSses().remove(this);
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
        BSCS bSCS = (BSCS) o;
        if (bSCS.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bSCS.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BSCS{" +
            "id=" + getId() +
            ", serviceName='" + getServiceName() + "'" +
            ", bscsService='" + getBscsService() + "'" +
            "}";
    }
}
