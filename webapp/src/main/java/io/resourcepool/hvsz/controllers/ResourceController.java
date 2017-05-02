package io.resourcepool.hvsz.controllers;

import io.resourcepool.hvsz.persistance.dao.DaoMapDb;
import io.resourcepool.hvsz.persistance.models.Game;
import io.resourcepool.hvsz.persistance.models.SafeZone;
import io.resourcepool.hvsz.persistance.models.SupplyZone;
import io.resourcepool.hvsz.service.HumanService;
import io.resourcepool.hvsz.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResourceController {

    @Autowired
    private DaoMapDb dao;

    @Autowired
    private HumanService humanService;

    @Autowired
    private ResourceService resourceService;

    private static final int ID_SUPPLY_ZONE = 1;

    /**
     * Drop a resource.
     * @param safeZone SupplyZone id
     * @param lifeToken lifeToken
     * @param model Model
     * @return String (human vue)
     */
    @PostMapping("/resource/drop")
    public String dropResource(
            @RequestParam(value = "safeZone") String safeZone,
            @RequestParam(value = "lifeToken") String lifeToken,
            Model model) {
        Game g = dao.get(1L);
        int lifeId = g.getStatus().getLifeByToken(lifeToken).getId();

        resourceService.dropById(Integer.parseInt(safeZone), 1, lifeId);
        SafeZone s = g.getSafeZoneById(Integer.parseInt(safeZone));
        model.addAttribute("nbResources",   "1 resource has been dropped : safe zone n°" + safeZone + " contains :" + s.getResource() + "resources");
        model.addAttribute("zone", s);
        return "safe-zone";
    }

    /**
     * Show the supply-zone view.
     * @return String (supply-zone vue)
     */
    @GetMapping("/resource")
    public String resourceGet() {
        return "supply-zone";
    }

    /**
     * Take a resource.
     * @param supplyZone SupplyZone id
     * @param lifeToken lifeToken
     * @param model Model
     * @return String (supply-zone)
     */
    @PostMapping("/resource/get")
    public String resourceGet(
            @RequestParam(value = "supplyZone") String supplyZone,
            @RequestParam(value = "lifeToken") String lifeToken,
            Model model) {
        Game g = dao.get(1L);
        int lifeId = g.getStatus().getLifeByToken(lifeToken).getId();
        int gotRes = humanService.getResources(Integer.parseInt(supplyZone), 1, lifeId);
        g = dao.get(1L);
        SupplyZone s = g.getSupplyZoneById(Integer.parseInt(supplyZone));
        model.addAttribute("nbSupplyResources", gotRes + " resource has been taken : remaining resources :" + s.getResource());
        model.addAttribute("zone", s);
        return "supply-zone";
    }
}