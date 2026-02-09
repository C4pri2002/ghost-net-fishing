package de.iu.ghostnetfishing.model;
import jakarta.persistence.*;

@Entity
public class Person {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

    private String name;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private PersonType type;

    // Getter & Setter

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }
}
