package com.florek.NBA_backend.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.florek.NBA_backend.model.basketball.Match;
import com.florek.NBA_backend.model.basketball.Performance;
import com.florek.NBA_backend.model.basketball.Player;

import java.io.IOException;

public class PerformanceDeserializer extends StdDeserializer {

    public PerformanceDeserializer() {
        this(null);
    }

    public PerformanceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int id = (int) node.get("id").numberValue();
        int ast = node.get("ast").asInt(0);
        int blk =  node.get("blk").asInt(0);
        int dreb =  node.get("dreb").asInt(0);
        int oreb =  node.get("oreb").asInt(0);
        int reb = node.get("reb").asInt(0);
        int pts = node.get("pts").asInt(0);
        double fg3pct = node.get("fg3_pct").asDouble();
        int fg3a = node.get("fg3a").asInt(0);
        int fg3m =  node.get("fg3m").asInt(0);
        double fgpct = node.get("fg_pct").asDouble();
        int fga =  node.get("fga").asInt(0);
        int fgm =  node.get("fgm").asInt(0);
        int stl = node.get("stl").asInt(0);
        int turnover =  node.get("turnover").asInt(0);
        int fta = node.get("fta").asInt(0);
        int ftm = node.get("ftm").asInt(0);
        double ftpct = node.get("ft_pct").asDouble();
        int pf = node.get("pf").asInt(0);

        String min = node.get("min").asText();
        int player_id = 17;
        if(node.get("player").hasNonNull("id")){
            player_id = node.get("player").get("id").asInt();
        }

        int match_id = node.get("game").get("id").asInt();
        int season = node.get("game").get("season").asInt();
        return new Performance(id, new Player(player_id), new Match(match_id), ast, blk, dreb, oreb, reb, pts, fg3pct, fg3a, fg3m, fgpct, fga, fgm, stl, turnover, fta, ftm, ftpct, pf, min, season);
    }
}
