package de.iu.ghostnetfishing.controller;

import de.iu.ghostnetfishing.model.GhostNet;
import de.iu.ghostnetfishing.service.GhostNetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/nets")
public class GhostNetController {

    private final GhostNetService ghostNetService;

    public GhostNetController(GhostNetService ghostNetService) {
        this.ghostNetService = ghostNetService;
    }

    // MUST 3: Offene Netze anzeigen
    @GetMapping
    public String listOpenNets(Model model) {
        List<GhostNet> nets = ghostNetService.getOpenNets();
        model.addAttribute("nets", nets);
        return "nets/list";
    }

    // MUST 1: Formular anzeigen
    @GetMapping("/report")
    public String showReportForm() {
        return "nets/report";
    }

    // MUST 1: Netz melden (Form submit)
    @PostMapping("/report")
    public String reportNet(@RequestParam Double latitude,
                            @RequestParam Double longitude,
                            @RequestParam String sizeEstimate,
                            @RequestParam(required = false) String name,
                            @RequestParam(required = false) String phoneNumber,
                            Model model) {

        // Wenn Name angegeben, dann Telefon Pflicht
        if (name != null && !name.isBlank() && (phoneNumber == null || phoneNumber.isBlank())) {
            model.addAttribute("error", "Wenn du einen Namen angibst, musst du auch eine Telefonnummer angeben.");
            return "nets/report";
        }

        ghostNetService.reportGhostNet(latitude, longitude, sizeEstimate, name, phoneNumber);
        return "redirect:/nets";
    }

    // MUST 2: Formular für Bergung übernehmen anzeigen
    @GetMapping("/{id}/assign")
    public String showAssignForm(@PathVariable Long id, Model model) {
        model.addAttribute("netId", id);
        return "nets/assign";
    }

    // MUST 2: Bergung übernehmen (Form submit)
    @PostMapping("/{id}/assign")
    public String assignSalvager(@PathVariable Long id,
                                 @RequestParam String name,
                                 @RequestParam String phoneNumber) {
        ghostNetService.assignSalvager(id, name, phoneNumber);
        return "redirect:/nets";
    }

    // MUST 4: Als geborgen melden
    @PostMapping("/{id}/recover")
    public String recoverNet(@PathVariable Long id) {
        ghostNetService.markAsRecovered(id);
        return "redirect:/nets";
    }

    // COULD 7: Als verschollen melden
    @PostMapping("/{id}/lost")
    public String markLost(@PathVariable Long id,
                           @RequestParam String name,
                           @RequestParam String phoneNumber) {
        ghostNetService.markAsLost(id, name, phoneNumber);
        return "redirect:/nets";
    }
    @GetMapping("/{id}/lost")
    public String showLostForm(@PathVariable Long id, Model model) {
        model.addAttribute("netId", id);
        return "nets/lost";
    }
}