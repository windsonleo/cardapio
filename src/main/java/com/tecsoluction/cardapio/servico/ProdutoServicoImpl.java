package com.tecsoluction.cardapio.servico;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsoluction.cardapio.dao.IProdutoDAO;
import com.tecsoluction.cardapio.entidade.Produto;
import com.tecsoluction.cardapio.framework.AbstractEntityService;



/*  criar validacaoes para que o servico as chamem caso nao haja erros execute a acao  */


@Service("produtoService")
@Transactional
public class ProdutoServicoImpl extends AbstractEntityService<Produto> {

    @Autowired
    private IProdutoDAO dao;


    public ProdutoServicoImpl() {

        super(Produto.class, "produto");

    }

    @Override
    protected JpaRepository<Produto, UUID> getDao() {

        return dao;
    }

    public Produto getProdutoPorCodebar(String codebar) {

        return dao.getProdutoPorCodebar(codebar);
    }

    public List<Produto> getAllProdutoPorCategoria(UUID idcategoria) {

        return dao.getAllProdutoPorCategoria(idcategoria);
    }

	@Override
	protected void validateSave(Produto post) {
		// TODO Auto-generated method stub
		
	}

    @Override
    protected String getIdEntity(Produto produto) {
        return produto.getId().toString();
    }

    @Override
	protected void validateEdit(Produto post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateDelete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Produto> findAllNew() {
		// TODO Auto-generated method stub
		return dao.findAllNew();
	}

	
    public List<Produto> ListaProdutoMaiorAvaliacao(Pageable pageable){
    	
//    	Pageable primeiroResultado = new PageRequest(0, 5);
    	List<Produto> result = dao.ListaProdutoMaiorAvaliacao(pageable);

    	
    	return result;
    }
    
    
    public List<Produto> ListaProdutoMenorPreco(Pageable pageable){
    	
//    	Pageable primeiroResultado = new PageRequest(0, 5);
    	List<Produto> result = dao.ListaProdutoMenorPreco(pageable);

    	
    	return result;
    }

}
