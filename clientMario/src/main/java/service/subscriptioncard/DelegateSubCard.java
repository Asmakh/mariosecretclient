package service.subscriptioncard;

import java.util.List;

import service.connection.ConnectionLocator;
import tn.mario.moovtn.entities.SubscriptionCard;
import tn.mario.moovtn.remotes.SubCardService;

public class DelegateSubCard {

	
	String jndi = "/persist/SubCardImplementation!"
			+SubCardService.class.getCanonicalName();
public static SubCardService getInstance(String jndi){
	
	return (SubCardService) ConnectionLocator.getInstance().getProxy(jndi);
}	

void Add(SubscriptionCard subCard){
	getInstance(jndi).Add(subCard);
}
public void Delete(SubscriptionCard subCard){
	getInstance(jndi).Delete(subCard);
}
void Update(SubscriptionCard subCard){
	getInstance(jndi).Update(subCard);
}
List<SubscriptionCard> FindAll(){
	return getInstance(jndi).FindAll();
}
public SubscriptionCard FindById(Integer id){
	return getInstance(jndi).FindById(id);
}

}
