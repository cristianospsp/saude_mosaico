package com.saudemosaico.usuarios.service;

import com.saudemosaico.usuarios.domain.Usuario;
import com.saudemosaico.usuarios.domain.Endereco;
import com.saudemosaico.usuarios.dto.UsuarioRequest;
import com.saudemosaico.usuarios.dto.UsuarioResponse;
import com.saudemosaico.usuarios.dto.EnderecoResponse;
import com.saudemosaico.usuarios.exception.UsuarioException;
import com.saudemosaico.usuarios.exception.UsuarioNotFoundException;
import com.saudemosaico.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    
    @Override
    @Transactional
    public UsuarioResponse criar(UsuarioRequest request) {
        validarNovoUsuario(request);
        
        Usuario usuario = new Usuario();
        preencherDadosUsuario(usuario, request);
        
        return converterParaResponse(usuarioRepository.save(usuario));
    }
    
    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        return usuarioRepository.findById(id)
            .map(this::converterParaResponse)
            .orElseThrow(() -> new UsuarioNotFoundException(id));
    }
    
    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
            .map(this::converterParaResponse)
            .orElseThrow(() -> new UsuarioNotFoundException(cpf));
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
            .map(this::converterParaResponse)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarAtivos() {
        return usuarioRepository.findByAtivoTrue().stream()
            .map(this::converterParaResponse)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNotFoundException(id));
            
        validarAtualizacaoUsuario(request, usuario);
        preencherDadosUsuario(usuario, request);
        
        return converterParaResponse(usuarioRepository.save(usuario));
    }
    
    @Override
    @Transactional
    public void inativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNotFoundException(id));
            
        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
    
    @Override
    @Transactional
    public void reativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new UsuarioNotFoundException(id));
            
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
    }
    
    private void validarNovoUsuario(UsuarioRequest request) {
        if (usuarioRepository.existsByCpf(request.getCpf())) {
            throw new UsuarioException("CPF já cadastrado");
        }
        
        if (request.getRg() != null && usuarioRepository.existsByRg(request.getRg())) {
            throw new UsuarioException("RG já cadastrado");
        }
        
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new UsuarioException("Email já cadastrado");
        }
    }
    
    private void validarAtualizacaoUsuario(UsuarioRequest request, Usuario usuario) {
        if (!usuario.getCpf().equals(request.getCpf()) && 
            usuarioRepository.existsByCpf(request.getCpf())) {
            throw new UsuarioException("CPF já cadastrado");
        }
        
        if (request.getRg() != null && 
            !request.getRg().equals(usuario.getRg()) && 
            usuarioRepository.existsByRg(request.getRg())) {
            throw new UsuarioException("RG já cadastrado");
        }
        
        if (!usuario.getEmail().equals(request.getEmail()) && 
            usuarioRepository.existsByEmail(request.getEmail())) {
            throw new UsuarioException("Email já cadastrado");
        }
    }
    
    private void preencherDadosUsuario(Usuario usuario, UsuarioRequest request) {
        usuario.setNome(request.getNome());
        usuario.setCpf(request.getCpf());
        usuario.setRg(request.getRg());
        usuario.setDataNascimento(request.getDataNascimento());
        usuario.setEmail(request.getEmail());
        usuario.setTelefone(request.getTelefone());
        usuario.setTipoSanguineo(request.getTipoSanguineo());
        usuario.setAlergias(new HashSet<>(request.getAlergias()));
        usuario.setMedicamentosEmUso(new HashSet<>(request.getMedicamentosEmUso()));
        
        if (request.getEnderecos() != null) {
            Set<Endereco> enderecos = request.getEnderecos().stream()
                .map(this::converterParaEndereco)
                .collect(Collectors.toSet());
            usuario.setEnderecos(enderecos);
        }
    }
    
    private Endereco converterParaEndereco(EnderecoRequest request) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(request.getLogradouro());
        endereco.setNumero(request.getNumero());
        endereco.setComplemento(request.getComplemento());
        endereco.setBairro(request.getBairro());
        endereco.setCidade(request.getCidade());
        endereco.setEstado(request.getEstado());
        endereco.setCep(request.getCep());
        endereco.setPrincipal(request.isPrincipal());
        return endereco;
    }
    
    private UsuarioResponse converterParaResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setNome(usuario.getNome());
        response.setCpf(usuario.getCpf());
        response.setRg(usuario.getRg());
        response.setDataNascimento(usuario.getDataNascimento());
        response.setEmail(usuario.getEmail());
        response.setTelefone(usuario.getTelefone());
        response.setTipoSanguineo(usuario.getTipoSanguineo());
        response.setAlergias(usuario.getAlergias());
        response.setMedicamentosEmUso(usuario.getMedicamentosEmUso());
        response.setAtivo(usuario.isAtivo());
        response.setCriadoEm(usuario.getCriadoEm());
        response.setAtualizadoEm(usuario.getAtualizadoEm());
        
        if (usuario.getEnderecos() != null) {
            Set<EnderecoResponse> enderecos = usuario.getEnderecos().stream()
                .map(this::converterParaEnderecoResponse)
                .collect(Collectors.toSet());
            response.setEnderecos(enderecos);
        }
        
        return response;
    }
    
    private EnderecoResponse converterParaEnderecoResponse(Endereco endereco) {
        EnderecoResponse response = new EnderecoResponse();
        response.setId(endereco.getId());
        response.setLogradouro(endereco.getLogradouro());
        response.setNumero(endereco.getNumero());
        response.setComplemento(endereco.getComplemento());
        response.setBairro(endereco.getBairro());
        response.setCidade(endereco.getCidade());
        response.setEstado(endereco.getEstado());
        response.setCep(endereco.getCep());
        response.setPrincipal(endereco.isPrincipal());
        return response;
    }
}
