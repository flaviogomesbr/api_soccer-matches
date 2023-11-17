package com.meli.soccermatches.service;

import com.meli.soccermatches.dto.SoccerMatchesDto;
import com.meli.soccermatches.model.SoccerMatchesModel;
import com.meli.soccermatches.repository.SoccerMatchesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class SoccerMatchesService {

    public final SoccerMatchesRepository soccerMatchesRepository;

    public SoccerMatchesService(SoccerMatchesRepository soccerMatchesRepository) {
        this.soccerMatchesRepository = soccerMatchesRepository;
    }

    public String adicionaPartida(SoccerMatchesDto soccerMatchesDto) {
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        if (soccerMatchesDto.getDataHora().isAfter(dataHoraAtual)) {
            return "ATENÇÃO: data e horário da partida deve ser menor que a data e hora atual";
        }

        LocalDateTime horaCadastrada = soccerMatchesDto.getDataHora();
        String dataHoraString = horaCadastrada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String apenasHoraString = dataHoraString.substring(11, 19);
        LocalTime apenasHora = LocalTime.parse(apenasHoraString);
        LocalTime horaMinimo = LocalTime.parse("08:00");
        if (apenasHora.isBefore(horaMinimo)) {
            return "ATENÇÃO: horário de início da partida não pode ser antes das 08:00";
        }

        LocalTime dataHoraMaximo = LocalTime.parse("22:00");
        if (apenasHora.isAfter(dataHoraMaximo)) {
            return "ATENÇÃO: horário de início da partida não pode ser após as 22:00";
        }

        String estadio = soccerMatchesDto.getEstadio();
        String clubeMandante = soccerMatchesDto.getNomeClubeMandante();
        String clubeVisitante = soccerMatchesDto.getNomeClubeVisitante();
        List<SoccerMatchesModel> partidasNoMesmoEstadioNoMesmoDia = soccerMatchesRepository
                .findByEstadioAndDataHoraBetween(estadio,
                        horaCadastrada.withHour(0).withMinute(0).withSecond(0),
                        horaCadastrada.withHour(23).withMinute(59).withSecond(59));

        if (!partidasNoMesmoEstadioNoMesmoDia.isEmpty()) {
            return "ATENÇÃO: Já existe uma partida cadastrada neste estádio na data que você indicou";
        }

        List<SoccerMatchesModel> partidasDoClubeMandante = soccerMatchesRepository
                .findByNomeClubeMandanteAndDataHoraBetween(clubeMandante,
                        horaCadastrada.minusDays(2), horaCadastrada);

        if (!partidasDoClubeMandante.isEmpty()) {
            return "ATENÇÃO: Já existe uma partida do clube mandante com menos de dois dias de intervalo";
        }

        List<SoccerMatchesModel> partidasDoClubeVisitante = soccerMatchesRepository
                .findByNomeClubeVisitanteAndDataHoraBetween(clubeVisitante,
                        horaCadastrada.minusDays(2), horaCadastrada);

        if (!partidasDoClubeVisitante.isEmpty()) {
            return "ATENÇÃO: Já existe uma partida do clube visitante com menos de dois dias de intervalo";
        }


        SoccerMatchesModel soccerMatchesModel = new SoccerMatchesModel();
        soccerMatchesModel.setNomeClubeMandante(soccerMatchesDto.getNomeClubeMandante());
        soccerMatchesModel.setResultadoClubeMandante(soccerMatchesDto.getResultadoClubeMandante());
        soccerMatchesModel.setNomeClubeVisitante(soccerMatchesDto.getNomeClubeVisitante());
        soccerMatchesModel.setResultadoClubeVisitante(soccerMatchesDto.getResultadoClubeVisitante());
        soccerMatchesModel.setDataHora(String.valueOf(soccerMatchesDto.getDataHora()));
        soccerMatchesModel.setEstadio(soccerMatchesDto.getEstadio());
        soccerMatchesRepository.save(soccerMatchesModel);
        return "OK: Partida " + soccerMatchesModel.getId() + " adicionada ao banco de dados";
    }

    public ResponseEntity<?> deletaPartida(long id) {
        Optional<SoccerMatchesModel> partidaOptional = soccerMatchesRepository.findById(id);
        if (!partidaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ATENÇÃO: Partida não encontrada no banco de dados.");
        }
        return soccerMatchesRepository.findById(id)
                .map(record -> {
                    soccerMatchesRepository.deleteById(id);
                    return ResponseEntity.ok().body("OK: Partida excluída do banco de dados!");
                }).orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> deletaTudo() {
        soccerMatchesRepository.deleteAll();
        return ResponseEntity.ok().body("OK: Todos os registros foram deletados do banco de dados.");
    }

    public ResponseEntity atualizaPartida(long id, SoccerMatchesDto soccerMatchesDto) {
        LocalDateTime localDateTime = LocalDateTime.now();

        if (soccerMatchesDto.getDataHora().isAfter(localDateTime)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ATENÇÃO: horário da partida não pode ser maior que o horário atual!");
        }

        LocalDateTime horaCadastrada = soccerMatchesDto.getDataHora();
        String dataHoraString = horaCadastrada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        String apenasHoraString = dataHoraString.substring(11, 19);
        LocalTime apenasHora = LocalTime.parse(apenasHoraString);
        LocalTime horaMinimo = LocalTime.parse("08:00");
        if (apenasHora.isBefore(horaMinimo)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ATENÇÃO: horário de início da partida não pode ser antes das 08:00!");
        }

        LocalTime dataHoraMaximo = LocalTime.parse("22:00");
        if (apenasHora.isAfter(dataHoraMaximo)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ATENÇÃO: horário de início da partida não pode ser após às 22:00!");
        }

        Optional<SoccerMatchesModel> partidaOptional = soccerMatchesRepository.findById(id);
        if (!partidaOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ATENÇÃO: Partida não encontrada!");
        }
        return soccerMatchesRepository.findById(id)
                .map(item -> {
                    item.setNomeClubeMandante(soccerMatchesDto.getNomeClubeMandante());
                    item.setResultadoClubeMandante(soccerMatchesDto.getResultadoClubeMandante());
                    item.setNomeClubeVisitante(soccerMatchesDto.getNomeClubeVisitante());
                    item.setResultadoClubeVisitante(soccerMatchesDto.getResultadoClubeVisitante());
                    item.setDataHora(String.valueOf(soccerMatchesDto.getDataHora()));
                    item.setEstadio(soccerMatchesDto.getEstadio());
                    SoccerMatchesModel atualizaSoccerMatchesModel = soccerMatchesRepository.save(item);
                    return ResponseEntity.ok().body(atualizaSoccerMatchesModel);
                }).orElse(ResponseEntity.notFound().build());
    }

    public List<SoccerMatchesModel> findAll() {
        return soccerMatchesRepository.findAll();
    }
}
