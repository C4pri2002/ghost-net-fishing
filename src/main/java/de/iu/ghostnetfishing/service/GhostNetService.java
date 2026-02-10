package de.iu.ghostnetfishing.service;

import de.iu.ghostnetfishing.model.*;
import de.iu.ghostnetfishing.repository.GhostNetRepository;
import de.iu.ghostnetfishing.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GhostNetService {

    private final GhostNetRepository ghostNetRepository;
    private final PersonRepository personRepository;

    public GhostNetService(GhostNetRepository ghostNetRepository,
                           PersonRepository personRepository) {
        this.ghostNetRepository = ghostNetRepository;
        this.personRepository = personRepository;
    }

    /**
     * MUST 1:
     * Geisternetz melden (anonym oder mit Name/Telefon)
     */
    public GhostNet reportGhostNet(Double latitude,
                                   Double longitude,
                                   String sizeEstimate,
                                   String name,
                                   String phoneNumber) {

        Person reporter = null;

        // Nur wenn ein Name angegeben ist, wird eine Person gespeichert
        if (name != null && !name.isBlank()) {
            reporter = new Person();
            reporter.setName(name);
            reporter.setPhoneNumber(phoneNumber);
            reporter.setType(PersonType.MELDEND);
            reporter = personRepository.save(reporter);
        }

        GhostNet net = new GhostNet();
        net.setLatitude(latitude);
        net.setLongitude(longitude);
        net.setSizeEstimate(sizeEstimate);
        net.setStatus(GhostNetStatus.GEMELDET);
        net.setReportingPerson(reporter);

        return ghostNetRepository.save(net);
    }

    /**
     * MUST 3:
     * Alle offenen Geisternetze anzeigen
     */
    public List<GhostNet> getOpenNets() {
        return ghostNetRepository.findByStatusIn(
                List.of(
                        GhostNetStatus.GEMELDET,
                        GhostNetStatus.BERGUNG_BEVORSTEHEND
                )
        );
    }

    /**
     * MUST 2:
     * Bergung für ein Geisternetz übernehmen
     */
    public GhostNet assignSalvager(Long netId,
                                   String name,
                                   String phoneNumber) {

        GhostNet net = ghostNetRepository.findById(netId)
                .orElseThrow(() -> new RuntimeException("Geisternetz nicht gefunden"));

        Person salvager = new Person();
        salvager.setName(name);
        salvager.setPhoneNumber(phoneNumber);
        salvager.setType(PersonType.BERGEND);
        salvager = personRepository.save(salvager);

        net.setSalvagingPerson(salvager);
        net.setStatus(GhostNetStatus.BERGUNG_BEVORSTEHEND);

        return net;
    }

    /**
     * MUST 4:
     * Geisternetz als geborgen melden
     */
    public void markAsRecovered(Long netId) {
        GhostNet net = ghostNetRepository.findById(netId)
                .orElseThrow(() -> new RuntimeException("Geisternetz nicht gefunden"));

        net.setStatus(GhostNetStatus.GEBORGEN);
    }

    /**
     * COULD 7:
     * Geisternetz als verschollen melden
     */
    public void markAsLost(Long netId) {
        GhostNet net = ghostNetRepository.findById(netId)
                .orElseThrow(() -> new RuntimeException("Geisternetz nicht gefunden"));

        net.setStatus(GhostNetStatus.VERSCHOLLEN);
    }
}
