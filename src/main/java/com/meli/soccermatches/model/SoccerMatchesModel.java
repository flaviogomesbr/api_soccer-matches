package com.meli.soccermatches.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "partida")
public class SoccerMatchesModel {
    @Id
    @Column(name = "idPartida")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Column(nullable = false)
    private String nomeClubeMandante;

    @Getter
    @Column(nullable = false)
    private int resultadoClubeMandante;

    @Getter
    @Column(nullable = false)
    private String nomeClubeVisitante;

    @Getter
    @Column(nullable = false)
    private int resultadoClubeVisitante;

    @Getter
    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Getter
    @Column(nullable = false)
    private String estadio;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public void setDataHora(String dataHora) {
        this.dataHora = LocalDateTime.parse(dataHora);
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }
}
