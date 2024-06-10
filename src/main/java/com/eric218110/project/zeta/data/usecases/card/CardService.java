package com.eric218110.project.zeta.data.usecases.card;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.eric218110.project.zeta.data.entities.card.CardEntity;
import com.eric218110.project.zeta.domain.http.card.AddCardRequest;
import com.eric218110.project.zeta.domain.http.card.ShowCardResponse;
import com.eric218110.project.zeta.domain.usecases.card.AddOneCard;
import com.eric218110.project.zeta.domain.usecases.card.LoadAllCards;
import com.eric218110.project.zeta.infra.repositories.database.card.CardRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CardService implements LoadAllCards, AddOneCard {
  private final CardRepository cardRepository;

  @Override
  public List<ShowCardResponse> listAll() {
    List<CardEntity> cardEntities = this.cardRepository.findAll();

    return cardEntities.stream().map(this::entityToDto).collect(Collectors.toList());
  }

  @Override
  public ShowCardResponse addCard(AddCardRequest addCardDto) {
    try {
      CardEntity cardEntity = this.addCardDtoToCardEntity(addCardDto);

      CardEntity cardSave = this.cardRepository.save(cardEntity);

      return this.entityToDto(cardSave);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
          "Invalid request, fix values and try again");
    }
  }

  private CardEntity addCardDtoToCardEntity(AddCardRequest addCardDto) {
    return CardEntity.builder().name(addCardDto.getName()).color(addCardDto.getColor())
        .currentLimit(addCardDto.getCurrentLimit()).flag(addCardDto.getFlag())
        .dueDate(addCardDto.getDueDate()).closeDate(addCardDto.getCloseDate())
        .type(addCardDto.getType()).build();
  }

  private ShowCardResponse entityToDto(CardEntity cardEntity) {
    return ShowCardResponse.builder().uuid(cardEntity.getUuid()).name(cardEntity.getName())
        .color(cardEntity.getColor()).currentLimit(cardEntity.getCurrentLimit())
        .flag(cardEntity.getFlag()).dueDate(cardEntity.getDueDate())
        .closeDate(cardEntity.getCloseDate()).type(cardEntity.getType()).build();
  }
}
