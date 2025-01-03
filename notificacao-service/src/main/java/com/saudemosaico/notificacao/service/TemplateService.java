package com.saudemosaico.notificacao.service;

import com.saudemosaico.notificacao.dto.TemplateRequest;
import com.saudemosaico.notificacao.dto.TemplateResponse;
import java.util.List;

public interface TemplateService {
    TemplateResponse criar(TemplateRequest request);
    TemplateResponse atualizar(String codigo, TemplateRequest request);
    TemplateResponse buscarPorCodigo(String codigo);
    List<TemplateResponse> listarTemplates();
    void inativar(String codigo);
    void reativar(String codigo);
}
