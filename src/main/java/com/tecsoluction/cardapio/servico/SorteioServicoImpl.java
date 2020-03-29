package com.tecsoluction.cardapio.servico;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecsoluction.cardapio.dao.ISorteioDAO;
import com.tecsoluction.cardapio.entidade.Sorteio;
import com.tecsoluction.cardapio.framework.AbstractEntityService;





@Service("sorteioService")
@Transactional
public class SorteioServicoImpl extends AbstractEntityService<Sorteio> {


    @Autowired
    private
    ISorteioDAO sorteiodao;

//	private Entity entityClass;


    public SorteioServicoImpl() {
        super(Sorteio.class, "sorteio");
    }

    @Override
    protected JpaRepository<Sorteio, UUID> getDao() {

        return sorteiodao;
    }

	@Override
	protected void validateSave(Sorteio post) {
		// TODO Auto-generated method stub
		
	}

    @Override
    protected String getIdEntity(Sorteio sorteio) {
        return sorteio.getId().toString();
    }

    @Override
	protected void validateEdit(Sorteio post) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateDelete(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Sorteio> findAllNew() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<Sorteio> findAllNew() {
//		// TODO Auto-generated method stub
//		return null;
//	}


}
