package com.zipline.controller;

import com.zipline.auth.payload.request.LoginRequestDTO;
import com.zipline.auth.payload.request.SignUpRequestDTO;
import com.zipline.auth.payload.response.JwtResponse;
import com.zipline.auth.security.jwt.JwtUtils;
import com.zipline.auth.security.services.UserDetailsImpl;
import com.zipline.auth.security.services.UserDetailsServiceImpl;
import com.zipline.dto.ConfigurationDTO;
import com.zipline.exception.EmailAlreadyExistsException;
import com.zipline.exception.ErrorResponse;
import com.zipline.exception.UsernameAlreadyExistsException;
import com.zipline.model.ConfirmationToken;
import com.zipline.model.Role;
import com.zipline.model.RoleType;
import com.zipline.model.User;
import com.zipline.repository.RoleRepository;
import com.zipline.repository.UserRepository;
import com.zipline.service.EmailSenderService;
import com.zipline.service.UtilService;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The type Auth controller.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Tag(name = "authentication-controller", description = "Authentication API")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final UtilService utilService;
    private final EmailSenderService emailSenderService;
    private final ConfigurationDTO configurationDTO;

    @Value("${zipline.app.api.url}")
    private String apiUrl;

    @Value("${spring.mail.username}")
    private String email;

    /**
     * Instantiates a new Auth controller.
     *
     * @param authenticationManager the authentication manager
     * @param userRepository        the user repository
     * @param roleRepository        the role repository
     * @param encoder               the encoder
     * @param jwtUtils              the jwt utils
     * @param userDetailsService    the user details service
     * @param utilService           the util service
     * @param emailSenderService    the email sender service
     * @param configurationDTO      the configuration dto
     */
    public AuthController(final AuthenticationManager authenticationManager,
                          final UserRepository userRepository,
                          final RoleRepository roleRepository,
                          final PasswordEncoder encoder,
                          final JwtUtils jwtUtils,
                          final UserDetailsServiceImpl userDetailsService,
                          final UtilService utilService,
                          final EmailSenderService emailSenderService,
                          final ConfigurationDTO configurationDTO) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.utilService = utilService;
        this.emailSenderService = emailSenderService;
        this.configurationDTO = configurationDTO;
    }

    /**
     * Authenticate user response entity.
     *
     * @param loginRequestDTO the login request
     * @return the response entity
     */
    @Operation(summary = "Authenticate user", description = "Authenticate user with login request", tags = {"authentication-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = JwtResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Bad request",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            final @ApiParam(value = "Login and password to authenticate") @Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        logger.debug("REST request to authenticate user");
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = jwtUtils.generateJwtToken(authentication);
        final UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        final JwtResponse jwtResponse = new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
        return new ResponseEntity<>(utilService.getResponseBody(jwtResponse), HttpStatus.OK);
    }

    /**
     * Authenticate user response entity.
     *
     * @return the response entity
     */
    @Operation(summary = "Get configuration", description = "Get configuration properties", tags = {"authentication-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ConfigurationDTO.class))))})
    @GetMapping("/config")
    public ResponseEntity<?> getConfiguration() {
        logger.debug("REST request to get configuration properties");
        return new ResponseEntity<>(utilService.getResponseBody(configurationDTO), HttpStatus.OK);
    }

    /**
     * Register user response entity.
     *
     * @param signUpRequestDTO the sign up request
     * @return the response entity
     */
    @Operation(summary = "Register user", description = "Register a new user", tags = {"authentication-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful registration",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "406", description = "Username is already taken",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "406", description = "Email is already taken",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            final @ApiParam(value = "Sign up request to register") @Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        logger.debug("REST request to register a new user");
        return registerNewUser(signUpRequestDTO, false);
    }

    /**
     * Register a new user response entity.
     *
     * @param signUpRequestDTO the sign up request
     * @return the response entity
     */
    @Operation(summary = "Register user by admin", description = "Register a new user, admin or moderator", tags = {"authentication-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "406", description = "Username is already taken",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
            @ApiResponse(responseCode = "406", description = "Email is already taken",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerUserByAdmin(
            final @ApiParam(value = "Sign up request to register") @Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        logger.debug("REST request to register a new user by admin");
        return registerNewUser(signUpRequestDTO, true);
    }

    private ResponseEntity<?> registerNewUser(final SignUpRequestDTO signUpRequestDTO, final Boolean isPrivileged) {
        if (userRepository.existsByUsername(signUpRequestDTO.getUsername())) {
            throw new UsernameAlreadyExistsException(signUpRequestDTO.getUsername());
        }
        if (userRepository.existsByEmail(signUpRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(signUpRequestDTO.getEmail());
        }
        final User user = new User(signUpRequestDTO.getUsername(),
                signUpRequestDTO.getEmail(),
                encoder.encode(signUpRequestDTO.getPassword()),
                false,
                false,
                false,
                false);
        final Set<RoleType> strRoles = signUpRequestDTO.getRole();
        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(RoleType.ROLE_USER));
        strRoles.forEach(role -> {
            switch (role) {
                case ROLE_ADMIN:
                    if (isPrivileged) {
                        roles.add(roleRepository.findByName(RoleType.ROLE_ADMIN));
                    }
                    break;
                case ROLE_MODERATOR:
                    if (isPrivileged) {
                        roles.add(roleRepository.findByName(RoleType.ROLE_MODERATOR));
                    }
                    break;
            }
        });
        user.setRoles(roles);
        userRepository.save(user);
        final ConfirmationToken token = new ConfirmationToken(UUID.randomUUID(), user);
        userDetailsService.saveConfirmationToken(token);
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete registration!");
        mailMessage.setFrom(email);
        mailMessage.setText("To confirm your account, please click here: " +
                apiUrl + "/api/auth/confirm?token=" + token.getToken());
        emailSenderService.sendEmail(mailMessage);
        return new ResponseEntity<>(utilService.getResponseBody("User registered successfully!"), HttpStatus.OK);
    }

    /**
     * Confirm user model and view.
     *
     * @param token the token
     * @return the model and view
     */
    @Operation(summary = "Confirm registration", description = "Confirm registration of the user", tags = {"authentication-controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful confirmation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "404", description = "Token not found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))})
    @RequestMapping(value = "/confirm", method = {RequestMethod.GET})
    public ModelAndView confirmUser(
            final @ApiParam(value = "Confirm account with confirmation token") @Valid @RequestParam("token") UUID token) {
        logger.debug("REST request to confirm registration of a new user");
        final User user = userDetailsService.loadUserByToken(token);
        user.setIsEnabled(true);
        user.setIsAccountNonLocked(true);
        user.setIsAccountNonExpired(true);
        user.setIsCredentialsNonExpired(true);
        userRepository.save(user);
        userDetailsService.deleteByToken(token);
        return new ModelAndView("redirect:" + "/login?=conf");
    }
}
