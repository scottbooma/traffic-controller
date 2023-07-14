package com.scottbooma.intersection.controllers;

import com.scottbooma.intersection.enums.Colors;
import com.scottbooma.intersection.models.ConfigModel;
import com.scottbooma.intersection.models.Intersection;
import com.scottbooma.intersection.models.Light;
import com.scottbooma.intersection.repositories.IntersectionRepository;
import com.scottbooma.intersection.services.LightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/intersection")
public class IntersectionController {

    @Autowired
    LightService lightService;

    @Autowired
    IntersectionRepository intersectionRepository;

    JwtDecoder jwtDecoder;

    @GetMapping("/{id}")
    public Intersection getIntersection(@PathVariable String id) {
        return intersectionRepository.findById(id);
    }

    @GetMapping("/{id}")
    public Light getLightState(@PathVariable String id,
                               @RequestParam String lightGroup) throws IllegalArgumentException {
        return lightService.getLightGroup(id, lightGroup);
    }

    @PostMapping
    public String createIntersection(@RequestBody Intersection intersection) {
        intersectionRepository.save(new Intersection(intersection, false, null));
        return "New Intersection Created.";
    }

    @PutMapping("/{id}/start")
    public String startIntersection(@PathVariable String id) {
        return lightService.startLightsAtIntersection(id);
    }

    @PutMapping("/{id}/stop")
    public String stopIntersection(@PathVariable String id) {
        return lightService.stopLightsAtIntersection(id);
    }

    @PutMapping("/{id}")
    public String setLightsAtIntersection(@PathVariable String id,
                                          @RequestParam Colors lightColorA,
                                          @RequestParam Colors lightColorB) {
        return lightService.setLightsAtIntersection(id, lightColorA, lightColorB);
    }

    @PutMapping("/{id}/emergency")
    public String setEmergencyServicesLights(@PathVariable String id,
                                             @RequestHeader(value = "Authorization") String token) {
        Jwt jwt =jwtDecoder.decode(token);

        if (Objects.equals(jwt.getClaimAsString("user"), "EmergencyServices")) {
            return lightService.setAllLightsToRed(id);
        } else {
            return "Unable to activate emergency sequence, please use correct authorization.";
        }
    }

    @GetMapping("/{id}/config")
    public ConfigModel getTimingConfig(@PathVariable String id) {
        return intersectionRepository.findById(id).getTimingConfig();
    }

    @PutMapping("/{id}/config")
    public String setNewTimingConfig(@PathVariable String id,
                                     @RequestParam Integer greenTiming,
                                     @RequestParam Integer yellowTiming,
                                     @RequestParam Integer redTiming) {
        return lightService.addConfigToIntersection(id, greenTiming, yellowTiming, redTiming);
    }
}
