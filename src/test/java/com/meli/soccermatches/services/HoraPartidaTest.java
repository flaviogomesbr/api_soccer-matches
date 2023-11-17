package com.meli.soccermatches.services;

import com.meli.soccermatches.dto.SoccerMatchesDto;
import com.meli.soccermatches.model.SoccerMatchesModel;
import com.meli.soccermatches.repository.SoccerMatchesRepository;
import com.meli.soccermatches.service.SoccerMatchesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HoraPartidaTest {

    @Mock
    private SoccerMatchesRepository soccerMatchesRepository;

    @InjectMocks
    private SoccerMatchesService soccerMatchesService;

    private SoccerMatchesDto createValidSoccerMatchesDto() {
        SoccerMatchesDto soccerMatchesDto = new SoccerMatchesDto();
        // Lançar data maior que atual para testar caso de erro, exemplo: year: 2024
        soccerMatchesDto.setDataHora(LocalDateTime.of(2023, 1, 25, 22, 0));
        return soccerMatchesDto;
    }

    @Test
    void testAdicionaPartidaValida() {
        SoccerMatchesDto soccerMatchesDto = createValidSoccerMatchesDto();

        when(soccerMatchesRepository.findByNomeClubeMandanteAndDataHoraBetween(any(), any(), any())).thenReturn(new ArrayList<>());
        when(soccerMatchesRepository.findByNomeClubeVisitanteAndDataHoraBetween(any(), any(), any())).thenReturn(new ArrayList<>());
        when(soccerMatchesRepository.findByEstadioAndDataHoraBetween(any(), any(), any())).thenReturn(new ArrayList<>());
        String result = soccerMatchesService.adicionaPartida(soccerMatchesDto);

        assertEquals("OK: Partida 0 adicionada ao banco de dados", result);
        verify(soccerMatchesRepository, times(1)).save(any(SoccerMatchesModel.class));
    }

    @Test
    void testAdicionaPartidaDataHoraMaiorQueAtual() {
        SoccerMatchesDto soccerMatchesDto = createValidSoccerMatchesDto();
        // Lançar data menor que atual para testar caso de erro, exemplo: year: 2020
        soccerMatchesDto.setDataHora(LocalDateTime.of(2024, 12, 31, 19, 35));
        String result = soccerMatchesService.adicionaPartida(soccerMatchesDto);

        assertEquals("ATENÇÃO: data e horário da partida deve ser menor que a data e hora atual", result);
        verify(soccerMatchesRepository, never()).save(any());
    }

    @Test
    void testAdicionaPartidaHorarioInicioAntesDasOito() {

        SoccerMatchesDto soccerMatchesDto = createValidSoccerMatchesDto();
        // Lançar hour e minute superior a 08:00 para testar caso de erro
        soccerMatchesDto.setDataHora(LocalDateTime.of(2023, 1, 1, 7, 59));

        String result = soccerMatchesService.adicionaPartida(soccerMatchesDto);

        assertEquals("ATENÇÃO: horário de início da partida não pode ser antes das 08:00", result);
        verify(soccerMatchesRepository, never()).save(any());
    }

    @Test
    void testAdicionaPartidaHorarioInicioDepoisDasDez() {

        SoccerMatchesDto soccerMatchesDto = createValidSoccerMatchesDto();
        // Lançar hour e minute inferior a 22:00 para testar erro
        soccerMatchesDto.setDataHora(LocalDateTime.of(2023, 1, 1, 22, 01));

        String result = soccerMatchesService.adicionaPartida(soccerMatchesDto);

        assertEquals("ATENÇÃO: horário de início da partida não pode ser após as 22:00", result);
        verify(soccerMatchesRepository, never()).save(any());
    }
}


