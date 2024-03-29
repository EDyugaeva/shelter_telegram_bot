package pro.sky.animal_shelter_telegram_bot.model.pets;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pro.sky.animal_shelter_telegram_bot.model.PetOwner;
import pro.sky.animal_shelter_telegram_bot.model.Report;

import javax.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Pet {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    private String nameOfPet;
    private String health;
    private String extraInfoOfPet;
    private String kindOfPet;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_owner_id")
    private PetOwner ownerOfPet;

    @OneToMany(mappedBy = "pet")
    @JsonIgnore
    private Collection<Report> reports;

    public Pet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameOfPet() {
        return nameOfPet;
    }

    public void setNameOfPet(String nameOfPet) {
        this.nameOfPet = nameOfPet;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getKindOfPet() {
        return kindOfPet;
    }

    public String getExtraInfoOfPet() {
        return extraInfoOfPet;
    }

    public void setExtraInfoOfPet(String extraInfoOfPet) {
        this.extraInfoOfPet = extraInfoOfPet;
    }

    public PetOwner getOwnerOfPet() {
        return ownerOfPet;
    }

    public void setOwnerOfPet(PetOwner ownerOfPet) {
        this.ownerOfPet = ownerOfPet;
    }

    public Collection<Report> getReports() {
        return reports;
    }

    public void setReports(Collection<Report> reports) {
        this.reports = reports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", nameOfPet='" + nameOfPet + '\'' +
                ", health='" + health + '\'' +
                ", extraInfoOfPet='" + extraInfoOfPet + '\'' +
                ", ownerOfPet=" + ownerOfPet.getFirstName() + '\'' +
                ", kind of pet=" + kindOfPet +
                '}';
    }
}
