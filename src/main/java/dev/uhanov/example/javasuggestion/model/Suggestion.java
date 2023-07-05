package dev.uhanov.example.javasuggestion.model;

import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

@Data
@Builder
public class Suggestion {
    private long id;
    @Min(value = 1, message = "Введен несуществующий сотрудник")
    private long employeId;
    @NotBlank(message = "Тема предложения не должна быть пустой")
    private String topic;
    @NotBlank(message = "Предложение не должно быть пустым")
    private String content;
    private Instant lastUpdate;
    private long statusId;

    public Map<String, Object> toMap() {
        return Map.of(
                "employe_id",  employeId,
                "topic",  topic,
                "content", content,
                "last_update", Timestamp.from(lastUpdate),
                "status_id", statusId
        );
    }
}
