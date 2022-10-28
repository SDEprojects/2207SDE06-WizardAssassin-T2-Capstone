import java.util.*;

class KingdomMap {
    private String[][] kingdomMap;

    public KingdomMap(String[][] kingdomMap) {
        this.setKingdomMap(kingdomMap);
    }

    public String[][] getKingdomMap() {
        return kingdomMap;
    }

    public void setKingdomMap(String[][] kingdomMap) {
        this.kingdomMap = kingdomMap;
    }

    public static void printMapHeader() {
        System.out.println("                         *********************************************");
        System.out.println("                                             MAP                       ");
        System.out.println("                         *********************************************");
    }

    public void printMap() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.printf("%2s", this.getKingdomMap()[i][j]);
            }
            System.out.println();
        }
    }

    public static List<KingdomMap> showKingdomMap () {
        List<KingdomMap> mapList = new ArrayList<>();
         String[][] areaMap = {
                 {"*", "***", "***", "**********", "*****************", "*********", "**************", "***", "***", "***", "***", "***", "***", "***", "****************"},
                 {"*", "************", "|", "**************", "|", " Lab", "|", " Wizards Tower  ", "|", "*", "*", "*", "|", "   Church  ", " | *****************"},
                 {"*", "************", "|", "**************", "|", "    ", "|", " ____X__________", "|", "*", "*", "*", "|", "           ", " | *****************"},
                 {"*", "************", "|", "**************", "|", "    ", " ", "                ", "|", "*", "*", "*", "|", " ____  ____", " | *****************"},
                 {"*", "************", "|", "**************", "|", "    ", "|", "   Foyer        ", "|", " ", " ", " ", " ", "           ", "              ******"},
                 {"*", "************", "|", "**************", "|", " ___", " | ____XX_________", "|", " ", " ", " ", " ", " ", "         ", "              ******"},
                 {"*", "************", "|", "**************", "|", "    ", "           ", "        |", " ", " ", " ", " ", " ", "          ", "             ******"},
                 {"*", "************", "|", "**************", "|", "    ", "           ", "        |", " ", " ", " ", " ", " ", "         Courtyard     ", "******"},
                 {"*", "            ", "|", "              ", "|", "    ", "           ", "        X", " ", " ", " ", " ", " ", "                       ", "******"},
                 {"*", "            ", "X", "              ", "|", "    ", "           ", "        X", " ", " ", " ", " ", " ", "                       ", "******"},
                 {"*", "            ", "X", "              ", "X", "    ", " Great Hall", "        |", " ", " ", " ", " ", " ", "                       ", "******"},
                 {"*", "King's Suite", "|", "    Lounge    ", "X", "    ", "           ", "        |", " ", " ", " ", " ", " ", "                       ", "******"},
                 {"*", "            ", "|", " _____  ______", "|", "    ", "           ", "__", "_____________", "___", "_____  _____", "___", "______", "*****", "****"},
                 {"*", "            ", "|", "              ", "|", "    ", "           ", "|", " |", " Dungeon", " |||", " Watchtower", " |||", " Armory", "|*********"},
                 {"*", "            ", "|", " Royal Library", "|", "    ", "           ", "|", " |", "        ", " |||", "           ", " |||", "       ", "|*********"},
                 {"*", "***", "***", "***", "***", "***", "***", "***", "***", "***", "***", "***", "***************", "*****************", "****************************"}
         };
         mapList.add(new KingdomMap(areaMap));
         return mapList;
    }
}