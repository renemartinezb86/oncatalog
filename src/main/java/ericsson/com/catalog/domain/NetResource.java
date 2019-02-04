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
 * A NetResource.
 */
@Document(collection = "net_resource")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "netresource")
public class NetResource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("parameter")
    private String parameter;

    @Field("value")
    private String value;

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

    public NetResource name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameter() {
        return parameter;
    }

    public NetResource parameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getValue() {
        return value;
    }

    public NetResource value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<BasicPO> getBasicPOS() {
        return basicPOS;
    }

    public NetResource basicPOS(Set<BasicPO> basicPOS) {
        this.basicPOS = basicPOS;
        return this;
    }

    public NetResource addBasicPO(BasicPO basicPO) {
        this.basicPOS.add(basicPO);
        basicPO.getNetResources().add(this);
        return this;
    }

    public NetResource removeBasicPO(BasicPO basicPO) {
        this.basicPOS.remove(basicPO);
        basicPO.getNetResources().remove(this);
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
        NetResource netResource = (NetResource) o;
        if (netResource.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), netResource.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NetResource{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", parameter='" + getParameter() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
