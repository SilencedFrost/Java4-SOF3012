package websocket;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String text;
    private int type;
    private String sender;
    private int count;
}
