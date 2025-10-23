package com.deliverytech.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteRequest {

    @NotBlank(message = "O nome não pode estar em branco")
    @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "A categoria não pode estar em branco")
    private String categoria;

    @NotBlank(message = "O telefone não pode estar em branco")
    private String telefone;

    @NotNull(message = "A taxa de entrega é obrigatória")
    @DecimalMin(value = "0.0", message = "A taxa de entrega não pode ser negativa")
    private BigDecimal taxaEntrega;

    @NotNull(message = "O tempo de entrega é obrigatório")
    @Min(value = 10, message = "O tempo de entrega deve ser de no mínimo 10 minutos")
    @Max(value = 120, message = "O tempo de entrega não pode exceder 120 minutos")
    private Integer tempoEntregaMinutos;
}