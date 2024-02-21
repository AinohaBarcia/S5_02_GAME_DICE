package cat.itacademy.barcelonactiva.barcia.ainoha.s05.t02.n01.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    @Email(message = "Please input a valid email")
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
