package models;

abstract class Site {
    private final Location location;

    Site(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
