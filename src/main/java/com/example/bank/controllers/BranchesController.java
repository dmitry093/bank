package com.example.bank.controllers;

import com.example.bank.models.Branch;
import com.example.bank.models.BranchWithDistance;
import com.example.bank.models.ErrorResponse;
import com.example.bank.repositories.BranchesRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@RestController
@RequiredArgsConstructor
public class BranchesController {
    private final BranchesRepository branchesRepository;

    private final static Integer RADIUS = 6371;

    /**
     *
     * @param lat1 широта первой точки, фи1
     * @param lon1 долгота первой точки, лямбда1
     * @param lat2 широта второй точки, фи2
     * @param lon2 долгота второй точки, лямбда2
     * @return расстояние
     */
    private static Integer getDistance(double lat1, double lon1, double lat2, double lon2){
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double radLon1 = Math.toRadians(lon1);
        double radLon2 = Math.toRadians(lon2);

        double firstTerm = Math.pow(Math.sin((radLat2 - radLat1) / 2), 2);
        double secondTerm = Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin((radLon2 - radLon1) / 2), 2);
        double distance = 2 * RADIUS * Math.asin(Math.sqrt(firstTerm + secondTerm));

        return (int) Math.round(distance / 1000);
    }

    @GetMapping("/branches/{id}")
    public ResponseEntity<?> getBranch(@PathVariable Integer id) {
        Optional<Branch> branch = branchesRepository.findById(id);
        if (branch.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Not found"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(branch);
    }

    @GetMapping("/branches")
    public ResponseEntity<?> getNearBranch(@RequestParam double lat, @RequestParam double lon){
        List<Branch> branches = branchesRepository.findAll();
        Map<Branch, Integer> distanceMap = branches
            .stream()
            .collect(toMap(b -> b, b -> getDistance(lat, lon, b.getLat(), b.getLon())));

        Optional<Map.Entry<Branch, Integer>> min = distanceMap
            .entrySet()
            .stream()
            .min(Comparator.comparingInt(Map.Entry::getValue));

        if (min.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Not found"), HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(new BranchWithDistance(min.get().getKey(), min.get().getValue()));
    }


}
