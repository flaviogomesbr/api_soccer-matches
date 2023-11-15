package com.meli.soccermatches.repository;

import com.meli.soccermatches.model.SoccerMatchesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;


public interface SoccerMatchesRepository extends JpaRepository<SoccerMatchesModel, Long> {
    List<SoccerMatchesModel> findByEstadioAndDataHoraBetween(String estadio, LocalDateTime start, LocalDateTime end);

    List<SoccerMatchesModel> findByNomeClubeMandanteAndDataHoraBetween(String nomeClubeMandante, LocalDateTime start, LocalDateTime end);

    List<SoccerMatchesModel> findByNomeClubeVisitanteAndDataHoraBetween(String nomeClubeVisitante, LocalDateTime start, LocalDateTime end);


    @Query("SELECT p FROM SoccerMatchesModel p WHERE p.resultadoClubeMandante = 0 AND p.resultadoClubeVisitante = 0")
    List<SoccerMatchesModel> buscaPartidaSemGols();

    @Query("SELECT p FROM SoccerMatchesModel p WHERE ABS(p.resultadoClubeMandante - p.resultadoClubeVisitante) >= 3")
    List<SoccerMatchesModel> buscaGoleada();

    @Query("SELECT p FROM SoccerMatchesModel p WHERE p.estadio = ?1")
    List<SoccerMatchesModel> buscaPartidasPorEstadio(String estadio);

    @Query("SELECT p FROM SoccerMatchesModel p WHERE p.nomeClubeMandante = ?1 OR p.nomeClubeVisitante = ?1")
    List<SoccerMatchesModel> buscaPartidasPorClube(String clube);

    @Query("SELECT p FROM SoccerMatchesModel p WHERE p.nomeClubeMandante = ?1")
    List<SoccerMatchesModel> buscaPartidasPorClubeMandante(String clube);

    @Query("SELECT p FROM SoccerMatchesModel p WHERE p.nomeClubeVisitante = ?1")
    List<SoccerMatchesModel> buscaPartidasPorClubeVisitante(String clube);

}
