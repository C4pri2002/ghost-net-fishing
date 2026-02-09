package de.iu.ghostnetfishing.repository;

import de.iu.ghostnetfishing.model.GhostNet;
import de.iu.ghostnetfishing.model.GhostNetStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GhostNetRepository extends JpaRepository<GhostNet, Long> {

    // Netze, die noch "offen" sind (gemeldet oder bergung bevorstehend)
    List<GhostNet> findByStatusIn(List<GhostNetStatus> statuses);
}
