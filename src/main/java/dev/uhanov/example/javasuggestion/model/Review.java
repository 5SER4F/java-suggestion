package dev.uhanov.example.javasuggestion.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.xml.stream.XMLEventWriter;
import java.util.Map;

@Data
@Builder
public class Review {
    long id;
    @Min(value = 1, message = "Введен несуществующий сотрудник")
    private long employeId;
    @Min(value = 1, message = "Введено несуществующее предложение")
    private long suggestionId;
    @NotBlank(message = "Ревью не может быть пустым")
    private String content;
    private boolean approve;

    public Map<String, Object> toMap(){
        return Map.of(
                "employe_id", employeId,
                "suggestion_id", suggestionId,
                "content", content,
                "approve", approve
        );
    }
}
