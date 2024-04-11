package br.com.meli.supermercado.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.meli.supermercado.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
  private LocalDateTime timestamp;

  @JsonInclude(Include.NON_NULL)
  private List<UserEntity> users;

  @JsonInclude(Include.NON_NULL)
  private Integer totalUsersResults;

  @JsonInclude(Include.NON_NULL)
  private UserEntity user;

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public UserResponse(
      @JsonProperty("timestamp") LocalDateTime timestamp,
      @JsonProperty("users") List<UserEntity> users,
      @JsonProperty("totalUsersResults") int totalUsersResults) {

    this.timestamp = timestamp;
    this.users = users;
    this.totalUsersResults = totalUsersResults;
  }

  @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
  public UserResponse(
      @JsonProperty("timestamp") LocalDateTime timestamp,
      @JsonProperty("user") UserEntity user, Integer totalUsersResults) {

    this.timestamp = timestamp;
    this.user = user;
    this.totalUsersResults = totalUsersResults;
  }
}