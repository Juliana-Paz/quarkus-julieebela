package br.unitins.tp2.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.unitins.tp2.dto.ItemPedidoDTO;
import br.unitins.tp2.dto.PedidoDTO;
import br.unitins.tp2.dto.PedidoResponseDTO;
import br.unitins.tp2.model.ItemPedido;
import br.unitins.tp2.model.Pedido;
import br.unitins.tp2.model.Usuario;
import br.unitins.tp2.repository.PedidoRepository;
import br.unitins.tp2.repository.PlanoRepository;
import br.unitins.tp2.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    PlanoRepository planoRepository;


    @Override
    public List<PedidoResponseDTO> getAll() {
        return pedidoRepository.findAll().stream().map(p -> PedidoResponseDTO.valueOf(p)).toList();
    }

    @Override
    // public PedidoResponseDTO findById(Long id, String username) {
    public PedidoResponseDTO findById(Long id) {
        // implementacao de segurança para retornar apenas pedidos de um determinado usuário
        // Pedido pedido = pedidoRepository.findById(id, username);
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null)
            throw new NotFoundException("Pedido não encontrada.");
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    @Transactional
    public PedidoResponseDTO create(PedidoDTO pedidoDTO, String username) throws ConstraintViolationException {

        Pedido entity = new Pedido();
        entity.setData(LocalDateTime.now());
        // vcs devem validar o total envidado pelo frontend com o total dos itens dos produtos 
        // pra casa!!!
        entity.setTotal(pedidoDTO.total());
        entity.setUsuario(usuarioRepository.findByUsername(username));


        List<ItemPedido> itensPedido = new ArrayList<ItemPedido>();
        for (ItemPedidoDTO itemDTO : pedidoDTO.itensPedido()) {
            ItemPedido ip = new ItemPedido();
            ip.setPedido(entity);
            ip.setPlano(planoRepository.findById(itemDTO.idPlano()));
            
            if (itemDTO.preco().equals(ip.getPlano().getPrecoMensal()))
                ip.setPreco(itemDTO.preco());
            else {
                // mandar uma mensagem de erro, pq o preco informado pela interface está diferente do preco do produto.
            }
            // verificar se possui estoque
            ip.setQuantidade(itemDTO.quantidade());

            // diminuir o estoque do produto

            // adicionando na lista
            itensPedido.add(ip);
        }


        entity.setItensPedido(itensPedido);

        // endereco
        // forma de pagamento
        // cupom de desconto
        // frete


        // salvando no banco
        pedidoRepository.persist(entity);

        return PedidoResponseDTO.valueOf(entity);
    }

    @Override
    public List<PedidoResponseDTO> findByUsuario(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        return pedidoRepository.findByUsuario(usuario).stream().map(e -> PedidoResponseDTO.valueOf(e)).toList();
    }

}