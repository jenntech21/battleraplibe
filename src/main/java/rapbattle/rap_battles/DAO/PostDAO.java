package rapbattles.rap_battles.DAO;

import rapbattles.rap_battles.Models.DTO.PostDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.Post;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import java.io.IOException;
import java.util.List;

public interface PostDAO {

    PostDTO getPostDTOByID(int post_ID);
    void incrementNumberOfLikes(int post_ID);
    void decrementNumberOfLikes(int post_ID);
    List<PostDTO> getAllPostsByUserID(int user_ID);
    List<PostDTO> getAllPostsLikedBy(int user_ID);
    void createPost(PostDTO postDTO, UserDTO userDTO) throws IOException, MainException;
    void deletePost(int post_ID, int user_ID);
    Post getPostByID(int post_ID);
}