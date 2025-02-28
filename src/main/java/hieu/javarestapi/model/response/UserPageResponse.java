package hieu.javarestapi.model.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserPageResponse extends PageResponseAbstract{
    private List<UserResponse> data;
}
