package rapbattles.rap_battles.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rapbattles.rap_battles.DAOImplementation.ActivationCodeDAOImplem;
import rapbattles.rap_battles.DAOImplementation.AvatarDAOImplem;
import rapbattles.rap_battles.DAOImplementation.UserDAOImplem;
import rapbattles.rap_battles.Models.DTO.UserDTO;
import rapbattles.rap_battles.Models.POJO.User;
import rapbattles.rap_battles.Service.UserService;
import rapbattles.rap_battles.Util.EmailSender;
import rapbattles.rap_battles.Util.Exceptions.*;
import rapbattles.rap_battles.Util.PasswordUtils;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImplem implements UserService {

    @Autowired
    UserDAOImplem userDAO;

    @Autowired
    ActivationCodeDAOImplem activationDao;

    @Autowired
    AvatarDAOImplem avatarDAO;

    @Autowired
    AvatarServiceImplem aSI;


    public static final String LOGGED = "logged";  // a constant for the session

    //Pretty much register user as a whole and send email confirmation
    @Override
    public UserDTO addUser(User user, HttpSession session) throws MainException {
        validateUsernameAndEmail(user);
        checkIfUsernameOrEmailExists(user);
        validateAndGenerateSecurePassword(user);
        userDAO.registerUser(user);
        session.setAttribute(LOGGED, user);
        return emailAndConfirmation(user);
    }

    //Service for logging in.
    @Override
    public UserDTO login(User user, HttpSession session) throws MainException {
        checkIfAccountIsActivated(user);
        checkUsernameOrEmail(user);
        return checkPassword(user, session);
    }

    //changes user password
    public void changePassword(User user, HttpSession session) throws MainException {
        if (user.getSecond_password().equals(user.getPassword())){
            throw new InvalidPasswordException("New password cannot match old password.");
        }
        UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED);
        boolean passwordMatch = PasswordUtils.verifyUserPassword(user.getPassword(), userDAO.findUserByEmail(userDTO.getEmail()).getPassword(),
                userDAO.findUserByEmail(userDTO.getEmail()).getSalt());
        if (passwordMatch){
            if (validatePassword(user.getSecond_password())) {
                user = generateSecureChangePassword(user);
                userDAO.changePassword(user.getPassword(), userDTO.getUser_ID(),user.getSalt());
            }
            else{
                throw new InvalidPasswordException("Password must be 8 characters or more, have at least one upper and lower case character" +
                        " and have at least 1 special character and digit and no spaces.");
            }
        }
        else{
            throw new InvalidPasswordException("The password you have entered is wrong.");
        }
    }

    @Override
    public int removeUser(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute(LOGGED);
        int id = user.getUser_ID();
        userDAO.deleteUserByID(user.getUser_ID());
        return id;
    }

    public byte[] viewUserAvatarByID(int user_ID) throws NotFoundException, IOException {
        if (avatarDAO.findImageById(user_ID)==null){
            throw new NotFoundException("This user doesn't have an avatar.");
        }
        else {
            return aSI.downloadImage(avatarDAO.findImageById(user_ID));
        }
    }

    //Checks if the correct password has been entered.
    private UserDTO checkPassword(User user, HttpSession session) throws MainException {
        boolean passwordMatch = false;
        if (userDAO.findUserByUsernameDTO(user.getUsername()) == null){
            passwordMatch = PasswordUtils.verifyUserPassword(user.getPassword(), userDAO.findUserByEmail(user.getEmail()).getPassword(),
                    userDAO.findUserByEmail(user.getEmail()).getSalt());
        }
        if (userDAO.findUserByEmailDTO(user.getEmail())==null){{
            passwordMatch = PasswordUtils.verifyUserPassword(user.getPassword(), userDAO.findUserByUsername(user.getUsername()).getPassword(),
                    userDAO.findUserByUsername(user.getUsername()).getSalt());
        }}
        if (passwordMatch) {
            if(userDAO.findUserByUsernameDTO(user.getUsername()) == null) {
                session.setAttribute(LOGGED, userDAO.findUserByEmailDTO(user.getEmail()));
                session.setMaxInactiveInterval(-1);
                return userDAO.findUserByEmailDTO(user.getEmail());
            }
            if(userDAO.findUserByEmailDTO(user.getEmail()) == null) {
                session.setAttribute(LOGGED, userDAO.findUserByUsernameDTO(user.getUsername()));
                session.setMaxInactiveInterval(-1);
                return userDAO.findUserByUsernameDTO(user.getUsername());
            }
        }
        else {
            throw new WrongEmailOrPasswordException("Wrong username,email or password.");
        }
        return null;
    }

    //Checks if the login info (username or email) are correct or existing.
    private void checkUsernameOrEmail(User user) throws MainException {
        if (userDAO.findUserByEmailDTO(user.getEmail())==null){
            if(userDAO.findUserByUsernameDTO(user.getUsername()) == null){
                throw new WrongEmailOrPasswordException("Wrong username, email or password.");
            }
        }

        if (userDAO.findUserByUsernameDTO(user.getUsername()) == null){
            if(userDAO.findUserByEmailDTO(user.getEmail()) == null){
                throw new WrongEmailOrPasswordException("Wrong username, email or password.");
            }
        }
    }

    //Checks if he account has been verified by email.
    private void checkIfAccountIsActivated(User user) throws MainException {
        checkIfUserExists(user);
        if (userDAO.findUserByEmailDTO(user.getEmail())==null){
            if(!userDAO.findUserByUsernameDTO(user.getUsername()).isActivated()){
                throw new AccountNotActivatedException("Your account is not activated.");
            }
        }
        if (userDAO.findUserByUsernameDTO(user.getUsername())==null){
            if(!userDAO.findUserByEmailDTO(user.getEmail()).isActivated()){
                throw new AccountNotActivatedException("Your account is not activated.");
            }
        }
    }

    //Checks if the 2 passwords match and validates if password matches requirements.
    private void validateAndGenerateSecurePassword(User user) throws MainException {
        if(!user.getPassword().equals(user.getSecond_password())){
            throw new InvalidPasswordException("Passwords don't match. Please check your spelling.");
        }
        if (validatePassword(user.getPassword())) {
            generateSecurePassword(user);
        }
        else{
            throw new InvalidPasswordException("Password must be 8 characters or more, have at least one upper and lower case character" +
                    " and have at least 1 special character and digit and no spaces.");
        }
    }

    //Checks if the user exists.
    private void checkIfUserExists(User user) throws MainException {
        if (userDAO.findUserByUsername(user.getUsername()) == null&& userDAO.findUserByEmail(user.getEmail()) == null) {
            throw new InvalidUsernameOrEmailException("This user does not exist.");
        }
    }

    //Checks if the provided username and email are valid.
    private void validateUsernameAndEmail(User user) throws MainException{
        if(!validateEmail(user.getEmail())){
            throw new InvalidUsernameOrEmailException("This is not a valid email.");
        }
        if(user.getUsername().length()<3||user.getUsername().length()>20){
            throw new InvalidUsernameOrEmailException("This is not a valid username.");
        }
    }

    //Checks if username or email have already been used before to register an account.
    private void checkIfUsernameOrEmailExists(User user) throws MainException{
        if (userDAO.findUserByUsernameDTO(user.getUsername()) != null) {
            throw new InvalidUsernameOrEmailException("This username is already taken.");
        }
        if (userDAO.findUserByEmailDTO(user.getEmail()) != null) {
            throw new InvalidUsernameOrEmailException("You have already registered with this email.");
        }
    }

    private User generateSecureChangePassword(User user){
        String salt = PasswordUtils.getSalt(30);
        String securedPassword = PasswordUtils.generateSecurePassword(user.getSecond_password(), salt);
        user.setPassword(securedPassword);
        user.setSecond_password(securedPassword);
        user.setSalt(salt);
        return user;
    }

    // Generates secure password with salt.
    private User generateSecurePassword(User user){
        String salt = PasswordUtils.getSalt(30);
        String securedPassword = PasswordUtils.generateSecurePassword(user.getPassword(), salt);
        user.setPassword(securedPassword);
        user.setSecond_password(securedPassword);
        user.setSalt(salt);
        return user;
    }

    // Validates if the password matches the requirements.
    private boolean validatePassword(String password) {
        String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=*()_<>])(?=\\S+$).{8,}$";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(password);
        return matcher.matches();
    }

    // For validating the email.
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

    // Generates a random activation code for the email link when registering.
    private String getRandomString() {
        String choices = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder randomString = new StringBuilder();
        Random rnd = new Random();
        while (randomString.length() < 18) {
            int index = (int) (rnd.nextFloat() * choices.length());
            randomString.append(choices.charAt(index));
        }
        String randomStr = randomString.toString();
        return randomStr;
    }

    //Generates random code and sends an email that needs to be confirmed to activate the account.
    private UserDTO emailAndConfirmation(User user){
        String activation_code = getRandomString();
        new Thread(new EmailSender(user.getEmail(),user.getUsername(),activation_code)).start();
        activationDao.uploadActivationCode(userDAO.findUserByUsernameDTO(user.getUsername()).getUser_ID(),activation_code);
        return userDAO.findUserByEmailDTO(user.getEmail());
    }

    //activates account.
    public void activateAccountService(String activation_code) throws MainException {
        if (activationDao.findUserIdByActivationCode(activation_code)==null){
            throw new WrongActivationCodeException("The activation code is wrong.");
        }
        UserDTO userDTO = userDAO.findUserByID(activationDao.findUserIdByActivationCode(activation_code).getUser_ID());
        userDAO.setActiveToTrue(userDTO);
    }
}