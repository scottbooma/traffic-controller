package com.scottbooma.intersection.services;

import com.scottbooma.intersection.enums.Colors;
import com.scottbooma.intersection.models.ConfigModel;
import com.scottbooma.intersection.models.Intersection;
import com.scottbooma.intersection.models.Light;
import com.scottbooma.intersection.repositories.IntersectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LightService {

    @Autowired
    IntersectionRepository intersectionRepository;

    public Light getLightGroup(String intersectionId, String lightGroup) throws IllegalArgumentException {
        Intersection intersection = intersectionRepository.findById(intersectionId);

        if (Objects.equals(lightGroup, "A")) {
            return intersection.getLightGroupA();
        } else if (Objects.equals(lightGroup, "B")) {
            return intersection.getLightGroupB();
        } else {
            throw new IllegalArgumentException("Invalid Light Group.");
        }
    }

    public String startLightsAtIntersection(String intersectionId) {
        Intersection intersection = intersectionRepository.findById(intersectionId);

        if (intersection.isActiveStatus()) {
            return "Intersection is already active.";
        } else {
            intersection.setActiveStatus(true);
            intersection.startLights();
            intersectionRepository.save(intersection);

            return "Successfully activated intersection.";
        }
    }

    public String stopLightsAtIntersection(String intersectionId) {
        Intersection intersection = intersectionRepository.findById(intersectionId);

        if (!intersection.isActiveStatus()) {
            intersection.stopLights();
            return "Intersection is already inactive.";
        } else {
            intersection.setActiveStatus(false);
            intersection.stopLights();
            intersectionRepository.save(intersection);

            return "Successfully deactivated intersection.";
        }
    }

    public String setLightsAtIntersection(String intersectionId, Colors lightColorA, Colors lightColorB) {
        Intersection intersection = intersectionRepository.findById(intersectionId);

        if (lightColorA != lightColorB &&
                !(lightColorA == Colors.GREEN && lightColorB == Colors.YELLOW) &&
                !(lightColorA == Colors.YELLOW && lightColorB == Colors.GREEN)) {
            intersection.getLightGroupA().setState(lightColorA);
            intersection.getLightGroupB().setState(lightColorB);

            return "Successfully set colors of lights.";
        } else {
            return "Please enter valid colors for lights.";
        }
    }

    public String setAllLightsToRed(String intersectionId) {
        Intersection intersection = intersectionRepository.findById(intersectionId);

        intersection.getLightGroupA().setState(Colors.RED);
        intersection.getLightGroupB().setState(Colors.RED);

        return "Emergency sequence activated.";
    }

    public boolean validateTimingConfig(Integer greenTiming, Integer yellowTiming, Integer redTiming) {
        return greenTiming + yellowTiming == redTiming;
    }

    public ConfigModel createNewTimingConfig(Integer greenTiming, Integer yellowTiming, Integer redTiming) {
        if (validateTimingConfig(greenTiming, yellowTiming, redTiming)) {
            return new ConfigModel(greenTiming, yellowTiming, redTiming);
        } else return null;
    }

    public String addConfigToIntersection(String intersectionId, Integer greenTiming, Integer yellowTiming, Integer redTiming) {
        Intersection intersection = intersectionRepository.findById(intersectionId);

        ConfigModel newTimingConfig = createNewTimingConfig(greenTiming, yellowTiming, redTiming);

        if (Objects.nonNull(newTimingConfig)) {
            intersection.setTimingConfig(newTimingConfig);

            return "Successfully added new timing configuration to intersection.";
        } else {
            return "Please enter a valid timing configuration.";
        }
    }
}
