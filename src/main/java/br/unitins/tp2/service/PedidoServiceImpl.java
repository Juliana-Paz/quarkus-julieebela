package br.unitins.tp2.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import br.unitins.tp2.dto.PedidoDTO;
import br.unitins.tp2.dto.PedidoResponseDTO;
import br.unitins.tp2.dto.pedido.DashboardStatsDTO;
import br.unitins.tp2.dto.pedido.PijamaMaisVendidoDTO;
import br.unitins.tp2.dto.pedido.ReceitaMensalDTO;
import br.unitins.tp2.exception.ValidationException;
import br.unitins.tp2.model.Cliente;
import br.unitins.tp2.model.CupomDesconto;
import br.unitins.tp2.model.Endereco;
import br.unitins.tp2.model.FormaPagamento;
import br.unitins.tp2.model.ItemPedido;
import br.unitins.tp2.model.Pedido;
import br.unitins.tp2.model.Pijama;
import br.unitins.tp2.model.StatusPedido;
import br.unitins.tp2.repository.ClienteRepository;
import br.unitins.tp2.repository.CupomRepository;
import br.unitins.tp2.repository.PedidoRepository;
import br.unitins.tp2.repository.PijamaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PedidoServiceImpl implements PedidoService {

    @Inject
    PedidoRepository pedidoRepository;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    PijamaRepository pijamaRepository;

    @Inject
    CupomRepository cupomRepository;

    @Inject
    Validator validator;

    @Override
    @Transactional
    public PedidoResponseDTO create(PedidoDTO dto, String username) {
        validar(dto);

        Cliente cliente = clienteRepository.findByUsuarioUsername(username);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");

        // Resolver e validar cupom, se informado
        CupomDesconto cupom = null;
        if (dto.codigoCupom() != null && !dto.codigoCupom().isBlank()) {
            cupom = cupomRepository.findByCodigo(dto.codigoCupom());
            if (cupom == null)
                throw ValidationException.of("codigoCupom", "Cupom não encontrado.");
            if (!cupom.getAtivo())
                throw ValidationException.of("codigoCupom", "Cupom inativo.");
            LocalDate hoje = LocalDate.now();
            if (hoje.isBefore(cupom.getDataInicio()))
                throw ValidationException.of("codigoCupom", "Cupom ainda não está vigente.");
            if (hoje.isAfter(cupom.getDataFim()))
                throw ValidationException.of("codigoCupom", "Cupom expirado.");
        }

        // Montar itens e calcular total
        List<ItemPedido> itens = new ArrayList<>();
        double totalBruto = 0.0;

        for (var itemDto : dto.itens()) {
            Pijama pijama = pijamaRepository.findById(itemDto.idPijama());
            if (pijama == null)
                throw ValidationException.of("itens", "Pijama id=" + itemDto.idPijama() + " não encontrado.");
            if (!pijama.getAtivo())
                throw ValidationException.of("itens", "Pijama '" + pijama.getNome() + "' está inativo.");

            double subtotal = pijama.getPreco() * itemDto.quantidade();
            totalBruto += subtotal;

            ItemPedido item = new ItemPedido();
            item.setQuantidade(itemDto.quantidade());
            item.setPrecoUnitario(pijama.getPreco());
            item.setSubtotal(subtotal);
            item.setPijama(pijama);
            itens.add(item);
        }

        // Calcular desconto
        double valorDesconto = 0.0;
        if (cupom != null) {
            valorDesconto = cupom.getPercentual()
                    ? totalBruto * (cupom.getValorDesconto() / 100.0)
                    : cupom.getValorDesconto();
            valorDesconto = Math.min(valorDesconto, totalBruto);
        }

        double total = totalBruto - valorDesconto;

        // Copiar endereço de entrega
        Endereco enderecoEntrega = new Endereco();
        enderecoEntrega.setLogradouro(dto.enderecoEntrega().logradouro());
        enderecoEntrega.setNumero(dto.enderecoEntrega().numero());
        enderecoEntrega.setComplemento(dto.enderecoEntrega().complemento());
        enderecoEntrega.setBairro(dto.enderecoEntrega().bairro());
        enderecoEntrega.setCep(dto.enderecoEntrega().cep());
        enderecoEntrega.setMunicipio(dto.enderecoEntrega().municipio());
        enderecoEntrega.setEstado(dto.enderecoEntrega().estado());
        enderecoEntrega.setPrincipal(dto.enderecoEntrega().principal());

        // Montar pedido
        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedido.setFormaPagamento(FormaPagamento.valueOf(dto.idFormaPagamento()));
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setCupom(cupom);
        pedido.setValorDesconto(valorDesconto);
        pedido.setTotal(total);

        itens.forEach(item -> {
            item.setPedido(pedido);
            pedido.getItens().add(item);
        });

        pedidoRepository.persist(pedido);
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    public PedidoResponseDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) throw new NotFoundException("Pedido não encontrado.");
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    public List<PedidoResponseDTO> findByCliente(String username, int page, int pageSize) {
        Cliente cliente = clienteRepository.findByUsuarioUsername(username);
        if (cliente == null) throw new NotFoundException("Cliente não encontrado.");
        return pedidoRepository.findByClienteId(cliente.getId()).page(page, pageSize).list()
                .stream().map(PedidoResponseDTO::valueOf).toList();
    }

    @Override
    public List<PedidoResponseDTO> findAll(int page, int pageSize) {
        return pedidoRepository.findAll().page(page, pageSize).list()
                .stream().map(PedidoResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        return pedidoRepository.count();
    }

    @Override
    @Transactional
    public PedidoResponseDTO updateStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) throw new NotFoundException("Pedido não encontrado.");
        pedido.setStatus(novoStatus);
        return PedidoResponseDTO.valueOf(pedido);
    }

    @Override
    public DashboardStatsDTO getStats() {
        List<Pedido> todos = pedidoRepository.listAll();

        long totalPedidos = todos.size();
        double receitaTotal = todos.stream()
                .mapToDouble(p -> p.getTotal() != null ? p.getTotal() : 0.0)
                .sum();

        YearMonth mesAtual = YearMonth.now();
        List<Pedido> pedidosMes = todos.stream()
                .filter(p -> p.getData() != null && YearMonth.from(p.getData()).equals(mesAtual))
                .toList();
        double receitaMesAtual = pedidosMes.stream()
                .mapToDouble(p -> p.getTotal() != null ? p.getTotal() : 0.0)
                .sum();
        long pedidosMesAtual = pedidosMes.size();

        // Top 5 pijamas mais vendidos
        Map<Long, int[]> contagem = new HashMap<>();
        Map<Long, String> nomes = new HashMap<>();
        for (Pedido p : todos) {
            if (p.getItens() == null) continue;
            for (ItemPedido item : p.getItens()) {
                if (item.getPijama() == null) continue;
                Long pid = item.getPijama().getId();
                contagem.computeIfAbsent(pid, k -> new int[]{0})[0] += item.getQuantidade();
                nomes.put(pid, item.getPijama().getNome());
            }
        }
        List<PijamaMaisVendidoDTO> maisVendidos = contagem.entrySet().stream()
                .sorted((a, b) -> b.getValue()[0] - a.getValue()[0])
                .limit(5)
                .map(e -> new PijamaMaisVendidoDTO(e.getKey(), nomes.get(e.getKey()), e.getValue()[0]))
                .toList();

        // Receita últimos 6 meses
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM/yy", Locale.of("pt", "BR"));
        List<ReceitaMensalDTO> receitaMensal = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            YearMonth ym = mesAtual.minusMonths(i);
            double rec = todos.stream()
                    .filter(p -> p.getData() != null && YearMonth.from(p.getData()).equals(ym))
                    .mapToDouble(p -> p.getTotal() != null ? p.getTotal() : 0.0)
                    .sum();
            long qtd = todos.stream()
                    .filter(p -> p.getData() != null && YearMonth.from(p.getData()).equals(ym))
                    .count();
            receitaMensal.add(new ReceitaMensalDTO(ym.format(fmt), rec, qtd));
        }

        return new DashboardStatsDTO(totalPedidos, receitaTotal, receitaMesAtual,
                pedidosMesAtual, maisVendidos, receitaMensal);
    }

    private void validar(PedidoDTO dto) {
        Set<ConstraintViolation<PedidoDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }

}
