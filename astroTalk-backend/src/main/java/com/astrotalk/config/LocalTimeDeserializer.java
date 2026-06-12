package com.astrotalk.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalTime;

public class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {
    @Override
    public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        if (node.isTextual()) {
            return LocalTime.parse(node.asText());
        }
        int hour = node.get("hour").asInt();
        int minute = node.get("minute").asInt();
        int second = node.has("second") ? node.get("second").asInt() : 0;
        int nano = node.has("nano") ? node.get("nano").asInt() : 0;
        return LocalTime.of(hour, minute, second, nano);
    }
}
