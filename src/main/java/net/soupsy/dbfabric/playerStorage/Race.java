package net.soupsy.dbfabric.playerStorage;

public enum Race {
    HUMAN("human", "Human", 1),
    SAIYAN("saiyan", "Saiyan", 2),
    HALFSAIYAN("half-saiyan", "Half-Saiyan", 2.5f),
    NAMEKIAN("namekian", "Namekian", 1),
    FREIZA("freiza", "Freiza", 1),
    ANDROID("android", "Android", 0.5f),
    MAJIN("majin", "Majin", 1);


    private final String id;
    private final String displayName;
    private final float potentialRate;

    private Race(String id, String displayName, float potentialRate){
        this.id = id;
        this.displayName = displayName;
        this.potentialRate = potentialRate;
    }

    public String getId() {
        return this.id;
    }
    public String getName() {
        return this.displayName;
    }
    public Float getPotential() {
        return this.potentialRate;
    }

}
