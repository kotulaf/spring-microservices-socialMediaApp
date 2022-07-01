package User;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

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

    @Size(min=2, message="Name should have at least 2 characters")            // now the name will have a simple validation, we will be checking that it has two characters at the minimum
    private String name;

    @Past                   // here we check that the date the user inputs was in the past, it doesn't make any sense to add a date in the future
    private Date dob;

    @Override
    public String toString() {
        return String.format("User [id=%s, name=%s, birthDate=%s]", id, name, dob);
    }
}
