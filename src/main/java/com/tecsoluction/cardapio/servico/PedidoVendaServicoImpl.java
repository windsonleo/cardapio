package com.tecsoluction.cardapio.servico;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsoluction.cardapio.dao.IPedidoVendaDAO;
import com.tecsoluction.cardapio.entidade.PedidoVenda;
import com.tecsoluction.cardapio.framework.AbstractEntityService;
import com.tecsoluction.cardapio.util.StatusPedido;



/*  criar validacaoes para que o servico as chamem caso nao haja erros execute a acao  */


@Service("pedidovendaService")
@Transactional
public class PedidoVendaServicoImpl extends AbstractEntityService<PedidoVenda> {

    @Autowired
    private IPedidoVendaDAO dao;


    public PedidoVendaServicoImpl() {

        super(PedidoVenda.class, "pedidovenda");

    }

    @Override
    protected JpaRepository<PedidoVenda, UUID> getDao() {

        return dao;
    }

    public List<PedidoVenda> getAllPedidoPorMesa(UUID idmesa) {

        return dao.getAllPedidoPorMesa(idmesa);
    }

    public List<PedidoVenda> getAllPedidoPorData(Date dataini) {
    	
//    	Date dt = new Date(dataini);

        return dao.getAllPedidoPorData(dataini);
    }

    public List<PedidoVenda> getAllPedidoDelivery() {

        return dao.getAllPedidoDelivery();
    }

    public List<PedidoVenda> findAllByStatusIsAndSituacaoIs(String status) {
        StatusPedido statusPedido = Enum.valueOf(StatusPedido.class, status.toUpperCase());

        return dao.findAllByStatusIsOrderByDataAsc(statusPedido);
    }
    
    
    
    public List<PedidoVenda>  findAllByStatusIsOrderByDataAsc(StatusPedido status){
    	
    	
    	return dao.findAllByStatusIsOrderByDataAsc(status);
    }

    //TODO ajeitar metodo de buscar cliente por pedidos de venda
//    public long findClienteByPedidoVenda() {
//
//        return dao.findClienteByPedidoVenda();
//    }
    
    @Override
    protected void validateSave(PedidoVenda post) {
        // TODO Auto-generated method stub

    }

    @Override
    protected String getIdEntity(PedidoVenda pedidoVenda) {
        return pedidoVenda.getId().toString();
    }

    @Override
    protected void validateEdit(PedidoVenda post) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void validateDelete(UUID id) {
        // TODO Auto-generated method stub

    }

	@Override
	public List<PedidoVenda> findAllNew() {
		// TODO Auto-generated method stub
		return dao.findAllNew();
	}


}
