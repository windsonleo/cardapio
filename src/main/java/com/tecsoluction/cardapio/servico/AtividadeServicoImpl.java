package com.tecsoluction.cardapio.servico;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsoluction.cardapio.dao.IAtividadeDAO;
import com.tecsoluction.cardapio.dao.ICategoriaDAO;
import com.tecsoluction.cardapio.entidade.Atividade;
import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.framework.AbstractEntityService;


/*  criar validacaoes para que o servico as chamem caso nao haja erros execute a acao  */

@Service("AtividadeService")
@Transactional
public class AtividadeServicoImpl extends AbstractEntityService<Atividade> {

	  @Autowired
    private  IAtividadeDAO dao;

//    private ProdutoServicoImpl produtoServico;

//    @Autowired
//    public CategoriaServicoImpl(ICategoriaDAO dao, ProdutoServicoImpl produtoServico) {
//
//        super(Categoria.class, "categoria");
//
//        this.dao = dao;
//        this.produtoServico = produtoServico;
//    }
    
    
    @Autowired
    public AtividadeServicoImpl() {

        super(Atividade.class, "atividade");

//        this.dao = dao;
//        this.produtoServico = produtoServico;
    }

    @Override
    protected JpaRepository<Atividade, UUID> getDao() {

        return dao;
    }





    @Override
    protected void validateDelete(UUID id) {
//
//        Categoria catGenericaPai = getOnlyCategoriaPai();
//        List<Categoria> categoriasFilha = getCategoriasFilho(id);
//        for (Categoria cat : categoriasFilha) {
//            cat.setCatpai(catGenericaPai);
//            List<Produto> produtos = cat.getProdutos();
//            for (Produto prod : produtos) {
//                prod.setCategoria(catGenericaPai);
//                produtoServico.edit(prod);
//            }
//            edit(cat);
//        }

    }

    @Override
    protected void validateSave(Atividade post) {
        // TODO Auto-generated method stub

    }

    @Override
    protected String getIdEntity(Atividade atv) {
        return atv.getId().toString();
    }

    @Override
    protected void validateEdit(Atividade post) {
        // TODO Auto-generated method stub

    }

	@Override
	public List<Atividade> findAllNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
