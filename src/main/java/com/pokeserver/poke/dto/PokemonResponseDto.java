package com.pokeserver.poke.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class PokemonResponseDto {

    private int id;
    private String name;
    private int order;
    private int height;
    private int weight;
    
    @JsonProperty("base_experience")
    private int baseExperience;

    @JsonProperty("is_default")
    private boolean isDefault;

    @JsonProperty("location_area_encounters")
    private String locationAreaEncounters;

    private List<AbilityInfo> abilities;
    private List<FormInfo> forms;
    private List<GameIndex> game_indices;
    private List<HeldItemInfo> held_items;
    private List<MoveInfo> moves;
    private List<StatInfo> stats;
    private List<TypeInfo> types;
    private SpeciesInfo species;
    private SpritesInfo sprites;
    private CriesInfo cries;

    // --- 내부 계층 구조 클래스들 ---

    @Getter @NoArgsConstructor
    public static class AbilityInfo {
        private Ability ability;
        @JsonProperty("is_hidden")
        private boolean isHidden;
        private int slot;

        @Getter @NoArgsConstructor
        public static class Ability {
            private String name;
            private String url;
        }
    }

    @Getter @NoArgsConstructor
    public static class FormInfo {
        private String name;
        private String url;
    }

    @Getter @NoArgsConstructor
    public static class GameIndex {
        @JsonProperty("game_index")
        private int gameIndex;
        private Version version;
    }

    @Getter @NoArgsConstructor
    public static class HeldItemInfo {
        private Item item;
        @JsonProperty("version_details")
        private List<VersionDetail> versionDetails;

        @Getter @NoArgsConstructor
        public static class Item {
            private String name;
            private String url;
        }

        @Getter @NoArgsConstructor
        public static class VersionDetail {
            private int rarity;
            private Version version;
        }
    }

    @Getter @NoArgsConstructor
    public static class MoveInfo {
        private Move move;
        @JsonProperty("version_group_details")
        private List<VersionGroupDetail> versionGroupDetails;

        @Getter @NoArgsConstructor
        public static class Move {
            private String name;
            private String url;
        }

        @Getter @NoArgsConstructor
        public static class VersionGroupDetail {
            @JsonProperty("level_learned_at")
            private int levelLearnedAt;
            @JsonProperty("move_learn_method")
            private MoveLearnMethod moveLearnMethod;
            @JsonProperty("version_group")
            private VersionGroup versionGroup;
        }
    }

    @Getter @NoArgsConstructor
    public static class StatInfo {
        @JsonProperty("base_stat")
        private int baseStat;
        private int effort;
        private Stat stat;

        @Getter @NoArgsConstructor
        public static class Stat {
            private String name;
            private String url;
        }
    }

    @Getter @NoArgsConstructor
    public static class TypeInfo {
        private int slot;
        private Type type;

        @Getter @NoArgsConstructor
        public static class Type {
            private String name;
            private String url;
        }
    }

    @Getter @NoArgsConstructor
    public static class SpeciesInfo {
        private String name;
        private String url;
    }

    @Getter @NoArgsConstructor
    public static class SpritesInfo {
        @JsonProperty("front_default")
        private String frontDefault;
        @JsonProperty("back_default")
        private String backDefault;
        @JsonProperty("front_shiny")
        private String frontShiny;
        @JsonProperty("back_shiny")
        private String backShiny;
        // 필요에 따라 other, versions 하위 필드도 추가 가능합니다.
    }

    @Getter @NoArgsConstructor
    public static class CriesInfo {
        private String latest;
        private String legacy;
    }
    @Getter @NoArgsConstructor
    public static class Version {
        private String name;
        private String url;
    }

    @Getter @NoArgsConstructor
    public static class MoveLearnMethod {
        private String name;
        private String url;
    }

    @Getter @NoArgsConstructor
    public static class VersionGroup {
        private String name;
        private String url;
    }
}
