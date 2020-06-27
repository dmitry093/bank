package com.example.bank.models;

import lombok.Getter;

@Getter
public class BranchWithDistance {
    private final Integer id;

    private final String title;

    private final double lon;

    private final double lat;

    private final String address;

    private final Integer distance;

    public BranchWithDistance(Branch branch, Integer distance) {
        this.id = branch.getId();
        this.title = branch.getTitle();
        this.lon = branch.getLon();
        this.lat = branch.getLat();
        this.address = branch.getAddress();
        this.distance = distance;
    }
}
