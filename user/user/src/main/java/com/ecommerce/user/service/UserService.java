package com.ecommerce.user.service;


import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
//    private List<User> userList=new ArrayList<>();
    private final UserRepository userRepository;
    private final KeyCloakAdminService keyCloakAdminService;


    public List<UserResponse> fetchAllUsers()
    {
        return userRepository.findAll().stream().map(this::mapToUserResponse).collect(Collectors.toList());
//        List<User> users= userRepository.findAll();
//        List<UserResponse> userResponses=new ArrayList<>();
//        for(User user:users)
//        {
//            userResponses.add(mapToUserResponse(user));
//        }
//        return userResponses;
    }

    private UserResponse mapToUserResponse(User user)
    {
        UserResponse response=new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setEmail(user.getEmail());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        response.setKeycloakId(user.getKeyCloakId());
       if(user.getAddress()!=null)
       {
           AddressDTO addressDTO=new AddressDTO();
           addressDTO.setState(user.getAddress().getState());
           addressDTO.setCountry(user.getAddress().getCountry());
           addressDTO.setCity(user.getAddress().getCity());
           addressDTO.setStreet(user.getAddress().getStreet());
           addressDTO.setZipcode(user.getAddress().getZipcode());
           response.setAddress(addressDTO);
       }
       return response;

    }


    public Optional<UserResponse> fetchUser(String id)
    {
        return userRepository.findById(id).map(this::mapToUserResponse);
    }

    public void addUser(UserRequest userRequest)
    {
        String token= keyCloakAdminService.getAdminAccessToken();
        String keyCloakUserId=keyCloakAdminService.creaeUser(token,userRequest);
        User user=new User();
        updateUserFromRequest(user,userRequest);
        user.setKeyCloakId(keyCloakUserId);
        keyCloakAdminService.assignRealmRoleToUser(userRequest.getUsername(),"USER",keyCloakUserId);
        userRepository.save(user);
    }

    private void updateUserFromRequest(User user,UserRequest userRequest)
    {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhone(userRequest.getPhone());
        user.setEmail(userRequest.getEmail());
        if(userRequest.getAddress()!=null)
        {
            Address address=new Address();
            address.setState(userRequest.getAddress().getState());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }
    }

    public boolean updateUser(String id,UserRequest updatedUserRequest)
    {
        return userRepository.findById(id).map(existingUser-> {
                    updateUserFromRequest(existingUser,updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);

//       for(User user:userList)
//       {
//           if(user.getId().equals(id))
//           {
//               user.setFirstName(updatedUser.getFirstName());
//               user.setLastName(updatedUser.getLastName());
//               return true;
//           }
//
//       }
//       return false;
    }


}
