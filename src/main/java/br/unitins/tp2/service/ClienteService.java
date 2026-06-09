package br.unitins.tp2.service;

import br.unitins.tp2.dto.ClienteAlterarSenhaDTO;
import br.unitins.tp2.dto.ClienteDTO;
import br.unitins.tp2.dto.ClienteResponseDTO;
import br.unitins.tp2.dto.ClienteUpdateDTO;
import br.unitins.tp2.dto.EnderecoDTO;
import br.unitins.tp2.dto.TelefoneDTO;

public interface ClienteService {

    ClienteResponseDTO create(ClienteDTO dto);

    ClienteResponseDTO update(Long id, ClienteUpdateDTO dto);

    ClienteResponseDTO findById(Long id);

    ClienteResponseDTO findByUsername(String username);

    ClienteResponseDTO findByCpf(String cpf);

    ClienteResponseDTO adicionarEndereco(Long id, EnderecoDTO dto);

    ClienteResponseDTO atualizarEndereco(Long id, int index, EnderecoDTO dto);

    ClienteResponseDTO removerEndereco(Long id, int index);

    ClienteResponseDTO adicionarTelefone(Long id, TelefoneDTO dto);

    ClienteResponseDTO removerTelefone(Long id, int index);

    void alterarSenha(Long id, ClienteAlterarSenhaDTO dto);

}
