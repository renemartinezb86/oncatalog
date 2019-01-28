package ericsson.com.catalog.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Catalog.
 */
@Document(collection = "catalog")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "catalog")
public class Catalog implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("file_name")
    private String fileName;

    @Field("created_date")
    private Instant createdDate;

    @DBRef
    @Field("basicPOs")
    private Set<BasicPO> basicPOs = new HashSet<>();
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

    public Catalog name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public Catalog fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Catalog createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Set<BasicPO> getBasicPOs() {
        return basicPOs;
    }

    public Catalog basicPOs(Set<BasicPO> basicPOS) {
        this.basicPOs = basicPOS;
        return this;
    }

    public Catalog addBasicPOs(BasicPO basicPO) {
        this.basicPOs.add(basicPO);
        basicPO.setCatalog(this);
        return this;
    }

    public Catalog removeBasicPOs(BasicPO basicPO) {
        this.basicPOs.remove(basicPO);
        basicPO.setCatalog(null);
        return this;
    }

    public void setBasicPOs(Set<BasicPO> basicPOS) {
        this.basicPOs = basicPOS;
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
        Catalog catalog = (Catalog) o;
        if (catalog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), catalog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Catalog{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
