package showmessage;


/**
 * @author gntsi
 * @version 1.0
 * @created 23-���-2023 12:21:12
 */
public enum ColorsType {
    RESET,
    // ----------------------------------------Цвета
    BLACK,
    RED,
    GREEN,
    YELLOW,
    BLUE,
    PURPLE,
    CYAN,
    WHITE,
    //-----------------------------------------Типы цвета шрифтов
    REGULAR_COLORS,
    BOLD,
    UNDERLINE,
    HIGH_INTENSITY,
    BOLD_HIGH_INTENSITY,
    //-----------------------------------------Типы цвета фона
    BACKGROUND,
    HIGH_INTENSITY_BACKGROUNDS;


    public static String getColorText(ColorsType color, ColorsType colorType) {
        return  getTypeText(colorType) + getColor(color);
    }

    public static String getColorBack(ColorsType color, boolean intensity) {
        //   BACKGROUND -> s = "\033[4";
        //   HIGH_INTENSITY_BACKGROUNDS -> s = "\033[0;10";
        String s = (intensity) ? "\033[0;10" : "\033[4";
        return s + getColor(color);
    }

    public static String resetColor() {
        //   RESET -> s = "\033[0m";
        return "\033[0m";
    }
    private static String getColor(ColorsType color) {

        String s;
        switch (color) {
            case BLACK -> s = "0m";
            case RED -> s = "1m";
            case GREEN -> s = "2m";
            case YELLOW -> s = "3m";
            case BLUE -> s = "4m";
            case PURPLE -> s = "5m";
            case CYAN -> s = "6m";
            case WHITE -> s = "7m";
            default -> s = "";
        }
        return s;
    }

    private static String getTypeText(ColorsType colorsType) {
        String s;
        switch (colorsType) {
            case REGULAR_COLORS -> s = "\033[0;3";
            case BOLD -> s = "\033[1;3";
            case UNDERLINE -> s = "\033[4;3";
            case HIGH_INTENSITY -> s = "\033[0;9";
            case BOLD_HIGH_INTENSITY -> s = "\033[1;9";
            default -> s = "";
        }
        return s;
    }
}