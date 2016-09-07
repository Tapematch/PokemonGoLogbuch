package model;

/**
 * Created by philt on 07.09.2016.
 */
public enum Team {
    INTUITION, WEISHEIT, WAGEMUT;

    public static Team getTeam(String teamName){
        switch (teamName.toLowerCase()){
            case "intuition":
                return Team.INTUITION;
            case "weisheit":
                return Team.WEISHEIT;
            case "wagemut":
                return Team.WAGEMUT;
        }
        return null;
    }
}
