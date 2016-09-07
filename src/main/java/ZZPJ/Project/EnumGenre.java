package ZZPJ.Project;

/**
 * Possible movie genres
 */
public enum EnumGenre {
    Action("1"), Adventure("2"), Animation("3"), Biography("4"), Comedy("5"), Crime("6"), Drama("7"),
    Family("8"), Fantasy("9"), FilmNoir("10"), History("11"), Horror("12"), Music("13"), Musical("14"),
    Mystery("15"), Romance("16"), SciFi("17"), Sport("18"), Thriller("19"), War("20"), Western("21");

    public String value;

    /**
     * @param newValue - id of movie genres
     */
    private EnumGenre(String newValue) {
        value = newValue;
    }
}
