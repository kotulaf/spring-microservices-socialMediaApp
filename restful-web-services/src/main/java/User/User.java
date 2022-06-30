package User;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private int id;
    private String name;
    private Date dob;

    @Override
    public String toString() {
        return String.format("User [id=%s, name=%s, birthDate=%s]", id, name, dob);
    }
}
