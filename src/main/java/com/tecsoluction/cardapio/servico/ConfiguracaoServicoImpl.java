package com.tecsoluction.cardapio.servico;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsoluction.cardapio.dao.ICategoriaDAO;
import com.tecsoluction.cardapio.dao.IConfiguracaoDAO;
import com.tecsoluction.cardapio.entidade.Categoria;
import com.tecsoluction.cardapio.entidade.Configuracao;
import com.tecsoluction.cardapio.framework.AbstractEntityService;


/*  criar validacaoes para que o servico as chamem caso nao haja erros execute a acao  */

@Service("configuracaoService")
@Transactional
public class ConfiguracaoServicoImpl extends AbstractEntityService<Configuracao> {

	  @Autowired
    private  IConfiguracaoDAO dao;

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
    public ConfiguracaoServicoImpl() {

        super(Configuracao.class, "configuracao");

//        this.dao = dao;
//        this.produtoServico = produtoServico;
    }

    @Override
    protected JpaRepository<Configuracao, UUID> getDao() {

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
    protected void validateSave(Configuracao post) {
        // TODO Auto-generated method stub

    }

    @Override
    protected String getIdEntity(Configuracao categoria) {
        return categoria.getId().toString();
    }

    @Override
    protected void validateEdit(Configuracao post) {
        // TODO Auto-generated method stub

    }

	@Override
	public List<Configuracao> findAllNew() {
		// TODO Auto-generated method stub
		return null;
	}

}
