package com.saudemosaico.especialistas.domain;

public enum Especialidade {
    ASSISTENTE_SOCIAL("Assistente Social"),
    MEDICO_DE_FAMILIA("Médico de Família"),
    CLINICO_GERAL("Clínico Geral"),
    PEDIATRIA("Pediatria"),
    GINECOLOGIA("Ginecologia"),
    CARDIOLOGIA("Cardiologia"),
    DERMATOLOGIA("Dermatologia"),
    PSIQUIATRIA("Psiquiatria"),
    ORTOPEDIA("Ortopedia"),
    OFTALMOLOGIA("Oftalmologia"),
    NUTRICIONISTA("Nutricionista"),
    PSICOLOGIA("Psicologia");

    private final String descricao;

    Especialidade(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
