package com.tecsoluction.cardapio.servico;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsoluction.cardapio.dao.IPremioDAO;
import com.tecsoluction.cardapio.entidade.Premio;
import com.tecsoluction.cardapio.framework.AbstractEntityService;




@Service("premioService")
@Transactional
public class PremioServicoImpl extends AbstractEntityService<Premio> {


    @Autowired
    private
    IPremioDAO premiodao;

//	private Entity entityClass;


    public PremioServicoImpl() {
        super(Premio.class, "premio");
    }

    @Override
    protected JpaRepository<Premio, UUID> getDao() {

        return premiodao;
    }

	@Override
	protected void validateSave(Premio post) {
		// TODO Auto-generated method stub
		
	}

    @Override
    protected String getIdEntity(Premio premio) {
        return premio.getId().toString();
    }

    @Override
	protected void validateEdit(Premio post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateDelete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Premio> findAllNew() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<Premio> findAllNew() {
//		// TODO Auto-generated method stub
//		return null;
//	}


}
