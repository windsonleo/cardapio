package com.tecsoluction.cardapio.util;

import java.awt.Image;

public class FacebookAux {
	
	
	private String userid;
	
	private String url;
	
	
	private Image img;
	
	
	
	public FacebookAux() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
//	private String recuperaFotoPerfilFacebook(String userID) throws MalformedURLException {
//	    URI.Builder builder = URI.parse("https://graph.facebook.com").buildUpon();
//	    builder.appendPath(userID).appendPath("picture").appendQueryParameter("type", "large");
//	    return builder.toString();
//	}
	
//	private void obterDadosFacebook(){
//
//		List permissionNeeds= Arrays.asList("email","user_about_me");
//
//		LoginManager.getInstance().logInWithReadPermissions(
//		        this,
//		        permissionNeeds);
//		LoginManager.getInstance().registerCallback(mCallbackManager,
//		        new FacebookCallback() {
//		            @Override
//		            public void onSuccess(LoginResult loginResults) {
//
//		                GraphRequest request = GraphRequest.newMeRequest(
//		                        loginResults.getAccessToken(),
//		                        new GraphRequest.GraphJSONObjectCallback() {
//		                            @Override
//		                            public void onCompleted(
//		                                    JSONObject object,
//		                                    GraphResponse response) {
//		                                Log.v("TAG", "JSON: " + object);
//		                                try {
//		                                   //String foto = object.getJSONObject("picture").getJSONObject("data").getString("url");
//		                                   String id = object.getString("id");
//		                                   String foto = "https://graph.facebook.com/"+id+"/picture?height=120&width=120";
//		                                   String nome = object.getString("name");
//		                                   String email = object.getString("email");
//		                                    Glide.with(Context de sua class)
//		                                            .load(foto)
//		                                            .centerCrop()
//		                                            .error(R.drawable.erro)
//		                                            .into(profileImageView); // id do teu imageView.
//		                                } catch (JSONException e) {
//		                                    e.printStackTrace();
//		                                }
//		                            }
//		                        });
//		                Bundle parameters = new Bundle();
//		                parameters.putString("fields", "id,name,email,picture.width(120).height(120)");
//		                request.setParameters(parameters);
//		                request.executeAsync();
//		            }
//
//
//		        });
//		}

}
