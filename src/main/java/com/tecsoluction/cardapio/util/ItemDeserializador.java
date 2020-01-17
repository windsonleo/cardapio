package com.tecsoluction.cardapio.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsoluction.cardapio.entidade.Item;




public class ItemDeserializador  extends KeyDeserializer {

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public Object deserializeKey(String key, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return mapper.readValue(key, Item.class);
    }
}
//	@Override
//	public Item deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
//
//		JsonNode node = jp.readValueAsTree();
//		Item it = new Item();
//		
//		JsonNode nodeiD = node.get("id");
//	    if(nodeiD != null){
//	        it.setId(UUID.fromString(nodeiD.asText()));
//	    }
//	    
//	    JsonNode nodeCodigo = node.get("codigo");
//	    if(nodeCodigo != null){
//	        it.setCodigo(nodeCodigo.asText());
//	    }
//	    
//	    JsonNode nodeNome = node.get("nome");
//	    if(nodeNome != null){
//	        it.setNome(nodeNome.asText());
//	    }
//	    
//	    JsonNode nodeDesc = node.get("descricao");
//	    if(nodeDesc != null){
//	        it.setDescricao(nodeDesc.asText());
//	    }
//	    
//	    JsonNode nodePrecounitario = node.get("precoUnitario");
//	    if(nodePrecounitario != null){
//	        it.setPrecoUnitario(new BigDecimal(nodePrecounitario.asText()).setScale(2, RoundingMode.HALF_UP));
//	    }
//	    
//	    JsonNode nodeprecoCusto = node.get("precoCusto");
//	    if(nodeprecoCusto != null){
//	        it.setPrecoCusto(new BigDecimal(nodeprecoCusto.asText()).setScale(2, RoundingMode.HALF_UP));
//	    }
//	    
//	    JsonNode nodeun_medida = node.get("un_medida");
//	    if(nodeun_medida != null){
//	        it.setUn_medida(UnidadeMedida.valueOf(nodeun_medida.asText()));
//	    }
//	    JsonNode nodetotalItem = node.get("totalItem");
//	    if(nodetotalItem != null){
//	        it.setTotalItem(new BigDecimal(nodetotalItem.asText()).setScale(2, RoundingMode.HALF_UP));
//	    }
//	    
//	    JsonNode nodesituacao = node.get("situacao");
//	    if(nodesituacao != null){
//	        it.setSituacao(SituacaoItem.valueOf(nodesituacao.asText()));
//	    }
//	    
//	    JsonNode nodefoto = node.get("foto");
//	    if(nodefoto != null){
//	        it.setFoto(nodesituacao.asText());
//	    }
//	    
//	    
//	    JsonNode nodeqtd = node.get("qtd");
//	    if(nodeqtd != null){
//	        it.setQtd(nodeqtd.asText());
//	    }
//		
//		
//		return it;
//	}
	
	

//}
