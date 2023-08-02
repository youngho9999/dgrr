package live.dgrr.domain.game.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;

@Getter @AllArgsConstructor
public class GameRoomUser {

    @JsonIgnore
    private String principalName;
    @JsonIgnore
    private Long memberId;
    private String nickname;
    private String image;
    private String description;
    private int rating;
}
