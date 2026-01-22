package Enums;

public enum AchievementType {
    LargestArmy("Awarded to the player who has played at least 3 Knight cards and has the largest army.  \n" +
        "This player receives the \"Largest Army\" card and gains 2 victory points.  \n" +
        "If another player plays more Knight cards, they take the card and the points."),

    LongestRoad("Awarded to the player who has built the longest continuous road of at least 5 segments.  \n" +
        "This player receives the \"Longest Road\" card and gains 2 victory points.  \n" +
        "If another player builds a longer road, they take the card and the points.");



    private final String description;

    AchievementType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
