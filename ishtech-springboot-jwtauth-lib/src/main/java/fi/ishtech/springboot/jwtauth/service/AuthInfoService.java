package fi.ishtech.springboot.jwtauth.service;

public interface AuthInfoService {

	Long getUserId();

	String getUsername();

	String getEmail();

	boolean isAdmin();
}