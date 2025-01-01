package com.saudemosaico.agendamento.domain;

public enum Especialidade {
    CLINICO_GERAL("Clínico Geral"),
    PEDIATRIA("Pediatria"),
    GINECOLOGIA("Ginecologia"),
    CARDIOLOGIA("Cardiologia"),
    DERMATOLOGIA("Dermatologia"),
    PSIQUIATRIA("Psiquiatria"),
    ORTOPEDIA("Ortopedia"),
    OFTALMOLOGIA("Oftalmologia"),
    NUTRICIONISTA("Nutricionista"),
    PSICOLOGIA("Psicologia"),
    FISIOTERAPIA("Fisioterapia"),
    FONOAUDIOLOGIA("Fonoaudiologia"),
    ASSISTENTE_SOCIAL("Assistente Social");

    private final String descricao;

    Especialidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}