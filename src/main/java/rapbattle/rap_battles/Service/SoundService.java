package rapbattles.rap_battles.Service;

import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Util.Exceptions.MainException;

import java.io.IOException;

public interface SoundService {

    int uploadSound(String soundFile, UserDTO userDTO) throws IOException, MainException;

    byte[] downloadSound(String soundName)throws IOException;
}