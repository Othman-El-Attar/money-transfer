package com.aman.intern.moneyTransfer.services;

import com.aman.intern.moneyTransfer.Mappers.FavoriteRecipientMapper;
import com.aman.intern.moneyTransfer.models.DTO.FavoriteRecipientRequestDTO;
import com.aman.intern.moneyTransfer.models.DTO.FavoriteRecipientResponseDTO;
import com.aman.intern.moneyTransfer.models.entities.FavoriteRecipient;
import com.aman.intern.moneyTransfer.models.entities.User;
import com.aman.intern.moneyTransfer.reposatories.FavoriteRecipientRepository;
import com.aman.intern.moneyTransfer.reposatories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteRecipientService {

    private final UserRepository USERREPOSITORY;
    private final FavoriteRecipientRepository FAVORITERECIPIENTREPOSITORY;
    private final FavoriteRecipientMapper MAPPER;

    public FavoriteRecipientService(FavoriteRecipientRepository favoriteRecipientRepository
            , UserRepository userRepository, FavoriteRecipientMapper favoriteRecipientMapper) {
        this.FAVORITERECIPIENTREPOSITORY = favoriteRecipientRepository;
        this.USERREPOSITORY = userRepository;
        this.MAPPER = favoriteRecipientMapper;
    }

    public List<FavoriteRecipientResponseDTO> getFavoriteRecipients(
             UserDetails userDetails) {
        Optional<User> user = USERREPOSITORY.findByEmail(userDetails.getUsername());
        if (user.isEmpty()) throw new IllegalArgumentException("user not found");
        List<FavoriteRecipient> favoriteRecipient =
                FAVORITERECIPIENTREPOSITORY.findByUserId(user.get().getId());


        return favoriteRecipient.stream()
                .map(MAPPER::FavoriteRecipientToDTO)
                .toList();
    }

    public FavoriteRecipientResponseDTO createFavoriteRecipient(
            UserDetails userDetails
            , FavoriteRecipientRequestDTO favoriteRecipientRequestDTO){
        Optional<User> user = USERREPOSITORY.findByEmail(userDetails.getUsername());
        if(user.isEmpty()) throw new IllegalArgumentException("user not found");
        FavoriteRecipient favoriteRecipient = new FavoriteRecipient();
        favoriteRecipient.setUser(user.get());
        favoriteRecipient.setRecipientAccountId(favoriteRecipientRequestDTO.getRecipientAccountId());
        favoriteRecipient.setNickName(favoriteRecipientRequestDTO.getNickName());

        FAVORITERECIPIENTREPOSITORY.save(favoriteRecipient);

        return MAPPER.FavoriteRecipientToDTO(favoriteRecipient );

    }
    public FavoriteRecipientResponseDTO DeleteFavoriteRecipient(
             UserDetails userDetails
            ,Long id){

        User user = USERREPOSITORY.findByEmail(userDetails.getUsername())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        FavoriteRecipient favoriteRecipient =
                FAVORITERECIPIENTREPOSITORY.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Favorite recipient not found"
                                ));

        if (favoriteRecipient.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException(
                    "You cannot delete this favorite recipient"
            );
        }


        FavoriteRecipientResponseDTO favoriteRecipientDTO = MAPPER.FavoriteRecipientToDTO(favoriteRecipient);
        FAVORITERECIPIENTREPOSITORY.delete(favoriteRecipient);

        return favoriteRecipientDTO;

    }

}
