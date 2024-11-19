package br.edu.atitus.apisample.services;

import org.springframework.stereotype.Service;

import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.repositories.UserRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
	
	private final UserRepository repository;
		
    public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}

	// Expressão regular para validar e-mails
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$";
	
	public UserEntity save(UserEntity newUser) throws Exception {
		
		// Validar regras de negócio 
		if (newUser == null)
			throw new Exception("Objeto nulo!");
		
		if (newUser.getName() == null || newUser.getName().isEmpty())
			throw new Exception ("Nome inválido");
		newUser.setName(newUser.getName().trim());
		
		if (newUser.getEmail() == null || newUser.getEmail().isEmpty())
			throw new Exception ("Email inválido");
		newUser.setEmail(newUser.getEmail().trim());
		
		//Validar email com regex
		if(!isValidEmail(newUser.getEmail()))
			throw new Exception("Email inválido");
			
		newUser.setEmail(newUser.getEmail().trim());
		
		if (repository.existsByEmail(newUser.getEmail()))
			throw new Exception("Já existe usuário com este e-mail");
		
		this.repository.save(newUser);
		
			
		//Invocar método camada repository
		return newUser;
	}
	
	   private static boolean isValidEmail(String email) {
	        Pattern pattern = Pattern.compile(EMAIL_REGEX);
	        Matcher matcher = pattern.matcher(email);
	        return matcher.matches();
	    }
	   
	public List<UserEntity> findAll() throws Exception{
		return repository.findAll();
	}

}