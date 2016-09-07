package model;

/**
 * Created by philt on 07.09.2016.
 */
public enum Team {
    INTUITION, WEISHEIT, WAGEMUT;

    public static Team getTeam(String teamName){
        switch (teamName){
            case "INTUITION":
                return Team.INTUITION;
            case "WEISHEIT":
                return Team.WEISHEIT;
            case "WAGEMUT":
                return Team.WAGEMUT;
        }
        return null;
    }
}
