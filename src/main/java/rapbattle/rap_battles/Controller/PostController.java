package rapbattles.rap_battles.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rapbattles.rap_battles.Models.DTO.PostDTO;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.Text;
import rapbattles.rap_battles.ServiceImpl.PostPictureServiceImplem;
import rapbattles.rap_battles.ServiceImpl.PostServiceImplem;
import rapbattles.rap_battles.ServiceImpl.SoundServiceImplem;
import rapbattles.rap_battles.Util.Exceptions.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController extends BaseController {

    @Autowired
    PostServiceImplem psi;

    @Autowired
    PostPictureServiceImplem ppsi;

    @Autowired
    SoundServiceImplem ssi;

    @GetMapping("/post_id/{post_ID}")
    public PostDTO getPostById(@PathVariable(value = "post_ID") int post_ID) throws MainException {
        return psi.getPostDTOById(post_ID);
    }

    @GetMapping("/user_id/{user_ID}")
    public List<PostDTO> getAllPostsByUserID(@PathVariable(value = "user_ID") int user_ID){
        return psi.getAllPostsByUserID(user_ID);
    }

    @GetMapping("/liked_by_user_id/{user_ID}")
    public List<PostDTO> getAllPostsLikedBy(@PathVariable(value = "user_ID") int user_ID){
        return psi.getAllPostsLikedBy(user_ID);
    }

    @PostMapping("/create_post")
    public String createPost(@RequestBody PostDTO postDTO, HttpSession session) throws IOException, MainException, ForbiddenException {
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        psi.createPost(postDTO,userDTO);
        return "Post successfully created.";
    }

    @DeleteMapping("/delete/{post_id}")
    public String deletePost(@PathVariable(value = "post_id") int post_ID, HttpSession session) throws MainException, ForbiddenException {
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        psi.deletePost(post_ID, userDTO.getUsername(),userDTO.getUser_ID());
        return "Post successfully deleted.";
    }

    @PostMapping("/update/{post_id}")
    public String updatePost(@PathVariable(value = "post_id") int post_ID, @RequestBody Text text, HttpSession session) throws NotFoundException, ForbiddenException {
        validateLogged(session);
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        psi.updatePost(post_ID,userDTO.getUser_ID(),text.getContent());
        return "Post successfully updated.";
    }

    @GetMapping(value = "/post_pic/{name}", produces = "image/png")
    public byte[] downloadPostPic(@PathVariable("name") String imageName)throws IOException{
        return ppsi.downloadImage(imageName);
    }

    @GetMapping(value = "/sound/{name}", produces = "audio/mpeg")
    public byte[] downloadSound(@PathVariable("name") String soundName)throws IOException{
        return ssi.downloadSound(soundName);
    }
}