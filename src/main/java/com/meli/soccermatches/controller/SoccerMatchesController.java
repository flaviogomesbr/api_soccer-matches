package com.meli.soccermatches.controller;

import com.meli.soccermatches.dto.SoccerMatchesDto;
import com.meli.soccermatches.model.SoccerMatchesModel;
import com.meli.soccermatches.repository.SoccerMatchesRepository;
import com.meli.soccermatches.service.SoccerMatchesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("soccer-matches")
public class SoccerMatchesController {

    @Autowired
    private SoccerMatchesService soccerMatchesService;

    @Autowired
    private SoccerMatchesRepository soccerMatchesRepository;

    @GetMapping
    public ResponseEntity<List<SoccerMatchesModel>> getAllPartidas() {
        return ResponseEntity.status(HttpStatus.OK).body(soccerMatchesService.findAll());
    }

    @GetMapping("porEstadio/{estadio}")
    public ResponseEntity<?> listarPartidas(@PathVariable("estadio") String estadio) {
        return ResponseEntity.ok(this.soccerMatchesRepository.buscaPartidasPorEstadio(estadio));
    }

    @GetMapping("porClube/{clube}")
    public ResponseEntity<?> buscaPartidasPorClube(@PathVariable("clube") String clube) {
        return ResponseEntity.ok(this.soccerMatchesRepository.buscaPartidasPorClube(clube));
    }

    @GetMapping("porClubeMandante/{clube}")
    public ResponseEntity<?> buscaPartidasPorClubeMandante(@PathVariable("clube") String clube) {
        return ResponseEntity.ok(this.soccerMatchesRepository.buscaPartidasPorClubeMandante(clube));
    }

    @GetMapping("porClubeVisitante/{clube}")
    public ResponseEntity<?> buscaPartidasPorClubeVisitante(@PathVariable("clube") String clube) {
        return ResponseEntity.ok(this.soccerMatchesRepository.buscaPartidasPorClubeVisitante(clube));
    }

    @GetMapping("partidaSemGols")
    public List<SoccerMatchesModel> partidaSemGols() {
        return soccerMatchesRepository.buscaPartidaSemGols();
    }

    @GetMapping("buscaGoleada")
    public List<SoccerMatchesModel> buscaGoleada() {
        return soccerMatchesRepository.buscaGoleada();
    }

    @PostMapping
    public String adicionaPartida(@Valid @RequestBody SoccerMatchesDto soccerMatchesDto) {
        return soccerMatchesService.adicionaPartida(soccerMatchesDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity atualizaPartida(@PathVariable("id") long id,
                                          @Valid @RequestBody SoccerMatchesDto soccerMatchesDto) {
        return soccerMatchesService.atualizaPartida(id, soccerMatchesDto);
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> deletaPartida(@PathVariable long id) {
        return soccerMatchesService.deletaPartida(id);
    }

    @DeleteMapping("/deletaTudo")
    public ResponseEntity<?> deletaTodos() {
        return soccerMatchesService.deletaTudo();
    }

    @ControllerAdvice
    public class CustomExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });

            errors.put("message", "ATENÇÃO: Nome dos clubes, data, hora e estádio são campos obrigatórios.");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
    }
}
