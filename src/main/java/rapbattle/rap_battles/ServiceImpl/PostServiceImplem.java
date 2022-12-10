package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.PostDAOImplem;
import rapbattles.rap_battles.DAOImplementation.PostPictureDAOImplementation;
import rapbattles.rap_battles.DAOImplementation.SoundDAOImplem;
import rapbattles.rap_battles.DAOImplementation.TextDAOImplem;
import rapbattles.rap_battles.Models.DTO.PostDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.Post;
import rapbattles.rap_battles.Service.PostService;
import rapbattles.rap_battles.Util.Exceptions.ForbiddenException;
import rapbattles.rap_battles.Util.Exceptions.MainException;
import rapbattles.rap_battles.Util.Exceptions.NotAuthorisedException;
import rapbattles.rap_battles.Util.Exceptions.NotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class PostServiceImplem implements PostService {

    @Autowired
    PostDAOImplem postDAO;

    @Autowired
    TextDAOImplem textDAO;

    @Autowired
    PostPictureDAOImplementation ppDAO;

    @Autowired
    SoundDAOImplem soundDAO;

    public PostDTO getPostDTOById(int post_ID) throws MainException{
        PostDTO postDTO = postDAO.getPostDTOByID(post_ID);
        if (postDTO == null) {
            throw new NotFoundException("No such post exists.");
        }
        return postDTO;
    }

    public List<PostDTO> getAllPostsByUserID(int user_ID){
        return postDAO.getAllPostsByUserID(user_ID);
    }

    public List<PostDTO> getAllPostsLikedBy(int user_ID){
        return postDAO.getAllPostsLikedBy(user_ID);
    }

    public void createPost(PostDTO postDTO, UserDTO userDTO) throws IOException, MainException{
        postDAO.createPost(postDTO, userDTO);
    }

    public void deletePost(int post_ID, String username, int user_ID) throws MainException, ForbiddenException{
        Post post = postDAO.getPostByID(post_ID);
        PostDTO postDTO = postDAO.getPostDTOByID(post_ID);
        if (postDTO == null){
            throw new NotFoundException("There is no post with this ID.");
        }
        if(!postDTO.getUsername().equals(username)){
            throw new NotAuthorisedException("This post is not yours.");
        }
        postDAO.deletePost(post_ID,user_ID);
        soundDAO.deleteSound(post.getSound_ID());
        textDAO.deleteText(post.getText_ID());
        ppDAO.deletePostPicture(post.getPicture_ID());
    }

    public void updatePost(int post_ID, int user_ID, String content) throws NotFoundException, ForbiddenException{
        Post post = postDAO.getPostByID(post_ID);
        if (post == null){
            throw new NotFoundException("There is no post with this ID.");
        }
        if(post.getUser_ID()!=user_ID){
            throw new NotAuthorisedException("This post is not yours.");
        }
        textDAO.updateText(content,post.getText_ID());
    }
}