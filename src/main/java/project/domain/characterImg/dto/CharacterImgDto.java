package project.domain.characterImg.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

@Getter
@Setter
@Builder
public class CharacterImgDto {
    private String name;
}
