package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.TextDAOImplem;
import rapbattles.rap_battles.Service.TextService;

@Service
public class TextServiceImplementation implements TextService {

    @Autowired
    TextDAOImplem textDAO;

    public void writeText(String content){
        textDAO.writeText(content);
    }
}