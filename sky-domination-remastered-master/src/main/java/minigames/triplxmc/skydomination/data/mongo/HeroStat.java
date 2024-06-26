package minigames.triplxmc.skydomination.data.mongo;

import lombok.Getter;

public class HeroStat {

    @Getter
    private final int kills,deaths,games,abilityOne,abilityTwo,baseCaptured;


    public HeroStat(int kills, int deaths, int games, int abilityOne, int abilityTwo, int basesCaptured) {
        this.kills = kills;
        this.deaths = deaths;
        this.games = games;
        this.abilityOne = abilityOne;
        this.abilityTwo = abilityTwo;
        this.baseCaptured = basesCaptured;
    }



}
