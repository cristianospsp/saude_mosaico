package com.saudemosaico.agendamento.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class FeriadoService {
    private final Set<LocalDate> feriados = new HashSet<>();

    public FeriadoService() {
        // Adicionar feriados nacionais fixos
        int ano = LocalDate.now().getYear();
        feriados.add(LocalDate.of(ano, 1, 1));   // Ano Novo
        feriados.add(LocalDate.of(ano, 4, 21));  // Tiradentes
        feriados.add(LocalDate.of(ano, 5, 1));   // Dia do Trabalho
        feriados.add(LocalDate.of(ano, 9, 7));   // Independência
        feriados.add(LocalDate.of(ano, 10, 12)); // Nossa Senhora
        feriados.add(LocalDate.of(ano, 11, 2));  // Finados
        feriados.add(LocalDate.of(ano, 11, 15)); // Proclamação da República
        feriados.add(LocalDate.of(ano, 12, 25)); // Natal
    }

    public boolean isFeriado(LocalDate data) {
        return feriados.contains(data);
    }

    public void adicionarFeriado(LocalDate data) {
        feriados.add(data);
    }
}
