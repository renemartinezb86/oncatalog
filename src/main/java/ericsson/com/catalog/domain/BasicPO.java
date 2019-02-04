package ericsson.com.catalog.domain;


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
    @Field("poServices")
    private Set<PoService> poServices = new HashSet<>();

    @DBRef
    @Field("netResources")
    private Set<NetResource> netResources = new HashSet<>();

    @DBRef
    @Field("chargingSystems")
    private Set<ChargingSystem> chargingSystems = new HashSet<>();

    @DBRef
    @Field("bSCSses")
    private Set<BSCS> bSCSses = new HashSet<>();

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
        characteristic.getBasicPO(poId)S().add(this);
        return this;
    }

    public BasicPO removeCharacteristics(Characteristic characteristic) {
        this.characteristics.remove(characteristic);
        characteristic.getBasicPO(poId)S().remove(this);
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
        optionalService.getBasicPO(poId)S().add(this);
        return this;
    }

    public BasicPO removeOptionalServices(OptionalService optionalService) {
        this.optionalServices.remove(optionalService);
        optionalService.getBasicPO(poId)S().remove(this);
        return this;
    }

    public void setOptionalServices(Set<OptionalService> optionalServices) {
        this.optionalServices = optionalServices;
    }

    public Set<PoService> getPoServices() {
        return poServices;
    }

    public BasicPO poServices(Set<PoService> poServices) {
        this.poServices = poServices;
        return this;
    }

    public BasicPO addPoServices(PoService poService) {
        this.poServices.add(poService);
        poService.getBasicPO(poId)S().add(this);
        return this;
    }

    public BasicPO removePoServices(PoService poService) {
        this.poServices.remove(poService);
        poService.getBasicPO(poId)S().remove(this);
        return this;
    }

    public void setPoServices(Set<PoService> poServices) {
        this.poServices = poServices;
    }

    public Set<NetResource> getNetResources() {
        return netResources;
    }

    public BasicPO netResources(Set<NetResource> netResources) {
        this.netResources = netResources;
        return this;
    }

    public BasicPO addNetResources(NetResource netResource) {
        this.netResources.add(netResource);
        netResource.getBasicPO(poId)S().add(this);
        return this;
    }

    public BasicPO removeNetResources(NetResource netResource) {
        this.netResources.remove(netResource);
        netResource.getBasicPO(poId)S().remove(this);
        return this;
    }

    public void setNetResources(Set<NetResource> netResources) {
        this.netResources = netResources;
    }

    public Set<ChargingSystem> getChargingSystems() {
        return chargingSystems;
    }

    public BasicPO chargingSystems(Set<ChargingSystem> chargingSystems) {
        this.chargingSystems = chargingSystems;
        return this;
    }

    public BasicPO addChargingSystem(ChargingSystem chargingSystem) {
        this.chargingSystems.add(chargingSystem);
        chargingSystem.getBasicPO(poId)S().add(this);
        return this;
    }

    public BasicPO removeChargingSystem(ChargingSystem chargingSystem) {
        this.chargingSystems.remove(chargingSystem);
        chargingSystem.getBasicPO(poId)S().remove(this);
        return this;
    }

    public void setChargingSystems(Set<ChargingSystem> chargingSystems) {
        this.chargingSystems = chargingSystems;
    }

    public Set<BSCS> getBSCSses() {
        return bSCSses;
    }

    public BasicPO bSCSses(Set<BSCS> bSCS) {
        this.bSCSses = bSCS;
        return this;
    }

    public BasicPO addBSCSs(BSCS bSCS) {
        this.bSCSses.add(bSCS);
        bSCS.getBasicPO(poId)S().add(this);
        return this;
    }

    public BasicPO removeBSCSs(BSCS bSCS) {
        this.bSCSses.remove(bSCS);
        bSCS.getBasicPO(poId)S().remove(this);
        return this;
    }

    public void setBSCSses(Set<BSCS> bSCS) {
        this.bSCSses = bSCS;
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
