package de.iu.ghostnetfishing.model;
import jakarta.persistence.*;

@Entity
public class GhostNet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // GPS-Koordinaten
    private double latitude;
    private double longitude;

    // geschätzte Größe
    private String sizeEstimate;

    // Status des Geisternetzes
    @Enumerated(EnumType.STRING)
    private GhostNetStatus status;

    // meldende Person (kann null sein → anonym)
    @ManyToOne
    private Person reportingPerson;

    // bergende Person (maximal eine)
    @ManyToOne
    private Person salvagingPerson;

    // Getter & Setter

    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSizeEstimate() {
        return sizeEstimate;
    }

    public void setSizeEstimate(String sizeEstimate) {
        this.sizeEstimate = sizeEstimate;
    }

    public GhostNetStatus getStatus() {
        return status;
    }

    public void setStatus(GhostNetStatus status) {
        this.status = status;
    }

    public Person getReportingPerson() {
        return reportingPerson;
    }

    public void setReportingPerson(Person reportingPerson) {
        this.reportingPerson = reportingPerson;
    }

    public Person getSalvagingPerson() {
        return salvagingPerson;
    }

    public void setSalvagingPerson(Person salvagingPerson) {
        this.salvagingPerson = salvagingPerson;
    }
}
