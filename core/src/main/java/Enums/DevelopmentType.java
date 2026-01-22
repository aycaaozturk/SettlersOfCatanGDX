package Enums;

public enum DevelopmentType {
     Knight("Move the robber. Steal 1 resource from the owner of a settlement or city adjacent to the robber's new hex."),
    Monopoly("When you play this card, announce 1 type of resource. All other players must give you all of their resources of that type."),
    YearOfPlenty("Take any 2 resources from the supply. Add them to your hand. They can be 2 of the same resources or 2 different resources."),
    RoadBuilding("Place 2 new roads as if you had just built them.");


    private final String description;

    DevelopmentType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
