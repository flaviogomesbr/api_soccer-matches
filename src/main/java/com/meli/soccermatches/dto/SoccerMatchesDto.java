package com.meli.soccermatches.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SoccerMatchesDto {
    private Long id;
    @NotBlank
    private String nomeClubeMandante;

    @NotNull
    @Min(value = 0)
    private int resultadoClubeMandante;
    @NotBlank
    private String nomeClubeVisitante;
    @NotNull
    @Min(value = 0)
    private int resultadoClubeVisitante;

    @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
    private LocalDateTime dataHora;
    @NotBlank
    private String estadio;


    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeClubeMandante(String nomeClubeMandante) {
        this.nomeClubeMandante = nomeClubeMandante;
    }

    public void setResultadoClubeMandante(int resultadoClubeMandante) {
        this.resultadoClubeMandante = resultadoClubeMandante;
    }

    public void setNomeClubeVisitante(String nomeClubeVisitante) {
        this.nomeClubeVisitante = nomeClubeVisitante;
    }

    public void setResultadoClubeVisitante(int resultadoClubeVisitante) {
        this.resultadoClubeVisitante = resultadoClubeVisitante;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }
}
