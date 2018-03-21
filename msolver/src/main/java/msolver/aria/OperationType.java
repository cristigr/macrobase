package msolver.aria;

public enum OperationType {
    Min("Min"),
    Max("Max"),
    Count("Count"),
    Sum("Sum"),
    SecondPower("SecondPower"),
    ThirdPower("ThirdPower"),
    FourthPower("FourthPower"),
    FifthPower("FifthPower"),
    SixthPower("SixthPower"),
    SeventhPower("SeventhPower"),
    EightPower("EightPower"),
    NinthPower("NinthPower"),
    TenthPower("TenthPower"),
    Percentile001("Percentile001"),
    Percentile01("Percentile01"),
    Percentile05("Percentile05"),
    Percentile50("Percentile50"),
    Percentile95("Percentile95"),
    Percentile99("Percentile99"),
    Percentile999("Percentile999");

    private String asString;

    OperationType(String asString) {
        this.asString = asString;
    }
}
