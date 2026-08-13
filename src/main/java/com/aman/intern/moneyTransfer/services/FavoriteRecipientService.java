package com.aman.intern.moneyTransfer.services;

import com.aman.intern.moneyTransfer.Mappers.FavoriteRecipientMapper;
import com.aman.intern.moneyTransfer.models.DTO.FavoriteRecipientDTO;
import com.aman.intern.moneyTransfer.models.entities.FavoriteRecipient;
import com.aman.intern.moneyTransfer.models.entities.User;
import com.aman.intern.moneyTransfer.reposatories.FavoriteRecipientRepository;
import com.aman.intern.moneyTransfer.reposatories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    public List<FavoriteRecipientDTO> getFavoriteRecipients(
             UserDetails userDetails) {
        Optional<User> user = USERREPOSITORY.findByEmail(userDetails.getUsername());
        if (user.isEmpty()) throw new IllegalArgumentException("user not found");
        List<FavoriteRecipient> favoriteRecipient =
                FAVORITERECIPIENTREPOSITORY.findByUserId(user.get().getId());


        return favoriteRecipient.stream()
                .map(MAPPER::FavoriteRecipientToDTO)
                .toList();
    }

    public FavoriteRecipientDTO createFavoriteRecipient(
             UserDetails userDetails
            , FavoriteRecipientDTO favoriteRecipientDTO){
        Optional<User> user = USERREPOSITORY.findByEmail(userDetails.getUsername());
        if(user.isEmpty()) throw new IllegalArgumentException("user not found");
        FavoriteRecipient favoriteRecipient = new FavoriteRecipient();
        favoriteRecipient.setUserId(user.get().getId());
        favoriteRecipient.setRecipientAccountId(favoriteRecipientDTO.getRecipientAccountId());
        favoriteRecipient.setNickName(favoriteRecipientDTO.getNickName());

        FAVORITERECIPIENTREPOSITORY.save(favoriteRecipient);

        return MAPPER.FavoriteRecipientToDTO(favoriteRecipient );

    }
    public FavoriteRecipientDTO DeleteFavoriteRecipient(
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

        if (favoriteRecipient.getUserId() != user.getId()) {
            throw new IllegalArgumentException(
                    "You cannot delete this favorite recipient"
            );
        }


        FavoriteRecipientDTO favoriteRecipientDTO = MAPPER.FavoriteRecipientToDTO(favoriteRecipient);
        FAVORITERECIPIENTREPOSITORY.delete(favoriteRecipient);

        return favoriteRecipientDTO;

    }

}
