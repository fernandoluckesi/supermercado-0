package br.com.meli.supermercado.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.meli.supermercado.dtos.ApiSucess;
import br.com.meli.supermercado.dtos.UserDTO;
import br.com.meli.supermercado.dtos.UserResponse;
import br.com.meli.supermercado.dtosSwagger.ResponseErrorUnder18YearsOldDTO;
import br.com.meli.supermercado.dtosSwagger.ResponseErrorUserAlreadyExistsDTO;
import br.com.meli.supermercado.dtosSwagger.ResponseErrorUserNotFoundDTO;
import br.com.meli.supermercado.dtosSwagger.ResponseErrorValidationDTO;
import br.com.meli.supermercado.dtosSwagger.UserDeleteResponseDTO;
import br.com.meli.supermercado.dtosSwagger.UserGetByIdResponseDTO;
import br.com.meli.supermercado.dtosSwagger.UserListGetAllResponseDTO;
import br.com.meli.supermercado.entities.UserEntity;
import br.com.meli.supermercado.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuário")
public class UserController {

        @Autowired
        private UserService userService;

        @PostMapping
        @Operation(summary = "Criação de usuário", description = "Este endpoint tem a funcionalidade de criar um usuário através do método POST\n. É válidado se já existe algum usuário cadastrado com o e-mail ou cpf informado no boyd de request. Caso já existe um usuário cadastrado, um response é retornado com o body de acordo com o já existente no banco de dados")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class))
                        }),
                        @ApiResponse(responseCode = "400", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorValidationDTO.class)),
                        }),
                        @ApiResponse(responseCode = "400", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorUnder18YearsOldDTO.class)),
                        }),
                        @ApiResponse(responseCode = "409", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorUserAlreadyExistsDTO.class)) })
        })
        public ResponseEntity<Object> create(@Valid @RequestBody UserDTO userDTO) {

                var userCreated = this.userService.create(userDTO);
                return ResponseEntity.created(java.net.URI.create("/user/" + userCreated.getId())).body(userCreated);

        }

        @Operation(summary = "Edição de usuário", description = "Este endpoint tem a funcionalidade de editar um usuário através do id informado na url \"/user/{id}\" com o método PUT")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserEntity.class))
                        }),
                        @ApiResponse(responseCode = "400", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorUnder18YearsOldDTO.class)),
                        }),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorUserNotFoundDTO.class)),
                        }),
                        @ApiResponse(responseCode = "409", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorUserAlreadyExistsDTO.class)) })
        })
        @PutMapping("/{id}")
        public ResponseEntity<Object> update(@PathVariable UUID id, @Valid @RequestBody UserDTO userDTO) {

                var updatedUser = this.userService.update(id, userDTO);
                return ResponseEntity.ok().body(updatedUser);
        }

        @Operation(summary = "Deletar usuário", description = "Este endpoint tem a funcionalidade de editar um usuário através do id informado na url \"/user/{id}\" com o método DELETE")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserDeleteResponseDTO.class))
                        }),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorUserNotFoundDTO.class)),
                        }),
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiSucess> delete(@PathVariable UUID id) {

                this.userService.delete(id);
                ApiSucess sucessResponse = new ApiSucess("Usuário deletado com sucesso", HttpStatus.OK.value());

                return ResponseEntity.ok().body(sucessResponse);
        }

        @GetMapping
        @Operation(summary = "Retorna uma lista de usuários", description = "Este endpoint retorna uma lista de todos os usuários senão houver o parâmetro name na url \"/user?name={name}\" através do método GET. Se houver um parâmetro name ele vai retorna uma lista de todos os usuários em que name seja parcialmente o nome do usuário ou exatamente igual")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserListGetAllResponseDTO.class))
                        }),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorUserNotFoundDTO.class)),
                        }),

        })
        public ResponseEntity<UserResponse> getAll(@RequestParam(required = false) String name) {

                var users = this.userService.getAll(name);

                return ResponseEntity.ok().body(users);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Retorna um único usuário", description = "Este endpoint tem a funcionalidade de retorna um usuário através do id informado na url \"/user/{id}\" com o método GET")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = UserGetByIdResponseDTO.class))
                        }),
                        @ApiResponse(responseCode = "404", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorUserNotFoundDTO.class)),
                        }),

        })
        public ResponseEntity<UserResponse> getById(@PathVariable UUID id) {

                var user = this.userService.getById(id);

                return ResponseEntity.ok().body(user);
        }
}