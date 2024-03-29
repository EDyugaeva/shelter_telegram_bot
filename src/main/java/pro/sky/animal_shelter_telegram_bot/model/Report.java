package pro.sky.animal_shelter_telegram_bot.model;

import pro.sky.animal_shelter_telegram_bot.model.pets.Pet;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Report {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    private String dateOfReport;
    private String diet;
    private String health;
    private String changeInBehavior;
    private boolean isReportChecked;
    private String result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_owner_id")
    private PetOwner petOwner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_id")
    private Pet pet;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetOwner getPetOwner() {
        return petOwner;
    }

    public void setPetOwner(PetOwner petOwner) {
        this.petOwner = petOwner;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getDateOfReport() {
        return dateOfReport;
    }

    public void setDateOfReport(String dateOfReport) {
        this.dateOfReport = dateOfReport;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getChangeInBehavior() {
        return changeInBehavior;
    }

    public void setChangeInBehavior(String changeInBehavior) {
        this.changeInBehavior = changeInBehavior;
    }

    public boolean isReportChecked() {
        return isReportChecked;
    }

    public void setReportChecked(boolean reportChecked) {
        isReportChecked = reportChecked;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(id, report.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", petOwner=" +
                ", pet=" +
                ", dateOfReport='" + dateOfReport + '\'' +
                ", diet='" + diet + '\'' +
                ", health='" + health + '\'' +
                ", changeInBehavior='" + changeInBehavior + '\'' +
                ", isReportChecked=" + isReportChecked +
                ", result='" + result + '\'' +
                '}';
    }
}
