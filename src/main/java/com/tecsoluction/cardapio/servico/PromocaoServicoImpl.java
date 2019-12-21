package com.tecsoluction.cardapio.servico;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsoluction.cardapio.dao.IPromocaoDAO;
import com.tecsoluction.cardapio.dao.IRoleDAO;
import com.tecsoluction.cardapio.entidade.Promocao;
import com.tecsoluction.cardapio.entidade.Role;
import com.tecsoluction.cardapio.framework.AbstractEntityService;



@Service("promocaoService")
@Transactional
public class PromocaoServicoImpl extends AbstractEntityService<Promocao> {


    @Autowired
    private
    IPromocaoDAO dao;

//	private Entity entityClass;


    public PromocaoServicoImpl() {
        super(Promocao.class, "promocao");
    }

    @Override
    protected JpaRepository<Promocao, UUID> getDao() {

        return dao;
    }

	@Override
	protected void validateSave(Promocao post) {
		// TODO Auto-generated method stub
		
	}

    @Override
    protected String getIdEntity(Promocao role) {
        return role.getId().toString();
    }

    @Override
	protected void validateEdit(Promocao post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateDelete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Promocao> findAllNew() {
		// TODO Auto-generated method stub
		return null;
	}


}
